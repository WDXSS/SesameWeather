package com.sesameweather.android.ui.weather.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.sesameweather.android.logic.Repository
import com.sesameweather.android.logic.model.Location

/**
 * ViewModel 是是专门用来存放与UI界面相关的数据，
 * 1. 也就是说 只要是在界面上能看到数据，它的相关变量都应该存放在 ViewModel 中，而不是 activity 中
 * 2. ViewModel 生命周期
 * 3.通过 构造函数 向 ViewModel 中传递参数，用到的是ViewModelProvider.Factory
 */
class WeatherViewModel : ViewModel() {

    private val locationLiveData = MutableLiveData<Location>()
    var locationLng = ""
    var locationLat = ""
    // placeName 更新数据中并没有用到，但是还是放在了ViewModel 中，遵循了 第一条，
    // ViewModel 是用来存储数据的，只要是ui上卡到数据,都应该存储在ViewModel 中
    var placeName = ""
    //Transformations map 函数 和 switchMap 函数0

    var weatherLiveData = Transformations.switchMap(locationLiveData) { location ->
        Repository.refreshWeather(location.lng, location.lat, placeName)

    }
    fun refreshWeather(lng: String, lat: String) {
        locationLiveData.value = Location(lng, lat)
    }
}