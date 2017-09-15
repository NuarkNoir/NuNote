package xyz.nuark.nunote;

import android.content.Context;
import android.os.Environment;

import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by Nuark with love on 15.09.2017.
 * Protected by QPL-1.0
 */

public class Globals {
    private static String notesSchema = "notesSchema.json";

    public static void readNotesInfo(Context cont){
        File file = new File(cont.getFilesDir().getAbsolutePath(), notesSchema);
        if (!file.exists()){
            try { file.createNewFile(); } catch (IOException e) { e.printStackTrace(); }
            return;
        }
        try {
            if (Files.readLines(file, Charset.defaultCharset()).size() < 1) return;
            String json = Files.toString(file, Charset.defaultCharset());
            MainActivity.gna = new Gson().fromJson(json, GlobalNotesArray.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeNotesInfo(Context cont){
        File file = new File(cont.getFilesDir().getAbsolutePath(), notesSchema);
        try {file.createNewFile();} catch (IOException e) {e.printStackTrace();}
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(MainActivity.gna);
        System.out.println(json);
        try {
            Files.write(json.getBytes(), file);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean deleteFile(String path){
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/NuNotes/", path);
        return !file.exists() || file.delete();
    }
}

class GlobalNotesArray {
    @SerializedName("noteslist")
    ArrayList<Note> notes = new ArrayList<>();

    public void removeNote(int position){
        notes.remove(position);
        MainActivity.INSTANCE.updateList();
    }
}