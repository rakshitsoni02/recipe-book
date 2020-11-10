package com.rax.recipes.overview.view

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.rax.base.app.BaseFragment
import com.rax.base.lifecycle.ResourceObserver
import com.rax.base.utils.file.FileUriUtils
import com.rax.base.utils.view.toast
import com.rax.recipes.overview.R
import com.rax.recipes.overview.viewmodel.RecipesOverviewViewModel
import kotlinx.android.synthetic.main.fragment_create_recipe.*
import kotlinx.android.synthetic.main.layout_toolbar.*


class CreateRecipeFragment : BaseFragment() {
    private val viewModel: RecipesOverviewViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_recipe, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tb_title_text.text = getString(R.string.add_recipe)
        tb_icon_action_1.setImageResource(R.drawable.ic_save_512x512)
        tb_icon_action_1.setOnClickListener {
            viewModel.createRecipe()
        }
        viewModel.recipeCreationUpdate().observe(viewLifecycleOwner, ResourceObserver(
            data = {
                baseActivity?.onBackPressed()
            },
            error = {
                toast(it.text(requireActivity()))
            }
        ))
        RecipeImageHelper.createImageRow(parent = f_container_images, listener = ::handleImageClick)
        f_title_edit_text.addTextChangedListener(
            afterTextChanged = {
                viewModel.onRecipeTitleChanged(it.toString())
            }
        )
        f_description_edit_text.addTextChangedListener(
            afterTextChanged = {
                viewModel.onRecipeDescriptionChanged(it.toString())
            }
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_PHOTO_FOR_RECIPE && resultCode == Activity.RESULT_OK) {
            val imageData = data?.data ?: return
            val realUri = FileUriUtils.getRealPath(requireContext(), imageData) ?: return
            viewModel.onRecipeImageUpdate(image = realUri, isAdded = true)
            RecipeImageHelper.createImageRow(
                f_container_images,
                realUri,
                ::handleImageClick
            )
        }
    }

    private fun handleImageClick(imageURI: String?, view: View) {
        if (imageURI == null) {
            openImageBrowserIntent()
        } else {
            viewModel.onRecipeImageUpdate(imageURI, isAdded = false)
            f_container_images.removeView(view)
        }
    }


    private fun openImageBrowserIntent() {
        requestPermission(
            permissionType = Manifest.permission.READ_EXTERNAL_STORAGE,
            requestCode = RECIPE_IMAGE_STORAGE_PERMISSION_CODE,
            onGrantedListener = {
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = IMAGE_TYPE
                startActivityForResult(
                    intent,
                    PICK_PHOTO_FOR_RECIPE
                )
            }, onDeniedListener = {
                toast("Permission is required")
            }
        )
    }

    companion object {
        const val RECIPE_IMAGE_STORAGE_PERMISSION_CODE = 20100
        const val PICK_PHOTO_FOR_RECIPE = 20101
        const val IMAGE_TYPE = "image/*"
        fun newInstance() = CreateRecipeFragment()
    }
}