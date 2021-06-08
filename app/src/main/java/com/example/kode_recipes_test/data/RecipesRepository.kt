package com.example.kode_recipes_test.data

import com.example.kode_recipes_test.data.room.RecipeDao
import com.example.kode_recipes_test.network.RecipesApi
import com.example.kode_recipes_test.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import java.util.*

class RecipesRepository(private val api: RecipesApi, private val recipeDao: RecipeDao) {

    suspend fun getRecipe(uuid: UUID) = withContext(Dispatchers.IO) {
        networkBound(
            query = { recipeDao.getRecipe(uuid) },
            fetch = { api.getRecipe(uuid) },
            saveFetchResult = { recipeDao.deleteAndInsertRecipe(it.recipe) }
        )
    }

    suspend fun getAllRecipes(searchQuery: String, sortOrder: String) =
        networkBound(
            query = { recipeDao.getRecipes(searchQuery, sortOrder) },
            fetch = { api.getAllRecipes() },
            saveFetchResult = { recipeDao.deleteAndInsertAll(it.recipes) }
        ).flowOn(Dispatchers.IO)

    /*fun getAllRecipes(searchQuery: String, sortOrder: String): Flow<List<Recipe>> = flow {
        var recipesList = api.getAllRecipes().recipes
        recipesList = getRecipesByQuery(recipesList, searchQuery)
        recipesList = sortRecipesByOrder(recipesList, sortOrder)
        emit(recipesList)
    }.flowOn(Dispatchers.IO)

    private fun sortRecipesByOrder(recipesList: List<Recipe>, sortOrder: String): List<Recipe> {
        return if (sortOrder == Constants.SORT_BY_NAME) {
            recipesList.sortedBy { it.name }
        } else {
            recipesList.sortedByDescending { it.lastUpdated }
        }
    }

    private fun getRecipesByQuery(recipesList: List<Recipe>, query: String) =
        recipesList.filter { element ->
            checkStringsLikeness(
                element.name,
                query
            ) || checkStringsLikeness(
                element.description,
                query
            ) || checkStringsLikeness(element.instructions, query)
        }

    private fun checkStringsLikeness(string: String?, query: String) = if (!string.isNullOrEmpty())
        string.contains(query, ignoreCase = true) else false*/

}