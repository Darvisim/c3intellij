package org.c3lang.intellij.stubs;

import com.intellij.psi.PsiElement;
import com.intellij.psi.stubs.IndexSink;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import org.c3lang.intellij.index.NameIndex;
import org.c3lang.intellij.psi.C3MacroDefinition;
import org.c3lang.intellij.psi.C3StubElementType;
import org.c3lang.intellij.psi.C3StubElementTypeFactory;
import org.c3lang.intellij.psi.impl.C3MacroDefinitionImpl;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class C3MacroDefinitionElementType extends C3StubElementType<C3MacroDefinitionStub, C3MacroDefinition>
{
	private static final C3MacroDefinitionElementType INSTANCE = new C3MacroDefinitionElementType();

	private C3MacroDefinitionElementType()
	{
		super(C3StubElementTypeFactory.MACRO_DEFINITION);
	}

	public static C3MacroDefinitionElementType getInstance()
	{
		return INSTANCE;
	}

	@Override
	public @NotNull C3MacroDefinition createPsi(@NotNull C3MacroDefinitionStub stub)
	{
		return new C3MacroDefinitionImpl(stub, this);
	}

	@Override
	public @NotNull C3MacroDefinitionStub createStub(@NotNull C3MacroDefinition c3MacroDefinition, StubElement<? extends PsiElement> stubElement)
	{
		return new C3MacroDefinitionStub(stubElement, this, c3MacroDefinition);
	}

	@Override
	public void serialize(@NotNull C3MacroDefinitionStub stub, @NotNull StubOutputStream dataStream) throws IOException
	{
		stub.serialize(dataStream);
	}

	@Override public @NotNull C3MacroDefinitionStub deserialize(@NotNull StubInputStream stubInputStream, StubElement stubElement) throws IOException
	{
		return new C3MacroDefinitionStub(stubElement, this, stubInputStream);
	}

	@Override
	public void indexStub(@NotNull C3MacroDefinitionStub stub, @NotNull IndexSink sink)
	{
		sink.occurrence(NameIndex.KEY, stub.getFqName().getFullName());
	}
}
