package org.c3lang.intellij.psi.impl;

import com.intellij.lang.ASTNode;
import org.c3lang.intellij.psi.C3PsiNamedElement;
import org.jetbrains.annotations.NotNull;

public abstract class C3PsiNamedElementImpl extends C3PsiElementImpl implements C3PsiNamedElement
{
	public C3PsiNamedElementImpl(@NotNull ASTNode node) { super(node); }
}
