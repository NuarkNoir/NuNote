package xyz.nuark.nunote;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.MessageFormat;
import java.util.ArrayList;

/**
 * Created with love by Nuark on 02.01.2018.
 */
class NotesListAdapter extends BaseAdapter {

    private final Activity context;
    private final ArrayList<Note> notes;

    NotesListAdapter(Activity context, ArrayList<Note> notes) {
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
        tv_notecreationtime.setText(MessageFormat.format(context.getString(R.string.str_note_created), note.getDateOfCreation()));
        tv_noteredactedtime.setText(MessageFormat.format(context.getString(R.string.str_note_redacted), note.getDateOfLastModification()));
        ImageButton btn_delete = view.findViewById(R.id.btn_delete);
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.INSTANCE.deleteNote(note);
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivityForResult(new Intent(context, RedactorActivity.class).putExtra("note", note), 1);
            }
        });
        return view;
    }

    @Override
    public int getCount() {
        try {
            return notes.size();
        } catch (Exception e){
            System.out.println("Список заметок нуловый. Норм?");
            return 0;
        }
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
