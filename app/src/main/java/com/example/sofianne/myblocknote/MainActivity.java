package com.example.sofianne.myblocknote;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.sofianne.myblocknote.R.id.main_listview_notes;

public class MainActivity extends AppCompatActivity {

    private ListView mListViewnotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListViewnotes = findViewById(main_listview_notes);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_main_new_note:
                /*Intent newNoteActivity = new Intent(this,NoteActivity.class);*/
                startActivity(new Intent(this, NoteActivity.class));
                break;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mListViewnotes.setAdapter(null);
        ArrayList<Note> notes = Utilities.getAllSavedNotes(this);
        if (notes == null || notes.size() == 0) {
            Toast.makeText(this, "NO Saved Notes", Toast.LENGTH_SHORT).show();
            return;
        } else {
            NoteAdapter na = new NoteAdapter(this, R.layout.item_note, notes);
            mListViewnotes.setAdapter(na);

            mListViewnotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String fileName = ((Note) mListViewnotes.getItemAtPosition(position)).getDateTime() + Utilities.FILE_EXTENSION;
                    Intent viewNoteIntent = new Intent(getApplicationContext(), NoteActivity.class);
                    viewNoteIntent.putExtra("NOTE_FILE", fileName);
                    startActivity(viewNoteIntent);

                }
            });

        }
    }
}
