package xyz.nuark.nunote;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import in.cubestack.android.lib.storm.service.BaseService;
import in.cubestack.android.lib.storm.service.StormService;

/**
 * Created by Nuark with love on 15.09.2017.
 * Protected by QPL-1.0
 */

public class Globals {
    public static ArrayList<Note> readNotesInfo(Context cont){
        try {
            StormService service = new BaseService(cont, NotesDB.class);
            return (ArrayList<Note>) service.findAll(Note.class);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(cont, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            return new ArrayList<>();
        }
    }

    public static void writeNote(Context cont, Note note){
        try {
            StormService service = new BaseService(cont, NotesDB.class);
            service.save(note);
        }
        catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(cont, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public static void writeNotes(Context cont, ArrayList<Note> notes){
        try {
            StormService service = new BaseService(cont, NotesDB.class);
            service.save(notes);
        }
        catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(cont, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public static boolean deleteFile(String path){
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/NuNotes/", path);
        return !file.exists() || file.delete();
    }
}