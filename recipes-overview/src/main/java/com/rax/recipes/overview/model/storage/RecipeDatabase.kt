package com.rax.recipes.overview.model.storage

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rax.recipes.overview.model.storage.RecipeDatabase.Companion.latestVersion
import com.rax.recipes.overview.model.vo.Recipe

@Database(
    entities = [Recipe::class],
    version = latestVersion
)
@TypeConverters(Converters::class)
abstract class RecipeDatabase : RoomDatabase() {

    /**
     * Get recipe article DAO
     */
    abstract fun recipeArticlesDao(): RecipeArticlesDao

    companion object {

        private const val databaseName = "recipes-db"
        const val latestVersion = 1

        fun buildDefault(context: Context) =
            Room.databaseBuilder(context, RecipeDatabase::class.java, databaseName)
                .build()

    }
}