package com.example.kode_recipes_test.viewmodels

import androidx.lifecycle.*
import com.example.kode_recipes_test.data.Recipe
import com.example.kode_recipes_test.data.RecipesRepository
import com.example.kode_recipes_test.utils.Result
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class RecipesListViewModel(private val recipesRepository: RecipesRepository) : ViewModel() {

    private val _sortOrder = MutableStateFlow("")
    private val _searchQuery = MutableStateFlow("")

    private val _recipes: MutableStateFlow<Result<List<Recipe>>> =
        MutableStateFlow(Result.Empty())
    val recipes: StateFlow<Result<List<Recipe>>> = _recipes

    init {
        viewModelScope.launch {
            combine(_searchQuery, _sortOrder) { query, order ->
                Pair(query, order)
            }.flatMapLatest { (query, order) -> //on every change of searchQuery or sortOrder this will create a new request to repo
                _recipes.value = Result.Loading()

                recipesRepository.getAllRecipes(
                    query,
                    order
                )
            }
                .catch { exception ->
                    _recipes.value = Result.Error(exception)
                }
                .collect { recipesList ->
                    _recipes.value = Result.Success(recipesList)
                }
        }
    }

    fun onSortChange(itemAtPosition: Any) {
        val item = itemAtPosition as String
        if (_sortOrder.value != item)
            _sortOrder.value = item
    }

    fun onQueryChanged(newQuery: CharSequence) {
        if (_searchQuery.value != newQuery)
            _searchQuery.value = newQuery.toString()
    }

}
