package com.test.avgle.data.sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.test.avgle.main.MainApplication
import org.jetbrains.anko.db.*

/**
 * Created by 7a6ac0 on 2018/1/23.
 */
class VideoDetailDBHelper(ctx: Context = MainApplication.instance) :
        ManagedSQLiteOpenHelper(ctx, DB_NAME, null, DB_VERSION) {

    companion object {
        @JvmField
        val DB_NAME = "avgle.db"
        @JvmField
        val DB_VERSION = 1

        val instance by lazy { VideoDetailDBHelper() }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(VideoDetailTable.TABLE_NAME, true,
                VideoDetailTable.VID to INTEGER + PRIMARY_KEY,
                    VideoDetailTable.TITLE to TEXT,
                    VideoDetailTable.KEYWORD to TEXT,
                    VideoDetailTable.HD to TEXT,
                    VideoDetailTable.ADD_TIME to INTEGER,
                    VideoDetailTable.VIEW_NUMBER to INTEGER,
                    VideoDetailTable.LIKE to INTEGER,
                    VideoDetailTable.VIDEO_URL to TEXT,
                    VideoDetailTable.EMBEDDED_URL to TEXT,
                    VideoDetailTable.PREVIEW_URL to TEXT,
                    VideoDetailTable.PREVIEW_VIDEO_URL to TEXT
                )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(VideoDetailTable.TABLE_NAME, true)
        onCreate(db)
    }
}