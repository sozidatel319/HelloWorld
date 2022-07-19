package com.mikhail.weatherclient.presentation.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mikhail.weatherclient.presentation.adapters.DaysOfWeekAdapter;
import com.mikhail.weatherclient.R;
import com.mikhail.weatherclient.SettingsPresenter;
import com.mikhail.weatherclient.WeatherProvider;
import com.mikhail.weatherclient.WeatherProviderListener;
import com.mikhail.weatherclient.model.WeatherModel;

import java.util.ArrayList;
import java.util.Objects;


public class WeatherFragment extends Fragment implements WeatherProviderListener {
    private RecyclerView recyclerView;
    private String[] daysofweek;
    private String[] mintemptoweek;
    private String[] maxtempofweek;

    public WeatherFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onResume() {
        super.onResume();
        WeatherProvider.getInstance().addListener(this);
        initRecyclerView();
    }

    @Override
    public void onPause() {
        super.onPause();
        WeatherProvider.getInstance().removeListener(this);
    }

    private void initRecyclerView() {
        recyclerView = Objects.requireNonNull(getActivity()).findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        daysofweek = WeatherProvider.getInstance().getDays();
        mintemptoweek = new String[]{"--", "--", "--", "--", "--"};
        maxtempofweek = mintemptoweek;
        recyclerView.setAdapter(new DaysOfWeekAdapter(daysofweek, mintemptoweek, maxtempofweek));
    }

    @Override
    public void updateWeather(WeatherModel model, ArrayList<String> time) {
        if (model != null) {
            daysofweek = WeatherProvider.getInstance().getDays();
            if (!SettingsPresenter.getInstance().getUnitofmeasure()) {
                mintemptoweek = WeatherProvider.getInstance().tempMinToWeekInCelsius();
                maxtempofweek = WeatherProvider.getInstance().tempMaxToWeekInCelsius();
            } else {
                mintemptoweek = WeatherProvider.getInstance().tempMinToWeekInFahrenheit();
                maxtempofweek = WeatherProvider.getInstance().tempMaxToWeekInFahrenheit();
            }
            recyclerView.setAdapter(new DaysOfWeekAdapter(daysofweek, mintemptoweek, maxtempofweek));
        }
    }
}
