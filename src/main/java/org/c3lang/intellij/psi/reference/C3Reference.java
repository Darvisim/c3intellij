package org.c3lang.intellij.psi.reference;

import com.intellij.psi.PsiPolyVariantReference;
import org.c3lang.intellij.psi.C3PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public interface C3Reference extends PsiPolyVariantReference
{
	@Override
	@Nullable C3PsiElement resolve();

	@Override
	@NotNull C3PsiElement getElement();

	Collection<C3PsiElement> multiResolve();
}
