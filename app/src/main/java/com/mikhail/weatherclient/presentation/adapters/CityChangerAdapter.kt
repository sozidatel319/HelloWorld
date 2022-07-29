package com.mikhail.weatherclient.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mikhail.weatherclient.R

class CityChangerAdapter(
    private val cityList: List<String>,
    private val onItemClicked: ((String) -> Unit)?
) : RecyclerView.Adapter<CityChangerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.city_item, parent, false)
        return ViewHolder(view, onItemClicked)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setCityName(cityList[position])
    }

    override fun getItemCount(): Int {
        return cityList.size
    }

    class ViewHolder(itemView: View, private val onItemClicked: ((String) -> Unit)?) : RecyclerView.ViewHolder(itemView) {
        private var cityNameTextView: TextView

        init {
            cityNameTextView = itemView.findViewById(R.id.cityName)
        }

        fun setCityName(text: String) {
            cityNameTextView.text = text
            cityNameTextView.setOnClickListener {
                onItemClicked?.invoke(text)
            }
        }


    }
}