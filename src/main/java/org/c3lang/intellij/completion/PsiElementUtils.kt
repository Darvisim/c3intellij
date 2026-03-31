package org.c3lang.intellij.completion

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.util.PsiTreeUtil
import org.c3lang.intellij.C3Language
import org.c3lang.intellij.psi.C3ImportDecl

object PsiElementUtils {

    @JvmStatic
    fun createImport(project: Project, importPath: String): C3ImportDecl {
        val instance = PsiFileFactory.getInstance(project)
        val file = instance.createFileFromText(C3Language.INSTANCE, "import $importPath;")

        return checkNotNull(PsiTreeUtil.findChildOfType(file, C3ImportDecl::class.java))
    }

    @JvmStatic
    fun createNewLine(project: Project): PsiElement {
        val instance = PsiFileFactory.getInstance(project)
        val file = instance.createFileFromText(C3Language.INSTANCE, "\n")

        return file.firstChild
    }

}