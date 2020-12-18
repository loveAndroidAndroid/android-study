package com.moudle.xiecheng

import android.app.Application
import android.content.Context
import kotlin.properties.Delegates

class ZApp : Application() {

    companion object {
        var mContext: Context by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        mContext = applicationContext
    }
}

