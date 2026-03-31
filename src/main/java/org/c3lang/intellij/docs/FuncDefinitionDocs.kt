package org.c3lang.intellij.docs

import com.intellij.lang.documentation.DocumentationMarkup
import com.intellij.openapi.project.Project
import com.intellij.psi.presentation.java.SymbolPresentationUtil
import org.c3lang.intellij.psi.C3FuncDef

internal fun generateFuncDefDoc(element: C3FuncDef): String
{
    val name = element.fqName.name
    val type = element.returnType?.fullName!!
    val docs = findDocumentationComment(element.parent)
    val file = SymbolPresentationUtil.getFilePathPresentation(element.containingFile)
    val argsString = "(\${element.parameterListString})"
    val args = element.parameterTypes.map { it.name }

    return renderFullDoc(file, name, type, argsString, args, docs, element.project)
}

private fun renderFullDoc(file: String, name: String, type: String, argsString: String, args: List<String>, docs: String, project: Project): String
{
    val builder = StringBuilder()
    appendDefinition("fn $type $name$argsString", project, builder)
    builder.append(DocumentationMarkup.SECTIONS_START)
    builder.appendLine(extractDescriptionTextFromDoc(docs))
    appendParamsSection(docs, builder, args)
    appendReturnSection(docs, builder)
    appendFileSection(file, builder)
    builder.append(DocumentationMarkup.SECTIONS_END)

    return builder.toString()
}