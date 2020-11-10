package com.rax.recipes.overview.model.repo

import com.rax.base.lifecycle.ViewState
import com.rax.recipes.overview.model.vo.Recipe
import kotlinx.coroutines.flow.Flow

/**
 * Repository abstracts the logic of fetching the data.
 * They are the data source as the single source of truth.
 */
interface RecipeRepository {
    /**
     * Gets recipes list created by user
     */
    fun getRecipesList(): Flow<ViewState<List<Recipe>>>

    fun saveRecipe(recipe: Recipe): Flow<ViewState<Recipe>>

}