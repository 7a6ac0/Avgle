package com.test.avgle.data.sqlite


import com.test.avgle.data.model.video.VideoDetail
import com.test.avgle.util.parseList
import com.test.avgle.util.parseOpt
import com.test.avgle.util.toVarargArray
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
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

    fun getAllVideoDetail() = videoDetailDBHelper.use {
        val result = select(VideoDetailTable.TABLE_NAME)
                .parseList { VideoDetailClass(HashMap(it)) }
        result?.map { dataMapper.convertToDomain(it) }
    }

    fun deleteVideoDetailByVid(vid: Long) = videoDetailDBHelper.use {
        val request = "${VideoDetailTable.VID} = {vid}"
        delete(VideoDetailTable.TABLE_NAME, request, "vid" to vid.toString())
    }

    fun saveVideoDetail(video: VideoDetail) = videoDetailDBHelper.use {
        with(dataMapper.convertFromDomain(video)) {
            insert(VideoDetailTable.TABLE_NAME, *map.toVarargArray())
        }
    }
}