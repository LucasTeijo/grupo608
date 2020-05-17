package com.example.linterna;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.linterna.clients.LoginClient;
import com.example.linterna.entities.UserResponse;

import org.apache.commons.lang3.StringUtils;

public class LoginActivity extends AppCompatActivity {

    private TextView emailText;
    private TextView password;
    private TextView errorMessage;

    private LoginClient loginClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.emailText = findViewById(R.id.email_text);
        this.password = findViewById(R.id.password);
        this.errorMessage = findViewById(R.id.error_message);

        this.loginClient = new LoginClient(this::manageAccess, this::refuseAccess);
    }

    public void login(View view) {
        String emailText = this.emailText.getText().toString();
        String password = this.password.getText().toString();

        loginClient.login(emailText, password);
    }

    private void manageAccess(UserResponse response) {
        System.out.println("------------------");
        errorMessage.setText(StringUtils.EMPTY);
        System.out.println("------------------");
    }

    private void refuseAccess() {
        System.out.println("------------------");
        errorMessage.setText("Hubo un error vuelve a intentar");
        System.out.println("------------------");
    }
}
