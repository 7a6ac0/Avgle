package com.test.avgle.categorydetail

import com.test.avgle.data.api.AvgleServiceFactory
import com.test.avgle.data.model.video.VideoDetail
import com.test.avgle.data.sqlite.VideoDetailDB
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by 7a6ac0 on 2017/12/26.
 */
class CategoryDetailPresenter(private val categoryID: String,
                              private val categoryDetailView: CategoryDetailContract.View)
    : CategoryDetailContract.Presenter {

    private var firstLoad: Boolean = true

    private val avgleService by lazy { AvgleServiceFactory.APIService }

    private val videoDetailDB by lazy { VideoDetailDB() }

    init {
        categoryDetailView.presenter = this
    }

    companion object {
        private var page_offset: Int = 0
    }

    override fun start() {
        loadVideos(false)
    }

    override fun loadVideos(isNeedUpdate: Boolean) {
        loadVideos(isNeedUpdate || firstLoad, true)
        firstLoad = false
    }

    private fun loadVideos(isNeedUpdate: Boolean, showLoadingUI: Boolean) {
        if (isNeedUpdate) {
            if (showLoadingUI)
                categoryDetailView.setLoadingIndicator(true)

            page_offset = 0

            avgleService.getVideo(page_offset.toString(), categoryID)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({
                        if (categoryDetailView.isActive) {
                            categoryDetailView.showVideos(it)
                            categoryDetailView.showLoadingVideoSuccess()
                        }
                        if (showLoadingUI)
                            categoryDetailView.setLoadingIndicator(false)
                    }, {
                        it.printStackTrace()
                        if (showLoadingUI)
                            categoryDetailView.setLoadingIndicator(false)

                        categoryDetailView.showLoadingVideoError()
                    })
        }
    }

    override fun loadMoreVideos() {
        page_offset++

        avgleService.getVideo(page_offset.toString(), categoryID)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe ({
                    if (!it.response.has_more && it.response.videos.size == 0)
                        categoryDetailView.showNoMoreVideos()
                    else
                        categoryDetailView.showMoreVideos(it)

                }, {
                    it.printStackTrace()
                    categoryDetailView.showLoadingVideoError()
                })
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
        categoryDetailView.showVideoAndPlay(videoUrl)
    }
}