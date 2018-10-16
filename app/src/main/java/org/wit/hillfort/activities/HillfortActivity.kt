package org.wit.hillfort.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import kotlinx.android.synthetic.main.activity_hillfort.*
import org.jetbrains.anko.toast
import org.wit.hillfort.R
import org.wit.hillfort.main.MainApp
import org.wit.hillfort.models.HillfortModel

class HillfortActivity : AppCompatActivity(), AnkoLogger {

  var hillfort = HillfortModel()
  lateinit var app : MainApp

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_hillfort)
    app = application as MainApp

    btnAdd.setOnClickListener(){
      hillfort.title = hillfortTitle.text.toString()
      hillfort.description = description.text.toString()
      if (hillfort.title.isNotEmpty()) {
        app.hillforts.add(hillfort.copy())
        info("add Button Pressed: $hillfortTitle")
        app.hillforts.forEach{info("add Button Pressed: ${it.title}")}
        setResult(AppCompatActivity.RESULT_OK)
      }
      else {
        toast ("Please Enter a title")
      }
    }
  }
}
