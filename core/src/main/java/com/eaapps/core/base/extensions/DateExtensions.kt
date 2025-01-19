package com.eaapps.core.base.extensions

import java.text.SimpleDateFormat
import java.util.Locale


fun String.toDateFormatDMMMYYYY(): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    val outputFormat = SimpleDateFormat("d MMMM yyyy", Locale.getDefault())
    val date = inputFormat.parse(this)
    return date?.let { outputFormat.format(date) } ?: this
}