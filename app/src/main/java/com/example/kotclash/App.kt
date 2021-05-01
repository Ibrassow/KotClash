package com.example.kotclash

import android.app.Application
import android.content.Context

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        mContext = this
    }

    companion object {
        private lateinit var mContext: Context
        fun getContext(): Context {
            return mContext
        }
    }


}