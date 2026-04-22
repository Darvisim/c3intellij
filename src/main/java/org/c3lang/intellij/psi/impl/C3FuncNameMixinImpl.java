package org.c3lang.intellij.psi.impl;

import com.intellij.lang.ASTNode;
import org.c3lang.intellij.psi.C3FuncName;
import org.jetbrains.annotations.NotNull;

public abstract class C3FuncNameMixinImpl extends C3PsiElementImpl implements C3FuncName
{
	public C3FuncNameMixinImpl(@NotNull ASTNode node)
	{
		super(node);
	}
}
