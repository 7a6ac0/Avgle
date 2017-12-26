package com.test.avgle.categorydetail

import android.app.Fragment
import android.os.Bundle

/**
 * Created by admin on 2017/12/26.
 */
class CategoryDetailFragment : Fragment(), CategoryDetailContract.View {
    override lateinit var presenter: CategoryDetailContract.Presenter

    companion object {
        private val ARGUMENT_CATEGORY_ID = "CATEGORY_ID"


        fun newInstance(categoryID: String?) =
                CategoryDetailFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARGUMENT_CATEGORY_ID, categoryID)
                    }
                }
    }

    override fun showVides() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}