package com.example.marvellistapplication.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.marvellistapplication.SchedulerProvider
import com.example.marvellistapplication.api.ApiClient
import com.example.marvellistapplication.api.MarvelApiApi
import com.example.marvellistapplication.api.model.SuperHero
import javax.inject.Inject


class HeroDetailViewModel @Inject constructor(val api: ApiClient, private val schedulers: SchedulerProvider) : ViewModel() {
    val _data = MutableLiveData<List<SuperHero>>()
    val data: LiveData<List<SuperHero>> = _data

    fun getHero(id: String) {
        api.charactersId(id, MarvelApiApi.API_KEY, MarvelApiApi.API_HASH).subscribeOn(schedulers.io())
            .observeOn(schedulers.mainThread())
            .subscribe({
                _data.value = it.data.results
            }, {
                it.message?.let { it1 -> Log.i("ERROR", it1) }
            })
    }
}