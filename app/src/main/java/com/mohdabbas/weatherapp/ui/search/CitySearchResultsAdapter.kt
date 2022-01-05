package com.mohdabbas.weatherapp.ui.search

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mohdabbas.weatherapp.R
import com.mohdabbas.weatherapp.data.source.remote.citysearch.CitySearchDto
import com.mohdabbas.weatherapp.ui.details.CityWeatherDetailsActivity
import com.mohdabbas.weatherapp.util.ViewVisibilityUtil.makeGone
import com.mohdabbas.weatherapp.util.ViewVisibilityUtil.makeVisible
import kotlinx.android.synthetic.main.item_city_search.view.*

/**
 * Created by Mohammad Abbas
 * On: 1/5/22.
 */
class CitySearchResultsAdapter(private var data: List<CitySearchDto>) :
    RecyclerView.Adapter<CitySearchResultsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cityNameTextView: TextView = view.cityNameTextView
        val countryNameTextView: TextView = view.countryNameTextView
        val divider: View = view.divider
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_city_search, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.cityNameTextView.text = data[position].name
        viewHolder.countryNameTextView.text = data[position].country.name
        viewHolder.itemView.apply {
            setOnClickListener {
                val intent = Intent(context, CityWeatherDetailsActivity::class.java)

                intent.putExtra("lat", data[position].coordinates.latitude)
                intent.putExtra("lng", data[position].coordinates.longitude)

                context.startActivity(intent)
            }
        }

        if (data.isNotEmpty() && position == data.size - 1) {
            makeGone(viewHolder.divider)
        } else {
            makeVisible(viewHolder.divider)
        }
    }

    override fun getItemCount() = data.size

    fun updateData(newData: List<CitySearchDto>) {
        data = newData
        notifyDataSetChanged()
    }
}