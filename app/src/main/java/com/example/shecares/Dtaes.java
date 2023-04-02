package com.example.shecares;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shecares.data.MyDbHandler;
import com.example.shecares.model.DatesModel;

import java.util.ArrayList;
import java.util.Calendar;


public class Dtaes extends AppCompatActivity {


    TextView btn , duration ,cycleLength;
    LinearLayout layout;
    ListView listview;

    MyDbHandler db =new MyDbHandler(Dtaes.this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dtaes);

        btn=findViewById(R.id.Add);
        duration= findViewById(R.id.duration);
        cycleLength =findViewById(R.id.cyclelength);
        layout = findViewById(R.id.Layout);
        listview = findViewById(R.id.listview);

        duration.setText(String.valueOf(db.averagePeriodLength()) + " days");
        cycleLength.setText(String.valueOf(db.mean_cycle_length()) + " days");

        ArrayList<DatesModel> datesModelArrayList=db.getAllData();
        int s=datesModelArrayList.size();
        ArrayList<String> dates=new ArrayList<>();
        ArrayList<String> dur=new ArrayList<>();
        ArrayList<String> cycle=new ArrayList<>();

        for(int i=0; i<s; i++){
            dates.add(datesModelArrayList.get(i).getStart()+" - "+datesModelArrayList.get(i).getEnd());
            dur.add(datesModelArrayList.get(i).getDuration()+" Days");
            cycle.add(datesModelArrayList.get(i).getCycle()+" Days");
        }

        MyAdapter adapter = new MyAdapter(this, dates, dur , cycle);
        listview.setAdapter(adapter);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Dtaes.this,Updates.class);
                startActivity(intent);
            }
        });

    }

    class MyAdapter extends ArrayAdapter<String> {
        Context context;
        ArrayList<String> dates = new ArrayList<String>();
        ArrayList<String> dur =new ArrayList<String>();
        ArrayList<String> cycle =new ArrayList<String>();
        MyAdapter(Context c, ArrayList<String> ds, ArrayList<String> dr , ArrayList<String> Cycle){
            super(c,R.layout.row,R.id.date_txt,ds);
            this.context=c;
            this.dates=ds;
            this.dur=dr;
            this.cycle=Cycle;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row= layoutInflater.inflate(R.layout.row,parent,false);
            ImageView delete,edit;
            delete =row.findViewById(R.id.delete);
            edit=row.findViewById(R.id.edit);
            TextView textView =row.findViewById(R.id.date_txt);
            TextView textView1 = row.findViewById(R.id.dur_txt);
            TextView textview2 = row.findViewById(R.id.cycle_txt);
            String date = new String();
            String currdate = dates.get(position);
            int i=0;
            while(currdate.charAt(i)!=' ')
            {
                date+=String.valueOf(currdate.charAt(i));
                i++;
            }
            String temp =date;
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    db.deleteDate(temp);
                    layout.removeView(row);
                }
            });
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialog(temp);
                }
            });
            textView.setText(dates.get(position));
            textView1.setText("Duration : " + dur.get(position));
            textview2.setText("CycleLength : " + cycle.get(position));
            return row;
        }
    }
    private void showDialog(String temp) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View reg_layout = inflater.inflate(R.layout.updatedata, null);
        dialog.setView(reg_layout);
        Button update = (Button) reg_layout.findViewById(R.id.Update);
        Button cancel = (Button) reg_layout.findViewById(R.id.cancel);
        EditText startdate = reg_layout.findViewById(R.id.startdate);
        EditText enddate =reg_layout.findViewById(R.id.enddate);

        AlertDialog alert = dialog.create();
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String start = startdate.getText().toString();
                String end =enddate.getText().toString();
                DatesModel datesModel = new DatesModel(start, end);
                if (db.UpdateDate(temp,start,end ,datesModel))
                    Toast.makeText(Dtaes.this, "Added Successfully!!", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(Dtaes.this, "Some error occured!!", Toast.LENGTH_SHORT).show();
                return;
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        });

        Calendar calendar = Calendar.getInstance();
        startdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(Dtaes.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        startdate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        enddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(Dtaes.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        enddate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        dialog.show();
    }


}