package org.wit.hillfort.views.hillfortlist

import org.jetbrains.anko.AnkoLogger
import org.wit.hillfort.models.HillfortModel
import org.wit.hillfort.views.BasePresenter
import org.wit.hillfort.views.BaseView
import org.wit.hillfort.views.VIEW

class HillfortListPresenter(view: BaseView) : BasePresenter(view), AnkoLogger {

  var favorite = false

  fun doAddHillfort(){
    view?.navigateTo(VIEW.HILLFORT, 0)
  }

  fun doEditHillfort(hillfort : HillfortModel){
    view?.navigateTo(VIEW.HILLFORT, 0, "hillfort_edit", hillfort)
  }

  fun doSettings(){
    view?.navigateTo(VIEW.SETTINGS, 0)
  }

  fun doLogout() {
    view?.navigateTo(VIEW.LOGIN)
  }

  fun doShowHillfortMap() {
    view?.navigateTo(VIEW.MAPS)
  }

  fun loadHillforts() {
    if (favorite){
      view?.showHillforts(app.hillforts.filter { hf -> hf.favorite })
    } else {
      view?.showHillforts(app.hillforts)
    }
  }

  fun filterHillforts(search: String){
    var filteredHillforts = app.hillforts.filter { hf -> hf.title.contains(search, ignoreCase = true)}
    view?.showHillforts(filteredHillforts)
  }

}