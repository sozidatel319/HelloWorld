package com.mikhail.weatherclient.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mikhail.weatherclient.R
import com.mikhail.weatherclient.data.PartOfDay
import java.math.BigDecimal
import java.math.RoundingMode

class DailyAdapter(private val list: List<PartOfDay>) :
    RecyclerView.Adapter<DailyAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.day_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setLabel(
            if (position == 0) {
                "now"
            } else {
                list[position].time.toString()
            }
        )

        if (list.isEmpty()) return

        holder.setTemp(
            BigDecimal(
                list[position].temp.toString()
            ).setScale(0, RoundingMode.HALF_UP).toInt().toString()
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var label: TextView
        private var mintemp: TextView

        fun setLabel(text: String?) {
            label.text = text
        }

        fun setTemp(text: String?) {
            mintemp.text = text
        }

        init {
            label = itemView.findViewById(R.id.today)
            mintemp = itemView.findViewById(R.id.tempNow)
        }
    }


}
