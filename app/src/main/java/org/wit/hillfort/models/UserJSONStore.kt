package org.wit.hillfort.models

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.AnkoLogger
import org.wit.hillfort.helpers.*
import java.util.*

private val JSON_FILE = "users.json"
private val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
private val listType = object : TypeToken<java.util.ArrayList<UserModel>>(){}.type

private fun generateRandomId(): Long{
  return Random().nextLong()
}

class UserJSONStore : UserStore, AnkoLogger{

  val context : Context
  var users = mutableListOf<UserModel>()

  constructor (context: Context){
    this.context = context
    if(exists(context, JSON_FILE)){
      deserialize()
    }
  }

  override fun findAll(): MutableList<UserModel> {
    return users
  }

  override fun create(user: UserModel) {
    user.id = generateRandomId()
    users.add (user)
    serialize()
  }

  override fun delete(user: UserModel) {
    users.remove(user)
    serialize()
  }

  override fun update(user: UserModel) {
    var foundUser: UserModel ?= users.find {u-> u.id == user.id}
    if( foundUser != null){
      foundUser.email = user.email
      foundUser.password = user.password

      serialize()
    }
  }

  private fun serialize(){
    val jsonString = gsonBuilder.toJson(users, listType)
    write(context, JSON_FILE, jsonString)
  }

  private fun deserialize(){
    val jsonString = read(context, JSON_FILE)
    users = Gson().fromJson(jsonString, listType)
  }
}