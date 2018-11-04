package org.wit.hillfort.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import org.wit.hillfort.R
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import kotlinx.android.synthetic.main.activity_hillfort_list.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivityForResult
import org.wit.hillfort.main.MainApp
import org.wit.hillfort.models.HillfortModel
import org.jetbrains.anko.toast
import org.wit.hillfort.models.UserModel

class HillfortListActivity : AppCompatActivity(), HillfortListener{

  lateinit var app: MainApp
  lateinit var user: UserModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_hillfort_list)
    app = application as MainApp

    toolbarMain.title= title
    setSupportActionBar(toolbarMain)

    val layoutManager=LinearLayoutManager(this)
    recyclerView.layoutManager=layoutManager
    recyclerView.adapter = HillfortAdapter(app.hillforts.findAll(),this)
    loadHillforts()

    if(intent.hasExtra("user_session")){
      user = intent.extras.getParcelable<UserModel>("user_session")
    }

  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_main, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when(item?.itemId){
      R.id.item_add -> startActivityForResult<HillfortActivity>(0)
      R.id.item_settings -> startActivityForResult(intentFor<SettingsActivity>().putExtra("user_session",user),0)
      R.id.item_logout -> {
        startActivityForResult<LogInActivity>(0)
        toast(R.string.toast_logout)
      }

    }
    return super.onOptionsItemSelected(item)
  }

  override fun onHillfortClick(hillfort: HillfortModel) {
    startActivityForResult(intentFor<HillfortActivity>().putExtra("hillfort_edit",hillfort),0)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int,data: Intent?){
    loadHillforts()
    super.onActivityResult(requestCode, resultCode, data)
  }

  private fun loadHillforts(){
    showHillforts(app.hillforts.findAll())
  }

  fun showHillforts(hillforts: List<HillfortModel>){
    recyclerView.adapter = HillfortAdapter(hillforts,this)
    recyclerView.adapter?.notifyDataSetChanged()
  }
}


