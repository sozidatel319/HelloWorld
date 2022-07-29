package com.mikhail.weatherclient.presentation.fragments

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.mikhail.weatherclient.Constants
import com.mikhail.weatherclient.Constants.CITYCHANGER_CODE
import com.mikhail.weatherclient.Constants.CITY_NAME
import com.mikhail.weatherclient.LocationProvider
import com.mikhail.weatherclient.PreferenceWrapper
import com.mikhail.weatherclient.R
import com.mikhail.weatherclient.WeatherProvider.Companion.instance
import com.mikhail.weatherclient.presentation.City_changerPresenter
import com.mikhail.weatherclient.presentation.adapters.CityChangerAdapter
import java.util.*
import java.util.regex.Pattern

class CityChangerFragment : Fragment() {
    private lateinit var info: Switch
    private lateinit var uselocation: Switch
    private lateinit var inputCityName: TextInputEditText
    private lateinit var selectCityRecyclerView: RecyclerView
    private lateinit var okButton: Button
    private val isFirstOpened = City_changerPresenter.getInstance().opened
    private val PERMISSION_REQUEST_CODE = 10

    //TODO Надо добавить viewModel, useCase и сделать рефакторинг кода
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.city_changer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val patternCityName =
            Pattern.compile("^\\p{Lu}\\p{Ll}+(((-|\\s)\\p{Ll}+)?(-|\\s)\\p{Lu}\\p{Ll}+)?")

        init(view)

        inputCityName.onFocusChangeListener =
            OnFocusChangeListener { v, hasFocus ->
                if (hasFocus) return@OnFocusChangeListener
                if (patternCityName.matcher((v as TextView).text.toString()).matches()) {
                    v.error = null
                } else {
                    v.error = resources.getText(R.string.cityinputerror)
                }
            }

        okButton.setOnClickListener {

            PreferenceWrapper.getPreference(context)
                .putBoolean(Constants.INFO, info.isChecked)
            PreferenceWrapper.getPreference(context)
                .putBoolean(Constants.USE_LOCATION, uselocation.isChecked)

            val intent = Bundle()
            intent.putBoolean(Constants.INFO, info.isChecked)
            intent.putBoolean(Constants.USE_LOCATION, uselocation.isChecked)
            intent.putString(CITY_NAME, inputCityName.text.toString())
            if (!uselocation.isChecked and Objects.requireNonNull(inputCityName.text).toString()
                    .matches(Regex("^\\p{Lu}\\p{Ll}+(((-|\\s)\\p{Ll}+)?(-|\\s)\\p{Lu}\\p{Ll}+)?"))
            ) {
                if (inputCityName.text.toString() != City_changerPresenter.getInstance().cityName) {
                    intent.putString(CITY_NAME, inputCityName.text.toString())
                    City_changerPresenter.getInstance().cityName = inputCityName.text.toString()
                    instance!!.isFirstDownload = true
                }
            }
            if (isFirstOpened) {
                // City_changerPresenter.getInstance().setMistake(0);
                City_changerPresenter.getInstance().opened = false
            }
            //setResult(Activity.RESULT_OK, intent)
            //finish()

            setFragmentResult(CITYCHANGER_CODE.toString(), intent)

            val homeFragment = parentFragmentManager.fragments[0]
            parentFragmentManager.beginTransaction().replace(R.id.navHostFragmentRoot, homeFragment)
                .commit()
        }

        restoreData(savedInstanceState)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.size == 2 &&
                (grantResults[0] == PackageManager.PERMISSION_GRANTED || grantResults[1] == PackageManager.PERMISSION_GRANTED)
            ) {
                uselocation.isChecked = true
                LocationProvider.getLocationProvider(activity).requestLocationUpdates()
                instance!!.setWeatherByCoords(true)
            } else {
                uselocation.isChecked = false
            }
        }
    }

    private fun init(view: View) {
        inputCityName = view.findViewById(R.id.inputcity)

        info = view.findViewById(R.id.info)
        info.isChecked =
            PreferenceWrapper.getPreference(activity).getBoolean(Constants.INFO, false)

        okButton = view.findViewById(R.id.okButton)

        uselocation = view.findViewById(R.id.location)
        uselocation.isChecked =
            PreferenceWrapper.getPreference(activity).getBoolean(Constants.USE_LOCATION, false)

        uselocation.setOnCheckedChangeListener { buttonView, isChecked ->
            if (uselocation.isChecked) {
                if (LocationProvider.getLocationProvider(activity).isHavePermission) {
                    City_changerPresenter.getInstance().isUseloctation = true
                    LocationProvider.getLocationProvider(activity)
                        .requestLocationUpdates()
                    City_changerPresenter.getInstance().isUseloctation = true
                    instance?.setWeatherByCoords(true)
                } else {
                    LocationProvider.getLocationProvider(activity)
                        .requestLocationPermissions()
                    if (LocationProvider.getLocationProvider(activity).isHavePermission) {
                        City_changerPresenter.getInstance().isUseloctation = true
                        instance?.setWeatherByCoords(true)
                    }
                }
            } else {
                if (Objects.requireNonNull(inputCityName.text).toString()
                        .matches(Regex("^\\p{Lu}\\p{Ll}+(((-|\\s)\\p{Ll}+)?(-|\\s)\\p{Lu}\\p{Ll}+)?"))
                ) {
                    City_changerPresenter.getInstance().cityName = inputCityName.text.toString()
                }
                instance?.setWeatherByCoords(false)
                City_changerPresenter.getInstance().isUseloctation = false
            }
        }


        selectCityRecyclerView = view.findViewById(R.id.selectCityRecyclerView)
        selectCityRecyclerView.adapter =
            CityChangerAdapter(resources.getStringArray(R.array.cities).toList()) {
                inputCityName.setText(it)
            }


        //inputCityName.setText(City_changerPresenter.getInstance().getCityName());
        City_changerPresenter.getInstance().infoisChecked = info.isChecked
        City_changerPresenter.getInstance().isUseloctation = uselocation.isChecked
        instance!!.setWeatherByCoords(City_changerPresenter.getInstance().isUseloctation)
    }

    private fun restoreData(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) return
        inputCityName.setText(City_changerPresenter.getInstance().cityName)
        info.isChecked = City_changerPresenter.getInstance().infoisChecked
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        City_changerPresenter.getInstance().cityName = Objects.requireNonNull(
            inputCityName.text
        ).toString()
        City_changerPresenter.getInstance().infoisChecked = info.isChecked
    }

}