package com.test.avgle.videoview

import com.test.avgle.BasePresenter
import com.test.avgle.BaseView

/**
 * Created by admin on 2018/1/24.
 */
interface VideoViewContract {

    interface View : BaseView<Presenter> {

    }

    interface Presenter : BasePresenter {

    }

}