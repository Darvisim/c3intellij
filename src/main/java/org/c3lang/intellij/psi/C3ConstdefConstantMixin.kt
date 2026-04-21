package org.c3lang.intellij.psi

import com.intellij.psi.StubBasedPsiElement
import org.c3lang.intellij.stubs.C3ConstdefConstantStub

interface C3ConstdefConstantMixin :
    StubBasedPsiElement<C3ConstdefConstantStub>,
    C3PsiNamedElement,
    C3NameIdentProvider,
    C3TypeFullyQualifiedNamePsiElement {
    val constIdent: String
}
