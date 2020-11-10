package com.rax.recipes.overview.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.rax.base.lifecycle.ViewState
import com.rax.recipes.overview.model.repo.RecipeRepository
import com.rax.recipes.overview.model.vo.Recipe

class RecipesOverviewViewModel @ViewModelInject constructor(
    private val recipeRepository: RecipeRepository
) : ViewModel() {

    private var title: String = ""
        set(value) {
            if (field != value && value.isNullOrBlank().not()) {
                field = value
            }
        }
    private var description: String = ""
        set(value) {
            if (field != value && value.isNullOrBlank().not()) {
                field = value
            }
        }
    var images: MutableList<String> = mutableListOf()

    private val recipe: MutableLiveData<Recipe> = MutableLiveData()

    private val newRecipeUpdate = Transformations.switchMap(recipe) { newPageNo ->
        recipeRepository.saveRecipe(newPageNo).asLiveData()
    }

    fun recipeCreationUpdate(): LiveData<ViewState<Recipe>> = newRecipeUpdate

    private val recipesArticlesSource: LiveData<ViewState<List<Recipe>>> =
        recipeRepository.getRecipesList().asLiveData()

    /**
     * Return recipes articles to observeNotNull on the UI.
     */
    fun getRecipesArticles(): LiveData<ViewState<List<Recipe>>> = recipesArticlesSource


    /**
     * create new recipe article & saved locally
     */
    fun createRecipe() {
        recipe.value = Recipe(title = title, description = description, images = images)
    }

    /**
     * update Title of recipe
     */
    fun onRecipeTitleChanged(text: String) {
        title = text.trim()
    }

    /**
     * update Description of recipe
     */
    fun onRecipeDescriptionChanged(text: String) {
        description = text.trim()
    }

    /**
     * add/remove Image for recipe
     */
    fun onRecipeImageUpdate(image: String, isAdded: Boolean) {
        val imageAddress = image.toString()
        if (isAdded)
            images.add(imageAddress)
        else images.remove(imageAddress)
    }
}
