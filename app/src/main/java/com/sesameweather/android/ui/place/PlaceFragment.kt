package com.sesameweather.android.ui.place

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextSwitcher
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sesameweather.android.R
import com.sesameweather.android.ui.place.model.PlaceViewModel
import kotlinx.android.synthetic.main.fragment_place.*

/**
 * @author zhouchao
 * @date 2020/7/27
 */
class PlaceFragment : Fragment() {

    private var adapter: PlaceAdapter? = null
    private val viewModel by lazy {
        //        ViewModelProviders.of(this).get(PlaceViewModel::class.java)
        ViewModelProvider(this).get(PlaceViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_place, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = PlaceAdapter(this, viewModel.placeList)
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        //addTextChangedListener{} 1.扩展函数，2.内联函数，3.取消内联，4.Unit 相当于 void，
        searchPlaceEdit.addTextChangedListener { editable ->

            val content = editable.toString()
            if (content.isNotEmpty()) {
                viewModel.searchPlaces(content)
            } else {
                recyclerView.visibility = View.GONE
                bgImageView.visibility = View.VISIBLE
                viewModel.placeList.clear()
                adapter!!.notifyDataSetChanged()
            }
        }
//        searchPlaceEdit.addTextChangedListener(object :  TextWatcher {
//            override fun afterTextChanged(s: Editable?) {
//            }
//
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//            }
//
//        })

        viewModel.placeLiveData.observe(viewLifecycleOwner, Observer {result->
            val place = result.getOrNull()
            if (place != null) {
                recyclerView.visibility = View.VISIBLE
                bgImageView.visibility = View.GONE
                viewModel.placeList.clear()
                viewModel.placeList.addAll(place)
                adapter!!.notifyDataSetChanged()
            }else{
                Toast.makeText(activity, "未能查询到任何地点", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        })
    }
}


