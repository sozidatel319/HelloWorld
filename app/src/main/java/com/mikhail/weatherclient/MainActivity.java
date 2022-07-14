package com.mikhail.weatherclient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends BaseActivity {
    boolean first = true;
    boolean use_location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SettingsPresenter.getInstance().setUnitofmeasure(PreferenceWrapper.getPreference(this).getBoolean(Constants.UNIT_OF_MEASURE_FAHRENHEIT, false));
        City_changerPresenter.getInstance().setCityName(PreferenceWrapper.getPreference(this).getString(Constants.CITY_NAME, getString(R.string.Moscow)));
        City_changerPresenter.getInstance().setUseloctation(PreferenceWrapper.getPreference(this).getBoolean(Constants.USE_LOCATION, false));
        initUseLocation();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!first) {
            initUseLocation();
        }
        first = false;

        //Intent service = new Intent(this,WeatherService.class);
        //service.putExtra(Constants.CITY_NAME,City_changerPresenter.getInstance().getCityName());
        //startService(service);
        if (City_changerPresenter.getInstance().getMistake() == 1) {
            Intent intent = new Intent(this, ErrorActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (use_location) {
            LocationProvider.getInstance().turnOffLocaltionListener();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) {
            return;
        }
        android.app.Fragment weathertodayfragment = getFragmentManager().findFragmentById(R.id.weathertodayfragment);
        weathertodayfragment.onActivityResult(requestCode, resultCode, data);
        recreate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_citychange:
                startActivityForResult(new Intent(MainActivity.this, CityChangerActivity.class), Constants.CITYCHANGER_CODE);
                break;
            case R.id.menu_settings:
                startActivityForResult(new Intent(MainActivity.this, SettingsActivity.class), Constants.SETTINGS_CODE);
                break;
            case R.id.menu_aboutcity:
                String url = "https://ru.wikipedia.org/wiki/" + City_changerPresenter.getInstance().getCityName();
                Uri uri = Uri.parse(url);
                Intent browser = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(browser);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initUseLocation() {
        use_location = City_changerPresenter.getInstance().isUseloctation();
        if (use_location) {
            getLocation();
        } else {
            if (LocationProvider.getInstance() != null) {
                LocationProvider.getInstance().turnOffLocaltionListener();
            }
        }
        WeatherProvider.getInstance().setWeatherByCoords(use_location);
    }

    private void getLocation() {
        LocationProvider.getLocationProvider(this);
        if (!LocationProvider.getInstance().isHavePermission()) {
            return;
        }
        LocationProvider.getInstance().requestLocationUpdates();
    }
}

