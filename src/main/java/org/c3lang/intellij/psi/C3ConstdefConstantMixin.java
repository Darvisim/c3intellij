package org.c3lang.intellij.psi;

import com.intellij.psi.StubBasedPsiElement;
import org.c3lang.intellij.stubs.C3ConstdefConstantStub;
import org.jetbrains.annotations.NotNull;

public interface C3ConstdefConstantMixin extends StubBasedPsiElement<C3ConstdefConstantStub>, C3PsiNamedElement, C3NameIdentProvider, C3TypeFullyQualifiedNamePsiElement
{
	@NotNull String getConstIdent();
}
