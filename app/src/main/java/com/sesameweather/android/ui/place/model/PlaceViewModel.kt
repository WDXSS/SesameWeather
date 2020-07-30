package com.sesameweather.android.ui.place.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.sesameweather.android.logic.Repository
import com.sesameweather.android.logic.model.Place

/**
 * @author zhouchao
 * @date 2020/7/30
 */
class PlaceViewModel : ViewModel() {
    private val TAG = "PlaceViewModel"
    private val searchLiveData = MutableLiveData<String>()
    val placeList = ArrayList<Place>()
    val placeLiveData = Transformations.switchMap(searchLiveData) { query ->
        Repository.searchPlaces(query)
    }

    fun searchPlaces(query: String) {
        searchLiveData.value = query
    }
}