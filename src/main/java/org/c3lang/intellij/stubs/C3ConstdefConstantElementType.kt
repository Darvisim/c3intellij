package org.c3lang.intellij.stubs

import com.intellij.psi.PsiElement
import com.intellij.psi.stubs.IndexSink
import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream
import org.c3lang.intellij.index.ReturnTypeIndex
import org.c3lang.intellij.psi.C3ConstdefConstant
import org.c3lang.intellij.psi.C3StubElementType
import org.c3lang.intellij.psi.C3StubElementTypeFactory
import org.c3lang.intellij.psi.impl.C3ConstdefConstantImpl
import java.io.IOException

class C3ConstdefConstantElementType : C3StubElementType<C3ConstdefConstantStub, C3ConstdefConstant>(C3StubElementTypeFactory.CONSTDEF_CONSTANT) {
    override fun createPsi(stub: C3ConstdefConstantStub): C3ConstdefConstant {
        return C3ConstdefConstantImpl(stub, this)
    }

    override fun createStub(psi: C3ConstdefConstant, stubElement: StubElement<out PsiElement?>): C3ConstdefConstantStub {
        return C3ConstdefConstantStub(
            parent = stubElement,
            elementType = this,
            psi = psi
        )
    }

    @Throws(IOException::class)
    override fun serialize(stub: C3ConstdefConstantStub, dataStream: StubOutputStream) {
        stub.serialize(dataStream)
    }

    override fun indexStub(stub: C3ConstdefConstantStub, sink: IndexSink) {
//        stub.entries.forEach {
//            sink.occurrence(ReturnTypeIndex.KEY, "${stub.fqName.fullName}.$it")
//        }
    }

    @Throws(IOException::class)
    override fun deserialize(dataStream: StubInputStream, stubElement: StubElement<*>): C3ConstdefConstantStub {
        return C3ConstdefConstantStub(stubElement, this, dataStream)
    }

    companion object {
        @JvmStatic
        val instance: C3ConstdefConstantElementType = C3ConstdefConstantElementType()
    }
}
