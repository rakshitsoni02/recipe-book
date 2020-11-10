package com.rax.recipes.overview.di

import android.app.Application
import com.rax.recipes.overview.model.storage.RecipeArticlesDao
import com.rax.recipes.overview.model.storage.RecipeDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RecipeDatabaseModule {

    @Singleton
    @Provides
    fun provideDb(app: Application): RecipeDatabase = RecipeDatabase.buildDefault(app)

    @Singleton
    @Provides
    fun provideRecipeDao(db: RecipeDatabase): RecipeArticlesDao = db.recipeArticlesDao()
}