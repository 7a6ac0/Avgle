package com.test.avgle.data.model.video

/**
 * Created by 7a6ac0 on 2017/12/20.
 */
data class VideoDetail(val vid: Long,
                       val title: String,
                       val keyword: String,
                       val hd: Boolean,
                       val addtime: Long,
                       val viewnumber: Long,
                       val like: Long,
                       val video_url: String,
                       val embedded_url: String,
                       val preview_url: String,
                       val preview_video_url: String,
                       var isFavorite: Boolean)