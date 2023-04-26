package com.example.notewithdb.orlov;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {

    EditText etmlCtx;

    int nId;
    String nTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        etmlCtx = findViewById(R.id.etmlContent);

        Intent i = getIntent();
        nId = i.getIntExtra("id", 0);
        nTxt = i.getStringExtra("txt");
        setTitle("Note " + String.valueOf(nId));

        etmlCtx.setText(nTxt);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu2, menu);
        return true;
        //return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menuExit:
                finish();
                return true;
            case R.id.menuSave:
                String newTxt = etmlCtx.getText().toString();
                finalN.notes.Update(nId, newTxt);
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menuDelete:

                AlertDialog.Builder bld = new AlertDialog.Builder(this);
                bld.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) { }
                });
                bld.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int id) {
                        Toast.makeText(getApplicationContext(), String.format("Deleted note %d", nId), Toast.LENGTH_SHORT).show();
                        finalN.notes.Delete(nId);
                        Intent i = new Intent();
                        setResult(RESULT_OK, i);
                        finish();
                    }
                });
                AlertDialog dlg = bld.create();
                dlg.setTitle("Delete note");
                dlg.setMessage("Are you sure?");
                dlg.show();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}