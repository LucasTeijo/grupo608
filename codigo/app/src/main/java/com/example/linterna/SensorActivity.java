package com.example.linterna;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import com.example.linterna.clients.SendEventClient;
import com.example.linterna.entities.Event;
import com.example.linterna.entities.EventResponse;

import java.text.DecimalFormat;
import java.util.List;

import static com.example.linterna.HistoricActivity.HISTORY_KEY;
import static com.example.linterna.HistoricActivity.SENSORS_DATA_KEY;

public class SensorActivity extends LanternActivity implements SensorEventListener {

    public static final int REFRESH_DELAY = 500;
    public static final String MY_PREFERENCES = "MyPrefs";
    private DecimalFormat TWO_DECIMALS_FORMATTER = new DecimalFormat("###.##");

    private String token;
    private SensorManager mSensorManager;
    private TextView lightText;
    private TextView accelerometerText;

    private SharedPreferences sharedpreferences;
    private SharedPreferences.Editor editor;
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

        sharedpreferences = getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        sendEventClient = new SendEventClient(this::onSuccessSendEvent, this::onFailureSendEvent);


        new Handler().post(new Runnable() {
            @Override
            public void run() {
                String txt = "Luminosidad\n" + lightValue + " Lux \n";
                lightText.setText(txt);
                editor.putFloat("luminosity", lightValue).apply();
                new Handler().postDelayed(this, REFRESH_DELAY);
            }
        });

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                String txt = "Acelerometro:\n";
                txt += "x: " + (accelerometerX != null ? TWO_DECIMALS_FORMATTER.format(accelerometerX) : 0) + " m/seg2 \n";
                txt += "y: " + (accelerometerY != null ? TWO_DECIMALS_FORMATTER.format(accelerometerY) : 0) + " m/seg2 \n";
                txt += "z: " + (accelerometerZ != null ? TWO_DECIMALS_FORMATTER.format(accelerometerZ) : 0) + " m/seg2 \n";
                accelerometerText.setText(txt);
                editor.putFloat("acceleration_X", accelerometerX);
                editor.putFloat("acceleration_Y", accelerometerY);
                editor.putFloat("acceleration_Z", accelerometerZ);
                editor.apply();
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


    protected void sendEvent() {
        sendEventClient.sendEvent(token, new Event());
    }

    protected void onSuccessSendEvent(EventResponse response) {
        List<Event> events = getEventsHistory();

        events.add(response.getEvent());

        setEventsHistory(events);
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


}
