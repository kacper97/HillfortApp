package org.wit.hillfort.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.*
import org.wit.hillfort.main.MainApp
import org.wit.hillfort.R

class LogInActivity:  AppCompatActivity(), AnkoLogger {
  lateinit var app : MainApp

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login)
    app = application as MainApp

    toolbarLogIn.title = getString(R.string.login_title)
    setSupportActionBar(toolbarLogIn)

    btnLogIn.setOnClickListener() {
      val email = logInEmail.text.toString()
      val password = logInPassword.text.toString()

      var user = app.users.findAll().find { it.email == email && it.password == password }
      if (user != null) {
        toast("Logged In")
        startActivityForResult(intentFor<HillfortListActivity>().putExtra("user_session", user), 0)
        finish()
      }
      else {
        toast("Incorrect log in credentials, try again please")
    }
  }
  }
  override fun onOptionsItemSelected(item: MenuItem?): Boolean{
  when (item?.itemId){
  R.id.reg ->{
    startActivity<RegisterActivity>()
  }
  }
  return super.onOptionsItemSelected(item)
}

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_login,menu)
    return super.onCreateOptionsMenu(menu)
  }
}

