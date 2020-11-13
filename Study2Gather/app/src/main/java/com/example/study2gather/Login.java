package com.example.study2gather;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class Login extends AppCompatActivity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Launch the layout -> splash.xml
        setContentView(R.layout.login);
    }

    @Override
    public void onClick(View v){
        Intent i;
        switch(v.getId()){
            case R.id.loginLoginButton:
                TextView emailTxt = (TextView)findViewById(R.id.loginEmailAddress);
                TextView passwordTxt = (TextView)findViewById(R.id.loginPassword);
                String emailReg = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

                if(!emailTxt.getText().toString().matches(emailReg) || emailTxt.getText().toString().length() == 0 || passwordTxt.getText().toString().length() < 8){
                    Toast.makeText(getApplicationContext(), "Your Login Details are incorrect!", Toast.LENGTH_SHORT).show();
                }else{
                    i = new Intent(getApplicationContext(), Home.class);
                    startActivity(i);
                }
                break;
            case R.id.loginRegisterButton:
                i = new Intent(getApplicationContext(), Registration.class);
                startActivity(i);
        }
    }
}
