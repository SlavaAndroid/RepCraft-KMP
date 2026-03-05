package com.storytoys.disney.pixar.coloring.princess.googlep.data.local

import androidx.room.TypeConverter
import org.json.JSONArray

class Converters {
    @TypeConverter
    fun fromStringList(value: List<String>): String {
        val arr = JSONArray()
        value.forEach { arr.put(it) }
        return arr.toString()
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        val arr = JSONArray(value)
        return (0 until arr.length()).map { arr.getString(it) }
    }
}