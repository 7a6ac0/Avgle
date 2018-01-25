package com.test.avgle.videoview

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import com.test.avgle.R


/**
 * Created by admin on 2018/1/24.
 */
class VideoViewFragment : Fragment(), VideoViewContract.View {
    override lateinit var presenter: VideoViewContract.Presenter
    private lateinit var videoWebView: WebView

    companion object {
        private val ARGUMENT_VIDEO_NAME = "VIDEO_NAME"
        private val ARGUMENT_VIDEO_URL = "VIDEO_URL"

        fun newInstance(videoName: String?, videoUrl: String?) =
                VideoViewFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARGUMENT_VIDEO_NAME, videoName)
                        putString(ARGUMENT_VIDEO_URL, videoUrl)
                    }
                }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.videoview_fragment, container, false)
        var frameVideo: String = "<html><body><iframe width=\"100%\" height=\"100%\" src=\"${arguments.getString(ARGUMENT_VIDEO_URL)}\" frameborder=\"0\" allowfullscreen></iframe></body></html>"

        with(root) {
            videoWebView = findViewById<WebView>(R.id.videoview_webview).apply {
                settings.apply {
                    javaScriptEnabled = true
                    javaScriptCanOpenWindowsAutomatically = true
                }
//                webViewClient = object : WebViewClient() {
//                    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
//                        return super.shouldOverrideUrlLoading(view, request)
//                    }
//                }
                setBackgroundColor(0)
                loadData(frameVideo, "text/html", "utf-8")
                //loadDataWithBaseURL("https://avgle.com", frameVideo, "text/html", "utf-8", null)
            }
        }
        return root
    }
}