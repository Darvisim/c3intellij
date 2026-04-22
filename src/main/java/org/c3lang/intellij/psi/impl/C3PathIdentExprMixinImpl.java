package org.c3lang.intellij.psi.impl;

import com.intellij.lang.ASTNode;
import org.c3lang.intellij.psi.C3PathIdentExpr;
import org.jetbrains.annotations.NotNull;

public abstract class C3PathIdentExprMixinImpl extends C3PsiElementImpl implements C3PathIdentExpr
{
	public C3PathIdentExprMixinImpl(@NotNull ASTNode node)
	{
		super(node);
	}
}
