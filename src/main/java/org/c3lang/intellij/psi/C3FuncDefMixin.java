package org.c3lang.intellij.psi;

import com.intellij.psi.StubBasedPsiElement;
import org.c3lang.intellij.stubs.C3FuncDefStub;

public interface C3FuncDefMixin extends C3CallablePsiElement, StubBasedPsiElement<C3FuncDefStub>, C3PsiNamedElement, C3NameIdentProvider
{
}
