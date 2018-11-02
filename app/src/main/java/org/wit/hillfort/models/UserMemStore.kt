package org.wit.hillfort.models

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

var userId=0L
internal fun getUserId():Long{
  return userId++
}

class UserMemStore: UserStore, AnkoLogger{

  val users = ArrayList<UserModel>()

  override fun findAll(): List<UserModel> {
    return users
  }

  override fun create(hillfort: UserModel) {
    user.id = getid()
    users.add(user)
    logAll()
  }

  override fun delete(hillfort: UserModel) {
    users.remove(hillfort)
  }

  override fun update(hillfort: HillfortModel) {
    var foundUser: UserModel? = users.find { u -> u.id == user.id }
    if (foundUser != null) {

      foundUser.email = user.email
      foundUser.password = user.password
      logAll()
    }
  }


  fun logAll(){
    users.forEach{info("${it}")}
  }
}