package com.example.kode_recipes_test.network

import com.example.kode_recipes_test.data.Recipe
import com.example.kode_recipes_test.data.RecipeDetailsResponse
import com.example.kode_recipes_test.data.RecipesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.*

interface RecipesApi {

    @GET("recipes")
    suspend fun getAllRecipes(): RecipesResponse

    @GET("recipes/{uuid}")
    suspend fun getRecipe(@Path("uuid") uuid: UUID): RecipeDetailsResponse
}