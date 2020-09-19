package com.example.marvellistapplication

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton
import com.example.marvellistapplication.api.NetworkModule
import com.example.marvellistapplication.view.hero.HeroDetailActivity
import com.example.marvellistapplication.view.heroes.HeroesActivity
import dagger.android.ContributesAndroidInjector


@Module(includes = [
    NetworkModule::class,
    SchedulerModule::class
])
class AppModule

@Module
abstract class AndroidInjectorsModule {
    @ContributesAndroidInjector
    abstract fun heroesActivity(): HeroesActivity
    @ContributesAndroidInjector
    abstract fun heroDetailActivity(): HeroDetailActivity
}

@Singleton
@Component(modules = arrayOf(
    AndroidInjectionModule::class,
    AppModule::class,
    AndroidInjectorsModule::class

))
interface AppComponent : AndroidInjector<MyApp> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<MyApp>() {
        @BindsInstance
        abstract fun appContext(appContext: Context): Builder
    }
}