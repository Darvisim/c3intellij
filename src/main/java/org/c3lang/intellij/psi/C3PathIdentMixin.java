package org.c3lang.intellij.psi;

import org.jetbrains.annotations.NotNull;
import java.util.List;

public interface C3PathIdentMixin extends C3PsiNamedElement, C3NameIdentProvider, C3FullyQualifiedTypeNameProvider
{
	@NotNull List<C3LocalDeclAfterType> findLocalDeclAfterType();
}
