package com.example.marvellistapplication.view.hero

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.marvellistapplication.R
import com.example.marvellistapplication.api.model.SuperHero
import com.example.marvellistapplication.view_model.HeroDetailViewModel
import com.example.marvellistapplication.view_model.ViewModelFactory
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_hero_detail.*
import javax.inject.Inject

class HeroDetailActivity : AppCompatActivity() {
    @Inject
    lateinit var charactersIdVMFactory: ViewModelFactory<HeroDetailViewModel>

    private val heroDetailViewModel by lazy {
        ViewModelProviders.of(this, charactersIdVMFactory)[HeroDetailViewModel::class.java]
    }
    protected val ItemsObserver = Observer<List<SuperHero>>(::onItemsFetched)

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hero_detail)
        val Item = intent.extras!!.getString("superHero")
        var idSuperHero: String
        idSuperHero = Item.toString()

        if(idSuperHero != null) {
            heroDetailViewModel.data.observe(this, ItemsObserver)
            heroDetailViewModel.getHero(idSuperHero)
        }else{
            text_hero_description.text = "Ainda não existem informações cadastradas \n para este herói! =("
            progress_bar_detail.visibility = View.GONE
        }
    }

    private fun onItemsFetched(list: List<SuperHero>?) {
        if (list != null) {
            val thumbnail = list.first().thumbnail.path.split(":")

            Glide.with(this.applicationContext)
                .load("https:${thumbnail[1]}.${list.first().thumbnail.extension}")
                .into(image_hero_avatar)
            text_hero_name.text = list[0].name
            if (list[0].description != "") {
                text_hero_description.text = list[0].description
            } else {
                text_hero_description.text = getString(R.string.warning_no_hero)
            }
            progress_bar_detail.visibility = View.GONE
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    companion object {
        private const val ID_HERO = "ID_HERO"
        fun getStartIntent(context: Context, id: Long): Intent {
            return Intent(context, HeroDetailActivity::class.java).apply {
                putExtra(ID_HERO, id)
            }
        }
    }
}