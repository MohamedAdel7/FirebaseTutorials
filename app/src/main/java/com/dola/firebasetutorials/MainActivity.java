package com.dola.firebasetutorials;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Button mFirebaseBtn;
    private Button mDeleteBtn;
    private EditText mNameField;
    private EditText mPhoneField;
    private DatabaseReference mDatabase;
    private EditText mdeletedNumber;

    HashMap<String,String> dataMap = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseBtn = (Button) findViewById(R.id.firebaseBtn);
        mDeleteBtn   = (Button) findViewById(R.id.deleteBtn);
        mNameField = (EditText) findViewById(R.id.name_field);
        mPhoneField = (EditText) findViewById(R.id.phone_field);
        mdeletedNumber = (EditText) findViewById(R.id.deletedNumber);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("USERS");



        mFirebaseBtn.setOnClickListener(new View.OnClickListener() {        //add button
            @Override
            public void onClick(View v) {

                String name = mNameField.getText().toString().trim();
                String phone = mPhoneField.getText().toString().trim();
                if(dataMap.containsValue(phone)){
                    Toast.makeText(MainActivity.this,"the phone is existing ",Toast.LENGTH_LONG).show();
                    return;
                }
                else {
                HashMap<String,String> newdataMap = new HashMap<String, String>();
                newdataMap.put("Name",name);
                newdataMap.put("Phone",phone);
                dataMap.put("Name",name);
                dataMap.put("Phone",phone);

                mDatabase.push().setValue(newdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this,"success",Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(MainActivity.this,"faild",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }}
        });
        mDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number =  mPhoneField.getText().toString();
                deleteNum(number);
            }
        });
    }
    private void deleteNum(String Number){

        if(dataMap.containsValue(Number)){
            dataMap.remove(Number);
            DatabaseReference drf = FirebaseDatabase.getInstance().getReference().child("USERS");
            drf.removeValue();
            drf.push().setValue(dataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(MainActivity.this,"success update",Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(MainActivity.this,"faild",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
        else
            Toast.makeText(MainActivity.this,"number not found",Toast.LENGTH_LONG).show();
    }
}
