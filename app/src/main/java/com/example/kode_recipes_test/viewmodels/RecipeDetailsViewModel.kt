package com.example.kode_recipes_test.viewmodels

import androidx.core.text.HtmlCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.denzcoskun.imageslider.models.SlideModel
import com.example.kode_recipes_test.data.RecipeDetails
import com.example.kode_recipes_test.data.RecipesRepository
import com.example.kode_recipes_test.utils.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*

class RecipeDetailsViewModel(private val recipesRepository: RecipesRepository) : ViewModel() {

    private val _recipe: MutableStateFlow<Result<RecipeDetails>> =
        MutableStateFlow(Result.Empty())
    val recipe: StateFlow<Result<RecipeDetails>> = _recipe

    fun setRecipe(recipeUuid: UUID) {
        viewModelScope.launch {
            try {
                _recipe.value = Result.Loading()

                val data = recipesRepository.getRecipe(recipeUuid)
                    ?: throw Exception("Error while loading recipe")

                data.instructions =
                    HtmlCompat.fromHtml(data.instructions, HtmlCompat.FROM_HTML_MODE_LEGACY)
                        .toString()

                _recipe.value = Result.Success(data)
            } catch (e: Exception) {
                _recipe.value = Result.Error(e)
            }
        }
    }

    fun getRecipeSlides(): List<SlideModel>? = _recipe.value.data?.images?.map { SlideModel(it) }
}