package org.wit.hillfort.views.settings

import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.toast
import org.wit.hillfort.R
import org.wit.hillfort.models.UserModel
import org.wit.hillfort.views.BasePresenter
import org.wit.hillfort.views.BaseView

class SettingsPresenter(view: BaseView) : BasePresenter(view){

  var auth: FirebaseAuth = FirebaseAuth.getInstance()

  fun doSaveSettings(settings_email : String, settings_password : String){

    if (settings_email.isEmpty() || settings_password.isEmpty()){
      view?.toast(R.string.toast_changed_cant_be_blank)
    } else {
      view?.toast("Cant change these with firebase yet")
    }

    view?.setResult(AppCompatActivity.RESULT_OK)
    view?.finish()
  }

}