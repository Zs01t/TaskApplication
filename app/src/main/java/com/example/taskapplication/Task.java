package com.example.taskapplication;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by thorsten on 21.03.20.
 */

public class Task implements Parcelable {

    // simple ID generator
    private static int MAX_ID = 0;

    private int mId;
    private String mShortName;
    private String mDescription;
    private Date mCreationDate;
    private boolean mDone;

    public Task(String shortName) {
        this.mId = MAX_ID++;
        this.mShortName = shortName;
        this.mCreationDate = GregorianCalendar.getInstance().getTime();
    }

    public int getId() {
        return this.mId;
    }

    public String getShortName() {
        return mShortName;
    }

    public void setShortName(String shortName) {
        this.mShortName = shortName;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public Date getCreationDate() {
        return mCreationDate;
    }

    public boolean isDone() {
        return mDone;
    }

    public void setDone(boolean done) {
        this.mDone = done;
    }

    @Override
    public String toString() {
        return mShortName;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Task) {
            return this.getId() == ((Task) obj).getId();
        }
        return false;
    }


    //implementing Parcelable interface

    protected Task(Parcel in)
    {
        this.mShortName = in.readString();
        this.mDescription = in.readString();
        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);

        try{
            this.mCreationDate = formatter.parse(in.readString()); ;
        }
        catch (ParseException e ){
            e.printStackTrace();
        }

        this.mDone = in.readByte() != 0;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(mShortName);
        dest.writeString(mDescription);
        dest.writeString(mCreationDate.toString());
        //Could it be writeBoolean?
        dest.writeByte((byte)(mDone ? 1 : 0));
    }

    public  static  final Parcelable.Creator<Task> CREATOR
        = new Parcelable.Creator<Task>(){
                @Override
                public Task createFromParcel(Parcel source){
                    return new Task(source);
                }

                @Override
                public  Task[] newArray(int size){
                    return new Task[size];
                }
            };



}
