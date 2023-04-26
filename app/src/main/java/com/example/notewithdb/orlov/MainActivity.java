package com.example.notewithdb.orlov;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayAdapter<Note> adp;
    ArrayList<Note> list = new ArrayList<Note>();
    ListView lv;

    Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ctx = this;
        finalN.notes = new DB(this, "notes.db", null, 1);

        adp = new ArrayAdapter<Note>(this, android.R.layout.simple_list_item_1);
        lv = findViewById(R.id.list);
        lv.setAdapter(adp);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                Note note = adp.getItem(pos);
                Intent i = new Intent(ctx, MainActivity2.class);
                i.putExtra("id", note.id);
                i.putExtra("txt", note.txt);
                startActivityForResult(i, 1);
            }
        });

        updateNotes();
    }

    void updateNotes() {
        list.clear();
        adp.clear();
        finalN.notes.getAllNotes(list);
        for (int i = 0; i < list.size(); i++) {
            adp.add(list.get(i));
        }
        adp.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menuNew:
                int nid = finalN.notes.getMaxId() + 1;
                finalN.notes.addNote(nid, "Text...");
                updateNotes();

                Intent i = new Intent(ctx, MainActivity2.class);
                i.putExtra("id", nid);
                i.putExtra("txt", "Text...");
                startActivityForResult(i, 1);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int reqCode, int resCode, @Nullable Intent data){
        updateNotes();
        super.onActivityResult(reqCode, resCode, data);
    }

/*
    public void New(View v)
    {
        Note note = new Note();
        note.title = "New note";
        note.content = "Some content";

        adp.add(note);
        int pos = adp.getPosition(note);

        Intent i = new Intent(this, DataNote.class);
        i.putExtra("id", pos);
        i.putExtra("title", note.title);
        i.putExtra("content", note.content);

        startActivityForResult(i, 0);
    }

    public void Edit(View v)
    {
        Note note = adp.getItem(sel);

        Intent i = new Intent(this, DataNote.class);
        i.putExtra("id", sel);
        i.putExtra("title", note.title);
        i.putExtra("content", note.content);

        startActivityForResult(i, 0);
    }
    */
}