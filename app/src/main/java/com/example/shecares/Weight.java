package com.example.shecares;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Weight extends AppCompatActivity {

    EditText wt, f, i;
    Button calc;
    TextView b, d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight);
        calc = findViewById(R.id.calculate);
        wt = findViewById(R.id.kg);
        f = findViewById(R.id.feet);
        i = findViewById(R.id.inch);
        b = findViewById(R.id.bmi);
        d = findViewById(R.id.detail);

        calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double w = Double.valueOf(String.valueOf(wt.getText()));
                double feet = Double.valueOf(String.valueOf(f.getText()));
                double inch = Double.valueOf(String.valueOf(i.getText()));

                feet = feet * 0.3048;
                inch = inch * 0.0254;
                double h = feet + inch;
                double bmi = w / (h * h);
                b.setText("BMI = " + String.format("%.1f", bmi) + " kg/sq.m");
                if (bmi <= 15.0) d.setText("Vey severely underweight");
                else if (bmi > 15.0 && bmi <= 16.0) d.setText("Severely underweight");
                else if (bmi > 16.0 && bmi <= 18.5) d.setText("Underweight");
                else if (bmi > 18.5 && bmi <= 25.0) d.setText("Healthy weight");
                else if (bmi > 25.0 && bmi <= 30.0) d.setText("Overweight");
                else if (bmi > 30.0 && bmi <= 35.0) d.setText("Moderately obese");
                else if (bmi > 35.0 && bmi <= 40.0) d.setText("Severely obese");
                else d.setText("Very severely obese");
            }
        });
    }
}