package com.zlove.movie.kotlin.app

import android.app.Application
import android.os.StrictMode
import com.zlove.movie.kotlin.details.DetailsComponent
import com.zlove.movie.kotlin.details.DetailsModule
import com.zlove.movie.kotlin.favorites.FavoritesModule
import com.zlove.movie.kotlin.listing.ListingComponent
import com.zlove.movie.kotlin.listing.ListingModule
import com.zlove.movie.kotlin.network.NetworkModule
import io.realm.Realm

class BaseApplication: Application() {

    private lateinit var appComponent: AppComponent
    private lateinit var detailsComponent: DetailsComponent
    private lateinit var listingComponent: ListingComponent

    override fun onCreate() {
        super.onCreate()
        StrictMode.enableDefaults()
        initRealm()
        appComponent = createAppComponent()
    }

    private fun initRealm() {
        Realm.init(this)
    }

    private fun createAppComponent(): AppComponent {
        return DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .networkModule(NetworkModule())
                .favoritesModule(FavoritesModule())
                .build()
    }


    fun createDetailsComponent(): DetailsComponent {
        detailsComponent = appComponent.plus(DetailsModule())
        return detailsComponent
    }

    fun releaseDetailsComponent() {

    }

    fun createListingComponent(): ListingComponent {
        listingComponent = appComponent.plus(ListingModule())
        return listingComponent
    }

    fun releaseListingComponent() {

    }

    fun getListingComponent(): ListingComponent {
        return listingComponent
    }
}