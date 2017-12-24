package xyz.nuark.nunote;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;

import static xyz.nuark.nunote.Globals.readNotesInfo;

public class MainActivity extends AppCompatActivity {

    public static MainActivity INSTANCE;
    private ListView lv_noteslist;
    public static ArrayList<Note> gna;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        INSTANCE = this;
        if (ContextCompat.checkSelfPermission(INSTANCE, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            Toast.makeText(INSTANCE, "Requesting access to SD-Card...", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(INSTANCE, new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE }, 1);
            recreate();
        }
        checkForDir();
        lv_noteslist = findViewById(R.id.notesList);
        gna = Globals.readNotesInfo(INSTANCE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        readNotesInfo(INSTANCE);
        updateList();
    }

    private void checkForDir() {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/NuNotes/";
        if (new File(path).exists()) return;
        if (!new File(path).mkdir())
            Toast.makeText(this, "Error while creating notes folder", Toast.LENGTH_SHORT).show();
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
        switch (requestCode) {
            case 1: // Redactor RQ
                Note note = data.getParcelableExtra("note");
                System.out.println(note.getName());
                gna.add(note);
                updateList();
                Toast.makeText(INSTANCE, note.getName() + " saved", Toast.LENGTH_SHORT).show();
                Globals.writeNote(INSTANCE, note);
                break;
            case 2: // Creator RQ
                Note note2 = data.getParcelableExtra("note");
                INSTANCE.startActivityForResult(new Intent(INSTANCE, RedactorActivity.class).putExtra("note", note2), 1);
                break;
        }
    }

    public void updateList() {
        lv_noteslist.setAdapter(new NotesListAdapter(INSTANCE, gna));
    }
}

class NotesListAdapter extends BaseAdapter {

    private final Activity context;
    private final ArrayList<Note> notes;

    public NotesListAdapter(Activity context, ArrayList<Note> notes) {
        this.context = context;
        this.notes = notes;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = mInflater.inflate(R.layout.note_item, null);
        }
        final Note note = getItem(i);
        TextView tv_notetitle = view.findViewById(R.id.tv_notetitle);
        TextView tv_notecreationtime = view.findViewById(R.id.tv_notecreationtime);
        TextView tv_noteredactedtime = view.findViewById(R.id.tv_noteredactedtime);
        tv_notetitle.setText(note.getName());
        tv_notecreationtime.setText(MessageFormat.format("Создана: {0}", note.getDateOfCreation()));
        tv_noteredactedtime.setText(MessageFormat.format("Отредактированна: {0}", note.getDateOfLastModification()));
        ImageButton btn_delete = view.findViewById(R.id.btn_delete);
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Globals.deleteFile(note.getPath())) {
                    MainActivity.gna.remove(i);
                    Globals.writeNotes(context, MainActivity.gna);
                }
                else Toast.makeText(context, MessageFormat.format(context.getString(R.string.deleting_error), note.getName()), Toast.LENGTH_SHORT).show();
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.gna.remove(i);
                context.startActivityForResult(new Intent(context, RedactorActivity.class).putExtra("note", note), 1);
            }
        });
        return view;
    }

    @Override
    public int getCount() {
        return notes.size();
    }

    @Override
    public Note getItem(int i) {
        return notes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
}
