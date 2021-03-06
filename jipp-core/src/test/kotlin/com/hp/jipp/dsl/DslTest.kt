package com.hp.jipp.dsl

import com.hp.jipp.encoding.Cycler.cycle
import com.hp.jipp.encoding.IntOrIntRange
import com.hp.jipp.encoding.MediaSizes
import com.hp.jipp.encoding.Name
import com.hp.jipp.encoding.Tag
import com.hp.jipp.model.BindingType
import com.hp.jipp.model.Media
import com.hp.jipp.model.MediaCol
import com.hp.jipp.model.Operation
import com.hp.jipp.model.Status
import com.hp.jipp.model.Types
import java.net.URI
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test

class DslTest {
    private val uri = URI.create("ipp://192.168.0.101:631/ipp/print")
    private val mediaSize = MediaSizes.parse(Media.naLetter8p5x11in)!!

    @Test
    fun packetTest() {
        val packet = ippPacket(Operation.printJob) {
            operationAttributes {
                attr(Types.attributesCharset, "utf-8")
                attr(Types.attributesNaturalLanguage, "en")
                attr(Types.printerUri, uri)
                attr(Types.requestingUserName, "Test User")
            }
            jobAttributes {
                attr(Types.mediaCol, MediaCol(mediaSize = mediaSize))
                attr(Types.documentMessage, "A description of the document")
            }
            printerAttributes {
                attr(Types.bindingTypeSupported, BindingType.adhesive)
            }
            unsupportedAttributes {
                attr(Types.outputBin.noValue())
            }
        }
        val cycled = cycle(packet)

        assertEquals("utf-8", cycled.getValue(Tag.operationAttributes, Types.attributesCharset))
        assertEquals(mediaSize, cycled.getValue(Tag.jobAttributes, Types.mediaCol)!!.mediaSize)
        assertEquals(listOf(BindingType.adhesive), cycled.getValues(Tag.printerAttributes, Types.bindingTypeSupported))
        assertEquals(Types.outputBin.noValue(), cycled[Tag.unsupportedAttributes]?.get(Types.outputBin))
        assertEquals("A description of the document", cycled[Tag.jobAttributes]?.getValue(Types.documentMessage)?.value)
    }

    @Test
    fun intOrIntRange() {
        val packet = ippPacket(Status.successfulOk) {
            group(Tag.printerAttributes) {
                attr(Types.numberUpSupported, IntOrIntRange(5..6))
            }
        }
        Assert.assertNotEquals(listOf<IntOrIntRange>(),
            packet.getValues(Tag.printerAttributes, Types.numberUpSupported))
    }

    @Test
    fun `extend a group`() {
        val packet = ippPacket(Operation.printJob) {
            // We can get and set the status if we want to
            assertEquals(Operation.printJob.code, status.code)
            status = Status.clientErrorBadRequest
            operationAttributes {
                attr(Types.attributesCharset, "utf-8")
                attr(Types.attributesNaturalLanguage, "en")
            }
            extend(Tag.operationAttributes) {
                attr(Types.printerUri, uri)
                attr(Types.attributesCharset, "utf-16") // replace extant
                attr(Types.requestingUserName, "Test User")
            }
        }
        // Status and code go in the same place
        assertEquals(Status.clientErrorBadRequest.code, packet.code)

        // Only a single group is added
        assertEquals(1, packet.attributeGroups.size)
        assertEquals(Name("Test User"), packet.getValue(Tag.operationAttributes, Types.requestingUserName))

        // Earlier entries are replaced
        assertEquals("utf-16", packet.getValue(Tag.operationAttributes, Types.attributesCharset))

        // Order is preserved
        assertEquals(listOf(Types.attributesCharset, Types.attributesNaturalLanguage, Types.printerUri,
            Types.requestingUserName), packet[Tag.operationAttributes]!!.map { it.type })
    }

    @Test
    fun `modify a group`() {
        val packet = ippPacket(Operation.printJob) {
            // We can get and set the status if we want to
            assertEquals(Operation.printJob.code, status.code)
            status = Status.clientErrorBadRequest
            operationAttributes {
                // Try a variety of accessors
                this += Types.attributesCharset.of("utf-8")
                this[Types.attributesNaturalLanguage] = "en"
            }
            extend(Tag.operationAttributes) {
                add(Types.printerUri.of(uri))
                addAll(listOf(Types.attributesCharset.of("utf-16"))) // replace prior attributesCharset
                attr(listOf(Types.requestingUserName.of("Test User")))
            }
        }
        // Status and code go in the same place
        assertEquals(Status.clientErrorBadRequest.code, packet.code)

        // Only a single group is added
        assertEquals(1, packet.attributeGroups.size)
        assertEquals(Name("Test User"), packet.getValue(Tag.operationAttributes, Types.requestingUserName))

        // Earlier entries are replaced
        assertEquals("utf-16", packet.getValue(Tag.operationAttributes, Types.attributesCharset))

        // Order is preserved
        assertEquals(listOf(Types.attributesCharset, Types.attributesNaturalLanguage, Types.printerUri,
            Types.requestingUserName), packet[Tag.operationAttributes]!!.map { it.type })
    }

    @Test
    fun `extend a non-existent group`() {
        val packet = ippPacket(Operation.printJob) {
            // Extend a tag that's not there
            extend(Tag.printerAttributes) {
                attr(Types.jobAccountId, "25")
            }
        }
        assertEquals(Name("25"), packet.getValue(Tag.printerAttributes, Types.jobAccountId))
    }

    @Test
    fun `add a group`() {
        val operationGroup = group(Tag.operationAttributes) {
            attr(Types.attributesNaturalLanguage, "en")
        }
        val packet = ippPacket(Operation.printJob) {
            group(operationGroup)
        }
        assertEquals("en", packet.getValue(Tag.operationAttributes, Types.attributesNaturalLanguage))
    }

    @Test
    fun `mess with packet fields`() {
        val packet = ippPacket(Operation.printJob) {
            operationAttributes { }
            if (operation == Operation.printJob) {
                operation = Operation.createJob
            }
            versionNumber = 2000
            requestId++
        }
        assertEquals(Operation.createJob, packet.operation)
        assertEquals(2000, packet.versionNumber)
        assertEquals(ippPacket.DEFAULT_REQUEST_ID + 1, packet.requestId)
    }

    @Test
    fun `set packet code directly`() {
        val packet = ippPacket(Operation.printJob) {
            code = Operation.createJob.code
        }
        assertEquals(Operation.createJob, packet.operation)
    }
}
