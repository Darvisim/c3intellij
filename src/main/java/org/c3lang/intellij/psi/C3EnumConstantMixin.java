package org.c3lang.intellij.psi;

import com.intellij.psi.StubBasedPsiElement;
import org.c3lang.intellij.stubs.C3EnumConstantStub;
import org.jetbrains.annotations.NotNull;

public interface C3EnumConstantMixin extends StubBasedPsiElement<C3EnumConstantStub>, C3PsiNamedElement, C3NameIdentProvider, C3TypeFullyQualifiedNamePsiElement
{
	@NotNull String getConstIdent();
}
