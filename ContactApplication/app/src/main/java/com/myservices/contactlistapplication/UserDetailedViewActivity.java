package com.myservices.contactlistapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static java.net.Proxy.Type.HTTP;

public class UserDetailedViewActivity extends AppCompatActivity {

    private static int REQUEST_CODE_PHONE_CALL=100;
    private static int REQUEST_CODE_MESSAGE=200;
    private static int REQUEST_CODE_MODIFY=200;
    private static int REQUEST_CODE_EMAIL=400;

TextView usernameTextView,phoneNumberTextView,emailTextView;
ImageView phoneCallButton,messageButton,emailButton;
String phoneNumber;
String username;
String modifiedName;
    String modifiedPhoneNumber,modifiedEmail;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detailed_view);
        usernameTextView=findViewById(R.id.username_detailview);
        phoneCallButton=findViewById(R.id.phone_call_button);
        messageButton=findViewById(R.id.messge_button);
        emailTextView=findViewById(R.id.gmail_text_view);
        phoneNumberTextView=findViewById(R.id.phoneNumber_textView);
        emailButton=findViewById(R.id.email_image_view);
        /*if(getSupportActionBar()!=null){

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }*/

        /*if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }*/
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        username=getIntent().getStringExtra("username");
        phoneNumber=getIntent().getStringExtra("phoneNumber");
        email=getIntent().getStringExtra("email_id");
        //phoneCallButton.setText(phoneNumber);
        phoneNumberTextView.setText(phoneNumber);
        usernameTextView.setText(username);
        emailTextView.setText(email);
        phoneCallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makePhoneCall();
            }
        });

        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeMessage();
            }
        });

        emailButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                makeEmail();
            }
        });

        }
        public void makePhoneCall(){
        if(phoneNumber.length()>0){

            if(ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){

                ActivityCompat.requestPermissions(UserDetailedViewActivity.this,new String[]{Manifest.permission.CALL_PHONE},REQUEST_CODE_PHONE_CALL
                );
            }
            else{

                String dial="tel:"+phoneNumber;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        }


        }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==REQUEST_CODE_PHONE_CALL){

            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                makePhoneCall();
            }
            else{
                Toast.makeText(this,"permission dinied ",Toast.LENGTH_LONG).show();
            }
        }
        else if(requestCode==REQUEST_CODE_MESSAGE){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                makeMessage();
            }
            else{
                Toast.makeText(this,"permission denied",Toast.LENGTH_LONG).show();
            }
        }
        else if(requestCode==REQUEST_CODE_EMAIL){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                makeEmail();
            }
        }
    }

    /*@Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }*/

    public void makeMessage(){
        if(ContextCompat.checkSelfPermission(UserDetailedViewActivity.this,Manifest.permission.SEND_SMS)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(UserDetailedViewActivity.this,new String[]{Manifest.permission.SEND_SMS},REQUEST_CODE_MESSAGE);
        }
        else{
            Intent intent=new Intent(Intent.ACTION_SENDTO);
            //intent.addCategory(Intent.CATEGORY_APP_MESSAGING);
            intent.setData(Uri.parse("sms:" + phoneNumber));
            startActivity(intent);
        }



    }

    public void makeEmail(){

            Intent intent=new Intent(Intent.ACTION_SENDTO);
            //intent.setData(Uri.parse("sendto:"+ emailTextView.getText().toString()));
            //intent.setType("plain/text");
            intent.setData(Uri.parse("mailto:"+emailTextView.getText().toString()));
        //Intent intent=new Intent(Intent.ACTION_MAIN);
        //intent.addCategory(Intent.CATEGORY_APP_EMAIL);
        //intent.setType("message/rfc822");
        try{
            startActivity(intent);
        }
        catch (Exception e){
            Toast.makeText(this,"your receiver mail is not correct ",Toast.LENGTH_SHORT).show();
        }

            //intent.putExtra(Intent.EXTRA_EMAIL,new String []{emailTextView.getText().toString()});
           // startActivity(Intent.createChooser(intent,emailTextView.getText().toString()));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.action_menu,menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();

    }

    @Override
    public void onBackPressed() {
        Intent result_backToHomeActivityIntent=new Intent();
        if(modifiedName!=null){

            if(!(username.equals(modifiedName))){
                result_backToHomeActivityIntent.putExtra("username",usernameTextView.getText().toString());
            }
        /*else{
            result_backToHomeActivityIntent.putExtra("username",username);
        }*/
        }
        if(modifiedPhoneNumber!=null) {

            if (!(phoneNumber.equals(modifiedPhoneNumber))) {
                result_backToHomeActivityIntent.putExtra("phoneNumber", phoneNumberTextView.getText().toString());
            }
        /*else
        {
            result_backToHomeActivityIntent.putExtra("phoneNumber",phoneNumber);
        }*/
        }
        if(modifiedEmail!=null) {
            if(email!=null){

                if (!(email.equals(modifiedEmail))) {
                    result_backToHomeActivityIntent.putExtra("email_id", emailTextView.getText().toString());
                }
        /*else
        {
            result_backToHomeActivityIntent.putExtra("email_id",email);
        }*/
            }
            else{
                result_backToHomeActivityIntent.putExtra("email_id",emailTextView.getText().toString());
            }

        }
        setResult(RESULT_OK,result_backToHomeActivityIntent);
        finish();
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete :
                Intent intent =new Intent();
                intent.putExtra("delete","delete");
                setResult(RESULT_OK,intent);
                finish();
                break;
            case R.id.modify :
                Intent modifyIntent =new Intent(UserDetailedViewActivity.this,ModifyUserDataActivity.class);
                modifyIntent.putExtra("username",usernameTextView.getText().toString());
                modifyIntent.putExtra("phoneNumber",phoneNumberTextView.getText().toString());
                modifyIntent.putExtra("email_id",emailTextView.getText().toString());
                startActivityForResult(modifyIntent,REQUEST_CODE_MODIFY);
                break;
            case android.R.id.home :
                 onBackPressed();
                 break;

        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_MODIFY){
            if(resultCode==RESULT_OK){
                if(data!=null){
                     modifiedName=data.getStringExtra("username");
                     modifiedPhoneNumber=data.getStringExtra("phoneNumber");
                     modifiedEmail =data.getStringExtra("email_id");
                    if(!(modifiedName.equals(username))){
                        Toast.makeText(this,"your contact is saved succesfullly",Toast.LENGTH_LONG).show();
                        usernameTextView.setText(modifiedName);
                    }
                    if(!(modifiedPhoneNumber.equals(phoneNumber))){
                        Toast.makeText(this,"your contact is saved   succesfully ",Toast.LENGTH_LONG).show();
                        phoneNumberTextView.setText(modifiedPhoneNumber);
                    }
                    if(!(modifiedEmail.equals(email))){
                        Toast.makeText(this,"your contact is saved successfully",Toast.LENGTH_LONG).show();
                        emailTextView.setText(modifiedEmail);
                    }
                }

            }


        }
    }
}

