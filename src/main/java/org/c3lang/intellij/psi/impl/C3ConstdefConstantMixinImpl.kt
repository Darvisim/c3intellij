package org.c3lang.intellij.psi.impl

import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.impl.source.tree.LeafPsiElement
import com.intellij.psi.stubs.IStubElementType
import com.intellij.psi.tree.IElementType
import com.intellij.psi.util.parentOfType
import org.c3lang.intellij.psi.*
import org.c3lang.intellij.stubs.C3ConstdefConstantStub
import org.c3lang.intellij.stubs.C3TypeEnum


abstract class C3ConstdefConstantMixinImpl : C3StubBasedPsiElementBase<C3ConstdefConstantStub>, C3ConstdefConstant {

    constructor(node: ASTNode) : super(node)

    constructor(
        stub: C3ConstdefConstantStub, nodeType: IStubElementType<*, *>
    ) : super(stub, nodeType)

    constructor(
        stub: C3ConstdefConstantStub, nodeType: IElementType?, node: ASTNode?
    ) : super(stub, nodeType, node)

    override fun getName(): String? {
        return nameIdent
    }

    override fun setName(name: String): PsiElement {
        nameIdentElement.replaceWithText(name)
        return this
    }

    override fun getNameIdentifier(): PsiElement? {
        return nameIdentElement
    }

    override fun getTextOffset(): Int {
        return nameIdentElement.textOffset
    }

    override val nameIdent: String
        get() = nameIdentElement.text

    override val nameIdentElement: LeafPsiElement
        get() = node.findChildByType(C3Types.CONST_IDENT)?.psi as LeafPsiElement

    override val fqName: FullyQualifiedName
        get() = greenStub?.fqName ?: parentFullyQualifiedName

    override val constIdent: String
        get() = greenStub?.constIdent ?: nameIdent

    override val moduleName: ModuleName?
        get() = greenStub?.module ?: moduleDefinition.moduleName

    override val typeEnum: C3TypeEnum
        get() = C3TypeEnum.CONSTDEF

    private val parentFullyQualifiedName : FullyQualifiedName
        get() {
            return checkNotNull(
                parentOfType<C3FullyQualifiedNamePsiElement>()
            ).fqName
        }
}