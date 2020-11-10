package com.rax.recipes.overview.view

import android.net.Uri
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rax.base.utils.view.inflate
import com.rax.recipes.overview.R
import com.rax.recipes.overview.model.vo.Recipe
import kotlinx.android.synthetic.main.item_recipe.view.*

class RecipesAdapter : ListAdapter<Recipe, RecipesAdapter.RecipeItemHolder>(DIFF_CALLBACK) {

    /**
     * Inflate the view
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        RecipeItemHolder(parent.inflate(R.layout.item_recipe))

    /**
     * Bind the view with the data
     */
    override fun onBindViewHolder(recipeItemHolder: RecipeItemHolder, position: Int) =
        recipeItemHolder.bind(getItem(position))

    /**
     * View Holder Pattern
     */
    class RecipeItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        /**
         * Binds the UI with the data and handles clicks
         */
        fun bind(recipe: Recipe) = with(itemView) {
            i_recipe_title.text = recipe.title
            i_recipe_description.text = recipe.description
            recipe.images.forEach {
                RecipeImageHelper.createImageRow(
                    parent = f_container_images,
                    imageURI = it
                )
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Recipe>() {
            override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: Recipe,
                newItem: Recipe
            ): Boolean = oldItem == newItem
        }
    }
}