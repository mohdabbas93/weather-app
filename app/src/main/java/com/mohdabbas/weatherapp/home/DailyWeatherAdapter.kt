package com.mohdabbas.weatherapp.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mohdabbas.weatherapp.R
import com.mohdabbas.weatherapp.data.source.remote.dto.DailyWeatherDto
import kotlinx.android.synthetic.main.item_weather_daily.view.*

/**
 * Created by Mohammad Abbas
 * On: 1/2/22.
 */
class DailyWeatherAdapter(private var data: List<DailyWeatherDto>, private val context: Context) :
    RecyclerView.Adapter<DailyWeatherAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val dayTextView: TextView = view.dayTextView
        val weatherConditionTextView: TextView = view.weatherConditionTextView
        val minTempTextView: TextView = view.minTempTextView
        val maxTempTextView: TextView = view.maxTempTextView
        val weatherConditionIcon: ImageView = view.weatherConditionIcon
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_weather_daily, viewGroup, false)

        return ViewHolder(view)
    }


    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.dayTextView.text = "Sunday"

        viewHolder.weatherConditionTextView.text =
            data[position].weather.firstOrNull()?.weatherCondition ?: ""

        viewHolder.minTempTextView.text = viewHolder.itemView.context.getString(
            R.string.current_temp,
            data[position].temperature.minTemperature.toInt()
        )

        viewHolder.maxTempTextView.text =
            viewHolder.itemView.context.getString(
                R.string.current_temp,
                data[position].temperature.maxTemperature.toInt()
            )

        Glide.with(context)
            .load("http://openweathermap.org/img/wn/${data[position].weather.firstOrNull()?.icon ?: ""}@2x.png")
            .centerCrop()
            .into(viewHolder.weatherConditionIcon)
    }

    override fun getItemCount() = data.size

    fun updateData(newData: List<DailyWeatherDto>) {
        data = newData
        notifyDataSetChanged()
    }
}