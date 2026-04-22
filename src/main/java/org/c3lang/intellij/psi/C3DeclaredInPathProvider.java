package org.c3lang.intellij.psi;

import org.jetbrains.annotations.Nullable;

public interface C3DeclaredInPathProvider extends C3PsiElement
{
	@Nullable String getDeclaredInPath();
}
