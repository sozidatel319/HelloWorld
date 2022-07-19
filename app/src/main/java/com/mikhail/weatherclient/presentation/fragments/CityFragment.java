package com.mikhail.weatherclient.presentation.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.mikhail.weatherclient.R;
import com.google.android.material.textfield.TextInputEditText;

public class CityFragment extends Fragment {
    private TextView city1;
    private TextView city2;
    private TextView city3;
    private TextView city4;
    private TextInputEditText cityName;

    public CityFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_city, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        if (getActivity() != null) {
            cityName = getActivity().findViewById(R.id.inputcity);
            city1 = getActivity().findViewById(R.id.city1);
            city2 = getActivity().findViewById(R.id.city2);
            city3 = getActivity().findViewById(R.id.city3);
            city4 = getActivity().findViewById(R.id.city4);
        }
    }

    @Override
    public void onResume() {
        super.onResume();


        city1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cityName.setText(city1.getText());
            }
        });
        city2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cityName.setText(city2.getText());
            }
        });
        city3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cityName.setText(city3.getText());
            }
        });
        city4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cityName.setText(city4.getText());
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

