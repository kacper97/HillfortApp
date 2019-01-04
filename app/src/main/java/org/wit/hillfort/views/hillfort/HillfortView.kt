package org.wit.hillfort.views.hillfort

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.google.android.gms.maps.GoogleMap
import kotlinx.android.synthetic.main.activity_hillfort.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.toast
import org.wit.hillfort.R
import org.wit.hillfort.models.HillfortModel
import org.wit.hillfort.views.BaseView

class HillfortView : BaseView(), AnkoLogger, HillfortImageListener {

  lateinit var presenter : HillfortPresenter
  lateinit var map: GoogleMap

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_hillfort)

    init(toolbarAdd)

    presenter = initPresenter(HillfortPresenter(this)) as HillfortPresenter

    chooseImage.setOnClickListener {
      tempSave()
      presenter.doSelectImage()
    }

    mapView.onCreate(savedInstanceState)
    mapView.getMapAsync {
      map = it
      presenter.doConfigureMap(map)
      it.setOnMapClickListener {
        tempSave()
        presenter.doSetLocation()
      }
    }

    checkbox_visited.setOnClickListener {
      tempSave()
      presenter.doVisitedCheckbox(checkbox_visited.isChecked)
    }

    hillfortFavorite.setOnClickListener {
      tempSave()
      presenter.doFavoriteCheckbox(hillfortFavorite.isChecked)
    }

    val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this, androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL, false)
    hillfort_images_list_view.layoutManager = layoutManager
    presenter.loadHillfortImages()
  }

  override fun showHillfort(hillfort: HillfortModel) {
    hillfortTitle.setText(hillfort.title)
    description.setText(hillfort.description)
    hillfortNotes.setText(hillfort.notes)
    checkbox_visited.setChecked(hillfort.visited)
    hillfortFavorite.setChecked(hillfort.favorite)
    hillfortDate.setText(hillfort.date)
    hillfortRating.setRating(hillfort.rating)
    display_lat.setText("%.6f".format(hillfort.lat))
    display_lng.setText("%.6f".format(hillfort.lng))
    showHillfortImages(hillfort.images)
  }

  override fun showHillfortImages (images: List<String>) {
    hillfort_images_list_view.adapter = HillfortImageAdapter(images, this)
    hillfort_images_list_view.adapter?.notifyDataSetChanged()
  }

  override fun onHillfortImageClick(image: String) {
    toast("Image chosen!")
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu_hillfort, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {

      R.id.item_save -> {
        var title = hillfortTitle.text.toString()
        var description = description.text.toString()
        var notes = hillfortNotes.text.toString()
        var visited = checkbox_visited.isChecked
        var favorite = hillfortFavorite.isChecked
        var date = hillfortDate.text.toString()
        var rating = hillfortRating.rating

        if (title.isEmpty()) {
          toast(R.string.toast_enterTitlePlease)
        } else {
          presenter.doAddOrSave(title, description, visited,notes, date,favorite , rating)
        }
        setResult(AppCompatActivity.RESULT_OK)
        finish()
      }

      R.id.item_cancel -> { presenter.doCancel() }
      R.id.item_delete -> { presenter.doDelete() }

    }
    return super.onOptionsItemSelected(item)
  }

  // Temporarily save hillfort in presenter hillfort object
  fun tempSave(){
    var title = hillfortTitle.text.toString()
    var description = description.text.toString()
    var notes = hillfortNotes.text.toString()
    var visited = checkbox_visited.isChecked
    var favorite = hillfortFavorite.isChecked()
    var date = hillfortDate.text.toString()
    var rating = hillfortRating.rating
    presenter.doTempSave(title, description, notes, visited, favorite, date, rating)
  }

}