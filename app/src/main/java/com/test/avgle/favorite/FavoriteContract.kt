package com.test.avgle.favorite

import com.test.avgle.BasePresenter
import com.test.avgle.BaseView
import com.test.avgle.data.model.video.VideoDetail

/**
 * Created by 7a6ac0 on 2018/1/23.
 */
interface FavoriteContract {
    interface View : BaseView<Presenter> {
        fun showFavoriteVideos(videos: MutableList<VideoDetail>)
    }

    interface Presenter : BasePresenter {
        fun loadFavoriteVideos()
    }
}