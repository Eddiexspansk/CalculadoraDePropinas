package com.developerCompany.calculadoraDePropinas.ui.util

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

object NumberUtils {
    private val decimalFormat: DecimalFormat by lazy {
        val symbols = DecimalFormatSymbols(Locale.ROOT)
        symbols.decimalSeparator = '.'
        DecimalFormat("#.##", symbols).apply {
            minimumFractionDigits = 2
            maximumFractionDigits = 2
        }
    }

    fun format(value: Double): String {
        return decimalFormat.format(value)
    }

    fun parse(value: String): Double {
        return value.replace(",", ".").toDoubleOrNull() ?: 0.0
    }
}
