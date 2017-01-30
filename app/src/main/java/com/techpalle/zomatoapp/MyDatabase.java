package com.techpalle.zomatoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by manasranjan on 1/27/2017.
 */

public class MyDatabase {
    MyHelper myHelper;
    SQLiteDatabase sqLiteDatabase;
    public MyDatabase(Context c){
        myHelper=new MyHelper(c,"resturant.db",null,1);
    }
    public void open(){
        sqLiteDatabase=myHelper.getWritableDatabase();
    }
   public void insertResturant(String name,String imageurl,String locality,String address,String longitude,String latitude){
       ContentValues contentValues=new ContentValues();
       contentValues.put("name",name);
       contentValues.put("imageurl",imageurl);
       contentValues.put("locality",locality);
       contentValues.put("address",address);
       contentValues.put("latitude",latitude);
       contentValues.put("longitude",longitude);

       sqLiteDatabase.insert("resturant",null,contentValues);
   }
    public Cursor queryResturant(){
        Cursor c=null;
        c=sqLiteDatabase.query("resturant",null,null,null,null,null,null);
        return c;
    }

    public void close(){
        sqLiteDatabase.close();
    }
    private class MyHelper extends SQLiteOpenHelper{

        public MyHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table resturant(_id integer primary key,name text,imageurl text,locality text,address text,latitude text,longitude text);");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
