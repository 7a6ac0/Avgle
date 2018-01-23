package com.test.avgle.data.sqlite

/**
 * Created by 7a6ac0 on 2018/1/23.
 */
class VideoDetailClass(var map: MutableMap<String, Any?>) {
    var _id: Long by map
    var title: String by map
    var keyword: String by map
    var hd: String by map
    var add_time: Long by map
    var view_number: Long by map
    var like: Long by map
    var embedded_url: String by map
    var preview_url: String by map
    var preview_video_url: String by map

    constructor(title: String,
                keyword: String,
                hd: String,
                add_time: Long,
                view_number: Long,
                like: Long,
                embedded_url: String,
                preview_url: String,
                preview_video_url: String) : this(HashMap()) {

        this.title = title
        this.keyword = keyword
        this.hd = hd
        this.add_time = add_time
        this.view_number = view_number
        this.like = like
        this.embedded_url = embedded_url
        this.preview_url = preview_url
        this.preview_video_url = preview_video_url
    }
}