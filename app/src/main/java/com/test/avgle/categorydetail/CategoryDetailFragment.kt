package com.test.avgle.categorydetail

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.test.avgle.R
import com.test.avgle.data.model.Video.VideoDetail
import com.test.avgle.main.MainFragment

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
        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun getItem(p0: Int): Any {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun getItemId(p0: Int): Long {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun getCount(): Int {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }

    interface VideoItemListener {
        fun onVideoClick(clickedCategory: VideoDetail)
    }
}