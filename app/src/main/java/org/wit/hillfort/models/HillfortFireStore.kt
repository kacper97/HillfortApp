package org.wit.hillfort.models

import android.content.Context
import android.graphics.Bitmap
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import org.jetbrains.anko.AnkoLogger
import org.wit.hillfort.helpers.readImageFromPath
import java.io.ByteArrayOutputStream
import java.io.File

class HillfortFireStore(val context: Context) : AnkoLogger {

  val hillforts = ArrayList<HillfortModel>()
  lateinit var userId: String
  lateinit var db: DatabaseReference
  lateinit var st: StorageReference

  fun findAll(): List<HillfortModel> {
    return hillforts
  }

  fun findById(id: Long): HillfortModel? {
    val foundHillfort: HillfortModel? = hillforts.find { p -> p.id == id }
    return foundHillfort
  }

  fun create(hillfort: HillfortModel) {
    val key = db.child("users").child(userId).child("hillforts").push().key
    hillfort.fbId = key!!
    hillforts.add(hillfort)
    db.child("users").child(userId).child("hillforts").child(key).setValue(hillfort)
    updateImage(hillfort)
  }

  fun update(hillfort: HillfortModel) {
    var foundHillfort: HillfortModel? = hillforts.find { p -> p.fbId == hillfort.fbId }
    if (foundHillfort != null) {
      foundHillfort.title = hillfort.title
      foundHillfort.description = hillfort.description
      foundHillfort.images = hillfort.images
      foundHillfort.lat = hillfort.lat
      foundHillfort.lng = hillfort.lng
      foundHillfort.zoom = hillfort.zoom

      db.child("users").child(userId).child("hillforts").child(hillfort.fbId).setValue(hillfort)
      if ((hillfort.images.size) > 0) {
        updateImage(hillfort)
      }
    }
    db.child("users").child(userId).child("hillforts").child(hillfort.fbId).setValue(hillfort)
  }

  fun delete(hillfort: HillfortModel) {
    db.child("users").child(userId).child("hillforts").child(hillfort.fbId).removeValue()
    hillforts.remove(hillfort)
  }

  fun clear() {
    hillforts.clear()
  }

  fun updateImage(hillfort: HillfortModel) {
    if (hillfort.images.size != 0) {

      for((index, image) in hillfort.images.withIndex()){
        val fileName = File(image)
        val imageName = fileName.getName()

        var imageRef = st.child(userId + '/' + imageName)
        val baos = ByteArrayOutputStream()
        val bitmap = readImageFromPath(context, image)

        bitmap?.let {
          bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
          val data = baos.toByteArray()
          val uploadTask = imageRef.putBytes(data)
          uploadTask.addOnFailureListener {
            println(it.message)
          }.addOnSuccessListener { taskSnapshot ->
            taskSnapshot.metadata!!.reference!!.downloadUrl.addOnSuccessListener {
              hillfort.images[index] = it.toString()
              db.child("users").child(userId).child("hillforts").child(hillfort.fbId).setValue(hillfort)
            }
          }
        }
      }
    }
  }

  fun fetchHillforts(hillfortsReady: () -> Unit) {
    val valueEventListener = object : ValueEventListener {
      override fun onCancelled(error: DatabaseError) {
      }
      override fun onDataChange(dataSnapshot: DataSnapshot) {
        dataSnapshot.children.mapNotNullTo(hillforts) { it.getValue<HillfortModel>(HillfortModel::class.java) }
        hillfortsReady()
      }
    }
    userId = FirebaseAuth.getInstance().currentUser!!.uid
    db = FirebaseDatabase.getInstance().reference
    st = FirebaseStorage.getInstance().reference
    hillforts.clear()
    db.child("users").child(userId).child("hillforts").addListenerForSingleValueEvent(valueEventListener)
  }

}