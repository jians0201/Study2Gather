package com.example.study2gather.ui.messages;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.study2gather.Chat;
import com.example.study2gather.R;

public class MessagesFragment extends Fragment {

    private MessagesViewModel messagesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        messagesViewModel = new ViewModelProvider(this).get(MessagesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_messages, container, false);
        final TextView textView = root.findViewById(R.id.text_messages);

        messagesViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        final Button button = root.findViewById(R.id.messagesChatButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View root){
                Intent i;

                switch(root.getId()){
                    case R.id.messagesChatButton:
                        i = new Intent(getContext(), Chat.class);
                        startActivity(i);
                        break;
                }
            }
        });
        return root;
    }

}