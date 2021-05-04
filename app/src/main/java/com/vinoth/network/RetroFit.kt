package com.vinoth.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetroFit {

    val api: Api by lazy {
        Retrofit.Builder().baseUrl("https://api.flickr.com/services/rest/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)
    }
}