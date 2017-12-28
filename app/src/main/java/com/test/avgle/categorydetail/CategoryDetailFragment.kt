package com.test.avgle.categorydetail

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import com.squareup.picasso.Picasso
import com.test.avgle.R
import com.test.avgle.data.model.Video.VideoDetail
import com.test.avgle.main.MainFragment
import org.w3c.dom.Text

/**
 * Created by admin on 2017/12/26.
 */
class CategoryDetailFragment : Fragment(), CategoryDetailContract.View {
    override lateinit var presenter: CategoryDetailContract.Presenter

    companion object {
        private val ARGUMENT_CATEGORY_ID = "CATEGORY_ID"

        fun newInstance(categoryID: String?) =
                CategoryDetailFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARGUMENT_CATEGORY_ID, categoryID)
                    }
                }
    }

    internal var itemListener: VideoItemListener = object : VideoItemListener {
        override fun onVideoClick(clickedCategory: VideoDetail) {

        }
    }

    private val listAdapter = VideoAdapter(ArrayList(0), itemListener)

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun showVides() {

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val root = inflater.inflate(R.layout.categorydetail_fragment, container, false)
        with(root) {
            val listView = findViewById<ListView>(R.id.categorydetail_list).apply {
                adapter = listAdapter
            }
        }
        setHasOptionsMenu(true)
        return root
    }

    private class VideoAdapter(videos: List<VideoDetail>, private val itemListener: CategoryDetailFragment.VideoItemListener)
        : BaseAdapter() {
        var videos: List<VideoDetail> = videos
            set(videos) {
                field = videos
                notifyDataSetChanged()
            }

        override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {
            val video = getItem(i)
            val rowView = view ?: LayoutInflater.from(viewGroup.context)
                    .inflate(R.layout.categorydetail_item, viewGroup, false)

            Picasso.with(viewGroup.context).load(video.preview_url)
                    .into(rowView.findViewById<ImageView>(R.id.video_image))

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
        fun onVideoClick(clickedCategory: VideoDetail)
    }
}