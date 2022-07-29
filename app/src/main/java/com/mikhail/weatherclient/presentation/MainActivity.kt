package com.mikhail.weatherclient.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mikhail.weatherclient.Constants
import com.mikhail.weatherclient.LocationProvider
import com.mikhail.weatherclient.PreferenceWrapper
import com.mikhail.weatherclient.R
import com.mikhail.weatherclient.WeatherProvider.Companion.instance
import com.mikhail.weatherclient.presentation.fragments.CityChangerFragment
import com.mikhail.weatherclient.presentation.fragments.SettingsFragment
import com.mikhail.weatherclient.presentation.viewmodel.MainViewModel

class MainActivity : BaseActivity() {
    var first = true
    var use_location = false

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        mainViewModel.getTheme()

        mainViewModel.isDarkThemeLiveData.observeForever {
            if (it) {
                setTheme(R.style.AppThemeDark)
            } else {
                setTheme(R.style.AppTheme)
            }
        }

        setContentView(R.layout.activity_main)



        City_changerPresenter.getInstance().cityName = PreferenceWrapper.getPreference(this)
            .getString(Constants.CITY_NAME, getString(R.string.Moscow))
        City_changerPresenter.getInstance().isUseloctation = PreferenceWrapper.getPreference(this)
            .getBoolean(Constants.USE_LOCATION, false)
        // initUseLocation();
    }

    override fun onResume() {
        super.onResume()
        if (!first) {
            initUseLocation()
        }
        first = false

        //Intent service = new Intent(this,WeatherService.class);
        //service.putExtra(Constants.CITY_NAME,City_changerPresenter.getInstance().getCityName());
        //startService(service);
    }

    override fun onPause() {
        super.onPause()
        if (use_location) {
            LocationProvider.getInstance().turnOffLocaltionListener()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_OK) {
            return
        }
        //recreate();
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val fragment: Fragment
        when (item.itemId) {
            R.id.menu_citychange -> {
                fragment = CityChangerFragment()
                supportFragmentManager
                    .beginTransaction()
                    .addToBackStack("CITY")
                    .add(R.id.navHostFragmentRoot, fragment)
                    .commit()
            }
            R.id.menu_settings -> {
                fragment = SettingsFragment()
                supportFragmentManager
                    .beginTransaction()
                    .addToBackStack("SETTINGS")
                    .add(R.id.navHostFragmentRoot, fragment)
                    .commit()
            }
            R.id.menu_aboutcity -> {
                //TODO: вынести адрес в константу, а имя города брать из репозитория через viewModel и UseCase
                val url =
                    "https://ru.wikipedia.org/wiki/" + City_changerPresenter.getInstance().cityName
                val uri = Uri.parse(url)
                val browser = Intent(Intent.ACTION_VIEW, uri)
                startActivity(browser)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initUseLocation() {
        use_location = City_changerPresenter.getInstance().isUseloctation
        if (use_location) {
            location
        } else {
            if (LocationProvider.getInstance() != null) {
                LocationProvider.getInstance().turnOffLocaltionListener()
            }
        }
        instance!!.setWeatherByCoords(use_location)
    }

    private val location: Unit
        private get() {
            LocationProvider.getLocationProvider(this)
            if (!LocationProvider.getInstance().isHavePermission) {
                return
            }
            LocationProvider.getInstance().requestLocationUpdates()
        }
}