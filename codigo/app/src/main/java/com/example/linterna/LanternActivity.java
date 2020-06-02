package com.example.linterna;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.linterna.entities.Event;
import com.example.linterna.entities.TypeEvent;
import com.example.linterna.utils.JsonUtils;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import static com.example.linterna.HistoricActivity.HISTORY_KEY;

public abstract class LanternActivity extends AppCompatActivity {

    public static final String TOKEN_KEY = "token";

    protected boolean checkInternetConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        return isConnected(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)) ||
                isConnected(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI));
    }

    private boolean isConnected(NetworkInfo networkInfo) {
        return NetworkInfo.State.CONNECTED.equals(networkInfo.getState());
    }

    protected void setErrorMessage(TextView errorMessage, String message) {
        errorMessage.setText(message);
        errorMessage.setBackgroundColor(Color.RED);
    }

    protected void clearErrorMessage(TextView errorMessage) {
        errorMessage.setText(StringUtils.EMPTY);
        errorMessage.setBackgroundColor(Color.TRANSPARENT);
    }

    protected List<Event> getEventsHistory(SharedPreferences sharedPreferences, TypeEvent typeEvent) {
        String history = sharedPreferences.getString(getKey(typeEvent), "[]");

        return JsonUtils.fromJsonList(history, Event.class);
    }

    protected String getKey(TypeEvent typeEvent) {
        return HISTORY_KEY + "_" + typeEvent.name();
    }
}
