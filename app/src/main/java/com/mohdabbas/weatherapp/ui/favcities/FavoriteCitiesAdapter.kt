package com.mohdabbas.weatherapp.ui.favcities

import android.content.Context
import android.content.Intent
import android.location.Geocoder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mohdabbas.weatherapp.R
import com.mohdabbas.weatherapp.data.source.local.CityWeather
import com.mohdabbas.weatherapp.ui.details.CityWeatherDetailsActivity
import kotlinx.android.synthetic.main.item_fav_city.view.*
import java.io.IOException
import java.util.*

/**
 * Created by Mohammad Abbas
 * On: 1/2/22.
 */
class FavoriteCitiesAdapter(private var data: List<CityWeather>) :
    RecyclerView.Adapter<FavoriteCitiesAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cityNameTextView: TextView = view.cityNameTextView
        val countryNameTextView: TextView = view.countryNameTextView
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_fav_city, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val (cityName, countryName) = getCityNameAndCountry(
            viewHolder.itemView.context,
            data[position].lat,
            data[position].lng
        )

        viewHolder.cityNameTextView.text = cityName
        viewHolder.countryNameTextView.text = countryName

        viewHolder.itemView.apply {
            setOnClickListener {
                val intent = Intent(context, CityWeatherDetailsActivity::class.java)

                intent.putExtra("lat", data[position].lat)
                intent.putExtra("lng", data[position].lng)

                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount() = data.size

    private fun getCityNameAndCountry(
        context: Context,
        lat: Double,
        lng: Double
    ): Pair<String, String> {
        try {
            val addresses = Geocoder(context, Locale.getDefault()).getFromLocation(lat, lng, 1)
            return addresses[0].adminArea to addresses[0].countryName
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return "" to ""
    }

    fun updateData(newData: List<CityWeather>) {
        data = newData
        notifyDataSetChanged()
    }
}