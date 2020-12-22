package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class Delete extends AppCompatActivity implements OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);


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

        Spinner spinner=(Spinner) findViewById(R.id.spinner);

        spinner.setOnItemSelectedListener(this);
        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);



        Button deleteButton = (Button) findViewById(R.id.deletebutton);

        Button cancel = (Button) findViewById(R.id.cancelDelete);
        cancel.setOnClickListener(v->{
            this.finish();
        });


        deleteButton.setOnClickListener(v -> {


            if(spinner.getSelectedItem()==(null)){
                Toast.makeText(getApplicationContext()," No item selected",Toast.LENGTH_SHORT).show();
            }
            else {
                String selectedName=spinner.getSelectedItem().toString();
                UserSQLHelper deleteHelper=new UserSQLHelper(this);
                deleteHelper.deleteUser(selectedName);
                Toast.makeText(getApplicationContext(),selectedName+" Deleted",Toast.LENGTH_SHORT).show();

                //refresh the activity (so that the spinner list will be updated too after deleted)
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }




        });




    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();

        TextView userDetails= (TextView) findViewById(R.id.userDetail);
        UserSQLHelper helper=new UserSQLHelper(parent.getContext());
        UserRecord user=helper.getUsers(item);
        userDetails.setText(user.toString());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}