package org.c3lang.intellij.stubs;

import com.intellij.psi.util.PsiTreeUtil;
import org.c3lang.intellij.psi.*;

public enum C3TypeEnum
{
	FALLBACK,
	BITSTRUCT,
	ENUM,
	CONSTDEF,
	FAULT,
	INTERFACE,
	STRUCT,
	UNION;

	public static C3TypeEnum find(C3TypeName psi)
	{
		C3StructDeclaration structDeclaration = PsiTreeUtil.getParentOfType(psi, C3StructDeclaration.class);
		if (structDeclaration != null)
		{
			return structDeclaration.getNode().findChildByType(C3Types.KW_UNION) != null ? UNION : STRUCT;
		}
		if (PsiTreeUtil.getParentOfType(psi, C3InterfaceDefinition.class) != null) return INTERFACE;
		if (PsiTreeUtil.getParentOfType(psi, C3EnumDeclaration.class) != null) return ENUM;
		if (PsiTreeUtil.getParentOfType(psi, C3ConstdefDeclaration.class) != null) return CONSTDEF;
		if (PsiTreeUtil.getParentOfType(psi, C3BitstructDeclaration.class) != null) return BITSTRUCT;
		return FALLBACK;
	}
}
