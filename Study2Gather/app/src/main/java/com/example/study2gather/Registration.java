package com.example.study2gather;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Registration extends AppCompatActivity implements View.OnClickListener{
    // Global vars
    private boolean sexValidate = false;
    private String DOBStr = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Launch the layout -> splash.xml
        setContentView(R.layout.registration);
    }

    // Handles radio button validations
    public void onRadioButtonClicked(View v){
        boolean checked = ((RadioButton) v).isChecked();


        switch(v.getId()){
            case R.id.registerSexMale:
                if(checked){
                    // Code for checking male
                    sexValidate = true;
                }
                break;
            case R.id.registerSexFemale:
                if(checked){
                    // Code for checking female // Ignore dup branch, 2 branches needed for data saving (FIREBASE)
                    sexValidate = true;
                }
                break;
        }
    }

    @Override
    public void onClick(View v){
        Intent i;
        switch(v.getId()){
            // Handles for DatePicker
            case R.id.registerDOBBtn:
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog dpd;

                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH)+1;
                int year = calendar.get(Calendar.YEAR);

                dpd = new DatePickerDialog(Registration.this, new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth){
                        TextView DOBLabel = findViewById(R.id.registerDOBLabel);
                        DOBStr = dayOfMonth+" / "+(month+1)+" / "+year;
                        DOBLabel.setText(DOBStr);
                        // Saving of data to firebase can be done here to get the DOB input!
                    }
                }, year, month, day);
                dpd.getDatePicker().setMaxDate(new Date().getTime());
                dpd.show();
                break;
            // Handles for Register Button
            case R.id.registerButton:
                // Input validation
                ArrayList<EditText> edtTxtAry = new ArrayList<>();
                edtTxtAry.add(findViewById(R.id.registerUsername));
                edtTxtAry.add(findViewById(R.id.registerEmail));
                edtTxtAry.add(findViewById(R.id.registerPassword));
                edtTxtAry.add(findViewById(R.id.registerConfirmPassword));

                // Change to String, push into new array
                ArrayList<String> edtTxtStrAry = new ArrayList<>();
                for(EditText edtTxt : edtTxtAry) edtTxtStrAry.add(edtTxt.getText().toString());

                // For email validations
                String emailReg = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

                // Validations
                if(edtTxtStrAry.get(0).length() < 4 || edtTxtStrAry.get(0).length() > 18 ){
                    // Username
                    Toast.makeText(Registration.this, "Username must be in between 4 to 18 characters!", Toast.LENGTH_SHORT).show();
                }else if(!edtTxtStrAry.get(1).matches(emailReg) || edtTxtStrAry.get(1).length() < 0 ){
                    // Email
                    Toast.makeText(Registration.this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
                }else if(edtTxtStrAry.get(2).length() < 8){
                    // Password
                    Toast.makeText( Registration.this, "Password must be 8 characters or above!", Toast.LENGTH_SHORT).show();
                }else if(!edtTxtStrAry.get(3).equals(edtTxtStrAry.get(2))){
                    // Confirm Password
                    Toast.makeText(Registration.this, "Password confirmed does not tally!", Toast.LENGTH_SHORT).show();
                }else if(!sexValidate){
                    // Sex RadioButton
                    Toast.makeText(Registration.this, "Please choose your sex!", Toast.LENGTH_SHORT).show();
                }else if(DOBStr.length() == 0){
                    Toast.makeText(Registration.this, "Please input your Date of Birth!", Toast.LENGTH_SHORT).show();
                }else{
                    // Use edtTxtStrAry to get input data for saving to FireBase
                    Toast.makeText(Registration.this, "Created new user successfully", Toast.LENGTH_SHORT).show();
                    i = new Intent(getApplicationContext(), Login.class);
                    startActivity(i);
                }
        }
    }
}
