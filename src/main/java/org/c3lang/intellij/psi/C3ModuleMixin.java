package org.c3lang.intellij.psi;

import com.intellij.psi.StubBasedPsiElement;
import org.c3lang.intellij.stubs.C3ModuleStub;

public interface C3ModuleMixin extends C3ModuleNamePsiElement, StubBasedPsiElement<C3ModuleStub>
{
}
