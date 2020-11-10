package com.rax.recipes.overview.model.storage

import androidx.room.TypeConverter
import com.google.gson.Gson

object Converters {
    @TypeConverter
    @JvmStatic
    fun listToJson(value: List<String>?): String = Gson().toJson(value)

    @TypeConverter
    @JvmStatic
    fun jsonToList(value: String) = Gson().fromJson(value, Array<String>::class.java).toList()
}