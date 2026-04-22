package org.c3lang.intellij.psi;

import com.intellij.psi.StubBasedPsiElement;
import org.c3lang.intellij.stubs.C3StructMemberDeclarationStub;
import org.jetbrains.annotations.Nullable;

public interface C3StructMemberDeclarationMixin
	extends StubBasedPsiElement<C3StructMemberDeclarationStub>,
		C3PsiNamedElement,
		C3DeclaredInPathProvider,
		C3DeclaredInProvider,
		C3NameIdentProvider,
		C3FullyQualifiedTypeNameProvider
{
	@Nullable FullyQualifiedName getStructType();
	@Nullable String getStructPath();
	@Nullable FullyQualifiedName getStructPathType();

	@Override
	default @Nullable FullyQualifiedName findTypeName()
	{
		return getStructPathType();
	}
}
