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

import java.text.DecimalFormat;

public class SensorActivity extends LanternActivity implements SensorEventListener {

    private String token;
    private SensorManager mSensorManager;
    private TextView luz;
    private TextView acelerometro;

    DecimalFormat dosdecimales = new DecimalFormat("###.###");

    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences.Editor editor;
    String txtLuz;
    String txtAce;
    Float valorLuz;
    Float valorAceX;
    Float valorAceY;
    Float valorAceZ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        this.token = (String) this.getIntent().getExtras().get(TOKEN_KEY);

        luz = findViewById(R.id.luz);
        acelerometro = findViewById(R.id.acelerometro);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();


        new Handler().post(new Runnable() {
            @Override
            public void run() {
                txtLuz ="";
                txtLuz += "Luminosidad\n";
                txtLuz += valorLuz + " Lux \n";
                luz.setText(txtLuz);
                editor.putFloat("Luminosidad",valorLuz).apply();
                new Handler().postDelayed(this, 3000);
            }
        });

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                txtAce = "";
                txtAce += "Acelerometro:\n";
                txtAce += "x: " + dosdecimales.format(valorAceX) + " m/seg2 \n";
                txtAce += "y: " + dosdecimales.format(valorAceY) + " m/seg2 \n";
                txtAce += "z: " + dosdecimales.format(valorAceZ) + " m/seg2 \n";
                acelerometro.setText(txtAce);
                editor.putFloat("Aceleracion X", valorAceX).apply();
                editor.putFloat("Aceleracion Y", valorAceY).apply();
                editor.putFloat("Aceleracion Z", valorAceZ).apply();
                new Handler().postDelayed(this, 3000);
            }
        });
    }

    protected void Ini_Sensores(){
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT), SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }
    protected void Parar_Sensores(){
        mSensorManager.unregisterListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT));
        mSensorManager.unregisterListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        synchronized (this){
            Log.d("sensor", event.sensor.getName());
            switch(event.sensor.getType())
            {
                case Sensor.TYPE_LIGHT :
                    valorLuz = event.values[0];

                    break;

                case Sensor.TYPE_ACCELEROMETER :
                    valorAceX =  event.values[0];
                    valorAceY =  event.values[1];
                    valorAceZ =  event.values[2];

                    break;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onResume(){
        super.onResume();
        Ini_Sensores();
    }

    @Override
    protected void onPause(){
        super.onPause();
        Parar_Sensores();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Parar_Sensores();
    }


}
