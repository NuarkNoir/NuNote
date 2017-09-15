package xyz.nuark.nunote;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RedactorActivity extends AppCompatActivity {

    private EditText et_notetitle;
    private EditText et_notetext;
    Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redactor);
        initView();
        note = getIntent().getExtras().getParcelable("note");
        et_notetitle.setText(note.getName());
        et_notetext.setText("Wait...");
        try {
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/NuNotes/", note.getPath());
			String text = Files.toString(file, Charset.defaultCharset());
            et_notetext.setText(text);
        } catch (IOException e){
            et_notetext.setText("");
            e.printStackTrace();
        }

    }

    private void initView() {
        et_notetitle = findViewById(R.id.et_notetitle);
        et_notetext = findViewById(R.id.et_notetext);
    }

    private void save() {
        String notetitle = et_notetitle.getText().toString().trim();
        if (TextUtils.isEmpty(notetitle)) note.setName("Untitled");
        String notetext = et_notetext.getText().toString().trim();
        note.setDateOfLastModification(new SimpleDateFormat("dd.MM.yy HH꞉mm꞉ss", Locale.getDefault()).format(new Date()));
        try {
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/NuNotes/", note.getPath());
            if (!file.exists()) file.createNewFile();
            Files.write(notetext.getBytes(), file);
        } catch (IOException e) {}
        Intent intent = new Intent();
        intent.putExtra("note", note);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void save(View view) {
        save();
    }
}

