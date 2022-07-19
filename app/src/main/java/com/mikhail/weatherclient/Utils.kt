package com.mikhail.weatherclient

import com.mikhail.weatherclient.Constants.MAXIMUM_DAYS_IN_LIST
import com.mikhail.weatherclient.data.WeatherDay
import com.mikhail.weatherclient.model.WeatherModel
import java.text.SimpleDateFormat
import java.util.*

object Utils {
    private val simpleDateFormatFromServer = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)

    private val simpleDateFormatOnlyDays = SimpleDateFormat("dd", Locale.ENGLISH)

    private val simpleDateFormatHours = SimpleDateFormat("HH", Locale.ENGLISH)

    /*fun WeatherModel.mapToWeatherDayList(): List<WeatherDay> {
        val arrayList = list ?: arrayListOf()
        val weatherDayArrayList = ArrayList<WeatherDay>(MAXIMUM_DAYS_IN_LIST)
        var currentDay: Int = 0
        var previousDay = currentDay

        var count = 0;
        for (item in arrayList.withIndex()) {
            currentDay = getNextDay(item.value.dtTxt)

            if (currentDay != previousDay && previousDay != 0 || item.index == arrayList.size - 1) {

                weatherDayArrayList.add(
                    WeatherDay(
                        previousDay,
                        arrayList.subList(
                            count, if (item.index == arrayList.size - 1) {
                                item.index + 1
                            } else {
                                item.index
                            }
                        )
                    )
                )

                count = item.index
            }
            previousDay = currentDay
        }

        return weatherDayArrayList
    }*/

    fun getNextDay(dateFromString: String): Int {
        val date = simpleDateFormatFromServer.parse(dateFromString)
        return if (date == null) {
            -1
        } else {
            simpleDateFormatOnlyDays.format(date).toInt()
        }
    }

    fun getNextTime(dateFromString: String):String{
        val date = simpleDateFormatFromServer.parse(dateFromString)
        return if (date == null) {
            ""
        } else {
            simpleDateFormatHours.format(date)
        }
    }

    fun Int.compareDates(): Boolean {
        val date = Date()
        val currentDay = simpleDateFormatOnlyDays.format(date).toInt()

        return this == currentDay
    }

    fun days(size: Int): Array<String?>{
            val result = arrayOfNulls<String>(size)
            val c = Calendar.getInstance()
            for (i in result.withIndex()) {
                result[i.index] =
                    c.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault())
                c.add(Calendar.DAY_OF_WEEK, +1)
            }
            return result
        }
}