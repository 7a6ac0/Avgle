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

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.test.avgle.R
import com.test.avgle.data.model.Category.CategoryDetail


/**
 * Display a grid of [Task]s. User can choose to view all, active or completed tasks.
 */
class MainFragment : Fragment(), MainContract.View {
    override lateinit var presenter: MainContract.Presenter

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun showCategory() {

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.category_fragment, container, false)
        // Set up main view
        with(root) {

        }
        return root
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    private class CategoryAdapter(categories: List<CategoryDetail>, private val itemListener: CategoryItemListener)
        : BaseAdapter() {
        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun getItem(p0: Int): Any {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun getItemId(p0: Int): Long {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun getCount(): Int {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }

    interface CategoryItemListener {
        fun onCategoryClick(clickedCategory: CategoryDetail)
    }

}
