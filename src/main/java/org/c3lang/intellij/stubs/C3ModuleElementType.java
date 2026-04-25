package org.c3lang.intellij.stubs;

import com.intellij.psi.PsiElement;
import com.intellij.psi.stubs.IndexSink;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import org.c3lang.intellij.index.ModuleIndex;
import org.c3lang.intellij.psi.C3Module;
import org.c3lang.intellij.psi.C3StubElementType;
import org.c3lang.intellij.psi.C3StubElementTypeFactory;
import org.c3lang.intellij.psi.impl.C3ModuleImpl;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class C3ModuleElementType extends C3StubElementType<C3ModuleStub, C3Module>
{
	private static final C3ModuleElementType INSTANCE = new C3ModuleElementType();

	private C3ModuleElementType()
	{
		super(C3StubElementTypeFactory.MODULE);
	}

	public static C3ModuleElementType getInstance()
	{
		return INSTANCE;
	}

	@Override
	public @NotNull C3Module createPsi(@NotNull C3ModuleStub stub)
	{
		return new C3ModuleImpl(stub, this);
	}

	@Override
	public @NotNull C3ModuleStub createStub(@NotNull C3Module psi, @NotNull StubElement<? extends PsiElement> parentStub)
	{
		return new C3ModuleStub(parentStub, this, psi);
	}

	@Override
	public void serialize(@NotNull C3ModuleStub stub, @NotNull StubOutputStream dataStream) throws IOException
	{
		stub.serialize(dataStream);
	}

	@Override
	public @NotNull C3ModuleStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub) throws IOException
	{
		return new C3ModuleStub(parentStub, this, dataStream);
	}

	@Override
	public void indexStub(@NotNull C3ModuleStub stub, @NotNull IndexSink sink)
	{
		if (stub.getModule() != null)
		{
			sink.occurrence(ModuleIndex.KEY, stub.getModule().getValue());
		}
	}
}
