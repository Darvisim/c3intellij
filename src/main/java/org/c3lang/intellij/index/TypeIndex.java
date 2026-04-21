package org.c3lang.intellij.index;

import com.intellij.psi.stubs.StubIndexKey;
import org.c3lang.intellij.psi.C3PsiElement;
import org.jetbrains.annotations.NotNull;

public class TypeIndex extends C3StringStubIndexExtension<C3PsiElement>
{
	public static final StubIndexKey<String, C3PsiElement> KEY = StubIndexKey.createIndexKey("c3.type");

	@Override
	public @NotNull StubIndexKey<String, C3PsiElement> getKey()
	{
		return KEY;
	}
}
