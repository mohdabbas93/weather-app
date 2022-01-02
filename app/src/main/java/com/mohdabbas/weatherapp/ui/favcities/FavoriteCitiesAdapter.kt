package com.mohdabbas.weatherapp.ui.favcities

import android.content.Context
import android.content.Intent
import android.location.Geocoder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mohdabbas.weatherapp.R
import com.mohdabbas.weatherapp.data.source.local.FavoriteCity
import com.mohdabbas.weatherapp.ui.details.CityWeatherDetailsActivity
import com.mohdabbas.weatherapp.util.TemperatureConverterUtil.convertTemperature
import kotlinx.android.synthetic.main.item_fav_city.view.*
import java.io.IOException
import java.util.*

/**
 * Created by Mohammad Abbas
 * On: 1/2/22.
 */
class FavoriteCitiesAdapter(private var data: List<FavoriteCity>, private val isCelsius: Boolean) :
    RecyclerView.Adapter<FavoriteCitiesAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val weatherConditionIcon: ImageView = view.weatherConditionIcon
        val currentTempTextView: TextView = view.currentTempTextView
        val weatherConditionTextView: TextView = view.weatherConditionTextView
        val cityNameTextView: TextView = view.cityNameTextView
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_fav_city, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        Glide.with(viewHolder.itemView.context)
            .load("http://openweathermap.org/img/wn/${data[position].weatherConditionIcon}@2x.png")
            .centerCrop()
            .into(viewHolder.weatherConditionIcon)

        viewHolder.weatherConditionTextView.text = data[position].weatherCondition

        viewHolder.currentTempTextView.text = viewHolder.itemView.context.getString(
            R.string.current_temp,
            data[position].currentTemperature.convertTemperature(isCelsius).toInt()
        )

        viewHolder.cityNameTextView.text = getCityNameAndCountry(
            viewHolder.itemView.context,
            data[position].lat,
            data[position].lng
        )

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
    ): String {
        try {
            val addresses = Geocoder(context, Locale.getDefault()).getFromLocation(lat, lng, 1)
            return "${addresses[0].adminArea}, ${addresses[0].countryName}"
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return ""
    }

    fun updateData(newData: List<FavoriteCity>) {
        data = newData
        notifyDataSetChanged()
    }
}