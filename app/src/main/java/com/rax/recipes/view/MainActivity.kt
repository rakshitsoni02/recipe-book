package com.rax.recipes.view

import android.os.Bundle
import com.rax.base.app.BaseActivity
import com.rax.recipes.R
import com.rax.recipes.overview.view.RecipesListingFragment

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showFragment(RecipesListingFragment.newInstance())
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount ==1) {
            finish()
        }else{
            super.onBackPressed()
        }
    }
}