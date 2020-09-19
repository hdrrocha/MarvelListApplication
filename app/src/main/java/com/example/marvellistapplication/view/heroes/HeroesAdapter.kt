package com.example.marvellistapplication.view.heroes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marvellistapplication.R
import com.example.marvellistapplication.api.model.SuperHero
import kotlinx.android.synthetic.main.item_hero.view.*

class HeroesAdapter(var superHeroes: List<SuperHero>, val clickListener: ((SuperHero) -> Unit)?) :
    RecyclerView.Adapter<HeroesAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(superHero: SuperHero, clickListener: ((SuperHero) -> Unit)?) {
            itemView.textNameHero.text = superHero.name
            itemView.textAvailable.text = superHero.comics.available.toString()
            itemView.setOnClickListener {
                if (clickListener != null) {
                    clickListener(superHero)
                }
            }
            val thumbnail = superHero.thumbnail.path.split(":")

            Glide.with(itemView)
                .load("https:${thumbnail[1]}.${superHero.thumbnail.extension}")
                .into(itemView.imageHero)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_hero, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = superHeroes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(superHeroes[position], clickListener)
    }
}
