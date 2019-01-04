package org.wit.hillfort.views.hillfortlist

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_hillfort.view.*
import org.wit.hillfort.R
import org.wit.hillfort.models.HillfortModel

interface HillfortListener {
  fun onHillfortClick(hillfort: HillfortModel)
}

class HillfortAdapter constructor(private var hillforts: List<HillfortModel>,
                                  private val listener: HillfortListener) : androidx.recyclerview.widget.RecyclerView.Adapter<HillfortAdapter.MainHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
    return MainHolder(LayoutInflater.from(parent?.context).inflate(R.layout.card_hillfort, parent, false))
  }

  override fun onBindViewHolder(holder: MainHolder, position: Int) {
    val hillfort = hillforts[holder.adapterPosition]
    holder.bind(hillfort, listener)
  }

  override fun getItemCount(): Int = hillforts.size

  class MainHolder constructor(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

    fun bind(hillfort: HillfortModel,  listener : HillfortListener) {
      itemView.hillfortTitle.text = hillfort.title
      itemView.description.text = hillfort.description
      itemView.hillfort_favorited.setChecked(hillfort.favorite)
      itemView.hillfort_visited.setChecked(hillfort.visited)

      if (hillfort.images.isEmpty()) {
        Picasso.get()
            .load(R.mipmap.ic_launcher_round)
            .into(itemView.imageIcon)
      } else {
        Picasso.get()
            .load(hillfort.images[0])// first image in arraylist
            .into(itemView.imageIcon)
      }

      itemView.setOnClickListener { listener.onHillfortClick(hillfort) }
    }
  }
}