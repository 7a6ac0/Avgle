package com.test.avgle.categorydetail

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.squareup.picasso.Picasso
import com.test.avgle.R
import com.test.avgle.data.model.video.VideoDetail
import com.test.avgle.main.ScrollChildSwipeRefreshLayout
import com.test.avgle.util.showSnackBar
import com.test.avgle.videoview.VideoViewActivity
import java.util.*

/**
 * Created by 7a6ac0 on 2017/12/26.
 */
class CategoryDetailFragment : Fragment(), CategoryDetailContract.View {
    override lateinit var presenter: CategoryDetailContract.Presenter
    override var isActive: Boolean = false
        get() = isAdded

    private lateinit var categorydetailView: LinearLayout
    private lateinit var categorydetailLabel: TextView

    companion object {
        private val ARGUMENT_CATEGORY_ID = "CATEGORY_ID"
        private val ARGUMENT_CATEGORY_NAME = "CATEGORY_NAME"

        fun newInstance(categoryID: String?, categoryName: String?) =
                CategoryDetailFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARGUMENT_CATEGORY_ID, categoryID)
                        putString(ARGUMENT_CATEGORY_NAME, categoryName)
                    }
                }
    }

    internal var itemListener: VideoItemListener = object : VideoItemListener {
        override fun onVideoClick(clickedVideo: VideoDetail) {
            // Need to write something to play video.
            presenter.openVideo(clickedVideo.embedded_url, clickedVideo.title)
        }
    }

    private val listAdapter = VideoAdapter(ArrayList(0), itemListener)

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val root = inflater.inflate(R.layout.categorydetail_fragment, container, false)
        with(root) {
            val listView = findViewById<ListView>(R.id.categorydetail_list).apply {
                adapter = listAdapter
                setOnScrollListener(object : AbsListView.OnScrollListener {
                    override fun onScrollStateChanged(view: AbsListView, scrollState: Int) {
                        when(scrollState) {
                            AbsListView.OnScrollListener.SCROLL_STATE_IDLE -> {
                                if(lastVisiblePosition == (count - 1)) {
                                    presenter.loadMoreVideos()
                                }
                            }
                        }
                    }

                    override fun onScroll(view: AbsListView?,
                                          firstVisibleItem: Int,
                                          visibleItemCount: Int,
                                          totalItemCount: Int) {

                    }
                })
            }
            // Set up progress indicator
            findViewById<ScrollChildSwipeRefreshLayout>(R.id.categorydetail_refresh_layout).apply {
                setColorSchemeColors(
                        ContextCompat.getColor(activity, R.color.colorPrimary),
                        ContextCompat.getColor(activity, R.color.colorAccent),
                        ContextCompat.getColor(activity, R.color.colorPrimaryDark)
                )
                // Set the scrolling view in the custom SwipeRefreshLayout.
                scrollUpChild = listView
                setOnRefreshListener { presenter.loadVideos(true) }

            }
            categorydetailView = findViewById(R.id.categorydetail_linear_layout)
            categorydetailLabel = findViewById(R.id.categorydetail_label)
        }
        setHasOptionsMenu(true)
        return root
    }

    override fun showVideos(videos: MutableList<VideoDetail>) {
        listAdapter.videos = videos
        categorydetailLabel.text = arguments.getString(ARGUMENT_CATEGORY_NAME)
        categorydetailView.visibility = View.VISIBLE
    }

    override fun showMoreVideos(videos: MutableList<VideoDetail>) {
        with(listAdapter) {
            this.videos.addAll(videos)
            notifyDataSetChanged()
        }
    }

    override fun setLoadingIndicator(active: Boolean) {
        val root = view ?: return
        with(root.findViewById<SwipeRefreshLayout>(R.id.categorydetail_refresh_layout)) {
            post { isRefreshing = active }
        }
    }

    override fun showLoadingVideoError() {
        showMessage(getString(R.string.loading_video_error))
    }

    override fun showLoadingVideoSuccess() {
        showMessage(getString(R.string.loading_video_success))
    }

    override fun showNoMoreVideos() {
        showMessage(getString(R.string.no_more_videos))
    }

    private fun showMessage(message: String) {
        view?.showSnackBar(message, Snackbar.LENGTH_LONG)
    }

    override fun showVideoAndPlay(videoUrl: String, videoName: String) {
        var intent = Intent(context, VideoViewActivity::class.java).apply {
            putExtra(VideoViewActivity.VIDEO_URL, videoUrl)
            putExtra(VideoViewActivity.VIDEO_NAME, videoName)
        }
        startActivity(intent)
    }

    private class VideoAdapter(videos: MutableList<VideoDetail>, private val itemListener: CategoryDetailFragment.VideoItemListener)
        : BaseAdapter() {
        var videos: MutableList<VideoDetail> = videos
            set(videos) {
                field = videos
                notifyDataSetChanged()
            }

        override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {
            val video = getItem(i)
            val rowView = view ?: LayoutInflater.from(viewGroup.context)
                    .inflate(R.layout.categorydetail_item, viewGroup, false)

            with(rowView.findViewById<TextView>(R.id.video_hd_label)) {
                post { visibility = if (video.hd) View.VISIBLE else View.INVISIBLE }
            }

            Picasso.with(viewGroup.context).load(video.preview_url)
                    .into(rowView.findViewById<ImageView>(R.id.video_image).apply {
                        imageAlpha = 200
                    })


            with(rowView.findViewById<TextView>(R.id.video_title)) {
                text = video.title
            }

            with(rowView.findViewById<TextView>(R.id.video_viewnumber)) {
                text = video.viewnumber.toString()
            }

            rowView.setOnClickListener { itemListener.onVideoClick(video) }
            return rowView
        }

        override fun getItem(i: Int) = videos[i]

        override fun getItemId(i: Int) = i.toLong()

        override fun getCount() = videos.size
    }

    interface VideoItemListener {
        fun onVideoClick(clickedVideo: VideoDetail)
    }
}