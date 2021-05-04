package com.vinoth.network

import com.vinoth.dataclass.PhotosSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("?method=flickr.photos.search&format=json&nojsoncallback=1&api_key=$FLICKR_KEY")
    fun searchImages(
        @Query(value = "text")
        searchTerm: String,
    ): Call<PhotosSearchResponse>

    companion object {
        const val FLICKR_KEY = "9799121d59a7be4b8f37dc7d76e2faba"
    }
}