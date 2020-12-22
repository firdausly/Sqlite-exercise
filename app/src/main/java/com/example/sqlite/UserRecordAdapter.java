package com.example.sqlite;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class UserRecordAdapter extends ArrayAdapter<UserRecord> {
    public UserRecordAdapter(Activity context, int resource, List<UserRecord> list){
        super(context,resource,list);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        UserRecord userRecord = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).
                    inflate(R.layout.user_record, parent,false);
        }

        TextView textViewPhone, textViewName, textViewEmail;

        textViewPhone = (TextView) convertView.findViewById(R.id.textViewPhone);
        textViewName = (TextView) convertView.findViewById(R.id.textViewName);
        textViewEmail = (TextView) convertView.findViewById(R.id.textViewEmail);

        textViewPhone.setText("Phone: "+userRecord.getPhone());
        textViewName.setText("Name: "+userRecord.getName());
        textViewEmail.setText("EMail: "+userRecord.getEmail());

        return convertView;

    }
}
