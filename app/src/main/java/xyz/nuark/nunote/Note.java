package xyz.nuark.nunote;

import android.os.Parcel;
import android.os.Parcelable;

import in.cubestack.android.lib.storm.FieldType;
import in.cubestack.android.lib.storm.annotation.Column;
import in.cubestack.android.lib.storm.annotation.PrimaryKey;
import in.cubestack.android.lib.storm.annotation.Table;

/**
 * Created by Nuark with love on 15.09.2017.
 * Protected by QPL-1.0
 */

@Table(name = "NOTES_ENTITY")
public class Note implements Parcelable {

    @PrimaryKey
    @Column(name="ID", type = FieldType.INTEGER)
    public int ID;

    @PrimaryKey
    @Column(name="NAME", type = FieldType.TEXT)
    private String Name;

    @PrimaryKey
    @Column(name="DOC", type = FieldType.TEXT)
    private String DateOfCreation;

    @PrimaryKey
    @Column(name="DOM", type = FieldType.TEXT)
    private String DateOfLastModification;

    public Note(int ID, String name, String dateOfCreation, String dateOfLastModification) {
        this.ID = ID;
        Name = name;
        DateOfCreation = dateOfCreation.replace(" ", ".");
        DateOfLastModification = dateOfLastModification.replace(" ", ".");
    }

    private String sanitize(String name){
        return name.trim()
                .replace("\\", "_")
                .replace("/", "_")
                .replace(":", "꞉")
                .replace("*", "_")
                .replace("?", "_")
                .replace("\"", "_")
                .replace("<", "_")
                .replace(">", "_")
                .replace("|", "_")
                .replace("+", "_")
                .replace("%", "_")
                .replace("!", "_")
                .replace("@", "_")
                .replace("\\", "_");
    }

    private Note(Parcel in) {
        ID = in.readInt();
        Name = in.readString();
        DateOfCreation = in.readString();
        DateOfLastModification = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeString(Name);
        dest.writeString(DateOfCreation);
        dest.writeString(DateOfLastModification);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public String getName() {
        return Name;
    }

    public String getPath() {
        return sanitize(ID + "." + Name.replace(" ", "") + "_" + DateOfCreation.replace(" ", "_") + ".txt");
    }

    public String getDateOfCreation() {
        return DateOfCreation;
    }

    public String getDateOfLastModification() {
        return DateOfLastModification;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setDateOfLastModification(String dateOfLastModification) {
        DateOfLastModification = dateOfLastModification;
    }
}
