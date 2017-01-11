package com.example.tm.firebaseauthdemo;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    private TextView textViewUserEmail;
    private Button logoutButton, saveButton;

    private EditText editTextUserName, editTextUserAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        databaseReference = FirebaseDatabase.getInstance().getReference();

        FirebaseUser user = firebaseAuth.getCurrentUser();

        logoutButton = (Button) findViewById(R.id.logoutButton);
        saveButton = (Button) findViewById(R.id.saveButton);

        textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);
        textViewUserEmail.setText("Welcome " + user.getEmail());

        editTextUserName = (EditText) findViewById(R.id.editTextUserName);
        editTextUserAddress = (EditText) findViewById(R.id.editTextUserAddress);

        logoutButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);

    }

    private void saveUserInformation(){

        String name = editTextUserName.getText().toString().trim();
        String address = editTextUserAddress.getText().toString().trim();

        UserInformation userInfo = new UserInformation(name, address);

        FirebaseUser user = firebaseAuth.getCurrentUser();

        databaseReference.child(user.getUid()).setValue(userInfo);

        Toast.makeText(this, "Information has been saved!", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onClick(View view) {

        if(view == logoutButton){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        if(view == saveButton){
            saveUserInformation();
        }
    }
}
