package com.test.avgle.categorydetail

import com.test.avgle.data.AvgleService

/**
 * Created by admin on 2017/12/26.
 */
class CategoryDetailPresenter(private val categoryID: String,
                              private val avgleService: AvgleService,
                              private val categoryDetailView: CategoryDetailContract.View)
    : CategoryDetailContract.Presenter {

    init {
        categoryDetailView.presenter = this
    }

    override fun start() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadVides() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}