package com.test.avgle.favorite

import com.test.avgle.data.model.video.VideoDetail
import com.test.avgle.data.sqlite.VideoDetailDB

/**
 * Created by admin on 2018/2/5.
 */
class FavoritePresenter(private val favoriteFragment: FavoriteFragment)
    : FavoriteContract.Presenter {

    private val videoDetailDB by lazy { VideoDetailDB() }

    init {
        favoriteFragment.presenter = this
    }

    override fun start() {
        loadFavoriteVideos()
    }

    override fun loadFavoriteVideos() {
        favoriteFragment.setLoadingIndicator(true)
        var favoriteVideos = videoDetailDB.getAllVideoDetail()
        favoriteFragment.showFavoriteVideos(favoriteVideos)
        favoriteFragment.setLoadingIndicator(false)
    }

    override fun getVideoDetailByVid(vid: Long): VideoDetail? {
        return videoDetailDB.getVideoDetailByVid(vid)
    }

    override fun saveVideoDetail(video: VideoDetail) {
        videoDetailDB.saveVideoDetail(video)
    }

    override fun deleteVideoDetailByVid(vid: Long) {
        videoDetailDB.deleteVideoDetailByVid(vid)
    }

    override fun openVideo(videoUrl: String) {
        favoriteFragment.showVideoAndPlay(videoUrl)
    }
}