package com.example.kode_recipes_test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.kode_recipes_test.adapters.RecipesAdapter
import com.example.kode_recipes_test.data.Recipe
import com.example.kode_recipes_test.databinding.FragmentRecipesListBinding
import com.example.kode_recipes_test.utils.Result
import com.example.kode_recipes_test.utils.showMessage
import com.example.kode_recipes_test.viewmodels.RecipesListViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class RecipesListFragment : Fragment(), RecipesAdapter.OnItemClickListener {
    private lateinit var binding: FragmentRecipesListBinding
    private val viewModel by viewModel<RecipesListViewModel>()
    private val adapter = RecipesAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecipesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.recipes.collect { uiState ->
                    when (uiState) {
                        is Result.Success -> adapter.submitList(uiState.data)
                        is Result.Error -> onErrorWhileLoadingRecipes(uiState)
                    }
                }
            }
        }

        setupBinding()
    }

    private fun setupBinding() {
        with(binding) {
            lifecycleOwner = viewLifecycleOwner
            viewmodel = viewModel
            recipesRecyclerView.adapter = adapter
        }
    }

    private fun onErrorWhileLoadingRecipes(uiState: Result.Error<List<Recipe>>) {
        uiState.throwable?.message?.let {
            showMessage(
                binding.root,
                it
            )
        }
    }

    override fun onItemClick(recipe: Recipe) {
        findNavController().navigate(
            RecipesListFragmentDirections.actionRecipesListToRecipeDetails(
                recipe.uuid.toString()
            )
        )
    }
}