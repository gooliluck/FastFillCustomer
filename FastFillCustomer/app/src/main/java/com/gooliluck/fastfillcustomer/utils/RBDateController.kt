package com.gooliluck.fastfillcustomer.utils

import android.widget.DatePicker
import java.util.*


class RBDateController {
    companion object{
        fun getDateFromDatePicker(datePicker: DatePicker): Date? {
            val day = datePicker.dayOfMonth
            val month = datePicker.month
            val year = datePicker.year
            val calendar = Calendar.getInstance()
            calendar.set(year,month,day)
            return calendar.time
        }
    }
}