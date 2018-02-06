package com.test.avgle.favorite

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.BounceInterpolator
import android.view.animation.ScaleAnimation
import android.widget.*
import com.squareup.picasso.Picasso
import com.test.avgle.R
import com.test.avgle.data.model.video.VideoDetail
import com.test.avgle.main.ScrollChildSwipeRefreshLayout
import com.test.avgle.util.findViewOften

/**
 * Created by 7a6ac0 on 2018/2/5.
 */
class FavoriteFragment : Fragment(), FavoriteContract.View {
    override lateinit var presenter: FavoriteContract.Presenter

    private lateinit var favoriteView: LinearLayout
    private lateinit var favoriteLabelView: TextView

    companion object {
        fun newInstance() = FavoriteFragment()
    }

    private var itemListener: FavoriteItemListener = object : FavoriteItemListener {
        override fun onFavoriteItemClick(video: VideoDetail) {
            presenter.openVideo(video.video_url)
        }
    }

    private val listAdapter by lazy { FavoriteAdapter(ArrayList(0), itemListener, presenter) }

    override fun showFavoriteVideos(videos: List<VideoDetail>) {
        listAdapter.videos = videos
        favoriteLabelView.text = resources.getString(R.string.all_favorites)
        favoriteView.visibility = View.VISIBLE
    }

    override fun setLoadingIndicator(active: Boolean) {
        val root = view ?: return
        with(root.findViewById<SwipeRefreshLayout>(R.id.favorite_refresh_layout)) {
            post { isRefreshing = active }
        }
    }

    override fun showVideoAndPlay(videoUrl: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl)))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.favorite_fragment, container, false)
        with(root) {
            val listView = findViewById<ListView>(R.id.favorite_list).apply { adapter = listAdapter }

            findViewById<ScrollChildSwipeRefreshLayout>(R.id.favorite_refresh_layout).apply {
                setColorSchemeColors(
                        ContextCompat.getColor(activity, R.color.colorPrimary),
                        ContextCompat.getColor(activity, R.color.colorAccent),
                        ContextCompat.getColor(activity, R.color.colorPrimaryDark)
                )
                scrollUpChild = listView
                setOnRefreshListener { presenter.loadFavoriteVideos() }
            }
            favoriteView = findViewById<LinearLayout>(R.id.favorite_linear_layout)
            favoriteLabelView = findViewById<TextView>(R.id.favorite_label)
        }

        setHasOptionsMenu(true)
        return root
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    private class FavoriteAdapter(videos: List<VideoDetail>,
                                  private val itemListener: FavoriteFragment.FavoriteItemListener,
                                  private val presenterInAdapter: FavoriteContract.Presenter)
        : BaseAdapter() {
        var videos: List<VideoDetail> = videos
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

            rowView.setOnClickListener { itemListener.onFavoriteItemClick(video) }
            return rowView
        }

        override fun getItem(i: Int) = videos[i]

        override fun getItemId(i: Int) = i.toLong()

        override fun getCount() = videos.size
    }

    interface FavoriteItemListener {
        fun onFavoriteItemClick(video: VideoDetail)
    }
}