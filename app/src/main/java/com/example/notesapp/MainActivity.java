package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    SharedPreferences sharedPreferences;
    static ArrayList<String> notes = new ArrayList<String>();
    static  ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = this.getSharedPreferences("com.example.notesapp", Context.MODE_PRIVATE);
        listView = (ListView) findViewById(R.id.listView);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,notes);

         HashSet<String> set = (HashSet<String>) sharedPreferences.getStringSet("notes",null);

            if(set == null)
            { notes.add("Example note");}
            else
            {
                notes = new ArrayList(set);
            }
            listView.setAdapter(arrayAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getApplicationContext(),SecondActivity.class);
                    intent.putExtra("noteItem",position);
                    startActivity(intent);

                }
            });
            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    new AlertDialog.Builder(getApplicationContext())
                            .setIcon(android.R.drawable.ic_delete)
                            .setTitle("Are You Sure?")
                            .setMessage("Do you permanently want to delete this?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    notes.remove(position);
                                    arrayAdapter.notifyDataSetChanged();
                                    HashSet<String> set = new HashSet(MainActivity.notes);
                                    sharedPreferences.edit().putStringSet("notes",set).apply();
                                }
                            })
                            .setNegativeButton("No",null)
                            .show();
                    return true;
                }
            });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
         super.onOptionsItemSelected(item);
         if(item.getItemId()==R.id.addNote)
         {
             Intent intent = new Intent(getApplicationContext(),SecondActivity.class);
             startActivity(intent);
             return true;
         }
         return false;
    }
}