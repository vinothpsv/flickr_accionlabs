package com.vinoth.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vinoth.dataclass.Photo
import com.vinoth.dataclass.PhotosSearchResponse
import com.vinoth.network.Api
import com.vinoth.network.RetroFit
import retrofit2.Call
import retrofit2.Callback;
import retrofit2.Response

class PhotoViewModel : ViewModel() {

    private var listMutableLiveData: MutableLiveData<List<Photo>>? = null
    var photosSearchResponse: PhotosSearchResponse? = null

    fun getLiveData(): MutableLiveData<List<Photo>>? {
        if (listMutableLiveData == null) {
            listMutableLiveData = MutableLiveData<List<Photo>>()
            /*loadPhoto("Nature")*/
        }
        return listMutableLiveData
    }

    fun loadPhoto(parameter: String = "Nature", page: Int = 1) {

        val api: Api = RetroFit.api

        val photoResponse = api.searchImages(parameter, page)
        photoResponse.enqueue(object : Callback<PhotosSearchResponse?> {

            override fun onResponse(call: Call<PhotosSearchResponse?>, response: Response<PhotosSearchResponse?>) {
                photosSearchResponse = response.body()
                val photoList = response.body()?.photos?.photo?.map {
                    Photo(id = it.id,
                        url = "https://farm${it.farm}.staticflickr.com/${it.server}/${it.id}_${it.secret}.jpg",
                        title = it.title)
                }
                listMutableLiveData?.postValue(photoList)
            }

            override fun onFailure(call: Call<PhotosSearchResponse?>, t: Throwable) {

            }
        })
    }
}