package com.example.codingfaction;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private EditText email, password;
    private Button submit;
    private String str_email = null, str_password = null;
    private DatabaseReference reference, userReference;
    private FirebaseAuth mAuth;
    private ProgressDialog pd;
    private TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();

        mAuth = FirebaseAuth.getInstance();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

    }

    private void registerUser() {
        boolean validate = validateInputFields();

        if (validate) {
            reference = FirebaseDatabase.getInstance().getReference("College email id");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String str = snapshot.getValue().toString();
                    if (str_email.contains(str)) {
                        pd = new ProgressDialog(RegisterActivity.this);
                        pd.setMessage("Please Wait...");
                        pd.show();
                        register();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Invalid Email ID", Toast.LENGTH_SHORT).show();
                    }
                }

                private void register() {
                    mAuth.createUserWithEmailAndPassword(str_email, str_password)
                            .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        final FirebaseUser firebaseUser = mAuth.getCurrentUser();
                                        String userid = firebaseUser.getUid();

                                        userReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userid);

                                        HashMap<String, Object> hashMap = new HashMap<>();
                                        hashMap.put("id", userid);
                                        hashMap.put("email", str_email);
                                        hashMap.put("access", "false");

                                        userReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    pd.dismiss();
                                                    firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                Toast.makeText(RegisterActivity.this, "Verifification link sent to Email ID", Toast.LENGTH_SHORT).show();
                                                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                                            } else {
                                                                Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });
                                                } else {
                                                    pd.dismiss();
                                                    Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });

                                    } else {
                                        Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    private boolean validateInputFields() {
        str_email = email.getText().toString();
        str_password = password.getText().toString();

        if (str_email == null || str_password == null) {
            Toast.makeText(this, "Invalid input credentials", Toast.LENGTH_SHORT).show();
            return false;
        }

        int emailLength = str_email.length();
        int passwordLength = str_password.length();

        if (emailLength <= 0) {
            Toast.makeText(this, "Invalid Email ID", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (passwordLength < 6) {
            Toast.makeText(this, "Password should at least be 6 characters", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void init() {
        email = findViewById(R.id.emailTxt);
        password = findViewById(R.id.passwordTxt);
        submit = findViewById(R.id.submit);
        login = findViewById(R.id.loginLink);
    }
}