package org.wit.hillfort.views.login

import android.os.Bundle
import android.provider.Settings.Global.getString
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.*
import org.wit.hillfort.R
import org.wit.hillfort.views.BaseView
import org.wit.hillfort.views.VIEW


class LoginView : BaseView(), AnkoLogger {

  lateinit var presenter : LoginPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login)

    presenter = initPresenter (LoginPresenter(this)) as LoginPresenter

    toolbarLogIn.title = getString(R.string.login_title)
    setSupportActionBar(toolbarLogIn)

    progressBar.visibility = View.GONE

    btnLogIn.setOnClickListener() {
      val email = logInEmail.text.toString()
      val password = logInPassword.text.toString()
      presenter.doLogIn(email, password)
    }
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {

      R.id.reg -> {
        navigateTo(VIEW.REGISTER)
      }

    }
    return super.onOptionsItemSelected(item)
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_login, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun showProgress() {
    progressBar.visibility = View.VISIBLE
  }

  override fun hideProgress() {
    progressBar.visibility = View.GONE
  }
}