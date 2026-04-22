package org.c3lang.intellij.psi;

import org.c3lang.intellij.C3TokenSets;

public interface C3BaseTypeMixin extends C3PsiNamedElement, C3NameIdentProvider
{
	default boolean isPrimitiveType()
	{
		return getNode().getChildren(C3TokenSets.KW_TYPES).length > 0
			|| getFirstChild().getNode().getChildren(C3TokenSets.KW_TYPES).length > 0;
	}
}
