package com.rax.recipes.overview.model.vo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rax.recipes.overview.model.vo.Recipe.RecipeArticles.Column
import com.rax.recipes.overview.model.vo.Recipe.RecipeArticles.tableName

/**
 * Describes how the recipe article are stored.
 */
@Entity(tableName = tableName)
data class Recipe(
    /**
     * Primary key for Room.
     */
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = Column.title)
    val title: String,
    @ColumnInfo(name = Column.description)
    val description: String,
    @ColumnInfo(name = Column.images)
    val images: List<String>
) {


    object RecipeArticles {
        const val tableName = "recipe_article"

        object Column {
            const val id = "id"
            const val title = "title"
            const val description = "description"
            const val images = "images"
            const val createdAt = "createdAt"
        }
    }
}

