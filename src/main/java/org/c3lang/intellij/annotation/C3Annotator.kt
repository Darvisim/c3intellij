package org.c3lang.intellij.annotation

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.psi.PsiComment
import com.intellij.psi.PsiElement
import com.intellij.psi.util.elementType
import org.c3lang.intellij.C3ParserDefinition
import org.c3lang.intellij.psi.C3CallExpr

class C3Annotator : Annotator
{

    override fun annotate(element: PsiElement, holder: AnnotationHolder)
    {
        when (element)
        {
//            is C3CallExpr -> annotateCallExpr(element, holder);
//            is C3CallExpr -> annotateMissingCallables(element, holder)
            is PsiComment ->
            {
                if (element.elementType == C3ParserDefinition.DOC_COMMENT) annotateDocComment(element, holder)
            }
        }

        org.c3lang.intellij.C3Annotator.INSTANCE.annotate(element, holder)
    }




}