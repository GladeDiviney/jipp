// Copyright 2018 HP Development Company, L.P.
// SPDX-License-Identifier: MIT
//
// DO NOT MODIFY. Code is auto-generated by genTypes.py. Content taken from registry at
// https://www.iana.org/assignments/ipp-registrations/ipp-registrations.xml, updated on 2019-05-28
@file:Suppress("MaxLineLength", "WildcardImport")

package com.hp.jipp.model

import com.hp.jipp.encoding.* // ktlint-disable no-wildcard-imports

/**
 * Data object corresponding to a "destination-uris" collection as defined in:
 * [PWG5100.15](https://ftp.pwg.org/pub/pwg/candidates/cs-ippfaxout10-20131115-5100.15.pdf),
 * [PWG5100.17](https://ftp.pwg.org/pub/pwg/candidates/cs-ippscan10-20140918-5100.17.pdf).
 */
@Suppress("RedundantCompanionReference", "unused")
data class DestinationUris
constructor(
    var destinationAttributes: List<UntypedCollection>? = null,
    var destinationUri: java.net.URI? = null,
    var postDialString: String? = null,
    var preDialString: String? = null,
    var t33Subaddress: Int? = null
) : AttributeCollection {

    /** Construct an empty [DestinationUris]. */
    constructor() : this(null, null, null, null, null)

    /** Produce an attribute list from members. */
    override val attributes: List<Attribute<*>> by lazy {
        listOfNotNull(
            destinationAttributes?.let { Types.destinationAttributes.of(it) },
            destinationUri?.let { Types.destinationUri.of(it) },
            postDialString?.let { Types.postDialString.of(it) },
            preDialString?.let { Types.preDialString.of(it) },
            t33Subaddress?.let { Types.t33Subaddress.of(it) }
        )
    }

    /** Type for attributes of this collection */
    class Type(override val name: String) : AttributeCollection.Type<DestinationUris>(DestinationUris)

    /** All member names as strings. */
    object Name {
        /** "destination-attributes" member name */
        const val destinationAttributes = "destination-attributes"
        /** "destination-uri" member name */
        const val destinationUri = "destination-uri"
        /** "post-dial-string" member name */
        const val postDialString = "post-dial-string"
        /** "pre-dial-string" member name */
        const val preDialString = "pre-dial-string"
        /** "t33-subaddress" member name */
        const val t33Subaddress = "t33-subaddress"
    }

    /** Types for each member attribute. */
    object Types {
        val destinationAttributes = UntypedCollection.Type(Name.destinationAttributes)
        val destinationUri = UriType(Name.destinationUri)
        val postDialString = TextType(Name.postDialString)
        val preDialString = TextType(Name.preDialString)
        val t33Subaddress = IntType(Name.t33Subaddress)
    }

    /** Defines types for each member of [DestinationUris] */
    companion object : AttributeCollection.Converter<DestinationUris> {
        override fun convert(attributes: List<Attribute<*>>): DestinationUris =
            DestinationUris(
                extractAll(attributes, Types.destinationAttributes),
                extractOne(attributes, Types.destinationUri),
                extractOne(attributes, Types.postDialString)?.value,
                extractOne(attributes, Types.preDialString)?.value,
                extractOne(attributes, Types.t33Subaddress)
            )
    }
}
