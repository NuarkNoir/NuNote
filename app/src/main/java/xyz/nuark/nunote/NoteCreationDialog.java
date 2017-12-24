package xyz.nuark.nunote;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NoteCreationDialog extends AppCompatActivity {

    private EditText et_newnotetitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_creation_dialog);
        et_newnotetitle = findViewById(R.id.et_newnotetitle);
    }

    public void createNote(View view) {
        String notetitle = et_newnotetitle.getText().toString().trim();
        if (TextUtils.isEmpty(notetitle)) notetitle = "Untitled";
        String date = new SimpleDateFormat("dd.MM.yy HH꞉mm꞉ss", Locale.getDefault()).format(new Date());
        Note note = new Note(MainActivity.gna.size(), notetitle, date, date);
        System.out.println(note.toString());
        Intent intent = new Intent();
        intent.putExtra("note", note);
        setResult(RESULT_OK, intent);
        finish();
    }
}
