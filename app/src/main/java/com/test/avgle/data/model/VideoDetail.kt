package com.test.avgle.data.model

/**
 * Created by admin on 2017/12/20.
 */
data class VideoDetail(val title: String,
                       val keyword: String,
                       val hd: Boolean,
                       val addtime: Int,
                       val viewnumber: Int,
                       val like: Int,
                       val embedded_url: String,
                       val preview_url: String,
                       val preview_video_url: String)