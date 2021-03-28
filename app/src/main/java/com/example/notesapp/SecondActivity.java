package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.io.IOException;
import java.util.HashSet;

public class SecondActivity extends AppCompatActivity {
    int noteItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        EditText textBox = (EditText) findViewById(R.id.textBox);
        Intent intent = getIntent();
        noteItem = intent.getIntExtra("noteItem",-1);
        if(noteItem!=-1)
        {
            textBox.setText(MainActivity.notes.get(noteItem));
        }
        else
        {
            MainActivity.notes.add("");
            noteItem = MainActivity.notes.size()-1;
            MainActivity.arrayAdapter.notifyDataSetChanged();
        }
        textBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                   MainActivity.notes.set(noteItem, String.valueOf(s));
                   MainActivity.arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.notesapp", Context.MODE_PRIVATE);
            HashSet<String> set = new HashSet(MainActivity.notes);
            sharedPreferences.edit().putStringSet("notes",set).apply();

    }
}