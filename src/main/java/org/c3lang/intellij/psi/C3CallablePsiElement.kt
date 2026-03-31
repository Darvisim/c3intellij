package org.c3lang.intellij.psi

interface C3CallablePsiElement : C3FullyQualifiedNamePsiElement {
    val sourceFileName: String
    val type: ShortType?
    val returnType: ShortType?
    val parameterTypes: List<ParamType>
    val parameterListString: String get() = parameterTypes.joinToString(", ") {
        listOfNotNull(
            it.type?.fullName,
            it.name
        ).joinToString(" ")
    }
}