package com.test.avgle.data

import com.test.avgle.data.model.CategoryRepository
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * Created by admin on 2017/12/19.
 */
interface AvgleService {
    @GET(ApiSetting.PATH_CATEGORY)
    fun getCategory() : Observable<CategoryRepository>
}