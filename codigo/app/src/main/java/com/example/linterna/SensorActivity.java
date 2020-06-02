package com.example.linterna;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.linterna.clients.SendEventClient;
import com.example.linterna.entities.Event;
import com.example.linterna.entities.EventResponse;
import com.example.linterna.entities.TypeEvent;
import com.example.linterna.utils.JsonUtils;

import java.text.DecimalFormat;
import java.util.List;

import static com.example.linterna.HistoricActivity.SENSORS_DATA_KEY;

public class SensorActivity extends LanternActivity implements SensorEventListener {

    public static final int REFRESH_DELAY = 3000;
    private DecimalFormat TWO_DECIMALS_FORMATTER = new DecimalFormat("###.##");

    private String token;
    private SensorManager mSensorManager;
    private TextView lightText;
    private TextView accelerometerText;

    private SharedPreferences sharedPreferences;
    private Float lightValue;
    private Float accelerometerX;
    private Float accelerometerY;
    private Float accelerometerZ;

    private SendEventClient sendEventClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        token = (String) this.getIntent().getExtras().get(TOKEN_KEY);

        lightText = findViewById(R.id.luz);
        accelerometerText = findViewById(R.id.acelerometro);

        sharedPreferences = getSharedPreferences(SENSORS_DATA_KEY, Context.MODE_PRIVATE);

        sendEventClient = new SendEventClient(this::onSuccessSendEvent, this::onFailureSendEvent);


        new Handler().post(new Runnable() {
            @Override
            public void run() {
                String txt = "Luminosidad\n" + lightValue + " Lux";
                Event event = new Event()
                        .setTypeEvents(TypeEvent.LIGHT_SENSOR)
                        .setState("ACTIVE")
                        .setDescription(txt);

                sendEvent(event);
                lightText.setText(txt);
                new Handler().postDelayed(this, REFRESH_DELAY);
            }
        });

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                String txt = "Acelerometro:\n";
                txt += "\t x: " + (accelerometerX != null ? TWO_DECIMALS_FORMATTER.format(accelerometerX) : 0) + " m/seg2 \n";
                txt += "\t y: " + (accelerometerY != null ? TWO_DECIMALS_FORMATTER.format(accelerometerY) : 0) + " m/seg2 \n";
                txt += "\t z: " + (accelerometerZ != null ? TWO_DECIMALS_FORMATTER.format(accelerometerZ) : 0) + " m/seg2";
                accelerometerText.setText(txt);

                Event event = new Event()
                        .setTypeEvents(TypeEvent.ACCELEROMETER_SENSOR)
                        .setState("ACTIVE")
                        .setDescription(txt);
                sendEvent(event);

                new Handler().postDelayed(this, REFRESH_DELAY);
            }
        });
    }

    protected void initializeSensors() {
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT), SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void stopSensors() {
        mSensorManager.unregisterListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT));
        mSensorManager.unregisterListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
    }

    @Override
    public synchronized void onSensorChanged(SensorEvent event) {
        Log.d("sensor", event.sensor.getName());

        switch (event.sensor.getType()) {
            case Sensor.TYPE_LIGHT:
                lightValue = event.values[0];
                break;
            case Sensor.TYPE_ACCELEROMETER:
                accelerometerX = event.values[0];
                accelerometerY = event.values[1];
                accelerometerZ = event.values[2];
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }


    protected void sendEvent(Event event) {
        sendEventClient.sendEvent(token, event);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    protected void onSuccessSendEvent(EventResponse response) {
        setEventToHistory(response.getEvent());
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private synchronized void setEventToHistory(Event newEvent) {
        List<Event> eventsHistory = getEventsHistory(sharedPreferences, newEvent.getTypeEvents());

        if (eventsHistory.size() == 5) {
            eventsHistory.remove(4);
        }

        eventsHistory.add(0, newEvent);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getKey(newEvent.getTypeEvents()), JsonUtils.toJson(eventsHistory));
        editor.apply();
    }

    protected void onFailureSendEvent() {
        // TODO:
    }


    @Override
    protected void onResume() {
        super.onResume();
        initializeSensors();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopSensors();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopSensors();
    }

    /**
     * This is called when the login button is pressed
     */
    public void goToHistoricView(View view) {
        startActivity(new Intent(this, HistoricActivity.class));
    }


}
