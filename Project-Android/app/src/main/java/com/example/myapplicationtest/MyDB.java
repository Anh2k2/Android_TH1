package com.example.myapplicationtest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyDB extends SQLiteOpenHelper {

    public static  final String TableName = "ContactTable";

    public  static final String Id = "Id";
    public  static final String Name = "FullName";
    public  static final String Phone = "Phonenumber";
    public  static final String ImgaePhone = "ImageP";
    public  static final String ImgaeSMS = "ImageS";
    public  static final String ImgaeAvatar = "ImageA";

    public MyDB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlquery = "Create table if not exists " + TableName + "(" + Id + " Interger Primary key, "
                + ImgaeAvatar + " Text, "
                + Name + " Text, "
                + Phone + " Text, "
                + ImgaePhone + " Text, "
                + ImgaeSMS + " Text)";
        db.execSQL(sqlquery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table if exists " + TableName);
        onCreate(db);
    }

    public ArrayList<Contact> GetAllContact(){
        ArrayList<Contact> list = new ArrayList<>();
        String sql = "Select * from " + TableName;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql,null);
        if(cursor != null){
            while(cursor.moveToNext()){

                Contact contact = new Contact(cursor.getInt(0), cursor.getString(1), cursor.getString(2),cursor.getString(3), cursor.getString(4),cursor.getString(5));
                list.add(contact);
            }
        }
        return list;
    }
    public void addContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Id,contact.getId());
        values.put(ImgaeAvatar,contact.getImages());
        values.put(ImgaePhone,contact.getImgphone());
        values.put(ImgaeSMS,contact.getImgsms());
        values.put(Name,contact.getName());
        values.put(Phone,contact.getPhone());
        db.insert(TableName,null,values);
        db.close();
    }
    public void UpdateContact(int id, Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Id,contact.getId());
        values.put(ImgaeAvatar,contact.getImages());
        values.put(ImgaePhone,contact.getImgphone());
        values.put(ImgaeSMS,contact.getImgsms());
        values.put(Name,contact.getName());
        values.put(Phone,contact.getPhone());
        db.update(TableName,values, Id + "=?", new String[]{String.valueOf(id)});
        db.close();
    }
    public void deleteContact(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "Delete from " + TableName + " Where ID=" + id;
        db.execSQL(sql);
        db.close();
    }
}
