package org.c3lang.intellij.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiWhiteSpace;
import org.c3lang.intellij.C3ParserDefinition;
import org.c3lang.intellij.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class C3ParserUtils
{
	private C3ParserUtils() {}

	public static @Nullable C3Path getPath(@NotNull C3PathIdent psi)
	{
		ASTNode node = psi.getNode().findChildByType(C3Types.PATH);
		return node != null ? new C3PathImpl(node) : null;
	}

	public static @Nullable String getImportIntention(@NotNull C3Path psi)
	{
		String text = psi.getText();
		String stripped = text.endsWith("::") ? text.substring(0, text.length() - 2) : text;
		return stripped.isEmpty() ? null : stripped;
	}

	public static boolean isDeprecated(@NotNull C3CallExpr el)
	{
		String docComment = findDocumentationComment(el);
		return docComment.matches(
			"@deprecated(\\s+(\"((?:[^\"\\\\]|\\\\.)*)\"|`((?:[^`\\\\]|\\\\.)*)`))?");
	}

	private static @NotNull String findDocumentationComment(@NotNull PsiElement element)
	{
		PsiElement parent = element.getParent();
		PsiElement prev = parent.getParent() instanceof C3DefaultModuleSection
			? parent.getParent().getPrevSibling()
			: parent.getPrevSibling();

		while (prev instanceof PsiWhiteSpace)
		{
			prev = prev.getPrevSibling();
		}

		if (prev == null) return "";

		StringBuilder builder = new StringBuilder();
		while (prev.getNode().getElementType() == C3ParserDefinition.DOC_COMMENT)
		{
			builder.append(prev.getText()).append('\n');
			prev = prev.getPrevSibling();
		}

		return builder.toString().replace("<*", "").replace("*>", "");
	}
}
