package com.mikhail.weatherclient.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.mikhail.weatherclient.City_changerPresenter;
import com.mikhail.weatherclient.Constants;
import com.mikhail.weatherclient.ErrorActivity;
import com.mikhail.weatherclient.PreferenceWrapper;
import com.mikhail.weatherclient.R;
import com.mikhail.weatherclient.SettingsPresenter;
import com.mikhail.weatherclient.WeatherProvider;
import com.mikhail.weatherclient.WeatherProviderListener;
import com.mikhail.weatherclient.database.DataReader;
import com.mikhail.weatherclient.database.DataSource;
import com.mikhail.weatherclient.model.WeatherModel;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

public class WeatherTodayFragment extends Fragment implements WeatherProviderListener {
    private TextView cityName;
    LinearLayout pressuregroup;
    private TextView pressure;
    LinearLayout windgroup;
    private TextView wind;
    private TextView temperature_of_day;
    TextView tempmeasure;
    private TextView clouds;
    private TextView today;
    private DataSource dataSource;
    private DataReader dataReader;
    boolean cel = true;
    private boolean city_in_db = false;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather_today, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        cityName = getActivity().findViewById(R.id.city);
        pressuregroup = getActivity().findViewById(R.id.pressuregroup);
        pressure = getActivity().findViewById(R.id.pressure);
        windgroup = getActivity().findViewById(R.id.windgroup);
        wind = getActivity().findViewById(R.id.wind);
        temperature_of_day = getActivity().findViewById(R.id.temperatureOfDay);
        tempmeasure = getActivity().findViewById(R.id.tempmeasure);
        if (!PreferenceWrapper.getPreference(getActivity()).getBoolean(Constants.UNIT_OF_MEASURE_FAHRENHEIT, false)) {
            tempmeasure.setText(Constants.CELSIUS);
        } else {
            tempmeasure.setText(Constants.FAHRENHEIT);
        }
        today = getActivity().findViewById(R.id.today);
        clouds = getActivity().findViewById(R.id.clouds);
        cityName.setText(City_changerPresenter.getInstance().getCityName());
        if (PreferenceWrapper.getPreference(getActivity()).getBoolean(Constants.INFO, false)) {
            windgroup.setVisibility(View.VISIBLE);
            pressuregroup.setVisibility(View.VISIBLE);
        } else {
            windgroup.setVisibility(View.GONE);
            pressuregroup.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    //    readFromDB();
        WeatherProvider.getInstance().addListener(this);
        today.setText(WeatherProvider.getInstance().getDays()[0]);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dataSource = new DataSource(context);
        try {
            dataSource.open();
            dataReader = dataSource.getReader();
            dataReader.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void updateWeather(WeatherModel model, ArrayList<String> ti) {
        // City_changerPresenter.getInstance().setCityName(Objects.requireNonNull(cityName.getText()).toString());
        if (model == null) {
            Intent intent = new Intent(getActivity(), ErrorActivity.class);
            startActivity(intent);
            wind.setText("--");
            pressure.setText("--");
            temperature_of_day.setText("--");
            clouds.setText("--");
        } else {
           // if (city_in_db) {
                //WeatherProvider.getInstance().removeListener(this);
          //  } else {
                cityName.setText(City_changerPresenter.getInstance().getCityName());
                wind.setText(WeatherProvider.getInstance().getWindSpeed());
                pressure.setText(String.valueOf(model.getList().get(0).getMain().getPressure()));
                if (!SettingsPresenter.getInstance().getUnitofmeasure()) {
                    temperature_of_day.setText(WeatherProvider.getInstance().tempInGradus(0));
                } else {
                    temperature_of_day.setText(WeatherProvider.getInstance().tempInFahrenheit(0));
                }
                clouds.setText(String.valueOf(model.getList().get(0).getClouds().getAll()));
          //  }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        saveToDB();
        PreferenceWrapper.getPreference(getActivity()).putString(Constants.CITY_NAME, cityName.getText().toString());
        WeatherProvider.getInstance().removeListener(this);
    }

    public void saveToDB() {
        String cityname = "";
        String cel_today = WeatherProvider.getInstance().tempInGradus(0);
        String far_today = WeatherProvider.getInstance().tempInFahrenheit(0);
        for (int i = 0; i < dataSource.getReader().getCount(); i++) {
            cityname = dataSource.getReader().getPosition(i).getCityname();
            if (cityname.equals(City_changerPresenter.getInstance().getCityName())) {

                dataSource.edit(dataReader.getPosition(i), City_changerPresenter.getInstance().getCityName(), cel_today, far_today,
                        clouds.getText().toString(), pressure.getText().toString(), wind.getText().toString(), receiveDate());
                break;
            }
        }
        if (cityname.isEmpty() || !cityname.equals(cityName.getText().toString())) {
            dataSource.add(cityName.getText().toString(), cel_today, far_today,
                    clouds.getText().toString(), pressure.getText().toString(), wind.getText().toString(), new Date().toString());
        }
        dataReader.refresh();
        try {
            dataReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readFromDB() {
        if (dataReader.getCount() > 0) {
            for (int i = 0; i < dataReader.getCount(); i++) {
                if (dataReader.getPosition(i).getCityname().equals(City_changerPresenter.getInstance().getCityName()) && dataReader.getPosition(i).getDatenow().equals(receiveDate())) {
                    if (cel) {
                        temperature_of_day.setText(dataReader.getPosition(i).getTemperature_today_cel());
                    } else {
                        temperature_of_day.setText(dataReader.getPosition(i).getTemperature_today_far());
                    }
                    clouds.setText(dataReader.getPosition(i).getClouds());
                    pressure.setText(dataReader.getPosition(i).getPressure());
                    wind.setText(dataReader.getPosition(i).getWind());
                    city_in_db = true;
                    break;
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == Constants.CITYCHANGER_CODE) {
            if (data.getStringExtra(Constants.CITY_NAME) != null && !data.getBooleanExtra(Constants.USE_LOCATION, false)) {
                cityName.setText(data.getStringExtra(Constants.CITY_NAME));
            }
            if (data.getBooleanExtra(Constants.INFO, false)) {
                pressuregroup.setVisibility(View.VISIBLE);
                pressure.setText(data.getStringExtra(Constants.PRESSURE));
                windgroup.setVisibility(View.VISIBLE);
                wind.setText(data.getStringExtra(Constants.WIND_SPEED));
            } else {
                pressuregroup.setVisibility(View.GONE);
                windgroup.setVisibility(View.GONE);
            }
        }

        if (requestCode == Constants.SETTINGS_CODE) {

            if (data.getBooleanExtra(Constants.UNIT_OF_MEASURE_FAHRENHEIT, false)) {
                SettingsPresenter.getInstance().setUnitofmeasure(true);
            } else {
                SettingsPresenter.getInstance().setUnitofmeasure(false);
            }
        }
    }

    private String receiveDate() {
        Date date = new Date();
        SimpleDateFormat sdf = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            sdf = new SimpleDateFormat("dd/MM/YYYY", Locale.ENGLISH);
        }
        return sdf.format(date);
    }

}

