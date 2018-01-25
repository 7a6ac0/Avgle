package com.test.avgle.videoview

/**
 * Created by admin on 2018/1/24.
 */
class VideoViewPresenter(private val videoView: VideoViewContract.View)
    : VideoViewContract.Presenter {

    init {
        videoView.presenter = this
    }

    override fun start() {

    }
}