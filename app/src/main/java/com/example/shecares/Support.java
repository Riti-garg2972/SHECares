package com.example.shecares;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Support extends AppCompatActivity {
    TextView num1,num2,num3,num4,num5,num6,num7,num8,num9,num10;
    String s = new String();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);

        num1=findViewById(R.id.num1);
        num2=findViewById(R.id.num2);
        num3=findViewById(R.id.num3);
        num4=findViewById(R.id.num4);
        num5=findViewById(R.id.num5);
        num6=findViewById(R.id.num6);
        num7=findViewById(R.id.num7);
        num8=findViewById(R.id.num8);
        num9=findViewById(R.id.num9);
        num10=findViewById(R.id.num10);

        num1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s = "100";
                call(s);
            }
        });


        num2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s= "155620";
                call(s);
            }
        });

        num3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s= "1098";
                call(s);
            }
        });

        num4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s= "181";
                call(s);
            }
        });
        num5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s= "1091";
                call(s);
            }
        });

        num6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s= "9xxxxxxxxx";
                call(s);
            }
        });

        num7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s= "9xxxxxxxxxx";
                call(s);
            }
        });

        num8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s= "9xxxxxxxxx";
                call(s);
            }
        });

        num9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s= "9999282390";
                call(s);
            }
        });

        num10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s= "9811628020";
                call(s);
            }
        });

    }

    public void call(String phone){
        if(phone.isEmpty()) Toast.makeText(this, "No Number To Call", Toast.LENGTH_SHORT).show();
        else{
            String s = "tel:"+phone;
            Intent intent = new Intent(Intent.ACTION_CALL , Uri.parse(s));
            startActivity(intent);
        }
    }
}