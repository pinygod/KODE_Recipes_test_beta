package com.example.kode_recipes_test.di

import com.example.kode_recipes_test.data.room.RecipeDatabase
import androidx.room.Room
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(androidApplication(), RecipeDatabase::class.java, "recipes-db")
            .build()
    }
    single { get<RecipeDatabase>().getRecipeDao() }
}