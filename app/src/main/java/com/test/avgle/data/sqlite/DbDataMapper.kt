package com.test.avgle.data.sqlite

import com.test.avgle.data.model.video.VideoDetail

/**
 * Created by admin on 2018/1/31.
 */
class DbDataMapper {
    fun convertFromDomain(video: VideoDetail) = with(video) {
        VideoDetailClass(
                vid,
                title,
                keyword,
                hd,
                addtime,
                viewnumber,
                like,
                video_url,
                embedded_url,
                preview_url,
                preview_video_url
        )
    }

    fun convertToDomain(video: VideoDetailClass) = with(video) {
        VideoDetail(
                vid,
                title,
                keyword,
                hd,
                addtime,
                viewnumber,
                like,
                video_url,
                embedded_url,
                preview_url,
                preview_video_url
        )
    }
}