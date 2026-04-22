package org.c3lang.intellij.psi;

import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.Objects;

public interface C3ModuleNamePsiElement extends C3PsiElement
{
	@Nullable ModuleName getModuleName();

	default boolean isSameModule(@NotNull C3FullyQualifiedNamePsiElement other)
	{
		return getContainingFile().getName().equals(other.getContainingFile().getName())
			&& Objects.equals(getModuleName(), other.getModuleName());
	}

	default boolean isImported(@NotNull C3FullyQualifiedNamePsiElement other)
	{
		C3ModuleDefinition moduleDefinition = Objects.requireNonNull(
			PsiTreeUtil.getParentOfType(this, C3ModuleDefinition.class, true));
		return other.getModuleDefinition().equals(moduleDefinition)
			|| moduleDefinition.getImports().contains(other.getModuleName());
	}

	default @NotNull String textToInsert(@Nullable ModuleName imported, @NotNull C3FullyQualifiedNamePsiElement element)
	{
		if (isSameModule(element) || imported == null)
			return element.getFqName().getName();
		if (isImported(element) || imported.equals(element.getModuleName()))
			return element.getFqName().getSuffixName();
		return element.getFqName().getFullName();
	}
}
