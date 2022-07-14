package com.mikhail.weatherclient.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mikhail.weatherclient.ErrorActivity;
import com.mikhail.weatherclient.R;
import com.mikhail.weatherclient.SettingsPresenter;
import com.mikhail.weatherclient.WeatherProvider;
import com.mikhail.weatherclient.WeatherProviderListener;
import com.mikhail.weatherclient.model.WeatherModel;

import java.util.ArrayList;
import java.util.Objects;

public class TimeAndTempTodayFragment extends Fragment implements WeatherProviderListener {
    TextView tempnow;
    TextView tempAfter3h;
    TextView tempAfter6h;
    TextView tempAfter9h;
    TextView tempAfter12h;
    TextView tempAfter15h;
    TextView tempAfter18h;
    TextView tempAfter21h;
    TextView tempAfter24h;
    TextView timenow;
    TextView time3;
    TextView time6;
    TextView time9;
    TextView time12;
    TextView time15;
    TextView time18;
    TextView time21;
    TextView time0;
    ArrayList<TextView> time;
    ArrayList<TextView> temperature;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.time_and_temp_today_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        temperature = new ArrayList<>();
        tempnow = Objects.requireNonNull(getActivity()).findViewById(R.id.now);
        tempAfter3h = getActivity().findViewById(R.id.temp3h);
        tempAfter6h = getActivity().findViewById(R.id.temp6h);
        tempAfter9h = getActivity().findViewById(R.id.temp9h);
        tempAfter12h = getActivity().findViewById(R.id.temp12h);
        tempAfter15h = getActivity().findViewById(R.id.temp15h);
        tempAfter18h = getActivity().findViewById(R.id.temp18);
        tempAfter21h = getActivity().findViewById(R.id.temp21);
        tempAfter24h = getActivity().findViewById(R.id.temp0);
        timenow = getActivity().findViewById(R.id.today);

        temperature.add(tempnow);
        temperature.add(tempAfter3h);
        temperature.add(tempAfter6h);
        temperature.add(tempAfter9h);
        temperature.add(tempAfter12h);
        temperature.add(tempAfter15h);
        temperature.add(tempAfter18h);
        temperature.add(tempAfter21h);
        temperature.add(tempAfter24h);

        time = new ArrayList<>();

        time3 = getActivity().findViewById(R.id.time3now);
        time6 = getActivity().findViewById(R.id.time6);
        time9 = getActivity().findViewById(R.id.time9);
        time12 = getActivity().findViewById(R.id.time12);
        time15 = getActivity().findViewById(R.id.time15);
        time18 = getActivity().findViewById(R.id.time18);
        time21 = getActivity().findViewById(R.id.time21);
        time0 = getActivity().findViewById(R.id.time0);

        time.add(timenow);
        time.add(time3);
        time.add(time6);
        time.add(time9);
        time.add(time12);
        time.add(time15);
        time.add(time18);
        time.add(time21);
        time.add(time0);
    }

    @Override
    public void onResume() {
        super.onResume();
        WeatherProvider.getInstance().addListener(this);
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
    }

    @Override
    public void onPause() {
        super.onPause();
        WeatherProvider.getInstance().removeListener(this);
    }

    public void updateWeather(WeatherModel model, ArrayList<String> nexttime) {
        if (model == null) {
            Intent intent = new Intent(getActivity(), ErrorActivity.class);
            startActivity(intent);
            for (int i = 0; i < temperature.size(); i++) {
                temperature.get(i).setText("--");
            }
            for (int i = 1; i < time.size(); i++) {
                time.get(i).setText("--");
            }
        } else {
            if (!SettingsPresenter.getInstance().getUnitofmeasure()) {
                for (int i = 0; i < temperature.size(); i++) {
                    temperature.get(i).setText(WeatherProvider.getInstance().tempInGradus(i));
                }
                if (time != null) {
                    for (int i = 1; i < time.size(); i++) {
                        time.get(i).setText(nexttime.get(i));
                    }
                }
            } else {
                for (int i = 0; i < temperature.size(); i++) {
                    temperature.get(i).setText(WeatherProvider.getInstance().tempInFahrenheit(i));
                }
                if (time != null) {
                    for (int i = 1; i < time.size(); i++) {
                        time.get(i).setText(nexttime.get(i));
                    }
                }


            }
        }
    }
}