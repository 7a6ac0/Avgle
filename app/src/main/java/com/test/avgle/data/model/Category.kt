package com.test.avgle.data.model

/**
 * Created by admin on 2017/12/19.
 */
data class Category(val success: Boolean,
                    val response: CategoryResponse)

data class CategoryResponse(val categories: List<Categories>)

data class Categories(val CHID: String,
                      val name: String,
                      val total_videos: Int,
                      val cover_url: String)

