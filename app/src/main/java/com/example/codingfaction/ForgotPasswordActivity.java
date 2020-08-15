package com.example.codingfaction;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText email;
    private String str_email = null;
    private Button sendPassword;
    private FirebaseAuth mAuth;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        email = findViewById(R.id.regEmailID);
        sendPassword = findViewById(R.id.sendPassword);

        mAuth = FirebaseAuth.getInstance();

        sendPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd = new ProgressDialog(ForgotPasswordActivity.this);
                pd.setMessage("Sending...");
                sendEmail();
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

    }

    private void sendEmail() {
        boolean validate = validateInput();
        if (validate) {
            mAuth.sendPasswordResetEmail(str_email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        pd.dismiss();
                        Toast.makeText(ForgotPasswordActivity.this, "Password reset link sent to you Email ID", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ForgotPasswordActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            pd.dismiss();
        }
    }

    private boolean validateInput() {

        str_email = email.getText().toString();

        if (str_email == null) {
            Toast.makeText(this, "Invalid Email ID", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (str_email.length() <= 0) {
            Toast.makeText(this, "Invalid Email ID", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}