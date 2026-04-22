package org.c3lang.intellij.psi;

import org.c3lang.intellij.stubs.C3TypeEnum;
import org.jetbrains.annotations.NotNull;

public interface C3TypeFullyQualifiedNamePsiElement extends C3FullyQualifiedNamePsiElement
{
	@NotNull C3TypeEnum getTypeEnum();
}
