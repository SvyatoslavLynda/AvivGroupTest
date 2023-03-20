package com.svdroid.avivgrouptest.utils.extension

import org.junit.Assert.assertEquals
import org.junit.Test

class DoubleExtensionTest {

    @Test
    fun testConvertPriceToString() {
        val price = 12.3456
        assertEquals("\u20ac 12.35", price.convertPriceToString())
    }

    @Test
    fun testConvertPriceToStringWithZeroValue() {
        val price = 0.0
        assertEquals("\u20ac 0.00", price.convertPriceToString())
    }

    @Test
    fun testConvertPriceToStringWithOneDigitAfterCommaValue() {
        val price = 10.1
        assertEquals("\u20ac 10.10", price.convertPriceToString())
    }

    @Test
    fun testConvertAreaToString() {
        val area = 25.6789
        assertEquals("25.68 \u33A1", area.convertAreaToString())
    }

    @Test
    fun testConvertAreaToStringWithZeroValue() {
        val area = 0.0
        assertEquals("0.00 \u33A1", area.convertAreaToString())
    }

    @Test
    fun testConvertAreaToStringWithOneDigitAfterCommaValue() {
        val area = 10.1
        assertEquals("10.10 \u33A1", area.convertAreaToString())
    }
}
