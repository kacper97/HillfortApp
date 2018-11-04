package org.wit.hillfort.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_hillfort.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import org.wit.hillfort.main.MainApp
import org.wit.hillfort.models.UserModel
import org.wit.hillfort.R

class RegisterActivity:  AppCompatActivity(), AnkoLogger {

  lateinit var app : MainApp

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_register)
    app = application as MainApp
    toolbarRegister.title = getString(R.string.reg_title)
    setSupportActionBar(toolbarRegister)


    supportActionBar?.setDisplayHomeAsUpEnabled(true)

            button_register.setOnClickListener() {
          val email = regEmail.text.toString()
          val password = regPassword.text.toString()

          if(email == "" || password == ""){
            toast("Please enter an email and password!")
          } else {
            var newuser = UserModel()
            newuser.email = email
            newuser.password = password
            app.users.create(newuser)
            finish()
          }

        }

      }

      override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {

          R.id.reg_cancel -> {
            finish()
          }

        }
        return super.onOptionsItemSelected(item)
      }

      override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_reg, menu)
        return super.onCreateOptionsMenu(menu)
      }
}



