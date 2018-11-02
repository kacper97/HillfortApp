package org.wit.hillfort.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.hillfort.models.*

class MainApp : Application(), AnkoLogger {

  lateinit var hillforts : HillfortStore
  lateinit var users : UserStore

  override fun onCreate() {
   //users.create(UserModel(0,"b","c"))
   // users.create(UserModel(1,"kacpx@w.com","password"))
   // users.create(UserModel(2,"kacpi@w.com","password"))
    super.onCreate()


    users = UserMemStore()
    hillforts = HillfortJSONStore(applicationContext)
    //users = UserJSONStore(applicationContext)
    info("Hillfort started")

    //
  }
}