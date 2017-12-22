package com.test.avgle.main

import com.test.avgle.data.AvgleService

/**
 * Created by admin on 2017/12/22.
 */
class MainPresenter(val avgleService: AvgleService,
                    val mainView: MainContract.View) : MainContract.Presenter {

    private var firstLoad = true

    init {
        mainView.presenter = this
    }

    override fun start() {
        loadCategory(false)
    }

    override fun loadCategory(forceUpdate: Boolean) {

    }
}