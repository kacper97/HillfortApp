package org.wit.hillfort.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import org.wit.hillfort.R
import org.wit.hillfort.main.MainApp

class HillfortListActivity : AppCompatActivity() {

  lateinit var app: MainApp

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_hillfort_list)
    app = application as MainApp
  }
}