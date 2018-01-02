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

    override fun start() {
        loadVides(true)
    }

    override fun loadVides(showLoadingUI: Boolean) {
        if (showLoadingUI)
            categoryDetailView.setLoadingIndicator(true)

        avgleService.getVideo("0", categoryID)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe ({
                    categoryDetailView.showVides(it.response.videos)
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
}