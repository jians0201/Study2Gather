package com.example.study2gather;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity implements View.OnClickListener{
    private EditText loginEmail, loginPword;
    private ProgressBar pgbar;

    private Intent i;

    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Launch the layout -> splash.xml
        setContentView(R.layout.login);
        loginEmail = findViewById(R.id.loginEmailAddress);
        loginPword = findViewById(R.id.loginPassword);
        pgbar = findViewById(R.id.loginProgressBar);
        fAuth = FirebaseAuth.getInstance();
    }

    private boolean validateUserData(String email, String password) {
        if (email.isEmpty()) {
            loginEmail.setError("Email is Required!");
            loginEmail.requestFocus();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            loginEmail.setError("Enter a Valid Email!");
            loginEmail.requestFocus();
        } else if (password.isEmpty()) {
            loginPword.setError("Password is Required!");
            loginPword.requestFocus();
        } else if (password.length() < 8) {
            loginPword.setError("Enter a Valid Password!");
            loginPword.requestFocus();
        }
        else {
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.loginLoginButton:
                String email = loginEmail.getText().toString().trim();
                String password = loginPword.getText().toString().trim();

                if (validateUserData(email, password)) {
                    pgbar.setVisibility(View.VISIBLE);
                    fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
//                                Toast.makeText(Login.this, "Logged In Successfully", Toast.LENGTH_SHORT).show();
                                pgbar.setVisibility(View.GONE);
//                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser(); //leave it first I might need later on
                                i = new Intent(getApplicationContext(), Home.class);
                                startActivity(i);
                            } else {
                                Toast.makeText(Login.this, "Failed to login! Email or Password is incorrect!", Toast.LENGTH_LONG).show();
                                pgbar.setVisibility(View.GONE);
                            }
                        }
                    });


                }

                break;
            case R.id.loginRegisterButton:
                i = new Intent(getApplicationContext(), Registration.class);
                startActivity(i);
                break;
        }
    }
}
