package com.test.avgle.categorydetail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.test.avgle.R
import com.test.avgle.data.AvgleServiceFactory
import com.test.avgle.util.replaceFragmentInActivity
import com.test.avgle.util.setupActionBar

/**
 * Created by admin on 2017/12/26.
 */
class CategoryDetailActivity : AppCompatActivity() {
    companion object {
        const val CATEGORY_ID = "CATEGORY_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.categorydetail_activity)

        // Set up the toolbar.
        setupActionBar(R.id.toolbar) {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        val categoryID = intent.getStringExtra(CATEGORY_ID)

        val categoryDetailFragment = supportFragmentManager
                .findFragmentById(R.id.contentFrame) as CategoryDetailFragment? ?:
                CategoryDetailFragment.newInstance(categoryID).also {
                    replaceFragmentInActivity(it, R.id.contentFrame)
                }

        val avgleService = AvgleServiceFactory.APIService
        CategoryDetailPresenter(categoryID, avgleService, categoryDetailFragment)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}