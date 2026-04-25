package org.c3lang.intellij.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.impl.source.resolve.reference.impl.PsiMultiReference;
import com.intellij.psi.impl.source.tree.LeafPsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import org.c3lang.intellij.completion.CompletionExtensionsKt;
import org.c3lang.intellij.index.NameIndexService;
import org.c3lang.intellij.index.StructService;
import org.c3lang.intellij.psi.*;
import org.c3lang.intellij.psi.reference.C3ReferenceBase;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public abstract class C3PathIdentMixinImpl extends C3PsiNamedElementImpl implements C3PathIdent
{
	public C3PathIdentMixinImpl(@NotNull ASTNode node)
	{
		super(node);
	}

	@Override
	public @Nullable String getName()
	{
		return getNameIdent();
	}

	@Override
	public @Nullable PsiElement setName(@NotNull String name)
	{
		LeafPsiElement ident = getNameIdentElement();
		if (ident != null) ident.replaceWithText(name);
		return this;
	}

	@Override
	public @Nullable PsiElement getNameIdentifier()
	{
		return getNameIdentElement();
	}

	@Override
	public int getTextOffset()
	{
		LeafPsiElement ident = getNameIdentElement();
		return ident != null ? ident.getTextOffset() : super.getTextOffset();
	}

	@Override
	public @Nullable String getNameIdent()
	{
		LeafPsiElement ident = getNameIdentElement();
		return ident != null ? ident.getText() : null;
	}

	@Override
	public @Nullable LeafPsiElement getNameIdentElement()
	{
		PsiElement last = getLastChild();
		return last instanceof LeafPsiElement ? (LeafPsiElement) last : null;
	}

	@Override
	public @Nullable FullyQualifiedName findTypeName()
	{
		List<C3LocalDeclAfterType> decls = findLocalDeclAfterType();
		if (decls.size() != 1) return null;
		C3PsiElement single = (C3PsiElement) decls.get(0);
		if (!(single instanceof C3FullyQualifiedTypeNameProvider)) return null;
		return ((C3FullyQualifiedTypeNameProvider) single).findTypeName();
	}

	@Override
	public @NotNull List<C3LocalDeclAfterType> findLocalDeclAfterType()
	{
		C3CompoundStatement compoundStatement =
			PsiTreeUtil.getParentOfType(this, C3CompoundStatement.class);
		if (compoundStatement == null) return Collections.emptyList();

		Collection<C3LocalDeclAfterType> all =
			PsiTreeUtil.collectElementsOfType(compoundStatement, C3LocalDeclAfterType.class);
		List<C3LocalDeclAfterType> result = new ArrayList<>();
		for (C3LocalDeclAfterType decl : all)
		{
			if (decl.getTextOffset() < getTextOffset()
				&& decl.getNameIdent() != null
				&& decl.getNameIdent().equals(getNameIdent()))
			{
				result.add(decl);
			}
		}
		return result;
	}

	@Override
	public @NotNull PsiReference getReference()
	{
		return new PsiMultiReference(
			new PsiReference[]{
				new C3LocalDeclAfterTypeReference(this),
				new C3ParameterReference(this),
				new C3FuncNameReference(this),
				new C3StructMemberReference(this)
			},
			this
		);
	}

	private static class C3LocalDeclAfterTypeReference extends C3ReferenceBase<C3PathIdent>
	{
		C3LocalDeclAfterTypeReference(@NotNull C3PathIdent element)
		{
			super(element);
		}

		@Override
		public @NotNull Collection<C3PsiElement> multiResolve()
		{
			C3CompoundStatement compoundStatement =
				PsiTreeUtil.getParentOfType(myElement, C3CompoundStatement.class);
			if (compoundStatement == null) return Collections.emptyList();

			Collection<C3LocalDeclAfterType> all =
				PsiTreeUtil.collectElementsOfType(compoundStatement, C3LocalDeclAfterType.class);
			List<C3PsiElement> result = new ArrayList<>();
			for (C3LocalDeclAfterType decl : all)
			{
				if (decl.getTextOffset() < myElement.getTextOffset()
					&& decl.getNameIdent() != null
					&& decl.getNameIdent().equals(myElement.getNameIdent()))
				{
					result.add(decl);
				}
			}
			return result;
		}
	}

	private static class C3ParameterReference extends C3ReferenceBase<C3PathIdent>
	{
		C3ParameterReference(@NotNull C3PathIdent element)
		{
			super(element);
		}

		@Override
		public @NotNull Collection<C3PsiElement> multiResolve()
		{
			C3FuncDefinition funcDef =
				PsiTreeUtil.getParentOfType(myElement, C3FuncDefinition.class);
			if (funcDef == null) return Collections.emptyList();

			Collection<C3Parameter> params =
				PsiTreeUtil.collectElementsOfType(funcDef, C3Parameter.class);
			List<C3PsiElement> result = new ArrayList<>();
			for (C3Parameter param : params)
			{
				if (param.getTextOffset() < myElement.getTextOffset()
					&& param.getNameIdent() != null
					&& param.getNameIdent().equals(myElement.getNameIdent()))
				{
					result.add(param);
				}
			}
			return result;
		}
	}

	private static class C3FuncNameReference extends C3ReferenceBase<C3PathIdent>
	{
		C3FuncNameReference(@NotNull C3PathIdent element)
		{
			super(element);
		}

		@Override
		public @NotNull Collection<C3PsiElement> multiResolve()
		{
			C3ModuleDefinition moduleDefinition = myElement.getModuleDefinition();
			List<C3PsiElement> result = new ArrayList<>();
			for (C3FullyQualifiedNamePsiElement el :
				NameIndexService.INSTANCE.findByNameEndsWith(myElement.getText(), myElement.getProject()))
			{
				if (el instanceof C3CallablePsiElement
					&& moduleDefinition.containsImportOrSameModule(el))
				{
					result.add(el);
				}
			}
			return result;
		}

		@Override
		public @NotNull TextRange getRangeInElement()
		{
			C3Path path = myElement.getPath();
			return TextRange.create(path != null ? path.getTextLength() : 0, myElement.getTextLength());
		}
	}

	private static class C3StructMemberReference extends C3ReferenceBase<C3PathIdent>
	{
		C3StructMemberReference(@NotNull C3PathIdent element)
		{
			super(element);
		}

		@Override
		public @NotNull Collection<C3PsiElement> multiResolve()
		{
			FullyQualifiedName rootType = CompletionExtensionsKt.getRootType(myElement);
			if (rootType == null) return Collections.emptyList();

			C3Arg parentArg = PsiTreeUtil.getParentOfType(myElement, C3Arg.class);
			C3PathNameProvider pathNameProvider =
				PsiTreeUtil.getParentOfType(myElement, C3PathNameProvider.class);
			if (pathNameProvider == null) return Collections.emptyList();
			List<String> path = pathNameProvider.findPathName(false);

			// Walk up through all C3PathNameProvider ancestors of parentArg
			List<String> fieldNames = new ArrayList<>();
			C3PathNameProvider currentProvider = parentArg != null
				? PsiTreeUtil.getParentOfType(parentArg, C3PathNameProvider.class)
				: null;
			while (currentProvider != null)
			{
				fieldNames.addAll(currentProvider.findPathName(false));
				currentProvider = PsiTreeUtil.getParentOfType(
					(PsiElement) currentProvider, C3PathNameProvider.class);
			}
			Collections.reverse(fieldNames);

			List<String> paths = new ArrayList<>(fieldNames);
			paths.addAll(path);
			paths.add(myElement.getText());

			return new ArrayList<>(
				StructService.INSTANCE.getStructMemberDeclaration(rootType, paths, myElement.getProject()));
		}
	}
}
