package com.example.kode_recipes_test.di

import com.example.kode_recipes_test.network.provideRecipesApi
import com.example.kode_recipes_test.network.provideRetrofit
import org.koin.dsl.module

val networkModule = module {
    factory { provideRecipesApi(get()) }
    single { provideRetrofit() }
}