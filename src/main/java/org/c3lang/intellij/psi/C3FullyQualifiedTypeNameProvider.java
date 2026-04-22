package org.c3lang.intellij.psi;

import org.jetbrains.annotations.Nullable;

public interface C3FullyQualifiedTypeNameProvider extends C3PsiElement
{
	@Nullable FullyQualifiedName findTypeName();
}
