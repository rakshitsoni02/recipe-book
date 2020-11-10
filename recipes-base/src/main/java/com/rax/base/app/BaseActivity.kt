package com.rax.base.app

import androidx.appcompat.app.AppCompatActivity
import com.rax.base.R
import dagger.hilt.android.AndroidEntryPoint

/**
 * Base activity providing common config & support
 */
@AndroidEntryPoint
abstract class BaseActivity : AppCompatActivity() {

    fun showFragment(fragment: BaseFragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(null)
            .commit()
    }

}