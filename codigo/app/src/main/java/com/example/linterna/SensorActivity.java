package com.example.linterna;

import android.os.Bundle;

public class SensorActivity extends LanternActivity {

    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        this.token = (String) this.getIntent().getExtras().get(TOKEN_KEY);
    }
}
