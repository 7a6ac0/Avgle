package com.test.avgle.categorydetail

import com.test.avgle.data.AvgleService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by admin on 2017/12/26.
 */
class CategoryDetailPresenter(private val categoryID: String,
                              private val avgleService: AvgleService,
                              private val categoryDetailView: CategoryDetailContract.View)
    : CategoryDetailContract.Presenter {

    init {
        categoryDetailView.presenter = this
    }

    companion object {
        private var page_offset: Int = 0
    }

    override fun start() {
        loadVideos(true)
    }

    override fun loadVideos(showLoadingUI: Boolean) {
        if (showLoadingUI)
            categoryDetailView.setLoadingIndicator(true)

        page_offset = 0

        avgleService.getVideo(page_offset.toString(), categoryID)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe ({
                    categoryDetailView.showVideos(it.response.videos)
                    if(showLoadingUI)
                        categoryDetailView.setLoadingIndicator(false)

                    categoryDetailView.showLoadingVideoSuccess()
                }, {
                    it.printStackTrace()
                    if(showLoadingUI)
                        categoryDetailView.setLoadingIndicator(false)

                    categoryDetailView.showLoadingVideoError()
                })
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
                        categoryDetailView.showMoreVideos(it.response.videos)

                }, {
                    it.printStackTrace()
                    categoryDetailView.showLoadingVideoError()
                })
    }

    override fun openVideo(originalVideoUrl: String) {
        // Need to parse whole html to get the video streaming url.
        categoryDetailView.showVideoAndPlay(originalVideoUrl)
    }
}