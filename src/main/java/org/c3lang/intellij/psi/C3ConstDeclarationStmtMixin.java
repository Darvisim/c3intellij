package org.c3lang.intellij.psi;

import com.intellij.psi.StubBasedPsiElement;
import org.c3lang.intellij.stubs.C3ConstDeclarationStmtStub;

public interface C3ConstDeclarationStmtMixin extends C3FullyQualifiedNamePsiElement, StubBasedPsiElement<C3ConstDeclarationStmtStub>, C3NameIdentProvider, C3PsiNamedElement
{
}
