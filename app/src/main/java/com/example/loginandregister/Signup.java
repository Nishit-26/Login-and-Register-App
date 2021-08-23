package com.example.loginandregister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {

    EditText username, password, email;
    TextView logintext;
    RadioGroup gender;
    RadioButton Gender;
    Button signup;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        username = findViewById(R.id.etUsername);
        password = findViewById(R.id.etPassword);
        email = findViewById(R.id.etEmail);
        logintext = findViewById(R.id.tvLogintext);
        signup = findViewById(R.id.btnSignup);
        gender = findViewById(R.id.rbGender);


        //Signup process
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get the entered data
                String inputsignupname = username.getText().toString();
                String inputsignuppassword = password.getText().toString();
                String inputsignupemail = email.getText().toString();


                //Check whether fields are empty or not
                if (inputsignupname.isEmpty()) {
                    username.setError("enter Username");
                    username.requestFocus();
                }  else if (inputsignupemail.isEmpty()) {
                    email.setError("enter Emailaddress");
                    email.requestFocus();
                } else if (!inputsignupemail.matches("^[a-z0-9](\\.?[a-z0-9]){5,}@g(oogle)?mail\\.com$")) {
                    email.setError("Please enter valid Emailaddress");
                } else if (inputsignuppassword.isEmpty()) {
                    password.setError("enter Password");
                    password.requestFocus();
                } else if (inputsignuppassword.length() < 8) {
                    password.setError("Password length should be minimum 8 characters");
                } else if (gender.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(getApplicationContext(), "Please select gender", Toast.LENGTH_SHORT).show();
                } else {
                    //firebase code
                    firebaseDatabase = FirebaseDatabase.getInstance();
                    reference = firebaseDatabase.getReference("Users");

                    //variables for getting data
                    String inputsignupname2 = username.getText().toString();
                    String inputsignuppassword2 = password.getText().toString();
                    String inputsignupemail2 = email.getText().toString();


                    //getting data using getter and setter method
                    Storingdata storingdata = new Storingdata(inputsignupname2, inputsignuppassword2, inputsignupemail2);

                    //add reference into firebase
                    reference.child(inputsignupname2).setValue(storingdata);


                    //Toast for registered
                    Toast.makeText(Signup.this, "Registered Successfully", Toast.LENGTH_SHORT).show();

                    Intent intentTOHomepage = new Intent(getApplicationContext(), Home.class);
                    startActivity(intentTOHomepage);
                    finish();
                }


            }
        });
        //End of signup button onclick listener


        //Login text
        logintext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentToLogin = new Intent(getApplicationContext(), Login.class);
                startActivity(intentToLogin);
                finish();

            }
        });
        //End of login text onclick listener
    }
}