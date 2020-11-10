package com.rax.recipes.overview.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.rax.base.app.BaseFragment
import com.rax.base.lifecycle.ResourceObserver
import com.rax.base.utils.view.toast
import com.rax.recipes.overview.R
import com.rax.recipes.overview.model.vo.Recipe
import com.rax.recipes.overview.viewmodel.RecipesOverviewViewModel
import kotlinx.android.synthetic.main.empty_layout.*
import kotlinx.android.synthetic.main.fragment_recipe_list.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import kotlinx.android.synthetic.main.progress_layout.*

class RecipesListingFragment : BaseFragment() {
    private val viewModel: RecipesOverviewViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recipe_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = RecipesAdapter()
        f_recipes_container_listing_recipes.adapter = adapter
        viewModel.getRecipesArticles().observe(
            viewLifecycleOwner,
            ResourceObserver(
                loadingState = {
                    progressViewRecipe.isVisible = it
                },
                error = { toast(it.text(requireActivity())) },
                data = ::updateRecipes
            )
        )
        fun refreshState() {
            when (adapter.itemCount) {
                0 -> {
                    progressViewRecipe.isVisible = false
                    f_recipes_empty_view.isVisible = true
                    f_recipes_container_listing_recipes.isVisible = false
                }
                else -> {
                    progressViewRecipe.isVisible = false
                    f_recipes_empty_view.isVisible = false
                    f_recipes_container_listing_recipes.isVisible = true
                }
            }
        }
        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() = refreshState()
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) = refreshState()
            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) = refreshState()
        })
        tb_icon_action_1.setOnClickListener {
            show(CreateRecipeFragment.newInstance(), baseActivity)
        }
    }


    private fun updateRecipes(recipes: List<Recipe>) {
        (f_recipes_container_listing_recipes.adapter as RecipesAdapter).submitList(recipes)
    }

    companion object {
        fun newInstance() = RecipesListingFragment()
    }
}