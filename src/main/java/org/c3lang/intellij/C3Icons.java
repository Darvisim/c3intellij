package org.c3lang.intellij;

import com.intellij.openapi.util.IconLoader;

import javax.swing.*;

/**
 * Undocumented Class
 *
 * @author Christoffer Lerno
 */
public class C3Icons
{
	private static Icon load(String icon_path)
	{
		return IconLoader.getIcon("/icons/" + icon_path, C3Icons.class.getClassLoader());
	}
	public static final Icon FILE = load("c3.svg");
	public static final Icon LIB_FILE = load("c3l.svg");
	public static final Icon LOGO = load("logo.svg");
	public static class Nodes
	{
		public static final Icon BITSTRUCT    = load("nodes/bitstruct.svg");
		public static final Icon CONSTANT     = load("nodes/constant.svg");
		public static final Icon ENUM         = load("nodes/enum.svg");
		public static final Icon CONSTDEF     = load("nodes/enum.svg");
		public static final Icon FAULT        = load("nodes/fault.svg");
		public static final Icon FUNCTION     = load("nodes/function.svg");
		public static final Icon INTERFACE    = load("nodes/interface.svg");
		public static final Icon MACRO        = load("nodes/macro.svg");
		public static final Icon MODULE       = load("nodes/module.svg");
		public static final Icon STRUCT       = load("nodes/struct.svg");
		public static final Icon STRUCT_FIELD = load("nodes/struct_field.svg");
		public static final Icon UNION        = load("nodes/union.svg");
		public static final Icon VARIABLE     = load("nodes/variable.svg");
	}

}
