package org.c3lang.intellij.psi;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.AbstractElementManipulator;
import com.intellij.psi.PsiNamedElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NamedElementManipulator extends AbstractElementManipulator<PsiNamedElement>
{
	@Override
	public @Nullable PsiNamedElement handleContentChange(
		@NotNull PsiNamedElement element,
		@NotNull TextRange range,
		@Nullable String newContent)
	{
		if (newContent != null)
		{
			element.setName(newContent);
			return element;
		}
		return null;
	}
}
