package com.example.marvellistapplication

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.LiveData
import com.example.marvellistapplication.api.ApiClient
import com.example.marvellistapplication.api.MarvelApiApi
import com.example.marvellistapplication.api.model.SuperHero
import io.kotlintest.specs.AbstractFreeSpec
import io.kotlintest.specs.FreeSpec
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.schedulers.TestScheduler
import org.mockito.Mockito
import org.mockito.Mockito.mock

class HeroesViewModelTest: FreeSpec({
    class TestSchedulerProvider : SchedulerProvider {
        override fun mainThread(): Scheduler {
            return TestScheduler()
        }

        override fun io(): Scheduler {
            return TestScheduler()
        }
    }

    val agrApi = Mockito.mock(MarvelApiApi::class.java)
    val apiClient = ApiClient(agrApi)
    val testScheduler = TestScheduler()
    val testSchedulerProvider = TestSchedulerProvider()

    fun mockApiHeroesResponse(response: SuperHero? = null, error: Error? = null) {
        val mockResponse: Observable<SuperHero> = if (response != null) Observable.just(response) else Observable.error(error)
        Mockito.`when`(agrApi.characters(Mockito.anyString(),Mockito.anyString(),Mockito.anyLong()))
            .thenReturn(mockResponse.subscribeOn(testScheduler).observeOn(testScheduler))
    }



    fun <T> observe(result: LiveData<T>, observer: (T?) -> Unit): Unit {
        val lifecycle = LifecycleRegistry(mock(LifecycleOwner::class.java))
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)

        result.observe({ lifecycle }, { observer(it) })
    }


})

private fun Any.thenReturn(observeOn: Observable<SuperHero>?) {

}