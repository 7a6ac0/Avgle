/*
 * Copyright 2017, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.test.avgle.main

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.squareup.picasso.Picasso
import com.test.avgle.R
import com.test.avgle.categorydetail.CategoryDetailActivity
import com.test.avgle.data.model.category.CategoryDetail
import com.test.avgle.util.showSnackBar


/**
 * Display a grid of [Task]s. User can choose to view all, active or completed tasks.
 */
class MainFragment : Fragment(), MainContract.View {
    override lateinit var presenter: MainContract.Presenter
    override var isActive: Boolean = false
        get() = isAdded

    private lateinit var categoryView: LinearLayout
    private lateinit var categoryLabelView: TextView

    internal var itemListener: CategoryItemListener = object : CategoryItemListener {
        override fun onCategoryClick(clickedCategory: CategoryDetail) {
            presenter.openCategoryDetails(clickedCategory)
        }
    }

    private val listAdapter = CategoryAdapter(ArrayList(0), itemListener)

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun showCategory(categories: List<CategoryDetail>) {
        listAdapter.categories = categories
        categoryLabelView.text = resources.getString(R.string.all_categories)
        categoryView.visibility = View.VISIBLE
    }

    override fun showCategoryDetailUi(categoryID: String, categoryName: String) {
        val intent = Intent(context, CategoryDetailActivity::class.java).apply {
            putExtra(CategoryDetailActivity.CATEGORY_ID, categoryID)
            putExtra(CategoryDetailActivity.CATEGORY_NAME, categoryName)
        }
        startActivity(intent)
    }

    override fun setLoadingIndicator(active: Boolean) {
        val root = view ?: return
        with(root.findViewById<SwipeRefreshLayout>(R.id.category_refresh_layout)) {
            post { isRefreshing = active }
        }
    }

    override fun showLoadingCategoryError() {
        showMessage(getString(R.string.loading_category_error))
    }

    override fun showLoadingCategorySuccess() {
        showMessage(getString(R.string.loading_category_success))
    }

    private fun showMessage(message: String) {
        view?.showSnackBar(message, Snackbar.LENGTH_LONG)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.category_fragment, container, false)
        // Set up main view
        with(root) {
            val listView = findViewById<ListView>(R.id.category_list).apply { adapter = listAdapter }

            // Set up progress indicator
            findViewById<ScrollChildSwipeRefreshLayout>(R.id.category_refresh_layout).apply {
                setColorSchemeColors(
                        ContextCompat.getColor(activity, R.color.colorPrimary),
                        ContextCompat.getColor(activity, R.color.colorAccent),
                        ContextCompat.getColor(activity, R.color.colorPrimaryDark)
                )
                // Set the scrolling view in the custom SwipeRefreshLayout.
                scrollUpChild = listView
                setOnRefreshListener { presenter.loadCategory(true) }
            }
            categoryLabelView = findViewById(R.id.category_label)
            categoryView = findViewById(R.id.category_linear_layout)
        }

        setHasOptionsMenu(true)
        return root
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    private class CategoryAdapter(categories: List<CategoryDetail>, private val itemListener: CategoryItemListener)
        : BaseAdapter() {
        var categories: List<CategoryDetail> = categories
            set(categories) {
                field = categories
                notifyDataSetChanged()
            }

        override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {
            val category = getItem(i)
            val rowView = view ?: LayoutInflater.from(viewGroup.context)
                    .inflate(R.layout.category_item, viewGroup, false)

            Picasso.with(viewGroup.context).load(category.cover_url)
                    .into(rowView.findViewById<ImageView>(R.id.category_image))

            with(rowView.findViewById<TextView>(R.id.category_title)) {
                text = category.name
            }

            rowView.setOnClickListener { itemListener.onCategoryClick(category) }
            return rowView
        }

        override fun getItem(i: Int) = categories[i]

        override fun getItemId(i: Int) = i.toLong()

        override fun getCount() = categories.size


    }

    interface CategoryItemListener {
        fun onCategoryClick(clickedCategory: CategoryDetail)
    }

}
