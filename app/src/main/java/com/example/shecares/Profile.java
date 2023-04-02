package com.example.shecares;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shecares.data.MyDbHandler;

import java.util.Calendar;

public class Profile extends AppCompatActivity {

   TextView feedback , nxtdate, cycleLength, periodLength, delete,share,weight,wt,ic,ht,ag,fl,np;
    LinearLayout reminder;
    int hour, minutes,second;
    //Spinner ms;
   // String[] maritalStatus = {"Married", "Unmarried", "prefer not to say"};
    MyDbHandler db= new MyDbHandler(Profile.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        reminder=findViewById(R.id.reminder);

        //TODO: Implemented for personal details of user
//        feedback=findViewById(R.id.feedback);
//        weight=findViewById(R.id.weight);
//        nxtdate=findViewById(R.id.nextdate);
//        cycleLength=findViewById(R.id.cyclelength);
//        periodLength=findViewById(R.id.periodlength);
//        delete=findViewById(R.id.delete);

//        ms = findViewById(R.id.maritalstatus);
//        wt = findViewById(R.id.wt);
//        ic = findViewById(R.id.ic);
//        ht = findViewById(R.id.ht);
//        ag = findViewById(R.id.ag);
//        fl = findViewById(R.id.fl);
//        np = findViewById(R.id.np);

//        wt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                callDialog();
//            }
//        });
//        ic.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                callDialog();
//            }
//        });
//        ht.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                callDialog();
//            }
//        });
//        ag.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                callDialog();
//            }
//        });
//        fl.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                callDialog();
//            }
//        });
//        np.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                callDialog();
//            }
//        });


        //setting spinner for marital status
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Profile.this, android.R.layout.simple_spinner_item, maritalStatus);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        ms.setAdapter(adapter);
//
//        ms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                String value = adapterView.getItemAtPosition(i).toString();
//            }
//        });

        // Feedback=findViewById(R.id.review);
        share = findViewById(R.id.share);
        if(db.getCount() > 0) {
            nxtdate.setText(String.valueOf(db.nextDate()));
            periodLength.setText(String.valueOf(db.averagePeriodLength()));
            cycleLength.setText(String.valueOf(db.average()));
        }

        weight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialog();
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String Body = "Download this App";
                String Sub = "https://drive.google.com/file/d/1_v2PmnvnpXNyngoKY6ymjHAEpCSewgAD/view?usp=sharing";
                intent.putExtra(Intent.EXTRA_TEXT,Body);
                intent.putExtra(Intent.EXTRA_TEXT,Sub);
                startActivity(Intent.createChooser(intent , "Share Using"));
            }
        });

        reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdialog();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteDatabase();
                Toast.makeText(Profile.this, "Profile Deleted Successfully!!", Toast.LENGTH_SHORT).show();
            }
        });

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }

    private void showDialog() {
        final AlertDialog.Builder dialog =new AlertDialog.Builder(this);
//        dialog.setTitle("Feedback Form");
//        dialog.setMessage("Provide Us Your Valuable Feedback");

        LayoutInflater inflater = LayoutInflater.from(this);
        View reg_layout = inflater.inflate(R.layout.feedback, null);
        dialog.setView(reg_layout);
        //set button
        dialog.setPositiveButton("SEND", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Toast.makeText(Profile.this, "Feedback Recived!", Toast.LENGTH_SHORT).show();
                return;
            }
        });

        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void showdialog() {
        final AlertDialog.Builder dialog =new AlertDialog.Builder(this);
        dialog.setTitle("SET Reminder");
        LayoutInflater inflater = LayoutInflater.from(this);
        View reg_layout = inflater.inflate(R.layout.reminder, null);
        dialog.setView(reg_layout);

        EditText time1 ,name;
        time1 =reg_layout.findViewById(R.id.timeEdt);
        name =reg_layout.findViewById(R.id.title);

        Calendar calendar= Calendar.getInstance();
        time1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hour=calendar.get(Calendar.HOUR);
                minutes=calendar.get(Calendar.MINUTE);
                second=calendar.get(Calendar.SECOND);

                TimePickerDialog timePickerDialog = new TimePickerDialog(Profile.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        time1.setText(hourOfDay + ":" + minute);
                    }
                },hour,minutes,false);
                timePickerDialog.show();

            }
        });

        //set button
        dialog.setPositiveButton("Set", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String n=name.getText().toString();
                Intent intent=new Intent(AlarmClock.ACTION_SET_ALARM);
                intent.putExtra(AlarmClock.EXTRA_HOUR,hour);
                intent.putExtra(AlarmClock.EXTRA_MINUTES,minutes);
                intent.putExtra(AlarmClock.EXTRA_MESSAGE,n);

                if(hour<=24 && minutes<=60) {
                    startActivity(intent);
                    return;
                }
                else
                    Toast.makeText(Profile.this, "Please Enter time correctly!!", Toast.LENGTH_SHORT).show();

            }
        });
        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void ShowDialog() {
        final AlertDialog.Builder dialog =new AlertDialog.Builder(this);
        dialog.setTitle("BMI Calculator");
        LayoutInflater inflater = LayoutInflater.from(this);
        View reg_layout = inflater.inflate(R.layout.bmicalculator, null);
        dialog.setView(reg_layout);

        EditText wt, f, i;
        Button calc;
        TextView b, d;
        calc = reg_layout.findViewById(R.id.calculate);
        wt = reg_layout.findViewById(R.id.kg);
        f = reg_layout.findViewById(R.id.feet);
        i = reg_layout.findViewById(R.id.inch);
        b = reg_layout.findViewById(R.id.bmi);
        d = reg_layout.findViewById(R.id.detail);


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

        dialog.show();
    }

}