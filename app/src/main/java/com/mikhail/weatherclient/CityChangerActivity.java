package com.mikhail.weatherclient;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.mikhail.weatherclient.fragments.WeatherTodayFragment;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;
import java.util.regex.Pattern;

public class CityChangerActivity extends BaseActivity implements ActivityCompat.OnRequestPermissionsResultCallback {
    private Switch info;
    private Switch uselocation;
    private TextInputEditText inputCityName;
    private boolean isFirstOpened = City_changerPresenter.getInstance().getOpened();
    private static final int PERMISSION_REQUEST_CODE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_changer);
        final Pattern patternCityName = Pattern.compile("^\\p{Lu}\\p{Ll}+(((-|\\s)\\p{Ll}+)?(-|\\s)\\p{Lu}\\p{Ll}+)?");
        init();

        inputCityName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) return;
                if (patternCityName.matcher(((TextView) v).getText().toString()).matches()) {
                    ((TextView) v).setError(null);
                } else {
                    ((TextView) v).setError(getResources().getText(R.string.cityinputerror));
                }
            }
        });

        findViewById(R.id.savecity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceWrapper.getPreference(CityChangerActivity.this).putBoolean(Constants.INFO, info.isChecked());
                PreferenceWrapper.getPreference(CityChangerActivity.this).putBoolean(Constants.USE_LOCATION, uselocation.isChecked());
                Intent intent = new Intent(CityChangerActivity.this, WeatherTodayFragment.class);
                intent.putExtra(Constants.INFO, info.isChecked());
                intent.putExtra(Constants.USE_LOCATION, uselocation.isChecked());

                if (!uselocation.isChecked() & Objects.requireNonNull(inputCityName.getText()).toString().matches("^\\p{Lu}\\p{Ll}+(((-|\\s)\\p{Ll}+)?(-|\\s)\\p{Lu}\\p{Ll}+)?")) {
                    if (!inputCityName.getText().toString().equals(City_changerPresenter.getInstance().getCityName())) {
                        intent.putExtra(Constants.CITY_NAME, inputCityName.getText().toString());
                        City_changerPresenter.getInstance().setCityName(inputCityName.getText().toString());
                        WeatherProvider.getInstance().setFirstDownload(true);
                    }
                }

                if (isFirstOpened) {
                    // City_changerPresenter.getInstance().setMistake(0);
                    City_changerPresenter.getInstance().setOpened(false);

                }
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        restoreData(savedInstanceState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length == 2 &&
                    (grantResults[0] == PackageManager.PERMISSION_GRANTED || grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                uselocation.setChecked(true);
                LocationProvider.getLocationProvider(this).requestLocationUpdates();
                WeatherProvider.getInstance().setWeatherByCoords(true);
            } else {
                uselocation.setChecked(false);
            }
        }
    }

    private void init() {
        inputCityName = findViewById(R.id.inputcity);
        info = findViewById(R.id.info);
        uselocation = findViewById(R.id.location);
        info.setChecked(PreferenceWrapper.getPreference(this).getBoolean(Constants.INFO, false));
        uselocation.setChecked(PreferenceWrapper.getPreference(this).getBoolean(Constants.USE_LOCATION, false));
        uselocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (uselocation.isChecked()) {
                    if (LocationProvider.getLocationProvider(CityChangerActivity.this).isHavePermission()) {
                        City_changerPresenter.getInstance().setUseloctation(true);
                        LocationProvider.getLocationProvider(CityChangerActivity.this).requestLocationUpdates();
                        City_changerPresenter.getInstance().setUseloctation(true);
                        WeatherProvider.getInstance().setWeatherByCoords(true);
                    } else {
                        LocationProvider.getLocationProvider(CityChangerActivity.this).requestLocationPermissions();
                        if (LocationProvider.getLocationProvider(CityChangerActivity.this).isHavePermission()) {
                            City_changerPresenter.getInstance().setUseloctation(true);
                            WeatherProvider.getInstance().setWeatherByCoords(true);
                        }
                    }
                } else {
                    if (Objects.requireNonNull(inputCityName.getText()).toString().matches("^\\p{Lu}\\p{Ll}+(((-|\\s)\\p{Ll}+)?(-|\\s)\\p{Lu}\\p{Ll}+)?")) {
                        City_changerPresenter.getInstance().setCityName(inputCityName.getText().toString());
                    }
                    WeatherProvider.getInstance().setWeatherByCoords(false);
                    City_changerPresenter.getInstance().setUseloctation(false);
                }
            }
        });

        //inputCityName.setText(City_changerPresenter.getInstance().getCityName());

        City_changerPresenter.getInstance().setInfoisChecked(info.isChecked());
        City_changerPresenter.getInstance().setUseloctation(uselocation.isChecked());
        WeatherProvider.getInstance().setWeatherByCoords(City_changerPresenter.getInstance().isUseloctation());
    }

    private void restoreData(Bundle savedInstanceState) {
        if (savedInstanceState == null) return;
        inputCityName.setText(City_changerPresenter.getInstance().getCityName());
        info.setChecked(City_changerPresenter.getInstance().getInfoisChecked());
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        City_changerPresenter.getInstance().setCityName(Objects.requireNonNull(inputCityName.getText()).toString());
        City_changerPresenter.getInstance().setInfoisChecked(info.isChecked());
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
