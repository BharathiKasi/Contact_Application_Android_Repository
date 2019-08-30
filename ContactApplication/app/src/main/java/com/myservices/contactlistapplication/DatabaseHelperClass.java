package com.myservices.contactlistapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelperClass extends SQLiteOpenHelper{
    SQLiteDatabase sqLiteDatabase1=getWritableDatabase();;
    DatabaseTableHadler databaseTableHadler;


    private static String DATABASE_NAME="contact_list.db";
    private static String TAG="DATABASEHELPERCLASS";

    public DatabaseHelperClass(Context context){
        super(context,DATABASE_NAME,null,1);



    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.i(TAG,"this is the databasehelper class oncreate method");
        databaseTableHadler=getDatabaseTableHadlerObject(sqLiteDatabase);
        databaseTableHadler.createTable();


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean insertData(String name,String phoneNumber,String email){
        databaseTableHadler=getDatabaseTableHadlerObject(sqLiteDatabase1);
        long result=databaseTableHadler.tInsertData(name,phoneNumber,email);
         if(result==-1){
             return  false;
         }
         else{
             return true;
         }

    }

    public Cursor getAlldata(){
        databaseTableHadler=getDatabaseTableHadlerObject(sqLiteDatabase1);
        Cursor cursor=databaseTableHadler.getAlldata(sqLiteDatabase1);
        return  cursor;

    }

    public DatabaseTableHadler getDatabaseTableHadlerObject(SQLiteDatabase sqLiteDatabase){

        if(databaseTableHadler==null){
            databaseTableHadler=new DatabaseTableHadler(sqLiteDatabase);
        }
        return databaseTableHadler;
    }

    public boolean deleteUserContact(String phoneNumber){
        databaseTableHadler=getDatabaseTableHadlerObject(sqLiteDatabase1);
        int result=databaseTableHadler.deleteuser(sqLiteDatabase1,phoneNumber);
        if(result==1){
            return true;
        }
        else{
            return  false;
        }


    }

    public boolean modifyName(String modifyname,String phoneNumber){

        return true;
    }

    public boolean modifyUserNameAndPhoneNumberAndEmail(String userName,String userNewPhoneNumber,String email,String userOldPhoneNumber){

        databaseTableHadler=getDatabaseTableHadlerObject(sqLiteDatabase1);
        int result=databaseTableHadler.modifyUserNameAndPhoneNumberAndEmail(sqLiteDatabase1,userName,userNewPhoneNumber,email,userOldPhoneNumber);
        if(result==1){
            return true;
        }
        else{
            return false;
        }

    }

    public boolean modifyUserNameAndNumber(String userName,String lnumber,String oldNumber){

        databaseTableHadler=getDatabaseTableHadlerObject(sqLiteDatabase1);
        int result=databaseTableHadler.modifyUsernameAndNumber(userName,lnumber,oldNumber,sqLiteDatabase1);
        if(result==1){
            return true;
        }
        else{
            return false;
        }

    }
    public boolean modifyUserNameAndEmail(String username,String emailId,String oldNumber){
        databaseTableHadler=getDatabaseTableHadlerObject(sqLiteDatabase1);
        int result=databaseTableHadler.modifyUserNameAndEmail(username,emailId,oldNumber,sqLiteDatabase1);
      if(result==1){
          return  true;
      }
      else{
          return false;
      }

    }
    public boolean modifyUserName(String username,String number){
        databaseTableHadler=getDatabaseTableHadlerObject(sqLiteDatabase1);
        int result=databaseTableHadler.modifyUserName(username,number,sqLiteDatabase1);
        if(result==1){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean modifyEmailAndNumber(String number,String email,String oldNumber){

        databaseTableHadler=getDatabaseTableHadlerObject(sqLiteDatabase1);

        int result=databaseTableHadler.modifyEmailAndNumber(number,email,oldNumber,sqLiteDatabase1);
        if(result==1){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean modifyuserNumber(String newNumber,String oldNumber){

        databaseTableHadler=getDatabaseTableHadlerObject(sqLiteDatabase1);
        int result=databaseTableHadler.modifyUserNumber(newNumber,oldNumber,sqLiteDatabase1);
        if(result==1){
            return  true;
        }
        else{
            return false;
        }
    }

    public boolean modifyUserEmail(String email,String oldNumber){

        databaseTableHadler=getDatabaseTableHadlerObject(sqLiteDatabase1);
        int result=databaseTableHadler.modifyUserEmail(email,oldNumber,sqLiteDatabase1);
        if(result==1){
            return  true;
        }
        else{
            return false;
        }
    }
}
