package org.c3lang.intellij.annotation;

import com.intellij.openapi.editor.colors.TextAttributesKey;

import java.awt.Font;

public final class Highlights
{
	public static final TextAttributesKey DOC_COMMENT_TAG;

	static
	{
		DOC_COMMENT_TAG = TextAttributesKey.createTextAttributesKey("C3_DOC_COMMENT_TAG");
		DOC_COMMENT_TAG.getDefaultAttributes().setFontType(Font.BOLD | Font.ITALIC);
	}

	private Highlights()
	{
	}
}