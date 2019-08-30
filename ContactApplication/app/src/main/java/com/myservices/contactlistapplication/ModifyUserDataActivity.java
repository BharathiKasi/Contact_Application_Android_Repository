package com.myservices.contactlistapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class ModifyUserDataActivity extends AppCompatActivity {

    EditText username, userPhoneNumber,emailEditText;
    String name, number,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_user_data);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        username = findViewById(R.id.username_modify_editText);
        userPhoneNumber = findViewById(R.id.userPhoneNumber_editText);
        emailEditText=findViewById(R.id.email_edit_text);
        name = getIntent().getStringExtra("username");
        number = getIntent().getStringExtra("phoneNumber");
        email=getIntent().getStringExtra("email_id");
        username.setText(name);
        userPhoneNumber.setText(number);
        emailEditText.setText(email);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.modify_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.modify_menu:
                Intent result_backIntent = new Intent();
                result_backIntent.putExtra("username", username.getText().toString());
                result_backIntent.putExtra("phoneNumber", userPhoneNumber.getText().toString());
                result_backIntent.putExtra("email_id",emailEditText.getText().toString());

                setResult(RESULT_OK, result_backIntent);
                finish();
                break;
            case android.R.id.home:
                if (name.equals(username.getText().toString()) && number.equals(userPhoneNumber.getText().toString())&&email.equals(emailEditText.getText().toString())) {
                    onBackPressed();

                } else {
                    Toast.makeText(this, "your edit data is not saved ", Toast.LENGTH_LONG).show();
                    onBackPressed();
                }
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
