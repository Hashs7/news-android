package me.sebhernoux.dmii.extensions

import android.widget.EditText
import me.sebhernoux.dmii.R

fun EditText.toDouble() : Double? {
    return text.toString().toDoubleOrNull() ?: kotlin.run {
        error = context.resources.getString(R.string.number_error)
        null
    }
}