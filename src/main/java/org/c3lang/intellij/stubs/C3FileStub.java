package org.c3lang.intellij.stubs;

import com.intellij.psi.stubs.PsiFileStubImpl;
import org.c3lang.intellij.psi.C3File;

public class C3FileStub extends PsiFileStubImpl<C3File>
{
	public C3FileStub(C3File file)
	{
		super(file);
	}
}
