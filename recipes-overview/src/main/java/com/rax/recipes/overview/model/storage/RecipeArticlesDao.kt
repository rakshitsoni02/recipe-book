package com.rax.recipes.overview.model.storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.rax.recipes.overview.model.vo.Recipe
import kotlinx.coroutines.flow.Flow

/**
 * Defines access layer to recipe articles table
 */
@Dao
interface RecipeArticlesDao {

    /**
     * Insert articles into the table
     */
    @Insert
    fun insertArticles(car: List<Recipe>): List<Long>

    /**
     * Get all the articles from table
     */
    @Query("SELECT * FROM recipe_article")
    fun getRecipeArticles(): Flow<List<Recipe>>
}