package com.example.linterna;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;

public abstract class LanternActivity extends AppCompatActivity {

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
}
