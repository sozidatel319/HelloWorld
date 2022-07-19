package com.mikhail.weatherclient.presentation;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.google.android.material.snackbar.Snackbar;
import com.mikhail.weatherclient.Constants;
import com.mikhail.weatherclient.PreferenceWrapper;
import com.mikhail.weatherclient.R;
import com.mikhail.weatherclient.SettingsPresenter;


public class SettingsActivity extends BaseActivity {
    private Switch themecolor;
    private Switch unit_of_measure;
    boolean statusTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        init();
        restoreData(savedInstanceState);
    }

    private void init() {
        themecolor = findViewById(R.id.themecolor);
        unit_of_measure = findViewById(R.id.unitofmeasure);
        themecolor.setChecked(isDarkTheme());
        themecolor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                statusTheme = true;
                SettingsPresenter.getInstance().setThemecolor(statusTheme);
                setDarkTheme(isChecked);
                recreate();
            }
        });

        unit_of_measure.setChecked(SettingsPresenter.getInstance().getUnitofmeasure());

        findViewById(R.id.savesettings_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Snackbar itsOk = Snackbar.make(v, R.string.savesettins, Snackbar.LENGTH_LONG);
                View snackBarView = itsOk.getView();
                snackBarView.setBackgroundColor(Color.RED);

                itsOk.setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                        SettingsPresenter.getInstance().setUnitofmeasure(unit_of_measure.isChecked());

                        if (themecolor.isChecked()) {
                        }

                        intent.putExtra(Constants.UNIT_OF_MEASURE_FAHRENHEIT, unit_of_measure.isChecked());
                        intent.putExtra(Constants.THEME, themecolor.isChecked());
                        SettingsPresenter.getInstance().setUnitofmeasure(unit_of_measure.isChecked());

                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });
                itsOk.show();

                itsOk.addCallback(new Snackbar.Callback() {
                    @Override
                    public void onDismissed(Snackbar transientBottomBar, int event) {
                        super.onDismissed(transientBottomBar, event);
                        if (event == Snackbar.Callback.DISMISS_EVENT_TIMEOUT) {

                            statusTheme = SettingsPresenter.getInstance().getThemecolor();
                            if (statusTheme) {
                                if (themecolor.isChecked()) {
                                    themecolor.setChecked(false);
                                    isDarkTheme();
                                } else themecolor.setChecked(true);
                            }
                            SettingsPresenter.getInstance().setUnitofmeasure(false);
                            unit_of_measure.setChecked(false);

                            Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                            setResult(RESULT_CANCELED, intent);
                            finish();
                        }
                    }
                });


            }
        });
    }

    private void restoreData(Bundle savedInstanceState) {

        if (savedInstanceState == null) return;

        themecolor.setChecked(isDarkTheme());
        unit_of_measure.setChecked(SettingsPresenter.getInstance().getUnitofmeasure());
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        SettingsPresenter.getInstance().setThemecolor(isDarkTheme());
        SettingsPresenter.getInstance().setUnitofmeasure(unit_of_measure.isChecked());
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
        PreferenceWrapper.getPreference(this).putBoolean(Constants.UNIT_OF_MEASURE_FAHRENHEIT, unit_of_measure.isChecked());
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
