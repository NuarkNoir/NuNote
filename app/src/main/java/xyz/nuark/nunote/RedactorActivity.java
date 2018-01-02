package xyz.nuark.nunote;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RedactorActivity extends AppCompatActivity {

    private EditText et_notetitle, et_notetext;
    Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redactor);
        try {
            note = getIntent().getExtras().getParcelable("note");
        } catch (NullPointerException e){
            System.out.println("Бандл пустой. Норма?");
            e.printStackTrace();
            finish();
        }
        initView();
        et_notetitle.setText(note.getName());
        et_notetext.setText(note.getText());

    }

    private void initView() {
        et_notetitle = findViewById(R.id.et_notetitle);
        et_notetext = findViewById(R.id.et_notetext);
    }

    private void save() {
        String note_title = et_notetitle.getText().toString().trim();
        if (note_title.length() <= 0)
            note.setName(getString(R.string.str_note_std_name));
        note.setDateOfLastModification(new SimpleDateFormat("dd.MM.yy HH꞉mm꞉ss", Locale.getDefault()).format(new Date()));
        note.setText(et_notetext.getText().toString().trim());
        setResult(RESULT_OK, new Intent().putExtra("note", note).putExtra("old", note.getId()));
        finish();
    }

    public void save(View view) {
        save();
    }
}

