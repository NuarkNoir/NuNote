package xyz.nuark.nunote;

import in.cubestack.android.lib.storm.annotation.Database;

/**
 * Created with love by Nuark on 24.12.2017.
 * Protected by QPL-1.0
 */


@Database(name="NOTES_DB", tables = {Note.class}, version = 2)
class NotesDB {}
