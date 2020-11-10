package com.rax.recipes.overview.model.repo.impl

import com.rax.base.lifecycle.ViewState
import com.rax.base.utils.string.StringProvider
import com.rax.recipes.overview.R
import com.rax.recipes.overview.model.repo.RecipeRepository
import com.rax.recipes.overview.model.storage.RecipeArticlesDao
import com.rax.recipes.overview.model.vo.Recipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * RecipeRepository implementation contains data mapping and processing
 */
@Singleton
class RecipeRepositoryImpl @Inject constructor(private val recipeDao: RecipeArticlesDao) :
    RecipeRepository {

    override fun getRecipesList(): Flow<ViewState<List<Recipe>>> = flow {
        emit(ViewState.loading())
        val cachedRecipes = recipeDao.getRecipeArticles()
        emitAll(cachedRecipes.map { ViewState.success(it) })
    }
        .flowOn(Dispatchers.IO)

    override fun saveRecipe(recipe: Recipe): Flow<ViewState<Recipe>> = flow {
        emit(ViewState.loading())
        if (recipe.title.isEmpty() || recipe.description.isEmpty() || recipe.images.isEmpty()) {
           emit(ViewState.error(StringProvider.of(R.string.error_invalid_input)))
            return@flow
        }
        recipeDao.insertArticles(listOf(recipe))
        emit(ViewState.success(recipe))
    }.flowOn(Dispatchers.IO)
}