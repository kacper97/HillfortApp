package org.wit.hillfort.main

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.hillfort.models.*

class MainApp : Application(), AnkoLogger {

  lateinit var hillforts : ArrayList<HillfortModel>
  lateinit var firestore : HillfortFireStore
  override fun onCreate() {
    super.onCreate()
    firestore = HillfortFireStore(applicationContext)
    hillforts = firestore.hillforts

    info("Hillfort started")

    //
  }
}