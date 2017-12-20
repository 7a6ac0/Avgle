package com.test.avgle.data.model

import com.google.gson.annotations.SerializedName

/**
 * Created by admin on 2017/12/20.
 */
data class Video(val has_more: Boolean,
                 val total_videos: Int,
                 val current_offset: String,
                 val videos: VideoDetail)