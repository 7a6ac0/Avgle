package com.test.avgle.data.sqlite

import com.antonioleiva.weatherapp.extensions.parseOpt
import org.jetbrains.anko.db.select

/**
 * Created by 7a6ac0 on 2018/1/31.
 */
class VideoDetailDB(private val videoDetailDBHelper: VideoDetailDBHelper = VideoDetailDBHelper.instance,
                    private val dataMapper: DbDataMapper = DbDataMapper()) {

    fun getVideoDetailByVid(vid: Long) = videoDetailDBHelper.use {
        val request = "${VideoDetailTable.VID} = ?"
        val result = select(VideoDetailTable.TABLE_NAME)
                .whereSimple(request, vid.toString())
                .parseOpt { VideoDetailClass(HashMap(it)) }
        result?.let { dataMapper.convertToDomain(it) }
    }
}