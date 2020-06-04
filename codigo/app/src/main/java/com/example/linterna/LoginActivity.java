package com.example.linterna;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.linterna.clients.LoginClient;
import com.example.linterna.clients.SendEventClient;
import com.example.linterna.entities.Event;
import com.example.linterna.entities.TypeEvent;
import com.example.linterna.entities.UserResponse;

public class LoginActivity extends LanternActivity {

    private TextView emailText;
    private TextView password;
    private TextView errorMessage;

    private LoginClient loginClient;
    private SendEventClient sendEventClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.emailText = findViewById(R.id.email_text);
        this.password = findViewById(R.id.password);
        this.errorMessage = findViewById(R.id.error_message);

        this.loginClient = new LoginClient(this::manageAccess, this::refuseAccess);
        this.sendEventClient = new SendEventClient(r -> System.out.print("Event sent"), () -> System.out.print("Fail sending event"));
    }

    public void login(View view) {
        clearErrorMessage(errorMessage);

        if (!checkInternetConnection()) {
            setErrorMessage(errorMessage, "No hay conexión a internet");
            return;
        }

        String emailText = this.emailText.getText().toString();
        String password = this.password.getText().toString();

        loginClient.login(emailText, password);
    }

    private void manageAccess(UserResponse response) {
        Intent intent = new Intent(this, SensorActivity.class);

        String token = response.getToken();
        sendEventClient.sendEvent(token, new Event()
                .setTypeEvents(TypeEvent.LOGIN)
                .setState("ACTIVE")
                .setDescription("User logged successfully"));

        intent.putExtra(TOKEN_KEY, token);

        startActivity(intent);
    }

    private void refuseAccess() {
        System.out.println("------------------");
        setErrorMessage(errorMessage, "Hubo un error vuelve a intentar");
        System.out.println("------------------");
    }
}
