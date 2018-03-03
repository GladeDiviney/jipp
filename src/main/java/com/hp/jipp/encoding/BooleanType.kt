package com.hp.jipp.encoding

import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.IOException

/** A boolean attribute type */
class BooleanType(tag: Tag, override val name: String) : AttributeType<Boolean>(BooleanType.ENCODER, tag) {
    companion object {
        private val TYPE_NAME = "Boolean"

        @JvmField
        val ENCODER: SimpleEncoder<Boolean> = object : SimpleEncoder<Boolean>(TYPE_NAME) {
            @Throws(IOException::class)
            override fun writeValue(out: DataOutputStream, value: Boolean) {
                out.writeShort(1)
                out.writeByte(if (value) 0x01 else 0x00)
            }

            @Throws(IOException::class)
            override fun readValue(input: DataInputStream, valueTag: Tag): Boolean {
                input.takeLength(1)
                return input.readByte().toInt() != 0
            }

            override fun valid(valueTag: Tag): Boolean {
                return valueTag === Tag.booleanValue
            }
        }
    }
}
