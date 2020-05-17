package com.example.linterna;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button loginButton;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.loginButton = findViewById(R.id.login_button);
        this.registerButton = findViewById(R.id.register_button);
    }

    /**
     * This is called when the login button is pressed
     */
    public void goToLoginView(View view) {
        System.out.println("Login");
    }

    /**
     * This is called when the register button is pressed
     */
    public void goToRegisterView(View view) {
        System.out.println("Register");
    }

}
