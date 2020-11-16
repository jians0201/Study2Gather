package com.example.study2gather;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Messages extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Launch the layout -> splash.xml
        setContentView(R.layout.messages);
    }

    public void onClick(View v){
        Intent i;

        switch(v.getId()){
            case R.id.messagesChatButton:
                i = new Intent(getApplicationContext(), Chat.class);
                startActivity(i);
                break;
        }
    }
}
