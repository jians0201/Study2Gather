package com.example.study2gather;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registration extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Launch the layout -> splash.xml
        setContentView(R.layout.registration);
    }

    public void onClick(View v){
        Intent i;
        switch(v.getId()){
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
                }else{
                    i = new Intent(getApplicationContext(), Login.class);
                    startActivity(i);
                }
        }
    }
}
