package com.test.avgle.videoview

import android.app.Activity
import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.ui.PlaybackControlView
import com.google.android.exoplayer2.ui.SimpleExoPlayerView
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.test.avgle.R
import java.util.*

/**
 * Created by 7a6ac0 on 2018/1/10.
 */
class VideoViewActivity : Activity(), Player.EventListener {
    private lateinit var videoView: SimpleExoPlayerView
    private lateinit var videoProgressbar: ProgressBar
    private lateinit var simpleExoplayer: SimpleExoPlayer
    private lateinit var mFullScreenDialog: Dialog
    private lateinit var mFullScreenButton: FrameLayout
    private lateinit var mFullScreenIcon: ImageView

    private var mExoPlayerFullscreen = false
    private var mResumePosition = 0L

    private val url = "http://demos.webmproject.org/exoplayer/glass.mp4"

    private val bandwidthMeter by lazy {
        DefaultBandwidthMeter()
    }

    private val adaptiveTrackSelectionFactory by lazy {
        AdaptiveTrackSelection.Factory(bandwidthMeter)
    }

    companion object {
        const val VIDEO_URL = "VIDEO_URL"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.videoview_activity)

        initFullscreenDialog()
        initFullscreenButton()
        initializeExoplayer()
    }

    override fun onResume() {

        super.onResume()
    }

    override fun onPause() {
        pauseExoplayer()
        super.onPause()
    }

    override fun onDestroy() {
        releaseExoplayer()
        super.onDestroy()
    }

    private fun initFullscreenDialog() {
        mFullScreenDialog = object : Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen) {
            override fun onBackPressed() {
                closeFullscreenDialog()
                super.onBackPressed()
            }
        }
    }

    private fun closeFullscreenDialog() {
        val viewGroup = videoView.parent as ViewGroup
        viewGroup.removeView(videoView)
        findViewById<FrameLayout>(R.id.video_framelayout).addView(videoView)
        mExoPlayerFullscreen = false
        mFullScreenDialog.dismiss()
        mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_fullscreen_expand))
    }

    private fun openFullscreenDialog() {
        val viewGroup = videoView.parent as ViewGroup
        viewGroup.removeView(videoView)
        mFullScreenDialog.addContentView(
                videoView,
                ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        )
        mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_fullscreen_skrink))
        mExoPlayerFullscreen = true
        mFullScreenDialog.show()
    }

    private fun initFullscreenButton() {
        val controlView = findViewById<PlaybackControlView>(R.id.exo_controller)
        with(controlView) {
            mFullScreenIcon = findViewById(R.id.exo_fullscreen_icon)
            mFullScreenButton = findViewById(R.id.exo_fullscreen_button)
            mFullScreenButton.setOnClickListener {
                if (!mExoPlayerFullscreen)
                    openFullscreenDialog()
                else
                    closeFullscreenDialog()
            }
        }
    }

    private fun initializeExoplayer() {
        simpleExoplayer = ExoPlayerFactory.newSimpleInstance(
                DefaultRenderersFactory(this),
                DefaultTrackSelector(adaptiveTrackSelectionFactory),
                DefaultLoadControl()
        )

        videoView = findViewById<SimpleExoPlayerView>(R.id.videoView).apply {
            player = simpleExoplayer
        }

        videoProgressbar = findViewById(R.id.video_progressbar)

        simpleExoplayer.seekTo(mResumePosition)
        simpleExoplayer.prepare(buildMediaSource(Uri.parse(url)))
        simpleExoplayer.playWhenReady = true
        simpleExoplayer.addListener(this)
    }

    private fun buildMediaSource(uri: Uri) : MediaSource {
        val dataSourceFactory = DefaultDataSourceFactory(this, Util.getUserAgent(this, "Avgle"), bandwidthMeter)
        return ExtractorMediaSource(uri, dataSourceFactory, DefaultExtractorsFactory(), null, null)
    }

    private fun pauseExoplayer() {
        simpleExoplayer.playWhenReady = false
        mResumePosition = simpleExoplayer.currentPosition
    }

    private fun releaseExoplayer() {
        if (simpleExoplayer != null)
            simpleExoplayer.release()

        if (mFullScreenDialog != null)
            mFullScreenDialog.dismiss()
    }

    override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters?) {
    }

    override fun onSeekProcessed() {
    }

    override fun onTracksChanged(trackGroups: TrackGroupArray?, trackSelections: TrackSelectionArray?) {
    }

    override fun onPlayerError(error: ExoPlaybackException?) {
    }

    override fun onLoadingChanged(isLoading: Boolean) {
    }

    override fun onPositionDiscontinuity(reason: Int) {
    }

    override fun onRepeatModeChanged(repeatMode: Int) {
    }

    override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {
    }

    override fun onTimelineChanged(timeline: Timeline?, manifest: Any?) {
    }

    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        when(playbackState) {
            Player.STATE_BUFFERING -> {
                videoProgressbar.visibility = View.VISIBLE
            }
            Player.STATE_READY -> {
                videoProgressbar.visibility = View.INVISIBLE
            }
        }
    }
}