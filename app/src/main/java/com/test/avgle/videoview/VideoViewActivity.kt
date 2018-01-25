package com.test.avgle.videoview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.test.avgle.R
import com.test.avgle.util.replaceFragmentInActivity
import com.test.avgle.util.setupActionBar

/**
 * Created by 7a6ac0 on 2018/1/10.
 */
class VideoViewActivity : AppCompatActivity() {
    companion object {
        const val VIDEO_URL = "VIDEO_URL"
        const val VIDEO_NAME = "VIDEO_NAME"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.videoview_activity)

        // Set up the toolbar.
        setupActionBar(R.id.toolbar) {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        val videoName = intent.getStringExtra(VIDEO_NAME)
        val videoUrl = intent.getStringExtra(VIDEO_URL)

        val videoViewFragment = supportFragmentManager
                .findFragmentById(R.id.contentFrame) as VideoViewFragment? ?:
                VideoViewFragment.newInstance(videoName, videoUrl).also {
                    replaceFragmentInActivity(it, R.id.contentFrame)
                }

        VideoViewPresenter(videoViewFragment)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}