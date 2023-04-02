package com.example.shecares.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.shecares.ml.Predictor;
import com.example.shecares.model.DatesModel;
import com.example.shecares.parameters.Parameters;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MyDbHandler extends SQLiteOpenHelper{

    int avg=0;
    private Context context;
    public MyDbHandler(Context context){
        super(context, Parameters.DB_Name, null, Parameters.DB_VERSION);
        this.context = context;
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String create1 = "CREATE TABLE "+Parameters.TABLE_NAME1+"("
                +Parameters.KEY_STARTDATE+" TEXT PRIMARY KEY, "
                +Parameters.KEY_ENDDATE+" TEXT, "+Parameters.KEY_DURATION+" INTEGER, "+Parameters.KEY_CYCLELENGTH+" INTEGER, "
                +Parameters.KEY_DAYOFOVULATION+" INTEGER, "+ Parameters.KEY_DAYSOFFERTILITY+" INTEGER, "
                +Parameters.KEY_BLEEDINGINTENSITY+" INTEGER)";
        sqLiteDatabase.execSQL(create1);

        String create2 = "CREATE TABLE "+Parameters.TABLE_NAME2+"("
                +Parameters.KEY_AGE+" INTEGER, "
                +Parameters.KEY_WEIGHT+" DECIMAL, "
                +Parameters.KEY_HEIGHT+" DECIMAL, "
                +Parameters.KEY_NUMBERPREG+" INTEGER, "
                +Parameters.KEY_MARITALSTATUS+" INTEGER, "
                +Parameters.KEY_BMI+" DECIMAL)";
        sqLiteDatabase.execSQL(create2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public int getCount(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM "+Parameters.TABLE_NAME1;
        Cursor cursor = db.rawQuery(query, null);
        int count= cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }


    public Boolean addDates(DatesModel datesModel) {

        int cyclelength = calcCycleLength(datesModel.getStart());
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Parameters.KEY_STARTDATE, datesModel.getStart());
        values.put(Parameters.KEY_ENDDATE, datesModel.getEnd());
        values.put(Parameters.KEY_DURATION, datesModel.getDuration());
        values.put(Parameters.KEY_CYCLELENGTH,cyclelength);
        long result= db.insert(Parameters.TABLE_NAME1, null, values);

        db.close();
        if(result==-1)
            return false;
        return true;
    }


    public int averagePeriodLength(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Integer> durationlist = new ArrayList<>();
        String select = "SELECT * FROM "+Parameters.TABLE_NAME1;
        Cursor cursor = db.rawQuery(select, null);
        int average = 0;
        if(cursor.moveToFirst()){
            do{
                durationlist.add(cursor.getInt(2));
            }while(cursor.moveToNext());
            for(int dur:durationlist){
                average +=dur;
            }
            average = average/ durationlist.size();
        }
        cursor.close();
        db.close();
        return average;
    }

    public int calcCycleLength(String  end ){
        SQLiteDatabase db = this.getReadableDatabase();
        String select = "SELECT * FROM "+Parameters.TABLE_NAME1;
        Cursor cursor = db.rawQuery(select,null);
        int length = 0;
        if(cursor.moveToLast())
        {
            String start = cursor.getString(0);
            SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
            long diff = 0;
            try {
                Date date1 = myFormat.parse(start);
                Date date2 = myFormat.parse(end);
                diff = date2.getTime() - date1.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            length =  (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        }
        else
            length =26;
        cursor.close();
        db.close();
        return length;
    }

    public String nextDate(){
        SQLiteDatabase db = this.getReadableDatabase();
        int count=0;
        String nextDate = new String();
        avg=0;
        String select = "SELECT * FROM "+Parameters.TABLE_NAME1;
        Cursor cursor = db.rawQuery(select, null);
        int[] arr = {26, 12, 3, 4, 4, 10, 5, 25, 1, 65, 150, 0, 24};
        if(cursor.moveToLast())
        {
//   Initial method (without ml model) for prediction
//            do {
//                avg = avg + cursor.getInt(3);
//                count++;
//            }while(cursor.moveToPrevious()&&count<3);
//
//            if(count==1) avg = 26;
//            else if (count==2)
//            {
//                avg /=2;
//            }
//            else avg /= 3;

            try {
                Predictor model = Predictor.newInstance(context);

                // Creates inputs for reference.
                TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 13}, DataType.FLOAT32);
                ByteBuffer bytebuffer = ByteBuffer.allocate(4*13);
                for(int i:arr){
                    bytebuffer.putInt(i);
                }
//                bytebuffer.rewind();
                inputFeature0.loadBuffer(bytebuffer);

                // Runs model inference and gets result.
                Predictor.Outputs outputs = model.process(inputFeature0);
                TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();
                float[] confidences = outputFeature0.getFloatArray();
                int maxPos = 0;
                float maxConfidence = 0;
                for(int i =0;i<confidences.length;i++)
                {
                    if(confidences[i] > maxConfidence){
                        maxConfidence = confidences[i];
                        maxPos = i;
                    }
                }
                avg = maxPos;
                // Releases model resources if no longer used.
                model.close();
            } catch (IOException e) {
                // TODO Handle the exception
                Toast.makeText(context, "Exception aa gya", Toast.LENGTH_SHORT).show();
            }


            String prevDate= new String();
            cursor.moveToLast();
            prevDate = cursor.getString(0);

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Calendar cal = Calendar.getInstance();
            try{
                cal.setTime(sdf.parse(prevDate));
            }catch(ParseException e){
                e.printStackTrace();
            }

            cal.add(Calendar.DAY_OF_MONTH, avg);
            nextDate = sdf.format(cal.getTime());
        }
        else
            nextDate =null;
        cursor.close();
        db.close();
        return nextDate;

    }

    public ArrayList<DatesModel> getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<DatesModel> displaylist= new ArrayList<>();
        String select = "SELECT * FROM "+Parameters.TABLE_NAME1;
        Cursor cursor = db.rawQuery(select, null);

        if(cursor.moveToLast()){
            do{
                DatesModel datesModel = new DatesModel();
                datesModel.setStart(cursor.getString(0));
                datesModel.setEnd(cursor.getString(1));
                datesModel.setDuration(Integer.parseInt(cursor.getString(2)));
                datesModel.setCycle(cursor.getInt(3));
                displaylist.add(datesModel);
            }while(cursor.moveToPrevious());
        }
        cursor.close();
        db.close();
        return displaylist;
    }

    public void deleteDate(String s){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Parameters.TABLE_NAME1, Parameters.KEY_STARTDATE+"=?", new String[]{s});
        db.close();
    }

    public Boolean UpdateDate(String s ,String start,String end,DatesModel datesModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Parameters.KEY_STARTDATE, start);
        values.put(Parameters.KEY_ENDDATE, end);
        values.put(Parameters.KEY_DURATION, datesModel.getDuration());
        long result = db.update(Parameters.TABLE_NAME1,values, Parameters.KEY_STARTDATE+"=?", new String[]{s});
        db.close();
        if(result==-1)
            return false;

        return true;

    }

    public void deleteDatabase(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Parameters.TABLE_NAME1,null,null);
        db.close();
    }

    public int average()
    {
        String s =nextDate();
        return avg;
    }

    public int mean_cycle_length(){
        int mean = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        List<Integer> cyclelist = new ArrayList<>();
        String select = "SELECT * FROM "+Parameters.TABLE_NAME1;
        Cursor cursor = db.rawQuery(select, null);
        if(cursor.moveToFirst()){
            do{
                cyclelist.add(cursor.getInt(3));
            }while(cursor.moveToNext());
            for(int cycle:cyclelist){
                mean +=cycle;
            }
            mean = mean/ cyclelist.size();
        }
        cursor.close();
        db.close();
        return mean;
    }
}
