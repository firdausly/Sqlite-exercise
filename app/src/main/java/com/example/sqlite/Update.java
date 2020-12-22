package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Update extends AppCompatActivity implements OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        EditText phone =(EditText) findViewById(R.id.updatePhone);
        EditText username =(EditText) findViewById(R.id.updateName);
        EditText email =(EditText) findViewById(R.id.updateEmail);

        UserSQLHelper userSQLHelper=new UserSQLHelper(this);
        final List<UserRecord> values = userSQLHelper.getAllUsers();
        ArrayList<String> name=new ArrayList<String>();
        for(int i=0;i<values.size();i++){
            name.add(values.get(i).getName());
        }

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, name);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner=(Spinner) findViewById(R.id.updateSpinner);
        spinner.setOnItemSelectedListener(this);
        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);


        Button cancel = (Button) findViewById(R.id.cancelUpdate);
        cancel.setOnClickListener(v->{
            this.finish();
        });

        Button updateButton = (Button) findViewById(R.id.updateButton);

        updateButton.setOnClickListener(v -> {

            if(spinner.getSelectedItem()==(null)){
                Toast.makeText(getApplicationContext()," No item selected",Toast.LENGTH_SHORT).show();
            }
            else {
                String selectedName=spinner.getSelectedItem().toString();
                UserSQLHelper updateHelper=new UserSQLHelper(this);



                UserRecord userRecord = new UserRecord();
                userRecord.setPhone(phone.getText().toString());
                userRecord.setName(username.getText().toString());
                userRecord.setEmail(email.getText().toString());

                updateHelper.updateUser(userRecord,selectedName);
                Toast.makeText(getApplicationContext(),selectedName+" Updated",Toast.LENGTH_SHORT).show();

                //refresh the activity (so that the spinner list will be updated too after deleted)
                Intent intent = getIntent();
                this.finish();

            }
        });


    }


    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();

        EditText phone =(EditText) findViewById(R.id.updatePhone);
        EditText username =(EditText) findViewById(R.id.updateName);
        EditText email =(EditText) findViewById(R.id.updateEmail);

        UserSQLHelper helper=new UserSQLHelper(parent.getContext());
        UserRecord selecteduser=helper.getUsers(item);

        phone.setText(selecteduser.getPhone());
        email.setText(selecteduser.getEmail());
        username.setText(selecteduser.getName());
    }
}