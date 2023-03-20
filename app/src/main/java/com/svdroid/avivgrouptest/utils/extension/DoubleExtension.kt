package com.svdroid.avivgrouptest.utils.extension

import java.math.BigDecimal
import java.math.RoundingMode

fun Double.convertPriceToString(): String {
    val converted = BigDecimal(this).setScale(2, RoundingMode.HALF_EVEN)

    return "\u20ac $converted"
}

fun Double.convertAreaToString(): String {
    val converted = BigDecimal(this).setScale(2, RoundingMode.HALF_EVEN)

    return "$converted \u33A1"
}