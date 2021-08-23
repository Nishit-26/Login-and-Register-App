package com.example.loginandregister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.local.ReferenceSet;

public class Login extends AppCompatActivity {

    EditText username, password;
    TextView signuptext;
    Button login;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        username = findViewById(R.id.etUsername);
        password = findViewById(R.id.etPassword);
        login = findViewById(R.id.btnLogin);
        signuptext = findViewById(R.id.tvSignuptext);


        //Login process
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get input
                String inputUsername = username.getText().toString();
                String inputPassword = password.getText().toString();

                //check whether input is available or not
                if (inputUsername.isEmpty()) {
                    username.setError("enter username");
                    username.requestFocus();
                } else if (inputPassword.isEmpty()) {
                    password.setError("enter password");
                    password.requestFocus();
                }else{

                    //checking username and password as per database and then login process
                    final String userData = username.getText().toString();
                    final String passData = password.getText().toString();

                    firebaseDatabase = FirebaseDatabase.getInstance();
                    reference = firebaseDatabase.getReference("Users");

                    //check whether entered username is available on database or not
                    Query checkUsername = reference.orderByChild("username").equalTo(userData);
                    checkUsername.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            //if username is available then check for password
                            if (snapshot.exists()) {
                                //Check password
                                String checkPassword = snapshot.child(userData).child("password").getValue(String.class);
                                if (checkPassword.equals(passData)) {
                                    //if password is correct
                                    Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();
                                    Intent intentToHomepage = new Intent(getApplicationContext(), Home.class);
                                    startActivity(intentToHomepage);
                                    finish();
                                } else {
                                    //if password is wrong
                                    password.setError("Wrong password");
                                    password.requestFocus();
                                }
                            } else {
                                //else set error
                                username.setError("username dose not exists");
                                username.requestFocus();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    //End of addListenerForSingleValueEven of checkUsername
                }


            }
        });
        //End of loginButton onclick listener

        //SignUp call
        signuptext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentToSignup = new Intent(getApplicationContext(), Signup.class);
                startActivity(intentToSignup);
            }
        });
        //End of signup text onclick listener
    }
}