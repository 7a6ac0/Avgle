package com.test.avgle.data

import com.test.avgle.data.model.Category.Category
import com.test.avgle.data.model.Video.Video
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by admin on 2017/12/19.
 */
interface AvgleService {
    @GET(ApiSetting.CATEGORY)
    fun getCategory() : Observable<Category>

    @GET(ApiSetting.VIDEO)
    fun getVideo(@Path(ApiSetting.PATH_VIDEO_OFFSET) current_offset: String,
                 @Query(ApiSetting.CATEGORY_ID) category_id: String) : Observable<Video>
}