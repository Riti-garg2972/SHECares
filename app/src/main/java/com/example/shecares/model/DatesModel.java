package com.example.shecares.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DatesModel {
    private String start;
    private String end;
    private int duration;
    private int cycle;
    private int EstimatedDayofOvulation;
    private int TotalDaysofFertility;
    private int MeanBleedingIntensity;

    public int getEstimatedDayofOvulation() {
        return EstimatedDayofOvulation;
    }

    public void setEstimatedDayofOvulation(int estimatedDayofOvulation) {
        EstimatedDayofOvulation = estimatedDayofOvulation;
    }

    public int getTotalDaysofFertility() {
        return TotalDaysofFertility;
    }

    public void setTotalDaysofFertility(int totalDaysofFertility) {
        TotalDaysofFertility = totalDaysofFertility;
    }

    public int getMeanBleedingIntensity() {
        return MeanBleedingIntensity;
    }

    public void setMeanBleedingIntensity(int meanBleedingIntensity) {
        MeanBleedingIntensity = meanBleedingIntensity;
    }

    public int getNumberofDaysofIntercourse() {
        return NumberofDaysofIntercourse;
    }

    public void setNumberofDaysofIntercourse(int numberofDaysofIntercourse) {
        NumberofDaysofIntercourse = numberofDaysofIntercourse;
    }

    public float getBMI() {
        return BMI;
    }

    public void setBMI(float BMI) {
        this.BMI = BMI;
    }

    private int NumberofDaysofIntercourse;
    private float BMI;

    public DatesModel(){
    }
    public DatesModel(String start){
        this.start = start;
        duration = calcDuration();
        end = null;
        cycle = 0;
    }
    public DatesModel(String start, String end){
        this.start = start;
        this.end = end;
        duration = calcDuration();
        cycle = -1;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
    public void setDuration(int duration){
        this.duration = duration;
    }

    public int calcDuration(){
        SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
        long diff = 0;
        try {
            Date date1 = myFormat.parse(start);
            Date date2 = myFormat.parse(end);
            diff = date2.getTime() - date1.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 1+(int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }
    public int getDuration(){
        return calcDuration();
    }

    public int getCycle() {
        return cycle;
    }

    public void setCycle(int cycle) {
        this.cycle = cycle;
    }



}
