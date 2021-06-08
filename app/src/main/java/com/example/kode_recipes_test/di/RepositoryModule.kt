package com.example.kode_recipes_test.di

import com.example.kode_recipes_test.data.ImageRepository
import com.example.kode_recipes_test.data.RecipesRepository
import org.koin.dsl.module

val repositoryModule = module {
    single {
        RecipesRepository(get(), get())
    }
    single {
        ImageRepository(get())
    }
}