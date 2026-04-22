package org.c3lang.intellij.psi.impl;

import com.intellij.lang.ASTNode;
import org.c3lang.intellij.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class C3ImportPathMixinImpl extends C3PsiElementImpl implements C3ImportPath
{
	public C3ImportPathMixinImpl(@NotNull ASTNode node)
	{
		super(node);
	}

	@Override
	public boolean endsWith(@NotNull C3Path path)
	{
		String pathText = path.getText();
		String stripped = pathText.endsWith("::") ? pathText.substring(0, pathText.length() - 2) : pathText;
		return getText().endsWith(stripped);
	}

	@Override
	public int getTextOffset()
	{
		return getFirstChild().getTextOffset();
	}

	@Override
	public @Nullable ModuleName getModuleName()
	{
		return new ModuleName(getText());
	}
}
