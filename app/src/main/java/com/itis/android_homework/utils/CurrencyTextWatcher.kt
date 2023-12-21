package com.itis.android_homework.utils

import android.text.Editable
import android.text.TextWatcher

class CurrencyTextWatcher : TextWatcher {
    var stringBuilder : StringBuilder = java.lang.StringBuilder()
    var ignore : Boolean = false

    private val numPlace : Char = 'X'

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

    }

    override fun afterTextChanged(editable: Editable) {
        if (!ignore) {
            removeFormat(editable.toString())
            applyFormat(stringBuilder.toString())
            ignore = true
            editable.replace(0, editable.length, stringBuilder.toString())
            ignore = false
        }
    }

    private fun removeFormat(text: String) {
        stringBuilder.setLength(0)
        for (i in 0 until text.length) {
            val c : Char = text[i]
            if (isNumberChar(c)) {
                stringBuilder.append(c)
            }
        }
    }

    private fun applyFormat(text: String) {
        val template: String = getTemplate(text)
        stringBuilder.setLength(0)
        var i = 0
        var textIndex = 0
        while (i < template.length && textIndex < text.length) {
            if (template[i] == numPlace) {
                stringBuilder.append(text[textIndex])
                textIndex++
            } else {
                stringBuilder.append(template[i])
            }
            i++
        }
    }

    private fun isNumberChar(c: Char): Boolean {
        return c in '0'..'9'
    }

    private fun getTemplate(text: String): String {
        if (text.startsWith("8")) {
            return "X (XXX)-XXX-XX-XX"
        }
        return if (text.startsWith("7")) {
            return "+X (XXX)-XXX-XX-XX"
        } else "+XXX (XXX)-XX-XX-XX"
    }

}