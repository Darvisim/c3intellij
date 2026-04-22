package org.c3lang.intellij.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.impl.source.tree.LeafPsiElement;
import org.c3lang.intellij.psi.C3Arg;
import org.c3lang.intellij.psi.C3NameIdentProvider;
import org.c3lang.intellij.psi.C3ParamPath;
import org.c3lang.intellij.psi.C3ParamPathElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class C3ArgMixinImpl extends C3PsiElementImpl implements C3Arg
{
	public C3ArgMixinImpl(@NotNull ASTNode node) { super(node); }

	@Override
	public @Nullable LeafPsiElement getNameIdentElement()
	{
		if (getExpr() instanceof C3NameIdentProvider p) return p.getNameIdentElement();
		return null;
	}

	@Override
	public @Nullable String getNameIdent()
	{
		LeafPsiElement ident = getNameIdentElement();
		return ident != null ? ident.getText() : null;
	}

	@Override
	public @NotNull List<String> findPathName(boolean includeSelf)
	{
		C3ParamPath paramPath = getParamPath();
		if (paramPath == null) return Collections.emptyList();
		List<C3ParamPathElement> elements = paramPath.getParamPathElementList();
		if (elements.isEmpty()) return Collections.emptyList();
		C3ParamPathElement last = elements.get(elements.size() - 1);
		List<String> result = new ArrayList<>(last.findPathName(true));
		Collections.reverse(result);
		return result;
	}
}
