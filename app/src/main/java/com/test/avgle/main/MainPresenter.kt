/*
 * Copyright 2017, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.test.avgle.main

import com.test.avgle.data.api.AvgleServiceFactory
import com.test.avgle.data.model.category.CategoryDetail
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Listens to user actions from the UI ([MainFragment]), retrieves the data and updates the
 * UI as required.
 */
class MainPresenter(private val mainView: MainContract.View)
    : MainContract.Presenter {

    private var firstLoad: Boolean = true

    private val avgleService by lazy { AvgleServiceFactory.APIService }

    init {
        mainView.presenter = this
    }

    override fun start() {
        loadCategory(false)
    }

    override fun loadCategory(isNeedUpdate: Boolean) {
        loadCategory(isNeedUpdate || firstLoad, true)
        firstLoad = false
    }

    private fun loadCategory(isNeedUpdate: Boolean, showLoadingUI: Boolean) {
        if (isNeedUpdate) {
            if (showLoadingUI)
                mainView.setLoadingIndicator(true)

            avgleService.getCategory()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({
                        if (mainView.isActive) {
                            mainView.showCategory(it)
                            mainView.showLoadingCategorySuccess()
                        }
                        if (showLoadingUI)
                            mainView.setLoadingIndicator(false)
                    }, {
                        it.printStackTrace()
                        if (showLoadingUI)
                            mainView.setLoadingIndicator(false)

                        mainView.showLoadingCategoryError()
                    })
        }
    }

    override fun openCategoryDetails(requestCategory: CategoryDetail) {
        mainView.showCategoryDetailUi(requestCategory.CHID, requestCategory.name)
    }
}
