package com.example.imovie

import android.content.Context
import com.example.imovie.di.ApiModule
import com.example.imovie.di.AppListModule
import com.example.imovie.di.ProviderModule
import com.example.imovie.presentation.view.fragment.DetailsFragment
import com.example.imovie.presentation.view.fragment.HomeFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiModule::class, ProviderModule::class, AppListModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(activity: MainActivity)

    fun inject(fragment: HomeFragment)

    fun inject(fragment: DetailsFragment)
}