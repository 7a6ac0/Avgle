package com.test.avgle.main

import android.app.Application

/**
 * Created by 7a6ac0 on 2018/1/23.
 */
class MainApplication : Application() {

    companion object {
        lateinit var instance: MainApplication private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}