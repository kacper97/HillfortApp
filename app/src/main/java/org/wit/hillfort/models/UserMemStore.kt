package org.wit.hillfort.models

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import java.util.*

private var lastId=0L

private fun getId():Long {
  return Random().nextLong()
}

class UserMemStore: UserStore, AnkoLogger{

  val users = ArrayList<UserModel>()

  override fun findAll(): List<UserModel> {
    return users
  }

  override fun create(user: UserModel) {
    user.id = getId()
    users.add(user)
    logAll()
  }

  override fun delete(user: UserModel) {
    users.remove(user)
  }

  override fun update(user: UserModel) {
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