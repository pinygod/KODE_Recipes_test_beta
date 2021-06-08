package com.example.kode_recipes_test.viewmodels

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kode_recipes_test.data.ImageRepository
import com.example.kode_recipes_test.utils.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RecipeImageViewModel(private val imageRepository: ImageRepository) : ViewModel() {

    private val _saveState: MutableStateFlow<Result<String?>> = MutableStateFlow(Result.Empty())
    val saveState: StateFlow<Result<String?>> = _saveState

    fun downloadImage(imageUrl: String) {
        viewModelScope.launch {
            try {
                _saveState.value = Result.Loading()

                val uri = imageRepository.saveImage(imageUrl)

                _saveState.value = Result.Success(uri.encodedPath)
            } catch (e: Exception) {
                _saveState.value = Result.Error(e)
            } finally {
                _saveState.value = Result.Empty()
            }
        }
    }
}
