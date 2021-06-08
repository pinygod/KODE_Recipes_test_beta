package com.example.kode_recipes_test

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.kode_recipes_test.databinding.FragmentRecipeImageBinding
import com.example.kode_recipes_test.utils.Result
import com.example.kode_recipes_test.utils.showMessage
import com.example.kode_recipes_test.viewmodels.RecipeImageViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class RecipeImageFragment : Fragment() {

    companion object {
        const val WRITE_PERMISSION = Manifest.permission.WRITE_EXTERNAL_STORAGE
    }

    private val viewModel by viewModel<RecipeImageViewModel>()
    private lateinit var binding: FragmentRecipeImageBinding
    private lateinit var imageUrl: String

    private val activityResult =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                callImageDownload(imageUrl)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            imageUrl = RecipeImageFragmentArgs.fromBundle(it).imageUrl
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecipeImageBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            lifecycleOwner = viewLifecycleOwner
            viewmodel = viewModel
            imageUrl = this@RecipeImageFragment.imageUrl

            downloadImageButton.setOnClickListener {
                tryToDownloadImage()
            }
        }
    }

    private fun tryToDownloadImage() {
        if (!checkPermission(requireContext()))
            activityResult.launch(WRITE_PERMISSION)
        else {
            callImageDownload(imageUrl)
        }
    }

    private fun callImageDownload(imageUrl: String) {
        viewModel.downloadImage(imageUrl)
        observeSaveState()
    }

    private fun observeSaveState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.saveState.collect { state ->
                    when (state) {
                        is Result.Error -> state.throwable?.message?.let {
                            showMessage(
                                binding.root,
                                it
                            )
                        }
                        is Result.Success -> showMessage(
                            binding.root,
                            "Saved at ${state.data}"
                        )
                    }
                }
            }
        }
    }

    private fun checkPermission(context: Context) =
        ContextCompat.checkSelfPermission(
            context,
            WRITE_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED
}