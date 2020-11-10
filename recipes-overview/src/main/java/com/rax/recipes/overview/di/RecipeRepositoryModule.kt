package com.rax.recipes.overview.di

import com.rax.recipes.overview.model.repo.RecipeRepository
import com.rax.recipes.overview.model.repo.impl.RecipeRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
interface RecipeRepositoryModule {
    /* Exposes the concrete implementation for the interface */
    @Binds
    fun it(it: RecipeRepositoryImpl): RecipeRepository
}