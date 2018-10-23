package org.wit.hillfort.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import kotlinx.android.synthetic.main.activity_hillfort.*
import org.jetbrains.anko.toast
import org.wit.hillfort.R
import org.wit.hillfort.helpers.readImage
import org.wit.hillfort.helpers.readImageFromPath
import org.wit.hillfort.helpers.showImagePicker
import org.wit.hillfort.main.MainApp
import org.wit.hillfort.models.HillfortModel

class HillfortActivity : AppCompatActivity(), AnkoLogger {

  var hillfort = HillfortModel()
  val IMAGE_REQUEST = 1
  lateinit var app: MainApp

  override fun onCreate(savedInstanceState: Bundle?) {
    var edit = false
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_hillfort)
    app = application as MainApp

    toolbarAdd.title = title
    setSupportActionBar(toolbarAdd)
    if (intent.hasExtra("hillfort_edit")) {
      edit = true
      hillfort = intent.extras.getParcelable<HillfortModel>("hillfort_edit")
      hillfortTitle.setText(hillfort.title)
      description.setText(hillfort.description)
      btnAdd.setText(R.string.save_hillfort)
      hillfortImage.setImageBitmap(readImageFromPath(this,hillfort.image))
      if(hillfort.image != null){
        chooseImage.setText(R.string.change_hillfort_image)
      }
    }

    btnAdd.setOnClickListener() {
      hillfort.title = hillfortTitle.text.toString()
      hillfort.description = description.text.toString()
      if (hillfort.title.isEmpty()) {
        toast(R.string.enter_hillfort_title)
      }else{
        if (edit) {
          app.hillforts.update(hillfort.copy())
        } else {
          app.hillforts.create(hillfort.copy())
        }
      }
      info("add Button Pressed: $hillfortTitle")
      setResult(AppCompatActivity.RESULT_OK)
      finish()
    }

    chooseImage.setOnClickListener {
      showImagePicker(this,IMAGE_REQUEST)
    }

    hillfortLocation.setOnClickListener {
      info("Set Location Pressed")
    }
  }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
      menuInflater.inflate(R.menu.menu_hillfort, menu)
      return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
      when (item?.itemId) {
        R.id.item_cancel -> {
          finish()
        }
      }
      return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int,resultCode: Int,data: Intent?){
    super.onActivityResult(requestCode, resultCode, data)
      when(requestCode){
        IMAGE_REQUEST->{
          if(data!= null){
            hillfort.image=data.getData().toString()
            hillfortImage.setImageBitmap(readImage(this,resultCode,data))
            chooseImage.setText(R.string.change_hillfort_image)
          }
        }
      }
  }
}