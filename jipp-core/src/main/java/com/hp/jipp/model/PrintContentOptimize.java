// Copyright 2018 HP Development Company, L.P.
// SPDX-License-Identifier: MIT
//
// DO NOT MODIFY. Code is auto-generated by genTypes.py. Content taken from registry at
// https://www.iana.org/assignments/ipp-registrations/ipp-registrations.xml, updated on 2019-05-28
//
// (Note: Uses Java, not Kotlin, to define public static values in the most efficient manner.)

package com.hp.jipp.model;

/**
 * Values applicable for "print-content-optimize" keywords.
 *
 * Also used by: "print-content-optimize-default", "print-content-optimize-supported".
 *
 * @see <a href="https://ftp.pwg.org/pub/pwg/candidates/cs-ippjobprinterext3v10-20120727-5100.13.pdf">PWG5100.13</a>
 * @see <a href="https://ftp.pwg.org/pub/pwg/candidates/cs-ippjobext10-20031031-5100.7.pdf">PWG5100.7</a>
 */
public class PrintContentOptimize {
    public static final String auto = "auto";
    public static final String graphic = "graphic";
    public static final String photo = "photo";
    public static final String text = "text";
    public static final String textAndGraphic = "text-and-graphic";
}