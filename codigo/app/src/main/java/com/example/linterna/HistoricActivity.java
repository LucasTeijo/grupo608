package com.example.linterna;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.widget.TextView;

import com.example.linterna.clients.SendEventClient;
import com.example.linterna.entities.Event;
import com.example.linterna.entities.EventResponse;
import com.example.linterna.entities.TypeEvent;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HistoricActivity extends LanternActivity {

    public static final String SENSORS_DATA_KEY = "sensors_data";
    public static final String HISTORY_KEY = "history";


    private TextView light;
    private TextView proximity;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        this.token = (String) this.getIntent().getExtras().get(TOKEN_KEY);

        Map<TypeEvent, List<Event>> eventsMap = getEventsHistory()
                .stream()
                .collect(Collectors.groupingBy(Event::getTypeEvents));

        setUpTextViews(eventsMap.get(TypeEvent.LIGHT_SENSOR), eventsMap.get(TypeEvent.ACCELEROMETER_SENSOR));
    }

    private void setUpTextViews(List<Event> lightEvents, List<Event> proximityEvents) {
        this.light = findViewById(R.id.light_sensor);
        this.proximity = findViewById(R.id.proximity_sensor);

        String lightText = "";
        String proximityText = "";

        for (Event event : lightEvents) {
            lightText = lightText.concat(event.getDescription()).concat("\n\n");
        }

        for (Event event : proximityEvents) {
            proximityText = proximityText.concat(event.getDescription()).concat("\n\n");
        }

        light.setText(lightText);
        proximity.setText(proximityText);
    }

    private List<Event> getEventsHistory() {
        SharedPreferences sharedPreferences = getSharedPreferences(SENSORS_DATA_KEY, Context.MODE_PRIVATE);

        String history = sharedPreferences.getString(HISTORY_KEY, "[]");

        return JsonUtil.fromJsonList(history, Event.class);
    }

    private void setEventsHistory(List<Event> events) {
        SharedPreferences.Editor edit = getSharedPreferences(SENSORS_DATA_KEY, Context.MODE_PRIVATE).edit();
        edit.putString(HISTORY_KEY, JsonUtil.toJson(events));
        edit.apply();
    }
}
