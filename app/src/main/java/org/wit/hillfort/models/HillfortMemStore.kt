package org.wit.hillfort.models

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

private var lastId=0L

private fun getId():Long{
  return lastId++
}

class HillfortMemStore: HillfortStore, AnkoLogger{

  val hillforts = ArrayList<HillfortModel>()

  override fun findAll(): List<HillfortModel> {
    return hillforts
  }

  override fun create(hillfort: HillfortModel) {
    hillfort.id =getId()
    hillforts.add(hillfort)
    logAll()
  }

  override fun delete(hillfort: HillfortModel) {
    hillforts.remove(hillfort)
  }

  override fun update(hillfort: HillfortModel){
    var foundHillfort: HillfortModel?= hillforts.find {h->h.id == hillfort.id}
    if(foundHillfort != null){
      foundHillfort.title=hillfort.title
      foundHillfort.description= hillfort.description
      foundHillfort.images = hillfort.images
      foundHillfort.lat = hillfort.lat
      foundHillfort.lng = hillfort.lng
      foundHillfort.zoom = hillfort.zoom
      logAll()
    }
  }

  fun logAll(){
    hillforts.forEach{info("${it}")}
  }
}