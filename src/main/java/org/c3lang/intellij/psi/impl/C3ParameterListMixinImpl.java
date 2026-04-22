package org.c3lang.intellij.psi.impl;

import com.intellij.lang.ASTNode;
import org.c3lang.intellij.psi.C3ParameterList;
import org.jetbrains.annotations.NotNull;

public abstract class C3ParameterListMixinImpl extends C3PsiElementImpl implements C3ParameterList
{
	public C3ParameterListMixinImpl(@NotNull ASTNode node)
	{
		super(node);
	}
}
