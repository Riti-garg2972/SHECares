package com.example.shecares;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shecares.data.MyDbHandler;
import com.example.shecares.model.DatesModel;

import java.util.Calendar;

public class Updates extends AppCompatActivity {
    EditText start, end;
    Button add, cancel;
    MyDbHandler db = new MyDbHandler(Updates.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updates);
        start = findViewById(R.id.startdate);
        end = findViewById(R.id.enddate);
        add = findViewById(R.id.Add);
        cancel = findViewById(R.id.cancel);


        Calendar calendar = Calendar.getInstance();
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(Updates.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        start.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);

                //for setting minimum date as current date of system
//                datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
                datePickerDialog.show();
            }
        });

        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(Updates.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        end.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);

                //for setting minimum date as current date of system
//                datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
                datePickerDialog.show();
            }
        });

//
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String startd = start.getText().toString();
                String endd = end.getText().toString();

                DatesModel datesModel = new DatesModel(startd, endd);
                if (db.addDates(datesModel))
                    Toast.makeText(Updates.this, "Added Successfully!!", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(Updates.this, "Some error occured!!", Toast.LENGTH_SHORT).show();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Updates.this, Dtaes.class);
                startActivity(intent);
            }
        });

    }

}