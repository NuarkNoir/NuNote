package xyz.nuark.nunote;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.common.collect.Lists;

import java.text.MessageFormat;
import java.util.ArrayList;

import static com.orm.SugarContext.init;

public class MainActivity extends AppCompatActivity {

    public static MainActivity INSTANCE;
    public static ArrayList<Note> notesList;
    private ListView lv_noteslist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(this); // Initialization of Sugar ORM
        setContentView(R.layout.activity_main);
        INSTANCE = this;
        lv_noteslist = findViewById(R.id.notesList);
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateNotesAndList();
    }

    public void buttonHandler(View view) {
        switch (view.getId()) {
            case R.id.btn_addnote:
                INSTANCE.startActivityForResult(new Intent(INSTANCE, NoteCreationDialog.class), 2);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {return;}
        Note note;
        switch (requestCode) {
            case 1: // Redactor RQ
                long old_note_id = data.getLongExtra("old", 1000000000L);
                deleteNote(old_note_id);
                note = data.getParcelableExtra("note");
                Toast.makeText(INSTANCE, MessageFormat.format("[{0}] {1} saved", note.getId(), note.getName()), Toast.LENGTH_SHORT).show();
                saveNote(note);
                break;
            case 2: // Creator RQ
                note = data.getParcelableExtra("note");
                INSTANCE.startActivityForResult(new Intent(INSTANCE, RedactorActivity.class).putExtra("note", note), 1);
                break;
        }
    }

    public void updateNotesAndList() {
        notesList = Lists.newArrayList(Note.findAll(Note.class));
        lv_noteslist.setAdapter(new NotesListAdapter(INSTANCE, notesList));
    }

    public void saveNote(Note note) {
        note.save();
        updateNotesAndList();
    }

    public void deleteNote(long id) {
        try {
            Note.findById(Note.class, id).delete();
            updateNotesAndList();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteNote(Note note) {
        deleteNote(note.getId());
    }
}

