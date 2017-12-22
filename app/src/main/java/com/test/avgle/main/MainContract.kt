package com.test.avgle.main

import com.test.avgle.BasePresenter
import com.test.avgle.BaseView

/**
 * Created by admin on 2017/12/22.
 */
interface MainContract {
    interface View : BaseView<Presenter> {
        fun showCategory()
    }

    interface Presenter : BasePresenter {

        fun loadCategory(forceUpdate: Boolean)

    }
}