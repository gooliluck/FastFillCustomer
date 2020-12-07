package com.gooliluck.fastfillcustomer.utils

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import java.lang.ref.WeakReference
import java.text.DecimalFormat
import java.text.NumberFormat

enum class TextWatcherType {
    BirthDay,Currency
}
class CustomTextWatcher(editText: EditText, val type: TextWatcherType) : TextWatcher {
    private var editTextWeakReference: WeakReference<EditText>? = null

    init {
        editTextWeakReference = WeakReference(editText)
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }
    private fun changeTextCurrency(unFormatString: String) : String {
        Log.i("jptest", "unFormatString is $unFormatString")
        val cleanString: String = unFormatString.replace("[$,]".toRegex(), "")
        if (cleanString.isNotEmpty()){
            Log.e("jptest", "show char ==================== $cleanString")
            val formatter : NumberFormat = DecimalFormat("#,###")
            return "$${formatter.format(cleanString.toLong())}"
        }
        return ""
    }
    private fun changeTextFormat(unFormatString: String) : String {
        var cleanString = unFormatString.replace("[-/.]".toRegex(), "")
        var formatString = ""
        if (cleanString.length > 8) {
            cleanString = cleanString.substring(0, 8)
        }
        if (cleanString.isNotEmpty() && cleanString.length <= 8){
            for (i in cleanString.indices){
                val char = cleanString[i]
                formatString += char
                if (i == 5 || i == 3) {
                    formatString += "/"
                }
            }
        }
        return formatString
    }
    override fun afterTextChanged(editable: Editable) {
        val editText = editTextWeakReference!!.get() ?: return
        val s = editable.toString()
        if (s.isEmpty()) return
        editText.removeTextChangedListener(this)
        val formatted = when (type){
            TextWatcherType.BirthDay -> {
                changeTextFormat(s)
            }
            TextWatcherType.Currency -> {
                changeTextCurrency(s)
            }
        }
        editText.setText(formatted)
        editText.setSelection(formatted.length)
        editText.addTextChangedListener(this)
    }
}