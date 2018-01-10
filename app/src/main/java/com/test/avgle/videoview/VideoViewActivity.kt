package com.test.avgle.videoview

import android.app.Activity
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.MediaController
import android.widget.ProgressBar
import android.widget.VideoView
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
    private var playbackPosition = 0L
    private val url = "http://www.sample-videos.com/video/mp4/720/big_buck_bunny_720p_1mb.mp4"

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
        initializeExoplayer()
    }

    override fun onStop() {
        releaseExoplayer()
        super.onStop()
    }

    private fun initializeExoplayer() {
        simpleExoplayer = ExoPlayerFactory.newSimpleInstance(
                DefaultRenderersFactory(this),
                DefaultTrackSelector(adaptiveTrackSelectionFactory),
                DefaultLoadControl()
        )
        prepareExoplayer()
        videoView = findViewById<SimpleExoPlayerView>(R.id.videoView).apply {
            player = simpleExoplayer
        }
        videoProgressbar = findViewById<ProgressBar>(R.id.video_progressbar)

        simpleExoplayer.seekTo(playbackPosition)
        simpleExoplayer.playWhenReady = true
        simpleExoplayer.addListener(this)
    }



    private fun prepareExoplayer() {
        val uri = Uri.parse(url)
        val mediaSource = buildMediaSource(uri)
        simpleExoplayer.prepare(mediaSource)
    }

    private fun buildMediaSource(uri: Uri) : MediaSource {
        val dataSourceFactory = DefaultDataSourceFactory(this, Util.getUserAgent(this, "Avgle"), bandwidthMeter)
        return ExtractorMediaSource(uri, dataSourceFactory, DefaultExtractorsFactory(), null, null)
    }

    private fun releaseExoplayer() {
        playbackPosition = simpleExoplayer.currentPosition
        simpleExoplayer.release()
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