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
import com.mikhail.weatherclient.data.WeatherToWeek
import com.mikhail.weatherclient.presentation.adapters.DailyAdapter
import com.mikhail.weatherclient.presentation.adapters.DaysOfWeekAdapter
import com.mikhail.weatherclient.presentation.viewmodel.WeatherViewModel

class HomeFragment : Fragment() {
    private lateinit var weeklyRecyclerView: RecyclerView
    private lateinit var dailyRecyclerView: RecyclerView
    private lateinit var daysofweek: Array<String?>
    private lateinit var mintemptoweek: Array<String?>
    private lateinit var maxtempofweek: Array<String?>

    private lateinit var cityNameTextView: TextView
    private lateinit var pressureGroupLinearLayout: LinearLayout
    private lateinit var pressureTextView: TextView
    private lateinit var windGroupLinearLayout: LinearLayout
    private lateinit var windTextView: TextView
    private lateinit var temperatureOfDayTextView: TextView
    private lateinit var tempMeasureTextView: TextView
    private lateinit var cloudsTextView: TextView
    private lateinit var todayTextView: TextView

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
            ViewModelProvider(this)[WeatherViewModel::class.java]

        iniViews(view)

        weatherViewModel.isFahrenheitLiveData.observe(viewLifecycleOwner) {

            tempMeasureTextView.text = if (it) {
                Constants.FAHRENHEIT
            } else {
                Constants.CELSIUS
            }
        }

        weatherViewModel.needToShowAdditionalInfo.observe(viewLifecycleOwner) { show ->
            if (show) {
                windGroupLinearLayout.visibility = View.VISIBLE
                pressureGroupLinearLayout.visibility = View.VISIBLE
            } else {
                windGroupLinearLayout.visibility = View.GONE
                pressureGroupLinearLayout.visibility = View.GONE
            }
        }

        weatherViewModel.weatherLiveData.observe(viewLifecycleOwner) {
            updateWeather(it)
        }

        weatherViewModel.responseErrorLiveData.observe(viewLifecycleOwner) {
            showError(it)
        }

        initRecyclerView()

        parentFragmentManager.setFragmentResultListener(
            Constants.CITYCHANGER_CODE.toString(), this
        ) { requestKey, result ->

            if (requestKey == Constants.CITYCHANGER_CODE.toString()) {
                if (result.getString(Constants.CITY_NAME) != null &&
                    !result.getBoolean(
                        Constants.USE_LOCATION,
                        false
                    )
                ) {
                    result.getString(Constants.CITY_NAME)?.let {
                        weatherViewModel.getWeather(it)
                    }
                    cityNameTextView.text = result.getString(Constants.CITY_NAME)
                }

                if (result.getBoolean(Constants.INFO, false)) {

                    pressureGroupLinearLayout.visibility = View.VISIBLE
                    pressureTextView.text = result.getString(Constants.PRESSURE)
                    windGroupLinearLayout.visibility = View.VISIBLE
                    windTextView.text = result.getString(Constants.WIND_SPEED)

                } else {

                    pressureGroupLinearLayout.visibility = View.GONE
                    windGroupLinearLayout.visibility = View.GONE
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        todayTextView.text = days(1).first()
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

            cityNameTextView.text = model.city

            windTextView.text = model.firstDay.first().wind.toString()

            pressureTextView.text = model.firstDay.first().pressure.toString()

            temperatureOfDayTextView.text = model.firstDay.first().temp.toString()

            cloudsTextView.text = model.firstDay.first().clouds.toString()

            daysofweek = days(model.weatherWeekList.size)

            mintemptoweek = model.weatherWeekList.withIndex().map {
                it.value.minTemp.toString()
            }.toTypedArray()
            maxtempofweek = model.weatherWeekList.withIndex().map {
                it.value.maxTemp.toString()
            }.toTypedArray()

            weeklyRecyclerView.adapter = DaysOfWeekAdapter(daysofweek, mintemptoweek, maxtempofweek)

            dailyRecyclerView.adapter = DailyAdapter(model.firstDay)
        }
    }

    private fun showError(error: String) {
        cityNameTextView.text = error
    }

    private fun iniViews(view: View) {
        cityNameTextView = view.findViewById(R.id.city)

        pressureGroupLinearLayout = view.findViewById(R.id.pressuregroup)

        pressureTextView = view.findViewById(R.id.pressure)

        windGroupLinearLayout = view.findViewById(R.id.windgroup)

        windTextView = view.findViewById(R.id.wind)

        temperatureOfDayTextView = view.findViewById(R.id.temperatureOfDay)

        tempMeasureTextView = view.findViewById(R.id.tempMeasureTextView)

        todayTextView = view.findViewById(R.id.today)

        cloudsTextView = view.findViewById(R.id.clouds)

        weeklyRecyclerView = view.findViewById(R.id.weeklyRecyclerView)

        dailyRecyclerView = view.findViewById(R.id.oneDayRecyclerView)
    }
}