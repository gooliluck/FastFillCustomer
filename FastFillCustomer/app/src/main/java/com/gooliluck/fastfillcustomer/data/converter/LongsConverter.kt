package com.gooliluck.rebornproject.database.converter

import android.R.attr.data
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.Collections.emptyList


object LongsConverter {
    @TypeConverter
    @JvmStatic
    fun toString(longs: List<Long>?): String? {
        val gson = Gson()
        return gson.toJson(longs)
    }

    @TypeConverter
    @JvmStatic
    fun toLongs(data: String?): List<Long>? {
        val gson = Gson()
        if (data == null || data.isEmpty()) {
            return emptyList()
        }
        val listType: Type = object : TypeToken<List<Long>>() {}.type
        return gson.fromJson(data, listType)
    }
}