package org.c3lang.intellij.psi.impl;

import com.intellij.extapi.psi.StubBasedPsiElementBase;
import com.intellij.lang.ASTNode;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.tree.IElementType;
import org.c3lang.intellij.psi.C3PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class C3StubBasedPsiElementBase<StubT extends StubElement<?>> extends StubBasedPsiElementBase<StubT>
	implements C3PsiElement
{
	public C3StubBasedPsiElementBase(@NotNull StubT stub, @NotNull IStubElementType<?, ?> nodeType)
	{
		super(stub, nodeType);
	}

	public C3StubBasedPsiElementBase(@NotNull ASTNode node)
	{
		super(node);
	}

	public C3StubBasedPsiElementBase(@NotNull StubT stub, @Nullable IElementType nodeType, @Nullable ASTNode node)
	{
		super(stub, nodeType, node);
	}

	@Override
	public @Nullable StubT getStub()
	{
		return getGreenStub();
	}

	@Override
	public String toString()
	{
		return getClass().getSimpleName() + "(" + getNode().getElementType() + ")";
	}
}
