package com.example.codingfaction;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText email, password;
    private String str_email = null, str_password = null;
    private Button login;
    private FirebaseAuth mAuth;
    private TextView register;

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser firebaseUser = mAuth.getCurrentUser();

        if (firebaseUser != null) {
            boolean check = checkEmailVerification();
            if (check) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
        }

    }

    private boolean checkEmailVerification() {
        FirebaseUser firebaseUser = mAuth.getCurrentUser();

        if (!firebaseUser.isEmailVerified()) {
            return false;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        init();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    private void loginUser() {
        boolean validate = validateInputs();

        if (validate) {
            mAuth.signInWithEmailAndPassword(str_email, str_password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser firebaseUser = mAuth.getCurrentUser();
                                if (firebaseUser.isEmailVerified()) {
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(LoginActivity.this, "Please verify your Email ID", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private boolean validateInputs() {
        str_email = email.getText().toString();
        str_password = password.getText().toString();

        int emailLength = str_email.length();
        int passwordLength = str_password.length();

        if (str_email == null || str_password == null) {
            return false;
        }

        if (emailLength <= 0) {
            Toast.makeText(this, "Invalid Email ID", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (passwordLength <= 0) {
            Toast.makeText(this, "Invalid Password", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void init() {
        email = findViewById(R.id.loginEmailTxt);
        password = findViewById(R.id.loginPasswordTxt);
        login = findViewById(R.id.login);
        register = findViewById(R.id.registerLink);
    }
}