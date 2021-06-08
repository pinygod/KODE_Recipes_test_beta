package com.example.kode_recipes_test.di

import com.example.kode_recipes_test.viewmodels.RecipeDetailsViewModel
import com.example.kode_recipes_test.viewmodels.RecipeImageViewModel
import com.example.kode_recipes_test.viewmodels.RecipesListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel { RecipesListViewModel(get()) }
    viewModel { RecipeDetailsViewModel(get()) }
    viewModel { RecipeImageViewModel(get()) }
}