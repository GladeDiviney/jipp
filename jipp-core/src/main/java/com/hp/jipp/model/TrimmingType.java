// Copyright 2018 HP Development Company, L.P.
// SPDX-License-Identifier: MIT
//
// DO NOT MODIFY. Code is auto-generated by genTypes.py. Content taken from registry at
// https://www.iana.org/assignments/ipp-registrations/ipp-registrations.xml, updated on 2019-05-28
//
// (Note: Uses Java, not Kotlin, to define public static values in the most efficient manner.)

package com.hp.jipp.model;

/**
 * Values applicable for "trimming-type" keywords (or names).
 *
 * Also used by: "trimming-type-supported".
 *
 * @see <a href="https://www.pwg.org/pipermail/ipp/2016/018744.html">IPPWG20160325</a>
 * @see <a href="https://ftp.pwg.org/pub/pwg/candidates/cs-ippfinishings10-20010205-5100.1.pdf">PWG5100.1</a>
 */
public class TrimmingType {
    public static final String drawLine = "draw-line";
    public static final String full = "full";
    public static final String partial = "partial";
    public static final String perforate = "perforate";
    public static final String score = "score";
    public static final String tab = "tab";
}