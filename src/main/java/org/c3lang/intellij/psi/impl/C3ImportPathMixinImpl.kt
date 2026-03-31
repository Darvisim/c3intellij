package org.c3lang.intellij.psi.impl

import com.intellij.lang.ASTNode
import com.intellij.psi.util.PsiTreeUtil
import org.c3lang.intellij.dropPostfix
import org.c3lang.intellij.psi.*
import org.c3lang.intellij.psi.reference.C3ReferenceBase

abstract class C3ImportPathMixinImpl(node: ASTNode) : C3PsiElementImpl(node), C3ImportPath {

    override fun endsWith(path: C3Path): Boolean {
        return text.endsWith(path.text.dropPostfix("::"))
    }

    override fun getTextOffset(): Int {
        return firstChild.textOffset
    }

//    override fun getTextRange(): TextRange? {
//        return TextRange.create(
//            firstChild.textOffset,
//            textOffset + name.length
//        )
//    }

    override val moduleName: ModuleName?
        get() = ModuleName(text)

//    override fun getReference(): PsiReference? {
//        return PsiMultiReference(
//            arrayOf(
//                C3PathIdentExprReference(this)
//            ),
//            this
//        )
//    }

}

