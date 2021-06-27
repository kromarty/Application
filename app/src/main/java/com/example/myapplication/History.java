package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class History extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history2);
        TextView hist = findViewById(R.id.history);
        String text = "";
        if (MainActivity.history.size() != 0){
            text = MainActivity.history.get(0);
            for(int i=1; i<MainActivity.history.size(); i++){
                text = text + "\n" + MainActivity.history.get(i);
            }
        }
        hist.setText(text);
    }

    public void onClick(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}