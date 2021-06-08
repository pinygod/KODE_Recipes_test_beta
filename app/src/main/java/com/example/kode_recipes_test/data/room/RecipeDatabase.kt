package com.example.kode_recipes_test.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.kode_recipes_test.data.Recipe
import com.example.kode_recipes_test.data.RecipeDetails

@Database(version = 1, entities = [Recipe::class, RecipeDetails::class], exportSchema = false)
@TypeConverters(Converters::class)
abstract class RecipeDatabase : RoomDatabase() {

    abstract fun getRecipeDao(): RecipeDao
}