package org.c3lang.intellij.psi.impl;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;

public interface C3ReferenceElementBase
{
	@Nullable PsiElement getReferenceNameElement();
}
