package com.example.codingfaction;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private Switch aSwitch, bSwitch, cSwitch, allSwitch;
    private DatabaseReference databaseReference, reference;
    private CheckBox checkBox;
    private int lightBulb1 = 0, lightBulb2 = 0, lightBulb3 = 0;
    private Button completedBtn, testBtn;
    private ImageView firstLightOn, firstLightOff, secondLightOn, secondLightOff, thirdLightOn, thirdLightOff;
    private FirebaseAuth mAuth;
    private SwipeRefreshLayout refreshLayout;
    private Dialog logoutDialog;

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser firebaseUser = mAuth.getCurrentUser();

        if (firebaseUser == null) {
            startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logoutItem:
                logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void logout() {
        final TextView yes, no;
        logoutDialog.setContentView(R.layout.logoutpopup);
        yes = logoutDialog.findViewById(R.id.yesLogout);
        no = logoutDialog.findViewById(R.id.noLogout);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                no.setBackgroundColor(Color.parseColor("#FFFFFF"));
                no.setTextColor(Color.parseColor("#2F333F"));
                logoutDialog.dismiss();
            }
        });
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yes.setBackgroundColor(Color.parseColor("#FFFFFF"));
                yes.setTextColor(Color.parseColor("#2F333F"));
                logoutDialog.dismiss();
                mAuth.signOut();
                finish();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
        logoutDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        logoutDialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();

        logoutDialog = new Dialog(this);

        init();

        if (!checkBox.isChecked()) {
            allSwitch.setClickable(false);
        }

        completedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDatabase();
            }
        });

        testBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TestActivity.class));
            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                checkDatabase();

                refreshLayout.setRefreshing(false);
            }
        });

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    allSwitch.setClickable(true);
                } else {
                    allSwitch.setClickable(false);
                }
            }
        });

        allSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    turnAllSwitchesOn();
                } else {
                    turnAllSwitchesOff();
                }
            }
        });

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    lightBulb1 = 1;
                    firstLightOn.setVisibility(View.VISIBLE);
                    firstLightOff.setVisibility(View.GONE);
                } else {
                    lightBulb1 = 0;
                    firstLightOff.setVisibility(View.VISIBLE);
                    firstLightOn.setVisibility(View.GONE);
                }
            }
        });

        bSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    lightBulb2 = 1;
                    secondLightOn.setVisibility(View.VISIBLE);
                    secondLightOff.setVisibility(View.GONE);
                } else {
                    lightBulb2 = 0;
                    secondLightOff.setVisibility(View.VISIBLE);
                    secondLightOn.setVisibility(View.GONE);
                }
            }
        });

        cSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    lightBulb3 = 1;
                    thirdLightOn.setVisibility(View.VISIBLE);
                    thirdLightOff.setVisibility(View.GONE);
                } else {
                    lightBulb3 = 0;
                    thirdLightOff.setVisibility(View.VISIBLE);
                    thirdLightOn.setVisibility(View.GONE);
                }
            }
        });
    }

    private void init() {
        aSwitch = findViewById(R.id.switchBtn1);
        bSwitch = findViewById(R.id.switchBtn2);
        cSwitch = findViewById(R.id.switchBtn3);
        allSwitch = findViewById(R.id.switchBtnAll);
        checkBox = findViewById(R.id.allLightsCheckBox);
        completedBtn = findViewById(R.id.completedBtn);
        firstLightOn = findViewById(R.id.firstLightOn);
        secondLightOn = findViewById(R.id.secondLightOn);
        thirdLightOn = findViewById(R.id.thirdLightOn);
        firstLightOff = findViewById(R.id.firstLightOff);
        secondLightOff = findViewById(R.id.secondLightOff);
        thirdLightOff = findViewById(R.id.thirdLightOff);
        refreshLayout = findViewById(R.id.swipe);
        testBtn = findViewById(R.id.testBtn);
    }

    private void refresh(int milliseconds) {

        final Handler handler = new Handler();

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                startActivity(getIntent());
            }
        };

        handler.postDelayed(runnable, milliseconds);

    }

    private void checkDatabase() {

        reference = FirebaseDatabase.getInstance().getReference().child("Lights");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild("Bulb1")) {
                    String s1 = snapshot.child("Bulb1").getValue().toString();

                    if (s1.equals("1") && !aSwitch.isChecked()) {
                        aSwitch.toggle();
                    }
                    if (s1.equals("0") && aSwitch.isChecked()) {
                        aSwitch.toggle();
                    }

                    String s2 = snapshot.child("Bulb2").getValue().toString();
                    if (s2.equals("1") && !bSwitch.isChecked()) {
                        bSwitch.toggle();
                    }
                    if (s2.equals("0") && bSwitch.isChecked()) {
                        bSwitch.toggle();
                    }

                    String s3 = snapshot.child("Bulb3").getValue().toString();
                    if (s3.equals("1") && !cSwitch.isChecked()) {
                        cSwitch.toggle();
                    }
                    if (s3.equals("0") && cSwitch.isChecked()) {
                        cSwitch.toggle();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void updateDatabase() {

        boolean checknet = isOnline();
        if (!checknet) {
            Toast.makeText(this, "No Internet Connection available! ", Toast.LENGTH_SHORT).show();
        }

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Lights");

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("Bulb1", lightBulb1);
        hashMap.put("Bulb2", lightBulb2);
        hashMap.put("Bulb3", lightBulb3);

        databaseReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, "Something went wrong!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        }
        catch (IOException | InterruptedException e) { e.printStackTrace(); }

        return false;
    }

    private void turnAllSwitchesOff() {

        if (aSwitch.isChecked()) {
            aSwitch.toggle();
        }
        if (bSwitch.isChecked()) {
            bSwitch.toggle();
        }
        if (cSwitch.isChecked()) {
            cSwitch.toggle();
        }

    }

    private void turnAllSwitchesOn() {

        if (!aSwitch.isChecked()) {
            aSwitch.toggle();
        }
        if (!bSwitch.isChecked()) {
            bSwitch.toggle();
        }
        if (!cSwitch.isChecked()) {
            cSwitch.toggle();
        }

    }
}
