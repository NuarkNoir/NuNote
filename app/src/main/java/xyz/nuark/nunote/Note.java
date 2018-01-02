package xyz.nuark.nunote;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

/**
 * Created by Nuark with love on 15.09.2017.
 * Protected by QPL-1.0
 */

public class Note  extends SugarRecord implements Parcelable {

    @Unique
    private Long Id;
    private String Name;
    private String DateOfCreation;
    private String DateOfLastModification;
    private String Text;

    public Note() {} // SugarORM needs empty constructor for some reasons

    public Note(Long id, String name, String dateOfCreation, String dateOfLastModification, String text) {
        Id = id;
        Name = name;
        DateOfCreation = dateOfCreation;
        DateOfLastModification = dateOfLastModification;
        Text = text;
    }

    public Long getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public String getDateOfCreation() {
        return DateOfCreation;
    }

    public String getDateOfLastModification() {
        return DateOfLastModification;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setDateOfLastModification(String dateOfLastModification) {
        DateOfLastModification = dateOfLastModification;
    }

    // Parcelable area

    private Note(Parcel in) {
        Id = in.readLong();
        Name = in.readString();
        DateOfCreation = in.readString();
        DateOfLastModification = in.readString();
        Text = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(Id);
        dest.writeString(Name);
        dest.writeString(DateOfCreation);
        dest.writeString(DateOfLastModification);
        dest.writeString(Text);
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
}
