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


import com.test.avgle.BasePresenter
import com.test.avgle.BaseView
import com.test.avgle.data.model.category.CategoryDetail

/**
 * This specifies the contract between the view and the presenter.
 */
interface MainContract {

    interface View : BaseView<Presenter> {
        var isActive: Boolean

        fun showCategory(categories: List<CategoryDetail>)

        fun setLoadingIndicator(active: Boolean)

        fun showLoadingCategoryError()

        fun showLoadingCategorySuccess()

        fun showCategoryDetailUi(categoryID: String, categoryName: String)

    }

    interface Presenter : BasePresenter {

        fun loadCategory(isNeedUpdate: Boolean)

        fun openCategoryDetails(requestCategory: CategoryDetail)

    }
}
