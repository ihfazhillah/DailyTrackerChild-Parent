package com.ihfazh.dailytrackerchild_parent

import android.app.Application
import com.ihfazh.dailytrackerchild_parent.ActivityCompositionRoot

class DailyTrackerChildApplication: Application() {
    lateinit var compositionRoot : ActivityCompositionRoot
    override fun onCreate() {
        super.onCreate()
        compositionRoot = ActivityCompositionRoot(this)
    }
}