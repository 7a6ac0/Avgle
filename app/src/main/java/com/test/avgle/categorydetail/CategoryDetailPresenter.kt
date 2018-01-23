package com.test.avgle.categorydetail

import com.test.avgle.data.api.AvgleService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by 7a6ac0 on 2017/12/26.
 */
class CategoryDetailPresenter(private val categoryID: String,
                              private val avgleService: AvgleService,
                              private val categoryDetailView: CategoryDetailContract.View)
    : CategoryDetailContract.Presenter {

    private var firstLoad: Boolean = true

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
                            categoryDetailView.showVideos(it.response.videos)
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
                        categoryDetailView.showMoreVideos(it.response.videos)

                }, {
                    it.printStackTrace()
                    categoryDetailView.showLoadingVideoError()
                })
    }

    override fun openVideo(originalVideoUrl: String, videoName: String) {
        categoryDetailView.showVideoAndPlay(originalVideoUrl, videoName)
    }
}