package com.test.avgle.data.sqlite

/**
 * Created by 7a6ac0 on 2018/1/23.
 */
class VideoDetailClass(var map: MutableMap<String, Any?>) {
    var vid: Long by map
    var title: String by map
    var keyword: String by map
    var hd: Long by map
    var addtime: Long by map
    var viewnumber: Long by map
    var like: Long by map
    var video_url: String by map
    var embedded_url: String by map
    var preview_url: String by map
    var preview_video_url: String by map
    var isFavorite: Long by map

    constructor(vid: Long,
                title: String,
                keyword: String,
                hd: Long,
                addtime: Long,
                viewnumber: Long,
                like: Long,
                video_url: String,
                embedded_url: String,
                preview_url: String,
                preview_video_url: String,
                isFavorite: Long) : this(HashMap()) {

        this.vid = vid
        this.title = title
        this.keyword = keyword
        this.hd = hd
        this.addtime = addtime
        this.viewnumber = viewnumber
        this.like = like
        this.video_url = video_url
        this.embedded_url = embedded_url
        this.preview_url = preview_url
        this.preview_video_url = preview_video_url
        this.isFavorite = isFavorite
    }
}