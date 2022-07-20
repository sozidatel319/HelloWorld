package com.mikhail.weatherclient.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.mikhail.weatherclient.*
import com.mikhail.weatherclient.Utils.days
import com.mikhail.weatherclient.WeatherProvider.Companion.instance
import com.mikhail.weatherclient.data.WeatherToWeek
import com.mikhail.weatherclient.database.DataReader
import com.mikhail.weatherclient.database.DataSource
import com.mikhail.weatherclient.presentation.adapters.DailyAdapter
import com.mikhail.weatherclient.presentation.adapters.DaysOfWeekAdapter
import com.mikhail.weatherclient.presentation.viewmodel.WeatherViewModel

class HomeFragment : Fragment() {
    private lateinit var weeklyRecyclerView: RecyclerView
    private lateinit var dailyRecyclerView: RecyclerView
    private lateinit var daysofweek: Array<String?>
    private lateinit var mintemptoweek: Array<String?>
    private lateinit var maxtempofweek: Array<String?>

    private lateinit var cityName: TextView
    private lateinit var pressuregroup: LinearLayout
    private lateinit var pressure: TextView
    private lateinit var windgroup: LinearLayout
    private lateinit var wind: TextView
    private lateinit var temperature_of_day: TextView
    private lateinit var tempmeasure: TextView
    private lateinit var clouds: TextView
    private lateinit var today: TextView
    private val dataSource: DataSource? = null
    private val dataReader: DataReader? = null
    var cel = true
    private val city_in_db = false

    private lateinit var weatherViewModel: WeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        weatherViewModel =
            ViewModelProvider(this).get(WeatherViewModel::class.java)

        cityName = view.findViewById(R.id.city)
        pressuregroup = view.findViewById(R.id.pressuregroup)
        pressure = view.findViewById(R.id.pressure)
        windgroup = view.findViewById(R.id.windgroup)
        wind = view.findViewById(R.id.wind)
        temperature_of_day = view.findViewById(R.id.temperatureOfDay)
        tempmeasure = view.findViewById(R.id.tempmeasure)

        if (!PreferenceWrapper.getPreference(activity)
                .getBoolean(Constants.UNIT_OF_MEASURE_FAHRENHEIT, false)
        ) {
            tempmeasure.text = Constants.CELSIUS
        } else {
            tempmeasure.text = Constants.FAHRENHEIT
        }

        today = view.findViewById(R.id.today)
        clouds = view.findViewById(R.id.clouds)
        //cityName.text = City_changerPresenter.getInstance().cityName

        if (PreferenceWrapper.getPreference(activity).getBoolean(Constants.INFO, false)) {
            windgroup.visibility = View.VISIBLE
            pressuregroup.visibility = View.VISIBLE
        } else {
            windgroup.visibility = View.GONE
            pressuregroup.visibility = View.GONE
        }

        weeklyRecyclerView = view.findViewById(R.id.weeklyRecyclerView)
        dailyRecyclerView = view.findViewById(R.id.oneDayRecyclerView)

        weatherViewModel.weatherLiveData.observe(this) {
            updateWeather(it)
        }

        initRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        today.text = days(1).first()
    }

    private fun initRecyclerView() {
        weeklyRecyclerView.setHasFixedSize(true)
        daysofweek = days(4)
        mintemptoweek = arrayOf("--", "--", "--", "--", "--")
        maxtempofweek = mintemptoweek
        weeklyRecyclerView.adapter = DaysOfWeekAdapter(daysofweek, mintemptoweek, maxtempofweek)

        dailyRecyclerView.setHasFixedSize(true)
        dailyRecyclerView.adapter = DailyAdapter(listOf())

    }

    private fun updateWeather(model: WeatherToWeek?) {
        if (model != null) {
            // if (city_in_db) {
            //  } else {
            cityName.text = model.city
            wind.text = model.firstDay.first().wind.toString()
            pressure.text = model.firstDay.first().pressure.toString()

            if (!SettingsPresenter.getInstance().unitofmeasure) {
                temperature_of_day.text = model.firstDay.first().temp.toString()
            } else {
                temperature_of_day.text = instance!!.tempInFahrenheit(0)
            }
            clouds.text = model.firstDay.first().clouds.toString()

            val s = instance

            daysofweek = days(model.weatherWeekList.size)

            if (!SettingsPresenter.getInstance().unitofmeasure) {
                mintemptoweek = model.weatherWeekList.withIndex().map {
                    it.value.minTemp.toString()
                }.toTypedArray()
                maxtempofweek = model.weatherWeekList.withIndex().map {
                    it.value.maxTemp.toString()
                }.toTypedArray()
            } else {
                mintemptoweek = instance!!.tempMinToWeekInFahrenheit()
                maxtempofweek = instance!!.tempMaxToWeekInFahrenheit()
            }
            weeklyRecyclerView.adapter = DaysOfWeekAdapter(daysofweek, mintemptoweek, maxtempofweek)

            dailyRecyclerView.adapter = DailyAdapter(model.firstDay)
        }
    }
}