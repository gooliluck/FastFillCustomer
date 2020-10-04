package com.gooliluck.fastfillcustomer.data.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*

object StringsConverter {
    @TypeConverter
    @JvmStatic
    fun toString(strings: List<String>?): String? {
        val gson = Gson()
        return gson.toJson(strings)
    }

    @TypeConverter
    @JvmStatic
    fun toStrings(data: String?): List<String>? {
        val gson = Gson()
        if (data == null || data.isEmpty()) {
            return Collections.emptyList()
        }
        val listType: Type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(data, listType)
    }
}