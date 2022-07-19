package com.mikhail.weatherclient.data

import com.mikhail.weatherclient.Constants
import com.mikhail.weatherclient.Utils.getNextDay
import com.mikhail.weatherclient.Utils.getNextTime
import com.mikhail.weatherclient.model.WeatherModel
import java.math.BigDecimal
import java.math.RoundingMode

object Mapper {

    fun WeatherModel.toWeatherToWeek(): WeatherToWeek {

        var partOfDayList = ArrayList<PartOfDay>()
        var previousDay = -1

        val weatherDayList = ArrayList<WeatherDay>(6)
        val firstDay = ArrayList<PartOfDay>(8)

        for (item in list.withIndex()) {

            val currentDay = getNextDay(item.value.dtTxt)

            val currentPartOfDay = PartOfDay(
                day = currentDay,
                time = getNextTime(item.value.dtTxt).toInt(),
                wind = item.value.wind.speed.windSpeedFromDoubleToInt(),
                pressure = item.value.main.pressure,
                clouds = item.value.clouds.all,
                temp = item.value.main.temp.ConvertTempFromDoubleToInt(),
                tempMax = item.value.main.tempMax.ConvertTempFromDoubleToInt(),
                tempMin = item.value.main.tempMin.ConvertTempFromDoubleToInt()
            )
            if (item.index < 8) {
                firstDay.add(currentPartOfDay)
            }

            if (currentDay != previousDay && previousDay != -1) {

                weatherDayList.add(partOfDayList.toWeatherDay())
                partOfDayList = arrayListOf()
            }

            previousDay = currentDay
            partOfDayList.add(currentPartOfDay)
        }

        return WeatherToWeek(city = city.name, country = "", firstDay, weatherDayList)
    }

    fun List<PartOfDay>.toWeatherDay(): WeatherDay =
        WeatherDay(
            currentDay = first().day,
            wind = maxOf { it.wind },
            pressure = maxOf { it.pressure},
            clouds = maxOf { it.clouds },
            minTemp = minOf { it.tempMin },
            maxTemp = maxOf { it.tempMax }
        )

    fun Double.ConvertTempFromDoubleToInt(): Int =
        BigDecimal(this.toString()).setScale(0, RoundingMode.HALF_UP).toDouble().toInt()


    fun tempMinToWeekInCelsius(): Array<String?> {
        // val weatherDayList = weatherModel?.mapToWeatherDayList()

        /*var i = 0

        while (i < result.size - 1) {
            result[i] = weatherDayList?.get(i)?.let {*/ /*if (weatherDayList != null) {*/
        val result = arrayOfNulls<String>(Constants.MAXIMUM_DAYS_IN_LIST)
        //if (weatherDayList != null) {
        //     calculateAverageMinTempTodayInCelsius(weatherDayList, result)
        // }
        // }/* }
        // i++
        //}*/
        return result
    }

/*private fun calculateAverageMinTempTodayInCelsius(
    weatherDayList: List<WeatherDay>, array: Array<String?>
): Array<String?> {

    var firstDay =
        if (weatherDayList[0].currentDay.compareDates() && weatherDayList.size < Constants.MAXIMUM_DAYS_IN_LIST) {
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

    fun Double.windSpeedFromDoubleToInt(): Int =
        BigDecimal(this.toString()).setScale(
            0,
            RoundingMode.HALF_UP
        ).toDouble().toInt()
}