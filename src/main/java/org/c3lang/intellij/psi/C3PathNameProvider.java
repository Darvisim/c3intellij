package org.c3lang.intellij.psi;

import org.jetbrains.annotations.NotNull;
import java.util.List;

public interface C3PathNameProvider extends C3PsiElement
{
	@NotNull List<String> findPathName(boolean includeSelf);
}
