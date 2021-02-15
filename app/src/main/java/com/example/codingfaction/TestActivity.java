package com.example.codingfaction;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
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

public class TestActivity extends AppCompatActivity {

    private Button update;
    private ImageView r1c1, r1c1_on;
    private ImageView r2c1, r2c1_on;
    private ImageView r3c1, r3c1_on;
    private ImageView r4c1, r4c1_on;
    private ImageView r5c1, r5c1_on;

    private ImageView r1c2, r1c2_on;
    private ImageView r2c2, r2c2_on;
    private ImageView r3c2, r3c2_on;
    private ImageView r4c2, r4c2_on;
    private ImageView r5c2, r5c2_on;

    private ImageView r1c3, r1c3_on;
    private ImageView r2c3, r2c3_on;
    private ImageView r3c3, r3c3_on;
    private ImageView r4c3, r4c3_on;
    private ImageView r5c3, r5c3_on;

    private DatabaseReference reference;

    private int k1 = 0, k2 = 0, k3 = 0, k4 = 0, k5 = 0, k6 = 0
            , k7 = 0, k8 = 0, k9 = 0, k10 = 0, k11 = 0, k12 = 0, k13 = 0, k14 = 0, k15 = 0;

    private Integer str_r1c1 = 0;
    private Integer str_r2c1 = 0;
    private Integer str_r3c1 = 0;
    private Integer str_r4c1 = 0;
    private Integer str_r5c1 = 0;

    private Integer str_r1c2 = 0;
    private Integer str_r2c2 = 0;
    private Integer str_r3c2 = 0;
    private Integer str_r4c2 = 0;
    private Integer str_r5c2 = 0;

    private Integer str_r1c3 = 0;
    private Integer str_r2c3 = 0;
    private Integer str_r3c3 = 0;
    private Integer str_r4c3 = 0;
    private Integer str_r5c3 = 0;

    private Switch allSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        init();

        Toolbar toolbar = findViewById(R.id.test_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Test");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        columnOne();

        columnTwo();

        columnThree();

        allSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    turnAllSwitchesOn();
                } else {
                    turnAllSwitchesOff();
                };
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDatabase();
            }
        });

    }

    private void turnAllSwitchesOff() {
        if (str_r1c1 == 1) {
            r1c1_on.performClick();
        }

        if (str_r1c2 == 1) {
            r1c2_on.performClick();
        }

        if (str_r1c3 == 1) {
            r1c3_on.performClick();
        }

        if (str_r2c2 == 1) {
            r2c2_on.performClick();
        }

        if (str_r2c1 == 1) {
            r2c1_on.performClick();
        }

        if (str_r2c3 == 1) {
            r2c3_on.performClick();
        }

        if (str_r3c1 == 1) {
            r3c1_on.performClick();
        }

        if (str_r3c2 == 1) {
            r3c2_on.performClick();
        }

        if (str_r3c3 == 1) {
            r3c3_on.performClick();
        }

        if (str_r4c1 == 1) {
            r4c1_on.performClick();
        }

        if (str_r4c2 == 1) {
            r4c2_on.performClick();
        }

        if (str_r4c3 == 1) {
            r4c3_on.performClick();
        }

        if (str_r5c1 == 1) {
            r5c1_on.performClick();
        }

        if (str_r5c2 == 1) {
            r5c2_on.performClick();
        }

        if (str_r5c3 == 1) {
            r5c3_on.performClick();
        }
    }

    private void turnAllSwitchesOn() {
        if (str_r1c1 == 0) {
            r1c1.performClick();
        }

        if (str_r1c2 == 0) {
            r1c2.performClick();
        }

        if (str_r1c3 == 0) {
            r1c3.performClick();
        }

        if (str_r2c2 == 0) {
            r2c2.performClick();
        }

        if (str_r2c1 == 0) {
            r2c1.performClick();
        }

        if (str_r2c3 == 0) {
            r2c3.performClick();
        }

        if (str_r3c1 == 0) {
            r3c1.performClick();
        }

        if (str_r3c2 == 0) {
            r3c2.performClick();
        }

        if (str_r3c3 == 0) {
            r3c3.performClick();
        }

        if (str_r4c1 == 0) {
            r4c1.performClick();
        }

        if (str_r4c2 == 0) {
            r4c2.performClick();
        }

        if (str_r4c3 == 0) {
            r4c3.performClick();
        }

        if (str_r5c1 == 0) {
            r5c1.performClick();
        }

        if (str_r5c2 == 0) {
            r5c2.performClick();
        }

        if (str_r5c3 == 0) {
            r5c3.performClick();
        }

    }


    private void updateDatabase() {

        boolean checknet = isOnline();
        if (!checknet) {
            Toast.makeText(this, "No Internet Connection available! ", Toast.LENGTH_SHORT).show();
        }

        reference = FirebaseDatabase.getInstance().getReference("Tests");

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("Bulb1", str_r1c1);
        hashMap.put("Bulb2", str_r1c2);
        hashMap.put("Bulb3", str_r1c3);
        hashMap.put("Bulb4", str_r2c1);
        hashMap.put("Bulb5", str_r2c2);
        hashMap.put("Bulb6", str_r2c3);
        hashMap.put("Bulb7", str_r3c1);
        hashMap.put("Bulb8", str_r3c2);
        hashMap.put("Bulb9", str_r3c3);
        hashMap.put("Bulb10", str_r4c1);
        hashMap.put("Bulb11", str_r4c2);
        hashMap.put("Bulb12", str_r4c3);
        hashMap.put("Bulb13", str_r5c1);
        hashMap.put("Bulb14", str_r5c2);
        hashMap.put("Bulb15", str_r5c3);

        reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(TestActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TestActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
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

    private void columnOne() {

        r1c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (k1%2 == 0) {
                    str_r1c1 = 1;
                    r1c1.setVisibility(View.GONE);
                    r1c1_on.setVisibility(View.VISIBLE);
                } else {
                    str_r1c1 = 0;
                }
                k1++;
            }
        });

        r1c1_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (k1%2 != 0) {
                    str_r1c1 = 0;
                    r1c1.setVisibility(View.VISIBLE);
                    r1c1_on.setVisibility(View.GONE);
                } else {
                    str_r1c1 = 1;
                }
                k1++;
            }
        });

        r2c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (k2%2 == 0) {
                    str_r2c1 = 1;
                    r2c1.setVisibility(View.GONE);
                    r2c1_on.setVisibility(View.VISIBLE);
                } else {
                    str_r2c1 = 0;
                }
                k2++;
            }
        });

        r2c1_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (k2%2 != 0) {
                    str_r2c1 = 0;
                    r2c1.setVisibility(View.VISIBLE);
                    r2c1_on.setVisibility(View.GONE);
                } else {
                    str_r2c1 = 1;
                }
                k2++;
            }
        });

        r3c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (k3%2 == 0) {
                    str_r3c1 = 1;
                    r3c1.setVisibility(View.GONE);
                    r3c1_on.setVisibility(View.VISIBLE);
                } else {
                    str_r3c1 = 0;
                }
                k3++;
            }
        });

        r3c1_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (k3%2 != 0) {
                    str_r3c1 = 0;
                    r3c1.setVisibility(View.VISIBLE);
                    r3c1_on.setVisibility(View.GONE);
                } else {
                    str_r3c1 = 1;
                }
                k3++;
            }
        });

        r4c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (k4%2 == 0) {
                    str_r4c1 = 1;
                    r4c1.setVisibility(View.GONE);
                    r4c1_on.setVisibility(View.VISIBLE);
                } else {
                    str_r4c1 = 0;
                }
                k4++;
            }
        });

        r4c1_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (k4%2 != 0) {
                    str_r4c1 = 0;
                    r4c1.setVisibility(View.VISIBLE);
                    r4c1_on.setVisibility(View.GONE);
                } else {
                    str_r4c1 = 1;
                }
                k4++;
            }
        });

        r5c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (k5%2 == 0) {
                    str_r5c1 = 1;
                    r5c1.setVisibility(View.GONE);
                    r5c1_on.setVisibility(View.VISIBLE);
                } else {
                    str_r5c1 = 0;
                }
                k5++;
            }
        });

        r5c1_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (k5%2 != 0) {
                    str_r5c1 = 0;
                    r5c1.setVisibility(View.VISIBLE);
                    r5c1_on.setVisibility(View.GONE);
                } else {
                    str_r5c1 = 1;
                }
                k5++;
            }
        });

    }

    private void columnTwo() {

        r1c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (k6%2 == 0) {
                    str_r1c2 = 1;
                    r1c2.setVisibility(View.GONE);
                    r1c2_on.setVisibility(View.VISIBLE);
                } else {
                    str_r1c2 = 0;
                }
                k6++;
            }
        });

        r1c2_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (k6%2 != 0) {
                    str_r1c2 = 0;
                    r1c2.setVisibility(View.VISIBLE);
                    r1c2_on.setVisibility(View.GONE);
                } else {
                    str_r1c2 = 1;
                }
                k6++;
            }
        });

        r2c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (k7%2 == 0) {
                    str_r2c2 = 1;
                    r2c2.setVisibility(View.GONE);
                    r2c2_on.setVisibility(View.VISIBLE);
                } else {
                    str_r2c2 = 0;
                }
                k7++;
            }
        });

        r2c2_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (k7%2 != 0) {
                    str_r2c2 = 0;
                    r2c2.setVisibility(View.VISIBLE);
                    r2c2_on.setVisibility(View.GONE);
                } else {
                    str_r2c2 = 1;
                }
                k7++;
            }
        });

        r3c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (k8%2 == 0) {
                    str_r3c2 = 1;
                    r3c2.setVisibility(View.GONE);
                    r3c2_on.setVisibility(View.VISIBLE);
                } else {
                    str_r3c2 = 0;
                }
                k8++;
            }
        });

        r3c2_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (k8%2 != 0) {
                    str_r3c2 = 0;
                    r3c2.setVisibility(View.VISIBLE);
                    r3c2_on.setVisibility(View.GONE);
                } else {
                    str_r3c2 = 1;
                }
                k8++;
            }
        });

        r4c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (k9%2 == 0) {
                    str_r4c2 = 1;
                    r4c2.setVisibility(View.GONE);
                    r4c2_on.setVisibility(View.VISIBLE);
                } else {
                    str_r4c2 = 0;
                }
                k9++;
            }
        });

        r4c2_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (k9%2 != 0) {
                    str_r4c2 = 0;
                    r4c2.setVisibility(View.VISIBLE);
                    r4c2_on.setVisibility(View.GONE);
                } else {
                    str_r4c2 = 1;
                }
                k9++;
            }
        });

        r5c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (k10%2 == 0) {
                    str_r5c2 = 1;
                    r5c2.setVisibility(View.GONE);
                    r5c2_on.setVisibility(View.VISIBLE);
                } else {
                    str_r5c2 = 0;
                }
                k10++;
            }
        });

        r5c2_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (k10%2 != 0) {
                    str_r5c2 = 0;
                    r5c2.setVisibility(View.VISIBLE);
                    r5c2_on.setVisibility(View.GONE);
                } else {
                    str_r5c2 = 1;
                }
                k10++;
            }
        });

    }

    private void columnThree() {

        r1c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (k11%2 == 0) {
                    str_r1c3 = 1;
                    r1c3.setVisibility(View.GONE);
                    r1c3_on.setVisibility(View.VISIBLE);
                } else {
                    str_r1c3 = 0;
                }
                k11++;
            }
        });

        r1c3_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (k11%2 != 0) {
                    str_r1c3 = 0;
                    r1c3.setVisibility(View.VISIBLE);
                    r1c3_on.setVisibility(View.GONE);
                } else {
                    str_r1c3 = 1;
                }
                k11++;
            }
        });

        r2c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (k12%2 == 0) {
                    str_r2c3 = 1;
                    r2c3.setVisibility(View.GONE);
                    r2c3_on.setVisibility(View.VISIBLE);
                } else {
                    str_r2c3 = 0;
                }
                k12++;
            }
        });

        r2c3_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (k12%2 != 0) {
                    str_r2c3 = 0;
                    r2c3.setVisibility(View.VISIBLE);
                    r2c3_on.setVisibility(View.GONE);
                } else {
                    str_r2c3 = 1;
                }
                k12++;
            }
        });

        r3c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (k13%2 == 0) {
                    str_r3c3 = 1;
                    r3c3.setVisibility(View.GONE);
                    r3c3_on.setVisibility(View.VISIBLE);
                } else {
                    str_r3c3 = 0;
                }
                k13++;
            }
        });

        r3c3_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (k13%2 != 0) {
                    str_r3c3 = 0;
                    r3c3.setVisibility(View.VISIBLE);
                    r3c3_on.setVisibility(View.GONE);
                } else {
                    str_r3c3 = 1;
                }
                k13++;
            }
        });

        r4c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (k14%2 == 0) {
                    str_r4c3 = 1;
                    r4c3.setVisibility(View.GONE);
                    r4c3_on.setVisibility(View.VISIBLE);
                } else {
                    str_r4c3 = 0;
                }
                k14++;
            }
        });

        r4c3_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (k14%2 != 0) {
                    str_r4c3 = 0;
                    r4c3.setVisibility(View.VISIBLE);
                    r4c3_on.setVisibility(View.GONE);
                } else {
                    str_r4c3 = 1;
                }
                k14++;
            }
        });

        r5c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (k15%2 == 0) {
                    str_r5c3 = 1;
                    r5c3.setVisibility(View.GONE);
                    r5c3_on.setVisibility(View.VISIBLE);
                } else {
                    str_r5c3 = 0;
                }
                k15++;
            }
        });

        r5c3_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (k15%2 != 0) {
                    str_r5c3 = 0;
                    r5c3.setVisibility(View.VISIBLE);
                    r5c3_on.setVisibility(View.GONE);
                } else {
                    str_r5c3 = 1;
                }
                k15++;
            }
        });

    }

    private void init() {

        allSwitch = findViewById(R.id.controlSwitchBtnAll);
        update = findViewById(R.id.testUpdate);

        r1c1 = findViewById(R.id.r1c1);
        r2c1 = findViewById(R.id.r2c1);
        r3c1 = findViewById(R.id.r3c1);
        r4c1 = findViewById(R.id.r4c1);
        r5c1 = findViewById(R.id.r5c1);

        r1c1_on = findViewById(R.id.r1c1_on);
        r2c1_on = findViewById(R.id.r2c1_on);
        r3c1_on = findViewById(R.id.r3c1_on);
        r4c1_on = findViewById(R.id.r4c1_on);
        r5c1_on = findViewById(R.id.r5c1_on);

        r1c2 = findViewById(R.id.r1c2);
        r2c2 = findViewById(R.id.r2c2);
        r3c2 = findViewById(R.id.r3c2);
        r4c2 = findViewById(R.id.r4c2);
        r5c2 = findViewById(R.id.r5c2);

        r1c2_on = findViewById(R.id.r1c2_on);
        r2c2_on = findViewById(R.id.r2c2_on);
        r3c2_on = findViewById(R.id.r3c2_on);
        r4c2_on = findViewById(R.id.r4c2_on);
        r5c2_on = findViewById(R.id.r5c2_on);

        r1c3 = findViewById(R.id.r1c3);
        r2c3 = findViewById(R.id.r2c3);
        r3c3 = findViewById(R.id.r3c3);
        r4c3 = findViewById(R.id.r4c3);
        r5c3 = findViewById(R.id.r5c3);

        r1c3_on = findViewById(R.id.r1c3_on);
        r2c3_on = findViewById(R.id.r2c3_on);
        r3c3_on = findViewById(R.id.r3c3_on);
        r4c3_on = findViewById(R.id.r4c3_on);
        r5c3_on = findViewById(R.id.r5c3_on);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}