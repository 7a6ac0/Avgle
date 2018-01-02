package com.test.avgle.categorydetail

import com.test.avgle.BasePresenter
import com.test.avgle.BaseView
import com.test.avgle.data.model.Video.VideoDetail

/**
 * Created by admin on 2017/12/26.
 */
interface CategoryDetailContract {
    interface View : BaseView<Presenter> {
        fun showVides(videos: List<VideoDetail>)

        fun setLoadingIndicator(active: Boolean)

        fun showLoadingVideoError()

        fun showLoadingVideoSuccess()
    }

    interface Presenter : BasePresenter {
        fun loadVides(showLoadingUI: Boolean)
    }
}