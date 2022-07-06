package com.asuka.example

import android.app.Application
import com.asuka.XLog

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        //init XLog
        XLog.getInstance()
            .enableDiskLog(false)
            .setLogFileSize(300)
            .setDiskLogLevel(4)
            .init(this)

        //ust it
        XLog.d("TEST DEBUG")
        XLog.e("TEST ERROR")
    }
}