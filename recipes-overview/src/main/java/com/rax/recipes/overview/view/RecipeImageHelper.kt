package com.rax.recipes.overview.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import coil.api.load
import com.rax.recipes.overview.R
import kotlinx.android.synthetic.main.item_recipe_image.view.*
import java.io.File


internal object RecipeImageHelper {

    fun createImageRow(
        parent: ViewGroup,
        imageURI: String? = null,
        listener: ((imageURI: String?, View) -> (Unit))? = null,

        ) {
        val imageItem = LayoutInflater.from(parent.context).inflate(
            R.layout.item_recipe_image,
            parent, false
        )
        imageItem.setOnClickListener {
            listener?.invoke(imageURI, it)
        }
        if (imageURI == null) {
            imageItem.recipe_delete_image.isVisible = false
        } else {
            if (listener == null)
                imageItem.recipe_delete_image.isVisible = false
            imageItem.recipe_image.load(File(imageURI))
        }
        parent.addView(imageItem)
    }

}