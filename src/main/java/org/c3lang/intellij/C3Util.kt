package org.c3lang.intellij

import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ProjectRootManager
import com.intellij.openapi.util.io.FileUtil
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VfsUtilCore
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiManager
import com.intellij.psi.search.FilenameIndex
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStreamReader
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.c3lang.intellij.psi.C3File
import org.c3lang.intellij.psi.C3FuncDefinition
import org.c3lang.intellij.psi.C3MacroDefinition
import org.c3lang.intellij.psi.C3ModuleDefinition
import org.c3lang.intellij.psi.C3ModuleSection
import org.c3lang.intellij.psi.C3TopLevel

fun String.dropPrefix(prefix: String): String {
    return if (startsWith(prefix)) {
        this.drop(prefix.length)
    } else {
        this
    }
}

fun String.dropPostfix(postfix: String): String {
    return if (endsWith(postfix)) {
        this.dropLast(postfix.length)
    } else {
        this
    }
}

object C3Util {
    private val log: Log = LogFactory.getLog(C3Util::class.java)

    fun writeToFile(moduleName: String?, name: String?, path: File) {
        val inputStream = C3Util::class.java.classLoader.getResourceAsStream(name)

        if (inputStream != null) {
            try {
                val content = StringBuilder()
                BufferedReader(InputStreamReader(inputStream)).use { reader ->
                    var line: String?
                    while ((reader.readLine().also { line = it }) != null) {
                        content.append(line).append("\n")
                    }
                }
                val contentString = content.toString().format(moduleName)

                FileUtil.writeToFile(path, contentString)
            } catch (e: IOException) {
                log.error(e.message, e)
            } finally {
                try {
                    inputStream.close()
                } catch (e: IOException) {
                    log.error(e.message, e)
                }
            }
        } else {
            throw RuntimeException("Unable to load file '$name'")
        }
    }

    private fun walkAllFiles(project: Project, processor: (C3File) -> Unit) {
        val psiManager = PsiManager.getInstance(project)
        val settings = C3SettingsState.getInstance()
        val stdLibPath = settings.stdlibPath

        // search project
        FilenameIndex.getAllFilesByExt(
            project,
            C3Language.INSTANCE.associatedFileType?.defaultExtension!!
        )
            .filter { it.isValid }
            .mapNotNull { psiManager.findFile(it) as? C3File }
            .forEach(processor)

        // search stdlib
        if (stdLibPath != null) {
            File(stdLibPath).walk().filter { file -> file.isFile && file.extension == "c3" }.forEach { file ->
                LocalFileSystem.getInstance().findFileByIoFile(file)?.let { virtualFile ->
                    (psiManager.findFile(virtualFile) as? C3File)?.let(processor)
                }
            }
        }
    }

    fun findDeclarationInModule(
        project: Project,
        module: String,
        name: String
    ): Either<C3MacroDefinition, C3FuncDefinition>? {
        var result: Either<C3MacroDefinition, C3FuncDefinition>? = null
        walkAllFiles(project) { file ->
            if (result != null) return@walkAllFiles
            file.children.filterIsInstance<C3ModuleDefinition>()
                .filter { it.moduleName?.value == module }
                .forEach { moduleDef ->
                    moduleDef.children.filterIsInstance<C3TopLevel>().forEach { topLevel ->
                        val macros = topLevel.children.filterIsInstance<C3MacroDefinition>()
                            .filter { it.macroHeader.macroName.text == name }
                        if (macros.isNotEmpty()) {
                            result = Either.forLeft(macros[0])
                            return@walkAllFiles
                        }

                        val functions = topLevel.children.filterIsInstance<C3FuncDefinition>()
                            .filter { it.funcDef.funcHeader.funcName.text == name }
                        if (functions.isNotEmpty()) {
                            result = Either.forRight(functions[0])
                            return@walkAllFiles
                        }
                    }
                }
        }
        return result
    }

    fun findC3ModulesStartingWith(project: Project, prefix: String): Set<String> {
        val modules = mutableSetOf<String>()
        walkAllFiles(project) { file ->
            file.children.filterIsInstance<C3ModuleDefinition>().forEach { module ->
                module.moduleName?.value?.takeIf { it.startsWith(prefix) }?.let { modules.add(it) }
            }
        }
        return modules
    }

    fun findDeclarationsInModule(
        project: Project,
        module: String
    ): ArrayList<Either<C3FuncDefinition, C3MacroDefinition>> {
        val matches = arrayListOf<Either<C3FuncDefinition, C3MacroDefinition>>()
        walkAllFiles(project) { file ->
            collectDeclarationsInFile(file, module, matches)
        }
        return matches
    }

    private fun collectDeclarationsInFile(
            file: C3File,
            module: String,
            matches: ArrayList<Either<C3FuncDefinition, C3MacroDefinition>>
    ) {
        val modules =
                file.children.filterIsInstance<C3ModuleSection>().filter {
                    it.moduleName?.value?.endsWith(module) ?: false
                }

        modules.forEach { moduleDef ->
            val topLevels = moduleDef.children.filterIsInstance<C3TopLevel>()

            topLevels.forEach { topLevel ->
                topLevel.children.filterIsInstance<C3FuncDefinition>().forEach {
                    matches.add(Either.forLeft(it))
                }
                topLevel.children.filterIsInstance<C3MacroDefinition>().forEach {
                    matches.add(Either.forRight(it))
                }
            }
        }
    }

    fun findBestMatch(target: String, candidates: List<String>): String? {
        if (candidates.isEmpty()) return null

        return candidates.minByOrNull { calculateLevenshteinDistance(target, it) }
    }

    private fun calculateLevenshteinDistance(s1: String, s2: String): Int {
        val m = s1.length
        val n = s2.length
        val dp = Array(m + 1) { IntArray(n + 1) }

        for (i in 0..m) {
            dp[i][0] = i
        }

        for (j in 0..n) {
            dp[0][j] = j
        }

        for (i in 1..m) {
            for (j in 1..n) {
                dp[i][j] =
                        when {
                            s1[i - 1] == s2[j - 1] -> dp[i - 1][j - 1]
                            else -> minOf(dp[i - 1][j] + 1, dp[i][j - 1] + 1, dp[i - 1][j - 1] + 1)
                        }
            }
        }

        return dp[m][n]
    }
}

sealed class Either<out L, out R> {
    data class Left<out L>(val value: L) : Either<L, Nothing>()
    data class Right<out R>(val value: R) : Either<Nothing, R>()

    companion object {
        fun <L, R> forLeft(value: L): Either<L, R> = Left(value)
        fun <L, R> forRight(value: R): Either<L, R> = Right(value)
    }

    fun isLeft(): Boolean = this is Left
    fun isRight(): Boolean = this is Right

    fun left(): L? = (this as? Left)?.value
    fun right(): R? = (this as? Right)?.value
}
