package org.c3lang.intellij.actions;

import com.intellij.ide.actions.CreateFileFromTemplateDialog;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDirectory;
import org.c3lang.intellij.C3Icons;
import org.jetbrains.annotations.NotNull;

public class C3NewInterfaceFileAction extends C3NewFileActionBase
{
	public C3NewInterfaceFileAction()
	{
		super("C3 Interface File", "Creates a new C3 file", C3Icons.FILE);
	}

	@Override protected void buildDialog(@NotNull Project project, @NotNull PsiDirectory psiDirectory,
	                                     @NotNull CreateFileFromTemplateDialog.Builder builder)
	{
		builder.setTitle("New C3 Interface File").addKind("C3 interface", C3Icons.LIB_FILE, "C3 Interface");
	}
}