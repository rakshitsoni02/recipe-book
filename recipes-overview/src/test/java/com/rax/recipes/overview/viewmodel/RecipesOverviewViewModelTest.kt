package com.rax.recipes.overview.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.rax.base.lifecycle.ViewState
import com.rax.recipes.overview.model.repo.RecipeRepository
import com.rax.recipes.overview.model.vo.Recipe
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RecipesOverviewViewModelTest {

    @Mock
    lateinit var recipeRepository: RecipeRepository

    lateinit var viewModel: RecipesOverviewViewModel

    @Before
    fun setup() {
        viewModel = RecipesOverviewViewModel(recipeRepository)
    }

    @Test
    fun getRecipesArticlesTest() {
        runBlocking {
            val result = viewModel.getRecipesArticles()
//            verify(recipeRepository, atLeastOnce()).getRecipesList()
            val liveData = mock<LiveData<ViewState<List<Recipe>>>>()
            whenever(recipeRepository.getRecipesList().asLiveData()) doReturn liveData
            assert(result == liveData)
        }
    }

}