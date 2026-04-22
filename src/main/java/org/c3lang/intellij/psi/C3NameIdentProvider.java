package org.c3lang.intellij.psi;

import com.intellij.psi.impl.source.tree.LeafPsiElement;
import org.jetbrains.annotations.Nullable;

public interface C3NameIdentProvider extends C3PsiElement
{
	@Nullable String getNameIdent();
	@Nullable LeafPsiElement getNameIdentElement();
}
