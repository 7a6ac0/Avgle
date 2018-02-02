package com.test.avgle.data.sqlite

import com.test.avgle.data.model.video.VideoDetail

/**
 * Created by admin on 2018/1/31.
 */
class DbDataMapper {
    fun convertFromDomain(video: VideoDetail) = with(video) {
        val video_hd: Long = if (hd) 1 else 0
        val video_isFavorite: Long = if (isFavorite) 1 else 0
        VideoDetailClass(
                vid,
                title,
                keyword,
                video_hd,
                addtime,
                viewnumber,
                like,
                video_url,
                embedded_url,
                preview_url,
                preview_video_url,
                video_isFavorite
        )
    }

    fun convertToDomain(video: VideoDetailClass) = with(video) {
        val video_hd: Boolean = (hd == 1.toLong())
        val video_isFavorite: Boolean = (isFavorite == 1.toLong())
        VideoDetail(
                vid,
                title,
                keyword,
                video_hd,
                addtime,
                viewnumber,
                like,
                video_url,
                embedded_url,
                preview_url,
                preview_video_url,
                video_isFavorite
        )
    }
}