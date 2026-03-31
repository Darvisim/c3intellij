package org.c3lang.intellij.psi

import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream

sealed interface StructDeclarationFields {
    val declaredIn: FullyQualifiedPath

    //    val fields: Map<String, ShortType>
    fun serialize(stream: StubOutputStream)

    data class Complex(
        override val declaredIn: FullyQualifiedPath,
//        override val fields: Map<String, ShortType>
    ) : StructDeclarationFields {
        override fun serialize(stream: StubOutputStream) {
            stream.writeUTFFast(declaredIn.typeName.fullName)
            stream.writeUTFFast(declaredIn.path)
//            stream.writeVarInt(fields.size)
//            fields.forEach { (name, value) ->
//                stream.writeUTFFast(name)
//                stream.writeUTFFast(value.fullName)
//            }
        }
    }

    data class Simple(
        override val declaredIn: FullyQualifiedPath,
    ) : StructDeclarationFields {
        //        override val fields: Map<String, ShortType> = emptyMap()
        override fun serialize(stream: StubOutputStream) {
            stream.writeUTFFast(declaredIn.typeName.fullName)
            stream.writeUTFFast(declaredIn.path)
//            stream.writeVarInt(0)
        }
    }

    companion object {

        fun deserialize(stream: StubInputStream): StructDeclarationFields {
            val typeName = FullyQualifiedName.parse(stream.readUTFFast())
            val path = stream.readUTFFast()
//            val fields = stream.readVarInt()

            val declaredIn = FullyQualifiedPath(typeName, path)

            return Complex(
                declaredIn = declaredIn,
            )
        }
    }
}