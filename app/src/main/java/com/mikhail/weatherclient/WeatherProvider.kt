package com.mikhail.weatherclient

import android.os.Handler
import com.mikhail.weatherclient.Constants.MAXIMUM_DAYS_IN_LIST
import com.mikhail.weatherclient.Utils.compareDates
//import com.mikhail.weatherclient.Utils.mapToWeatherDayList
import com.mikhail.weatherclient.data.WeatherDay
import com.mikhail.weatherclient.model.WeatherModel
import com.mikhail.weatherclient.presentation.City_changerPresenter
import com.mikhail.weatherclient.data.network.weatherapi.RetrofitInstance
import com.mikhail.weatherclient.data.network.weatherapi.WeatherApiService
import kotlinx.coroutines.*
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.Exception
import kotlin.collections.ArrayList

class WeatherProvider private constructor() {
    private val listeners: MutableSet<WeatherProviderListener>
    private var timer: Timer? = null
    private val handler = Handler()
    private val hours = SimpleDateFormat("HH", Locale.ENGLISH)
    private val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
    var weatherModel: WeatherModel? = null
        private set
    private var nexttime: ArrayList<String>? = null
    private var isWeatherByCoords = false
    lateinit var weatherApi: WeatherApiService

    init {
        listeners = HashSet()
        startData()
    }

    /*private WeatherModel getWeather(String cityname) {
        String urlString = "http://api.openweathermap.org/data/2.5/forecast?q=" + cityname + ",RU&appid=0507febdbdf6a636ec6bdcdfe0b909fc";
        WeatherModel model = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(1000);
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                final String result = in.lines().collect(Collectors.joining("\n"));
                Gson gson = new Gson();
                model = gson.fromJson(result, WeatherModel.class);
                City_changerPresenter.getInstance().setMistake(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        return model;
    }*/  var isFirstDownload = true
    fun setWeatherByCoords(weatherByCoords: Boolean) {
        isWeatherByCoords = weatherByCoords
    }

    fun isWeatherByCoords(): Boolean {
        return isWeatherByCoords
    }

    /*  internal interface OpenWeather {

      }

      internal interface OpenWeatherByCoords {

      }*/

    fun addListener(listener: WeatherProviderListener) {
        listeners.add(listener)
    }

    fun removeListener(listener: WeatherProviderListener?) {
        listeners.remove(listener)
    }

    @kotlin.jvm.Throws(Exception::class)
    private fun getWeather(cityname: String): WeatherModel {
        return runBlocking {
            try {
                weatherApi.getWeather(
                    cityname,
                    "metric",
                    "0507febdbdf6a636ec6bdcdfe0b909fc"
                )
            } catch (exception: Exception) {
                isFirstDownload = true
                throw exception
            }
        }
    }

    private fun getNextTime(model: WeatherModel?): ArrayList<String> {
        val result = ArrayList<String>()
        try {
            for (i in model!!.list!!.indices) {
                val d = simpleDateFormat.parse(model.list!![i].dtTxt)
                val a = Integer.valueOf(hours.format(Objects.requireNonNull(d)))
                result.add(a.toString())
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return result
    }

    val days: Array<String?>
        get() {
            val result = arrayOfNulls<String>(4)
            val c = Calendar.getInstance()
            for (i in 0..4) {
                result[i] =
                    c.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault())
                c.add(Calendar.DAY_OF_WEEK, +1)
            }
            return result
        }

    private fun startData() {
        timer = Timer()
        timer!!.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                try {
                    if (isFirstDownload) {
                        //if (!isWeatherByCoords) {
                        weatherApi =
                            RetrofitInstance.getInstance().create(WeatherApiService::class.java)
                        /*retrofit.create(
                            OpenWeather::class.java
                        )*/
                        weatherModel = getWeather(City_changerPresenter.getInstance().cityName)
                        // } else{
                        // weatherByCoords = retrofit.create(OpenWeatherByCoords.class);
                        // weatherModel = getWeatherByCoords(City_changerPresenter.getInstance().getLat(), City_changerPresenter.getInstance().getLon());
                        //  }
                        isFirstDownload = false
                    }
                    nexttime = getNextTime(weatherModel)
                    handler.post {
                        for (listener in listeners) {
                            listener.updateWeather(weatherModel, nexttime)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }, 0, 10000)
    }

    fun stop() {
        if (timer != null) {
            timer!!.cancel()
        }
        listeners.clear()
    }

    fun tempInGradus(numberofmodel: Int): String? {
        if (weatherModel == null) return null
        val temp = weatherModel!!.list!![numberofmodel].main!!.temp
        val tempInCelvin =
            BigDecimal(java.lang.Double.toString(temp)).setScale(0, RoundingMode.HALF_UP).toDouble()
        return tempInCelvin.toInt().toString()
    }

    fun tempInFahrenheit(numberofmodel: Int): String? {
        if (weatherModel == null) return null
        val temp = (weatherModel!!.list!![numberofmodel].main!!.temp - 273.15) * 1.8000 + 32.00
        return temp.toInt().toString()
    }

    val windSpeed: String
        get() {
            val windSpeed =
                BigDecimal(java.lang.Double.toString(weatherModel!!.list!![0].wind!!.speed!!)).setScale(
                    0,
                    RoundingMode.HALF_UP
                ).toDouble()
            return windSpeed.toInt().toString()
        }

    fun tempMinToWeekInFahrenheit(): Array<String?> {
        val result = arrayOfNulls<String>(weatherModel!!.list!!.size)
        var i = 0
        var j = 0
        while (j < result.size) {
            result[i] = getMinTempTodayInFahrenheit(j)
            i++
            j += 8
        }
        return result
    }

    private fun getMinTempTodayInFahrenheit(counter: Int): String {
        var minTemp = 273.15
        var tempnow: Double
        for (i in counter until counter + 8) {
            tempnow = weatherModel!!.list!![i].main!!.tempMin
            if (tempnow < minTemp) minTemp = tempnow
        }
        minTemp = (minTemp - 273.15) * 1.8000 + 32.00
        val tempInCelvin =
            BigDecimal(java.lang.Double.toString(minTemp)).setScale(0, RoundingMode.HALF_UP)
                .toDouble()
        return tempInCelvin.toInt().toString()
    }

    fun tempMaxToWeekInFahrenheit(): Array<String?> {
        val result = arrayOfNulls<String>(weatherModel!!.list!!.size)
        var i = 0
        var j = 0
        while (j < result.size) {
            result[i] = getMaxTempTodayInFahrenheit(j)
            i++
            j += 8
        }
        return result
    }

    private fun getMaxTempTodayInFahrenheit(counter: Int): String {
        var maxTemp = 273.15
        var tempnow: Double
        for (i in counter until counter + 8) {
            tempnow = weatherModel!!.list!![i].main!!.tempMax
            if (tempnow < maxTemp) maxTemp = tempnow
        }
        maxTemp = (maxTemp - 273.15) * 1.8000 + 32.00
        val tempInCelvin =
            BigDecimal(maxTemp.toString()).setScale(0, RoundingMode.HALF_UP)
                .toDouble()
        return tempInCelvin.toInt().toString()
    }

    fun tempMinToWeekInCelsius(): Array<String?> {
        //val weatherDayList = weatherModel?.mapToWeatherDayList()

        /*var i = 0

        while (i < result.size - 1) {
            result[i] = weatherDayList?.get(i)?.let {*/ /*if (weatherDayList != null) {*/
            val result = arrayOfNulls<String>(MAXIMUM_DAYS_IN_LIST)
        //if (weatherDayList != null) {
           // calculateAverageMinTempTodayInCelsius(weatherDayList, result)
       // }
       // }/* }
           // i++
        //}*/
        return result
    }

    /*private fun calculateAverageMinTempTodayInCelsius(
        weatherDayList: List<WeatherDay>, array: Array<String?>
    ): Array<String?> {

        var firstDay = if (weatherDayList[0].currentDay.compareDates() && weatherDayList.size < MAXIMUM_DAYS_IN_LIST) {
            0
        } else {
            1
        }

        var i = 0

        while (firstDay < weatherDayList.size) {
            var minTemp = weatherDayList[firstDay].weatherLists[0].main.tempMin

            for (item in weatherDayList[firstDay].weatherLists) {
                if (minTemp > item.main.tempMin) {
                    minTemp = item.main.tempMin
                }
            }
            array[i] =
                BigDecimal(minTemp.toString()).setScale(0, RoundingMode.HALF_UP).toDouble().toInt()
                    .toString()

            ++firstDay
            ++i
        }

        return array
    }*/

    fun tempMaxToWeekInCelsius(): Array<String?> {
        val result = arrayOfNulls<String>(weatherModel!!.list!!.size)
        var i = 0
        var j = 0
        while (j < result.size) {
            result[i] = getMaxTempTodayInCelsius(j)
            i++
            j += 8
        }
        return result
    }

    private fun getMaxTempTodayInCelsius(counter: Int): String {
        var maxtemp = 0.0
        var tempnow: Double
        for (i in counter until counter + 8) {
            tempnow = weatherModel!!.list!![i].main!!.tempMax
            if (tempnow > maxtemp) maxtemp = tempnow
        }
        val tempInCelvin =
            BigDecimal(java.lang.Double.toString(maxtemp)).setScale(0, RoundingMode.HALF_UP)
                .toDouble()
        return tempInCelvin.toInt().toString()
    }

    companion object {
        @JvmStatic
        var instance: WeatherProvider? = null
            get() {
                field = if (field == null) WeatherProvider() else field
                return field
            }
            private set
    }
}