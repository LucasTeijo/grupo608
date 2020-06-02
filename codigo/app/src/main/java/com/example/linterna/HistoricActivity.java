package com.example.linterna;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.example.linterna.entities.Event;
import com.example.linterna.entities.TypeEvent;

import java.util.List;

public class HistoricActivity extends LanternActivity {

    public static final String SENSORS_DATA_KEY = "sensors_data";
    public static final String HISTORY_KEY = "history";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historic);

        TextView light = findViewById(R.id.light_sensor);
        TextView proximity = findViewById(R.id.proximity_sensor);

        SharedPreferences sharedPreferences = getSharedPreferences(SENSORS_DATA_KEY, Context.MODE_PRIVATE);


        setUpTextView(light, getEventsHistory(sharedPreferences, TypeEvent.LIGHT_SENSOR));
        setUpTextView(proximity, getEventsHistory(sharedPreferences, TypeEvent.ACCELEROMETER_SENSOR));
    }

    private void setUpTextView(TextView textView, List<Event> events) {

        String text = "";

        int size = events.size();
        for (int i = 0; i < size; i++) {
            text = text.concat(events.get(i).getDescription()).concat("\n\n");
        }

        text = text.concat(events.get(size - 1).getDescription());


        textView.setText(text);
    }

}
