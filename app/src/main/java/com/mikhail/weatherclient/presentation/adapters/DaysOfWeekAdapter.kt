package com.mikhail.weatherclient.presentation.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import com.mikhail.weatherclient.R
import android.widget.TextView

class DaysOfWeekAdapter(
    private val daysOfWeek: Array<String?>,
    private val minTempOfWeek: Array<String?>,
    private val maxTempOfWeek: Array<String?>
) : RecyclerView.Adapter<DaysOfWeekAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.dayofweek_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setLabel(daysOfWeek[position])
        holder.setMinTemp(minTempOfWeek[position])
        holder.setMaxTemp(maxTempOfWeek[position])
    }

    override fun getItemCount(): Int {
        return daysOfWeek.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var label: TextView
        var mintemp: TextView
        var maxtemp: TextView
        fun setLabel(text: String?) {
            label.text = text
        }

        fun setMinTemp(text: String?) {
            mintemp.text = text
        }

        fun setMaxTemp(text: String?) {
            maxtemp.text = text
        }

        init {
            label = itemView.findViewById(R.id.label)
            mintemp = itemView.findViewById(R.id.mintemp)
            maxtemp = itemView.findViewById(R.id.maxtemp)
        }
    }
}