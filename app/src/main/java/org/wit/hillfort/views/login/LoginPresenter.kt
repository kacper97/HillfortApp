package org.wit.hillfort.views.login

import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.toast
import org.wit.hillfort.models.HillfortFireStore
import org.wit.hillfort.views.BasePresenter
import org.wit.hillfort.views.BaseView
import org.wit.hillfort.views.VIEW

class LoginPresenter(view: BaseView) : BasePresenter(view), AnkoLogger {

  var auth: FirebaseAuth = FirebaseAuth.getInstance()

  fun doLogin(email: String, password: String) {
    val showProgress: Any = view?.showProgress()!!
    auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(view!!) { task ->
      if (task.isSuccessful) {
        view?.toast("log in as " + FirebaseAuth.getInstance().currentUser?.email.toString())
        view?.navigateTo(VIEW.LIST)
      } else {
        view?.toast("log In Failed: ${task.exception?.message}")
      }
      view?.hideProgress()
    }
  }

  fun doLogIn(email: String, password: String) {
    view?.showProgress()
    auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(view!!) { task ->
      if (task.isSuccessful) {
        if (app.firestore != null) {
          app.firestore!!.fetchHillforts {
            view?.hideProgress()
            view?.navigateTo(VIEW.LIST)
          }
        } else {
          view?.hideProgress()
          view?.navigateTo(VIEW.LIST)
        }
      } else {
        view?.hideProgress()
        view?.toast("Logging Up Failed: ${task.exception?.message}")
      }
    }
  }


}