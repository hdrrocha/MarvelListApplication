package com.example.marvellistapplication.view.heroes

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marvellistapplication.R
import com.example.marvellistapplication.api.model.SuperHero
import com.example.marvellistapplication.view_model.HeroesViewModel
import com.example.marvellistapplication.view_model.ViewModelFactory
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_heroes.*
import javax.inject.Inject

class HeroesActivity : AppCompatActivity() {
    @Inject
    lateinit var charactersVMFactory: ViewModelFactory<HeroesViewModel>
    private val charactersViewModel by lazy {
        ViewModelProviders.of(this, charactersVMFactory)[HeroesViewModel::class.java]
    }

    protected val ItemsObserver = Observer<List<SuperHero>>(::onItemsFetched)

    var listHeroesView: MutableList<SuperHero> = mutableListOf()
    var totalItemCount: Int = 0
    var visibleItemCount: Int = 0
    var pastVisibleItemCount: Int = 0
    var loading: Boolean = false
    var page: Long = 1L

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_heroes)

        charactersViewModel.data.observe(this, ItemsObserver)
        charactersViewModel.getHeroes(page)
    }

    private fun onItemsFetched(list: List<SuperHero>?) {
        if (list != null) {
            loading = true
            setUpdateAdapter(list)
        }
    }

    private fun setUpdateAdapter(heroes: List<SuperHero>) {
        var currentPosition = 0
        if (listHeroesView.size == 0) {
            listHeroesView = heroes as MutableList<SuperHero>
            progress_bar.visibility = View.GONE

        } else {
            currentPosition =
                (recycler_heroes.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
            listHeroesView.addAll(heroes)
            progress_bar.visibility = View.GONE
        }

        with(recycler_heroes, {
            layoutManager = LinearLayoutManager(this@HeroesActivity, RecyclerView.VERTICAL, false)
            setHasFixedSize(true)
            adapter = HeroesAdapter(listHeroesView) { hero ->
                //TODO
            }
            adapter!!.notifyDataSetChanged()
            scrollToPosition(currentPosition)
            progress_bar.visibility = View.GONE
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (dy > 0) {
                        visibleItemCount = (layoutManager as LinearLayoutManager).childCount
                        totalItemCount = (layoutManager as LinearLayoutManager).itemCount
                        pastVisibleItemCount =
                            (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                        if (loading) {
                            if ((visibleItemCount + pastVisibleItemCount) >= totalItemCount) {
                                progress_bar.visibility = View.VISIBLE
                                loading = false
                                page++
                                var offSet: Long
                                offSet = page++
                                charactersViewModel.getHeroes(offSet * 10L)
                            }
                        }
                    }
                }
            })
        })
    }

}