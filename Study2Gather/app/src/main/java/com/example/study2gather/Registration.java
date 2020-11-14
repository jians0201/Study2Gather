package com.example.study2gather;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import android.util.Patterns;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Registration extends AppCompatActivity implements View.OnClickListener{
    // Global vars
    private String DOBStr = "";
    private String gender = "";

    private TextView DOBTextField;
    private EditText rgUname, rgEmail, rgPword, rgCfmPword;
    private ProgressBar pgbar;
    private RadioButton rgSexMale, rgSexFemale;

    private FirebaseAuth fAuth;
    private FirebaseDatabase fDb;

    private Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Launch the layout -> splash.xml
        setContentView(R.layout.registration);
        DOBTextField = findViewById(R.id.registerDOBTxt);
        rgUname = findViewById(R.id.registerUsername);
        rgEmail = findViewById(R.id.registerEmail);
        rgPword = findViewById(R.id.registerPassword);
        rgCfmPword = findViewById(R.id.registerConfirmPassword);
        fAuth = FirebaseAuth.getInstance();
        rgSexMale = findViewById(R.id.registerSexMale);
        rgSexFemale = findViewById(R.id.registerSexFemale);
        pgbar = findViewById(R.id.registerProgressBar);
    }

    // Handles radio button validations
    public void onRadioButtonClicked(View v){
        boolean checked = ((RadioButton) v).isChecked();
        rgSexFemale.setError(null);
        rgSexMale.setError(null);
        switch(v.getId()){
            case R.id.registerSexMale:
                if(checked){
                    // Code for checking male
                    gender = "male";
                }
                break;
            case R.id.registerSexFemale:
                if(checked){
                    // Code for checking female
                    gender = "female";
                }
                break;
        }
    }

    private boolean validateUserData(String uname, String email, String pword, String cfmPword) {
        // For email validations
//        String emailReg = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

        // Validations
        if(uname.length() < 4 || uname.length() > 18 ){
            // Username
            rgUname.setError("Username must be 4-18 characters!");
            rgUname.requestFocus();
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches() || email.length() < 0 ){ //edtTxtStrAry.get(1).matches(emailReg) replaced with patterns library
            // Email
            rgEmail.setError("Enter a valid Email!");
            rgEmail.requestFocus();
        }else if(pword.length() < 8){
            // Password
            rgPword.setError("Password must be at least 8 characters");
            rgPword.requestFocus();
        }else if(!cfmPword.equals(pword)){
            // Confirm Password
            rgCfmPword.setError("Passwords do not match!");
            rgCfmPword.requestFocus();
        }else if(gender.length()==0){
            // Sex RadioButton
            rgSexFemale.setError("Select a Gender!");
            rgSexMale.setError("Select a Gender!");
        }else if(DOBStr.length() == 0){
            DOBTextField.setError("Enter a Date of Birth!");
            DOBTextField.requestFocus();
        }else{
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View v){
//        Intent i;
        switch(v.getId()){
            // Handles for DatePicker
            case R.id.registerDOBBtn:
                DOBTextField.setError(null);
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog dpd;

                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH)+1;
                int year = calendar.get(Calendar.YEAR);

                dpd = new DatePickerDialog(Registration.this, new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth){
                        DOBStr = dayOfMonth+" / "+(month+1)+" / "+year;
                        DOBTextField.setText(DOBStr);
                    }
                }, year, month, day);
                dpd.getDatePicker().setMaxDate(new Date().getTime());
                dpd.show();
                break;

            // Handles for Register Button
            case R.id.registerButton:
                //Get text fields
                String username = rgUname.getText().toString().trim();
                String email = rgEmail.getText().toString().trim();
                String password = rgPword.getText().toString().trim();
                String confirmPassword = rgCfmPword.getText().toString().trim();

                //Validate Inputs
                if (validateUserData(username, email, password, confirmPassword)) {
                    //Show progress bar
                    pgbar.setVisibility(View.VISIBLE);
                    //Create user
                    fAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        UserObj user = new UserObj(username,email,password,gender,DOBStr);
                                        fDb.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Toast.makeText(Registration.this, "Created new user successfully", Toast.LENGTH_SHORT).show();
                                                            pgbar.setVisibility(View.GONE); //Remove progress bar
                                                            i = new Intent(getApplicationContext(), Login.class);
                                                            startActivity(i);
                                                        } else {
                                                            Toast.makeText(Registration.this, "Failed to Register! Try Again!", Toast.LENGTH_SHORT).show();
                                                            pgbar.setVisibility(View.GONE); //Remove progress bar
                                                        }
                                                    }
                                                });
                                    } else {
                                        Toast.makeText(Registration.this, "Failed to Register! Try Again!", Toast.LENGTH_SHORT).show();
                                        pgbar.setVisibility(View.GONE); //Remove progress bar
                                    }
                                }
                            });
                }
                break;
        }
    }
}
