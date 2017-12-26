package com.test.avgle.categorydetail

import com.test.avgle.BasePresenter
import com.test.avgle.BaseView

/**
 * Created by admin on 2017/12/26.
 */
interface CategoryDetailContract {
    interface View : BaseView<Presenter> {
        fun showVides()
    }

    interface Presenter : BasePresenter {
        fun loadVides()
    }
}