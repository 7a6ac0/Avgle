package com.test.avgle.categorydetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.PopupMenu
import android.view.*
import android.view.animation.Animation
import android.view.animation.BounceInterpolator
import android.view.animation.ScaleAnimation
import android.widget.*
import com.squareup.picasso.Picasso
import com.test.avgle.R
import com.test.avgle.data.model.video.Video
import com.test.avgle.data.model.video.VideoDetail
import com.test.avgle.main.ScrollChildSwipeRefreshLayout
import com.test.avgle.util.findViewOften
import com.test.avgle.util.showSnackBar
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

    private var currentSortType = CategoryDetailSortType.NONE_SORT

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

    private var itemListener: VideoItemListener = object : VideoItemListener {
        override fun onVideoClick(clickedVideo: VideoDetail) {
            // Need to write something to play video.
            presenter.openVideo(clickedVideo.video_url)
        }
    }

    private val listAdapter by lazy { VideoAdapter(ArrayList(0), itemListener, presenter) }

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_sort -> showFilteringPopUpMenu()
            android.R.id.home -> activity.finish()
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater) {
        inflater.inflate(R.menu.categorydetail_fragment_menu, menu)
    }

    override fun showVideos(video: Video) {
        listAdapter.videos = video.response.videos
        categorydetailLabel.text = arguments.getString(ARGUMENT_CATEGORY_NAME)
        categorydetailView.visibility = View.VISIBLE
    }

    override fun showMoreVideos(video: Video) {
        with(listAdapter) {
            this.videos.addAll(video.response.videos)
            when (currentSortType) {
                CategoryDetailSortType.NONE_SORT -> notifyDataSetChanged()
                CategoryDetailSortType.ASC_SORT -> sortVideoListByAscending()
                CategoryDetailSortType.DESC_SORT -> sortVideoListByDescending()
            }
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

    override fun showVideoAndPlay(videoUrl: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl)))
    }

    override fun showFilteringPopUpMenu() {
        PopupMenu(context, activity.findViewById(R.id.menu_sort)).apply {
            menuInflater.inflate(R.menu.sort_videos, menu)
            setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.sorted_by_viewnumber_with_asc -> sortVideoListByAscending()
                    R.id.sorted_by_viewnumber_with_desc -> sortVideoListByDescending()
                }
                true
            }
            show()
        }
    }

    private fun sortVideoListByAscending() {
        with(listAdapter) {
            videos.sortBy({ it.viewnumber })
            notifyDataSetChanged()
        }
        currentSortType = CategoryDetailSortType.ASC_SORT
    }

    private fun sortVideoListByDescending() {
        with(listAdapter) {
            videos.sortByDescending ( { it.viewnumber } )
            notifyDataSetChanged()
        }
        currentSortType = CategoryDetailSortType.DESC_SORT
    }

    private class VideoAdapter(videos: MutableList<VideoDetail>,
                               private val itemListener: CategoryDetailFragment.VideoItemListener,
                               private val presenterInAdapter: CategoryDetailContract.Presenter)
        : BaseAdapter() {

        var videos: MutableList<VideoDetail> = videos
            set(videos) {
                field = videos
                notifyDataSetChanged()
            }

        override fun getView(i: Int, convertView: View?, viewGroup: ViewGroup): View {
            val video = getItem(i)
            val videoInDB = presenterInAdapter.getVideoDetailByVid(video.vid)
            val rowView = convertView ?: LayoutInflater.from(viewGroup.context)
                    .inflate(R.layout.categorydetail_item, viewGroup, false)

            with(rowView.findViewOften<TextView>(R.id.video_hd_label)) {
                post { visibility = if (video.hd) View.VISIBLE else View.INVISIBLE }
            }

            Picasso.with(viewGroup.context).load(video.preview_url)
                    .into(rowView.findViewOften<ImageView>(R.id.video_image).apply {
                        imageAlpha = 200
                    })

            with(rowView.findViewOften<TextView>(R.id.video_title)) {
                text = video.title
            }

            with(rowView.findViewOften<TextView>(R.id.video_viewnumber)) {
                text = video.viewnumber.toString()
            }

            var scaleAnimation = ScaleAnimation(0.7f, 1.0f, 0.7f, 1.0f, Animation.RELATIVE_TO_SELF, 0.7f, Animation.RELATIVE_TO_SELF, 0.7f)
            scaleAnimation?.duration = 500
            val bounceInterpolator = BounceInterpolator()
            scaleAnimation?.interpolator = bounceInterpolator

            rowView.findViewOften<ToggleButton>(R.id.video_favorite_button).apply {
                isChecked = videoInDB?.isFavorite ?: false
                setOnClickListener(object : View.OnClickListener {
                    override fun onClick(view: View?) {
                        startAnimation(scaleAnimation)
                        if (isChecked) {
                            video.isFavorite = isChecked
                            presenterInAdapter.saveVideoDetail(video)
                        }
                        else {
                            presenterInAdapter.deleteVideoDetailByVid(video.vid)
                        }
                    }
                })
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