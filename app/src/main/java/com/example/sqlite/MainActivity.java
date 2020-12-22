package com.example.sqlite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView listViewRecords;
    UserSQLHelper userSQLHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        listViewRecords = (ListView) findViewById(R.id.listViewRecords);
        listViewRecords.setOnItemClickListener(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);


        fab.setOnClickListener((view) -> {
            Intent intent = new Intent(getApplicationContext(), AddActivity.class);
            startActivity(intent);
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.deleteitem:
                //user chose the Delete.
                Intent intent=new Intent(getApplicationContext(),Delete.class);
                startActivity(intent);
                return true;
            case R.id.updateItem:
                //user chose the Delete.
                Intent intent2=new Intent(getApplicationContext(),Update.class);
                startActivity(intent2);
                return true;


            default:
                //if we got here, the user's action not recognized
                //invoke the superclass to handle it
                return super.onOptionsItemSelected(item);
        }



    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getApplicationContext(),
                "Position: "+position, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateList();
    }

    private void updateList() {
        //Retrieve records from SQLite
        userSQLHelper = new UserSQLHelper(this);
        final List<UserRecord> values = userSQLHelper.getAllUsers();

        if(values.isEmpty()) {
            Toast.makeText(getApplicationContext()," No record",Toast.LENGTH_SHORT).show();
        }
        UserRecordAdapter adapter = new UserRecordAdapter(this,
                R.layout.user_record, values);

        listViewRecords.setAdapter(adapter);






    }
}