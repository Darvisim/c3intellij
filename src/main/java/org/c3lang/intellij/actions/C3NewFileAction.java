package org.c3lang.intellij.actions;

import com.intellij.ide.actions.CreateFileFromTemplateDialog;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDirectory;
import org.c3lang.intellij.C3Icons;
import org.jetbrains.annotations.NotNull;

public class C3NewFileAction extends C3NewFileActionBase
{
	public C3NewFileAction()
	{
		super("C3 File", "Creates a new C3 file", C3Icons.FILE);
	}

	@Override protected void buildDialog(@NotNull Project project, @NotNull PsiDirectory psiDirectory,
	                                     @NotNull CreateFileFromTemplateDialog.Builder builder)
	{
		builder.setTitle("New C3 File").addKind("C3 file", C3Icons.FILE, "C3 File");
	}
}