package com.myservices.contactlistapplication;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.widget.Toast;

public class DatabaseTableHadler {

    SQLiteDatabase sqLiteDatabase;




    private String gTABLE_NAME         ="USER_DATA_LIST";
    private String gUSER_NAME          ="NAME";
    private String gPHONE_NUMBER       ="NUMBER";
    private String gUSER_EMAIL         ="EMAIL_ID";
    private String gCREATE_TABLE       ="CREATE TABLE "+gTABLE_NAME +"("+ gUSER_NAME +" STRING ,"+
                                         gPHONE_NUMBER +" TEXT UNIQUE ,"+ gUSER_EMAIL +" TEXT"+")";

//NEW  HERE WE ADD THE NEW STRING CALLED EMAIL


    public DatabaseTableHadler(SQLiteDatabase sqLiteDatabase){

        /*
        * this method is invoked when we create the object for the databasetablehandler class.
        * it is used to initialize the sqlitedatabase object.
         */


        this.sqLiteDatabase=sqLiteDatabase;

    }


    public void createTable(){

        /*
        * this method is invoked when the databasehelper class oncreate method is invoked
        * it will create the new table for the database in contact_list.db file
         */


        sqLiteDatabase.execSQL( gCREATE_TABLE  );

    }

    public long tInsertData(String lName,String lPhoneNumber,String email){
        ContentValues contentValues=new ContentValues();
        contentValues.put(gUSER_NAME,lName);
        contentValues.put(gPHONE_NUMBER,lPhoneNumber);
        if(email!=null){
            contentValues.put(gUSER_EMAIL,email);
        }

        long result=sqLiteDatabase.insert(gTABLE_NAME,null,contentValues);
        return result;

    }

    public Cursor getAlldata(SQLiteDatabase sqLiteDatabase){
        Cursor cursor=sqLiteDatabase.rawQuery("SELECT * FROM "+gTABLE_NAME,null);
        return cursor;

    }

    public int deleteuser(SQLiteDatabase sqLiteDatabase, String delete_phone_number){

        int result=sqLiteDatabase.delete(gTABLE_NAME,gPHONE_NUMBER+" =?",new String[]{delete_phone_number});
        return result;
    }

    public int modifyUserNameAndPhoneNumberAndEmail(SQLiteDatabase sqLiteDatabase,String userName,String userNewPhoneNumber,String email,String oldPhoneNumber ){
        ContentValues contentValues=new ContentValues();
        contentValues.put(gUSER_NAME,userName);
        contentValues.put(gPHONE_NUMBER,userNewPhoneNumber);
        contentValues.put(gUSER_EMAIL,email);
        int result=sqLiteDatabase.update(gTABLE_NAME,contentValues,gPHONE_NUMBER+" =?", new String[]{oldPhoneNumber});

        return result;
    }

    public int modifyUsernameAndNumber(String username,String phoneNumber,String oldNumber,SQLiteDatabase sqLiteDatabase){
        ContentValues contentValues=new ContentValues();
        contentValues.put(gUSER_NAME,username);
        contentValues.put(gPHONE_NUMBER,phoneNumber);
        int  result=sqLiteDatabase.update(gTABLE_NAME,contentValues,gPHONE_NUMBER+" =?",new String[]{oldNumber});
        return  result;
    }

    public int modifyUserNameAndEmail(String username,String email,String oldNumber,SQLiteDatabase sqLiteDatabase){

        ContentValues contentValues=new ContentValues();
        contentValues.put(gUSER_NAME,username);
        contentValues.put(gUSER_EMAIL,email);
        int result=sqLiteDatabase.update(gTABLE_NAME,contentValues,gPHONE_NUMBER+" =?",new String[]{oldNumber});
        return result;
    }

    public int modifyUserName(String username,String oldNumber,SQLiteDatabase sqLiteDatabase){
        ContentValues contentValues=new ContentValues();
        contentValues.put(gUSER_NAME,username);
        int result=sqLiteDatabase.update(gTABLE_NAME,contentValues,gPHONE_NUMBER+" =?",new String[]{oldNumber});
        return result;
    }

    public int modifyEmailAndNumber(String number,String email,String oldNumber,SQLiteDatabase sqLiteDatabase){
        ContentValues contentValues=new ContentValues();
        contentValues.put(gPHONE_NUMBER,number);
        contentValues.put(gUSER_EMAIL,email);
        int result=sqLiteDatabase.update(gTABLE_NAME,contentValues,gPHONE_NUMBER+" =?",new String []{oldNumber});
        return result;
    }

    public int modifyUserNumber(String newNumber,String oldNumber,SQLiteDatabase sqLiteDatabase){

        ContentValues contentValues=new ContentValues();
        contentValues.put(gPHONE_NUMBER,newNumber);
        int result=sqLiteDatabase.update(gTABLE_NAME,contentValues,gPHONE_NUMBER+" =?", new String[]{oldNumber});
        return result;
    }

    public  int modifyUserEmail(String email,String oldNumber,SQLiteDatabase sqLiteDatabase){

        ContentValues contentValues=new ContentValues();
        contentValues.put(gUSER_EMAIL,email);
        int result=sqLiteDatabase.update(gTABLE_NAME,contentValues,gPHONE_NUMBER+" =?",new String[]{oldNumber});
        return result;
    }




}
