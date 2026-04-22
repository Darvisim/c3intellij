package org.c3lang.intellij.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.TokenSet;
import org.c3lang.intellij.psi.C3Path;
import org.c3lang.intellij.psi.C3Types;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public abstract class C3PathMixinImpl extends C3PsiNamedElementImpl implements C3Path
{
	public C3PathMixinImpl(@NotNull ASTNode node)
	{
		super(node);
	}

	@Override
	public @Nullable PsiElement getNameIdentifier()
	{
		return this;
	}

	@Override
	public @Nullable String getName()
	{
		return getText();
	}

	@Override
	public @NotNull PsiElement setName(@NotNull String name)
	{
		// TODO
		return this;
	}

	@Override
	public int getTextOffset()
	{
		return getNode().getStartOffset();
	}

	@Override
	public void shorten()
	{
		List<ASTNode> idents = new ArrayList<>(Arrays.asList(
			getNode().getChildren(TokenSet.create(C3Types.IDENT, C3Types.SCOPE))));

		if (idents.size() <= 2)
		{
			// bar::
			return;
		}

		idents.remove(idents.size() - 1); // ::
		idents.remove(idents.size() - 1); // IDENT

		// remove std::
		deleteChildRange(
			idents.get(0).getPsi(),
			idents.get(idents.size() - 1).getPsi()
		);
	}
}
