package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import javax.xml.validation.Validator;

public class AddActivity extends AppCompatActivity {
private Validator nonempty_validate;
private EditText editTextPhone,editTextName,editTextEmail;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Button cancel = (Button) findViewById(R.id.CancelButton);
        cancel.setOnClickListener(v->{
            this.finish();
        });
    }

    public void saveRecord(View v){
        editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextEmail = (EditText) findViewById(R.id.editTextMail);

        String phone, name , email;
        phone = editTextPhone.getText().toString();

        if(phone.isEmpty()){
            editTextPhone.setError("Please enter phone");
            return;
        }

        name= editTextName.getText().toString();
        if(name.isEmpty()){
            editTextName.setError("Name please");
            return;
        }

        email= editTextEmail.getText().toString();
        if(email.isEmpty()){
            editTextEmail.setError("Email please");
            return;
        } else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Invalid email");
        }

        UserRecord userRecord = new UserRecord();
        userRecord.setPhone(phone);
        userRecord.setName(name);
        userRecord.setEmail(email);

        UserSQLHelper userDataSource = new UserSQLHelper(this);
        userDataSource.insertUser(userRecord);

        this.finish();//terminate this activity


    }
}