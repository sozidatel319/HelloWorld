package com.mikhail.weatherclient.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mikhail.weatherclient.R;

public class ErrorActivity extends BaseActivity {
    Button error;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mistake);
        error = findViewById(R.id.mistake_button);
        error.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                City_changerPresenter.getInstance().setOpened(true);
                Intent intent = new Intent(ErrorActivity.this, CityChangerActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
