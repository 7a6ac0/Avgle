package com.test.avgle.favorite

import com.test.avgle.BasePresenter
import com.test.avgle.BaseView
import com.test.avgle.data.model.video.VideoDetail

/**
 * Created by 7a6ac0 on 2018/1/23.
 */
interface FavoriteContract {
    interface View : BaseView<Presenter> {
        fun showFavoriteVideos(videos: List<VideoDetail>)

        fun setLoadingIndicator(active: Boolean)

        fun showVideoAndPlay(videoUrl: String)
    }

    interface Presenter : BasePresenter {
        fun loadFavoriteVideos()

        fun getVideoDetailByVid(vid: Long): VideoDetail?

        fun saveVideoDetail(video: VideoDetail)

        fun deleteVideoDetailByVid(vid: Long)

        fun openVideo(videoUrl: String)
    }
}