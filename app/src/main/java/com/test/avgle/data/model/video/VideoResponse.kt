package com.test.avgle.data.model.video

/**
 * Created by 7a6ac0 on 2017/12/20.
 */
data class VideoResponse(val has_more: Boolean,
                         val total_videos: Int,
                         val current_offset: String,
                         val videos: MutableList<VideoDetail>)