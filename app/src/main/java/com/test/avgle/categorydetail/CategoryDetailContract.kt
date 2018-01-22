package com.test.avgle.categorydetail

import com.test.avgle.BasePresenter
import com.test.avgle.BaseView
import com.test.avgle.data.model.video.VideoDetail

/**
 * Created by 7a6ac0 on 2017/12/26.
 */
interface CategoryDetailContract {
    interface View : BaseView<Presenter> {
        var isActive: Boolean

        fun showVideos(videos: MutableList<VideoDetail>)

        fun showMoreVideos(videos: MutableList<VideoDetail>)

        fun showNoMoreVideos()

        fun setLoadingIndicator(active: Boolean)

        fun showLoadingVideoError()

        fun showLoadingVideoSuccess()

        fun showVideoAndPlay(videoUrl: String, videoName: String)
    }

    interface Presenter : BasePresenter {
        fun loadVideos(isNeedUpdate: Boolean)

        fun loadMoreVideos()

        fun openVideo(originalVideoUrl: String, videoName: String)
    }
}