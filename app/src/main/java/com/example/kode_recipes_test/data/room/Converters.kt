package com.example.kode_recipes_test.data.room

import androidx.room.TypeConverter
import com.example.kode_recipes_test.data.RecipeBrief
import com.google.gson.Gson
import java.util.*
import com.google.gson.reflect.TypeToken




class Converters {
    @TypeConverter
    fun stringListToJson(value: List<String>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToStringList(value: String) = Gson().fromJson(value, Array<String>::class.java).toList()

    @TypeConverter
    fun uuidToString(uuid: UUID): String {
        return uuid.toString()
    }

    @TypeConverter
    fun stringToUuid(string: String?): UUID {
        return UUID.fromString(string)
    }

    @TypeConverter
    fun fromRecipeBriefList(recipesList: List<RecipeBrief>?): String? {
        if (recipesList == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<RecipeBrief>?>() {}.type
        return gson.toJson(recipesList, type)
    }

    @TypeConverter
    fun toRecipeBriefList(recipesString: String?): List<RecipeBrief>? {
        if (recipesString == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<RecipeBrief>?>() {}.type
        return gson.fromJson<List<RecipeBrief>>(recipesString, type)
    }

}