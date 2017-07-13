package com.apptozee.drivingschool.Driver;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by shivam on 24/5/17.
 */

public class DBHome extends SQLiteOpenHelper {

    public HashMap<String,ArrayList<String>> getData()
    {
        HashMap<String,ArrayList<String>> store=new HashMap<>();
        ArrayList <String> number= new ArrayList<>();
        ArrayList <String> name= new ArrayList<>();
        ArrayList <String> time= new ArrayList<>(); //time as in duration
        ArrayList <String> slot= new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] cols = {"number","name","time","slot"};
        Cursor cur = db.query("customer",cols,null,null,null,null,null,null);
        if (cur.moveToFirst()){
            do {
                number.add(cur.getString(0)+"");
                name.add(cur.getString(1));
                time.add(cur.getString(2)+"");
                slot.add(cur.getString(3)+"");
            }while (cur.moveToNext());
            db.close();
        }
        store.put("number",number);
        store.put("name",name);
        store.put("time",time);
        store.put("slot",slot);
        cur.close();
        return store;
    }

    public void updateData(String s1, String s2, String s3, String s4)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name",s2);
        cv.put("time",s3);
        cv.put("slot",s4);
        db.update("customer",cv,"number=?",new String[]{s1+""});
        db.close();
    }

    public void deleteData(String s1)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("customer","number=?",new String[]{s1+""});
        db.close();
    }

    public void insertData(String s1, String s2, String s3,String s4)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("number",s1);
        cv.put("name",s2);
        cv.put("time",s3);
        cv.put("slot",s4);
        db.insert("customer",null,cv);
        db.close();
    }

    public boolean verification(String number) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT 1 FROM customer WHERE number=?", new String[] {number});
        boolean exists = c.moveToFirst();
        c.close();
        return exists;
    }

    Context con;
    public DBHome(Context context) {
        super(context, "CUSTOMERDB", null, 1);
        con=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String t1= "create table customer(number text, name text, time text, slot text);";
        db.execSQL(t1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}