package com.example.shecares;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.shecares.data.MyDbHandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    TextView calendar,Support,Profile, days,date , newdate;
    String end = new String();
    MyDbHandler db= new MyDbHandler(MainActivity.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Profile = findViewById(R.id.Profile);
        newdate = findViewById(R.id.newdate);
        Support = findViewById(R.id.Support);
        date=findViewById(R.id.date);
        calendar = findViewById(R.id.Calendar);
        days= findViewById(R.id.days);
        end = db.nextDate();

        Calendar c= Calendar.getInstance();
        SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
        String currdate= myFormat.format(c.getTime());

        if(end==null)
            end = currdate.toString();
        date.setText(end);
        int length = calculateDays(currdate, end);
        if(length<0)
        {
            length = length*-1;
            String set = String.valueOf(length);
            days.setText(set + " days ago");
        }
        else {
            String set = String.valueOf(length);
            days.setText(set + " days to go");
        }

        newdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Updates.class);
                startActivity(intent);
            }
        });

        Support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Support.class);
                startActivity(intent);

            }
        });
        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Dtaes.class);
                startActivity(intent);

            }
        });
        Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Profile.class);
                startActivity(intent);

            }
        });

    }

    private void setBootReceiverEnabled() {
    }

    int calculateDays(String curr, String end){
        SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
        long diff = 0;
        try {
            Date date1 = myFormat.parse(curr);
            Date date2 = myFormat.parse(end);
            diff = date2.getTime() - date1.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }
}

