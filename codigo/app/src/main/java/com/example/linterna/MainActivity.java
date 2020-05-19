package com.example.linterna;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends LanternActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This is called when the login button is pressed
     */
    public void goToLoginView(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }

    /**
     * This is called when the register button is pressed
     */
    public void goToRegisterView(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
    }

}
