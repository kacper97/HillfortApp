package org.wit.hillfort.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.hillfort.models.*

class MainApp : Application(), AnkoLogger {

  lateinit var hillforts : HillfortStore
  lateinit var users : UserStore

  override fun onCreate() {
    super.onCreate()
    // hillforts = HillfortMemStore(applicationContext)
    // users = UserMemStore(applicationContext)
    hillforts = HillfortJSONStore(applicationContext)
    users = UserJSONStore(applicationContext)
    //hillforts = HilfortFireStore(applicationContext)
    info("Hillfort started")

    //
  }
}