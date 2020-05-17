package com.example.linterna;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.linterna.clients.RegisterClient;
import com.example.linterna.entities.UserResponse;

import org.apache.commons.lang3.StringUtils;


public class RegisterActivity extends AppCompatActivity {

    private TextView name;
    private TextView lastnameText;
    private TextView emailText;
    private TextView dniText;
    private TextView password;
    private TextView errorMessage;

    private RegisterClient registerClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        this.name = findViewById(R.id.name_text);
        this.lastnameText = findViewById(R.id.lastname_text);
        this.emailText = findViewById(R.id.email_text);
        this.dniText = findViewById(R.id.dni_text);
        this.password = findViewById(R.id.password);
        this.errorMessage = findViewById(R.id.error_message);

        this.registerClient = new RegisterClient(this::manageAccess, this::refuseAccess);
    }


    public void register(View view) {
        String name = this.name.getText().toString();
        String lastNametText = this.lastnameText.getText().toString();
        String emailText = this.emailText.getText().toString();
        String dniText = this.dniText.getText().toString();
        String password = this.password.getText().toString();

        Integer dni = StringUtils.isNotEmpty(dniText) ? Integer.valueOf(dniText) : null;

        registerClient.register(name, lastNametText, emailText, dni, password);
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
