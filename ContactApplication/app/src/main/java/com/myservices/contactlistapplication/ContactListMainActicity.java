package com.myservices.contactlistapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.stetho.Stetho;

import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContactListMainActicity extends AppCompatActivity {

    private static int REQUEST_CODE = 100;

    RecyclerHelper recyclerHelper;
    public static String TAG = "ContactListMainActivity";
    private static int REQUEST_PERMISSION = 100;
    String contactrId;
    ContactListPojo contactListPojo;
    ContactListPojo contactListPojoReference;
    List<ContactListPojo> contactList;
    String lnumber;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerAdapterClass recyclerAdapterClass;
    TextView phoneNoTextView, userNameTextView, userSurnameTextView;
    EditText numberEditText, nameEditText, surnameEditText;
    Button insertButton, deleteButton, showButton, modifyButton;
    NameComparator nameComparator;
    List<ContactListPojo> nonameList;
    String userName;
    String regx = "^\\+(?:[0-9] ?){6,14}[0-9]$";
    DatabaseHelperClass databaseHelperClass;
    Pattern lpattern = Pattern.compile(regx);
    String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //old
        //setContentView(R.layout.activity_main);
        //new
        setContentView(R.layout.activity_main);
        databaseHelperClass = new DatabaseHelperClass(this);
        //phoneNoTextView=findViewById(R.id.user_phone_number);
        //numberEditText=findViewById(R.id.user_phone_number_edit_text);
        //userNameTextView=findViewById(R.id.user_name);
        //nameEditText=findViewById(R.id.user_name_edit_text_);
        //userSurnameTextView=findViewById(R.id.user_surname_text_view);
        //surnameEditText=findViewById(R.id.user_surname_edit_text);
        //insertButton=findViewById(R.id.insert_button);
        //deleteButton=findViewById(R.id.delete);
        //showButton=findViewById(R.id.show_button);
        //modifyButton=findViewById(R.id.modify);


        Stetho.initialize(Stetho.newInitializerBuilder(this).enableDumpapp(Stetho.defaultDumperPluginsProvider(this)).enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this)).build());

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS}, REQUEST_PERMISSION);

        } else {
            showData();
        }
        //old

        /*showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor=databaseHelperClass.getAlldata();
                contactList=getDataFromDatabase(cursor);
                Intent intent=new Intent(ContactListMainActicity.this,ShowData.class);
                intent.putExtra("contactList",(Serializable)contactList);
                startActivity(intent);

            }
        });*/


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                getContactFromDevice();

            }


        } else {
            Toast.makeText(this, "sorry your permission is denied ", Toast.LENGTH_LONG).show();
        }

    }


    /*public class AsynTask extends AsyncTask<Boolean, Boolean, Boolean> {
        Cursor cursor;
        Context context;

        AsynTask(Cursor cursor, Context context) {
            this.cursor = cursor;
            this.context = context;

        }

        @Override
        protected Boolean doInBackground(Boolean... booleans) {
            DatabaseHelperClass databaseHelperClass = new DatabaseHelperClass(context);
             cursor.move(-1);
            while(cursor.moveToNext()) {
                String name            = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phoneNumber     = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                databaseHelperClass.insertData(name,phoneNumber);

            }


            return true;
        }
    }*/


    public void getContactFromDevice() {
        ContentResolver contentResolver = getContentResolver();

        Uri contactUri = ContactsContract.Contacts.CONTENT_URI;
        //String daf=ContactsContract.Contacts.DISPLAY_NAME;
        String wer = ContactsContract.Contacts.HAS_PHONE_NUMBER;
        String order = ContactsContract.Contacts.DISPLAY_NAME + " ASC";
        //String[] columns = {ContactsContract.Contacts._ID,ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.Contacts.HAS_PHONE_NUMBER};
        Cursor cursor = contentResolver.query(contactUri, null, null, null, "DISPLAY_NAME ASC");//ContactsContract.CommonDataKinds.Phone.NUMBER+" ASC");
        int count = cursor.getCount();
        Log.i(TAG, "this count of the contact list" + count);
        if (cursor.getCount() > 0) {
            contactList = new ArrayList();
            while (cursor.moveToNext()) {

                contactListPojo = new ContactListPojo();


                userName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                //contactListPojo.setName(userName);
                int phone = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
                if (phone > 0) {

                    Cursor phonecursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " =? ", new String[]{contactId}, null);
                    List<String> list = new ArrayList<>();
                    while (phonecursor.moveToNext()) {

                        lnumber = phonecursor.getString(phonecursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        list.add(lnumber);
                    }
                    //new

                    Cursor gmailcursor = contentResolver.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + " =?", new String[]{contactId}, null);
                    //contactListPojo.setPhoneNumber(lnumber);
                    while (gmailcursor.moveToNext()) {

                        email = gmailcursor.getString(gmailcursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                        Log.i(TAG, "the email is " + email);
                    }
                    Matcher matcher = lpattern.matcher(userName);
                    //new
                    //Cursor imageCursor=contentResolver.query(ContactsContract.CommonDataKinds.Phone,null,ContactsContract.CommonDataKinds.Photo.CONTACT_ID +"=?",new String[]{contactId},null);
                    boolean result = emailChecker();
                    if (result) {
                        contactListPojo.setEmailId(email);

                    }
                    if (matcher.matches()) {
                        if (nonameList == null) {
                            nonameList = new ArrayList<>();
                        }


                        contactListPojo.setName("#");
                        contactListPojo.setPhoneNumber(lnumber);
                        nonameList.add(contactListPojo);
                        //databaseHelperClass.insertData("#", lnumber);


                    } else {
                        contactListPojo.setName(userName);
                        contactListPojo.setPhoneNumber(lnumber);
                        contactList.add(contactListPojo);
                        //sortData();
                        //new
                        //here we are adding the new string called email
                        databaseHelperClass.insertData(userName, lnumber, email);
                    }
                    email = "";

                }
            }
            //sortData();

            /*for (ContactListPojo contactListPojo1 : contactList) {

                userName = contactListPojo1.getName();
                lnumber = contactListPojo1.getPhoneNumber();
                databaseHelperClass.insertData(userName, lnumber);
            }*/
            for (ContactListPojo lcontactListPojo : nonameList) {


                userName = lcontactListPojo.getName();
                lnumber = lcontactListPojo.getPhoneNumber();
                //new
                email = lcontactListPojo.getEmailId();
                //new
                //here also we add the new string callled email
                databaseHelperClass.insertData(userName, lnumber, email);
            }


            showData();

        } else {
            Toast.makeText(this, "your contact list is empty ", Toast.LENGTH_LONG).show();
        }


    }

    public void showData() {
        Cursor cursor = databaseHelperClass.getAlldata();
        contactList = getDataFromDatabase(cursor);
        //old
        /*if(recyclerHelper==null){
            recyclerHelper=new RecyclerHelper(this,contactList);
        }
        recyclerHelper.setAdapter(recyclerView,layoutManager);
*/

        //new

        if (recyclerAdapterClass == null) {
            recyclerAdapterClass = new RecyclerAdapterClass(this, contactList);
        }
        recyclerView = findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(recyclerAdapterClass);
    }


    public void sortData() {
        nameComparator = new NameComparator();
        Collections.sort(contactList, nameComparator);
    }

    public List<ContactListPojo> getDataFromDatabase(Cursor cursor) {
        List<ContactListPojo> duplicateList;
        if (contactList != null) {
            duplicateList = contactList;
            contactList.removeAll(duplicateList);
        } else {
            contactList = new ArrayList<>();
        }
        while (cursor.moveToNext()) {
            userName = cursor.getString(0);
            lnumber = cursor.getString(1);
            email = cursor.getString(2);
            contactListPojo = new ContactListPojo();
            contactListPojo.setName(userName);
            contactListPojo.setPhoneNumber(lnumber);
            //new
            contactListPojo.setEmailId(email);
            contactList.add(contactListPojo);
        }
        return contactList;

    }

    public void getUserSelectPosition(int position) {

        Intent intent = new Intent(ContactListMainActicity.this, UserDetailedViewActivity.class);
        contactListPojoReference = contactList.get(position);
        intent.putExtra("username", contactListPojoReference.getName());
        intent.putExtra("phoneNumber", contactListPojoReference.getPhoneNumber());
        //new
        intent.putExtra("email_id", contactListPojoReference.getEmailId());
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                if (data != null) {

                    String delete = data.getStringExtra("delete");
                    if (delete != null) {
                        String delete_phone_number = contactListPojoReference.getPhoneNumber();
                        boolean result = databaseHelperClass.deleteUserContact(delete_phone_number);
                        if (result) {
                            Toast.makeText(this, "your contact is deleted ", Toast.LENGTH_LONG).show();
                            showData();
                        }

                    } else {

                        userName = data.getStringExtra("username");
                        lnumber = data.getStringExtra("phoneNumber");
                        email = data.getStringExtra("email_id");
                        if (userName != null && lnumber != null && email != null) {
                            //if(!(userName.equals(contactListPojoReference.getName()))){
                            //  if(!(lnumber.equals(contactListPojoReference.getPhoneNumber()))){
                            //    if(!(email.equals(contactListPojoReference.getEmailId()))){
                            boolean result = databaseHelperClass.modifyUserNameAndPhoneNumberAndEmail(userName, lnumber, email, contactListPojoReference.getPhoneNumber());
                            if (result) {
                                showData();
                                //Toast.makeText(this, "your conact is modified ", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(this, "your contact is not modified", Toast.LENGTH_LONG).show();
                            }
                        }




                        else if (userName != null && lnumber != null&&email==null) {
                            boolean result = databaseHelperClass.modifyUserNameAndNumber(userName, lnumber, contactListPojoReference.getPhoneNumber());
                            if (result) {
                                showData();
                                //Toast.makeText(this, "your name and phone number is saved succesfully", Toast.LENGTH_LONG).show();

                            } else {
                                Toast.makeText(this, "your contact is not modified ", Toast.LENGTH_LONG).show();
                            }


                        }


                        else if(userName!=null&&email!=null&&lnumber==null){
                            //if (!(email.equals(contactListPojoReference.getEmailId()))) {
                                boolean result = databaseHelperClass.modifyUserNameAndEmail(userName, email, contactListPojoReference.getPhoneNumber());
                                if (result) {
                                    showData();
                                    //Toast.makeText(this, "your name and email is modified ", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(this, "your contact is not modified ", Toast.LENGTH_LONG).show();
                                }
                            }
                        else if(email!=null&&lnumber!=null&&userName==null){

                            boolean result=databaseHelperClass.modifyEmailAndNumber(lnumber,email,contactListPojoReference.getPhoneNumber());
                            if(result){
                                showData();
                                //Toast.makeText(this,"your email and number is modified successfully",Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(this,"your email and number is not modified",Toast.LENGTH_LONG).show();
                            }
                        }


                        else if(userName!=null&&lnumber==null&&email==null) {

                                boolean result = databaseHelperClass.modifyUserName(userName, contactListPojoReference.getPhoneNumber());
                                if (result) {
                                    showData();
                                    //Toast.makeText(this, "your name is modified ", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(this, "your name is not modified ", Toast.LENGTH_LONG).show();
                                }
                            }
                        else if(lnumber!=null &&userName==null&&email==null){

                            boolean result=databaseHelperClass.modifyuserNumber(lnumber,contactListPojoReference.getPhoneNumber());
                            if(result){
                                showData();
                                //Toast.makeText(this,"contact list is modified ",Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(this,"your contact is not modified ",Toast.LENGTH_LONG).show();
                            }

                        }
                        else if(email!=null&&userName==null&&lnumber==null){
                            boolean result=databaseHelperClass.modifyUserEmail(email,contactListPojoReference.getPhoneNumber());
                            if(result) {
                                showData();
                               // Toast.makeText(this,"your contact is saved successfylly",Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(this,"your contact is not saved ",Toast.LENGTH_LONG).show();
                            }

                        }


                        }

                    }


                }


                }


            }







public boolean emailChecker(){
        if(email!=null){
        return true;
        }
        return false;
        }
        }

