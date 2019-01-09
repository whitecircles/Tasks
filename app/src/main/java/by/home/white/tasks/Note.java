package by.home.white.tasks;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.Date;

@Entity(tableName = "note_table")
public class Note implements Parcelable {





    //----------------field
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "note")
    private String mNote;

    //----------------field
    @NonNull
    @ColumnInfo(name = "isDone")
    private boolean mChecked;

    //----------------field
    @NonNull
    @ColumnInfo(name = "priority")
    @TypeConverters(PriorityConverter.class)
    private Priority mPriority;

    //----------------field
    @NonNull
    @ColumnInfo(name = "date")
    @TypeConverters(DateConverter.class)
    private Date mDate;


    //----------------field
    //@NonNull
    @ColumnInfo(name = "photo")
    @TypeConverters(BitmapConverter.class)
    private Bitmap mPhoto;



    @ColumnInfo(name = "pendDate")
    @TypeConverters(DateConverter.class)
    private Date mPendingDate;


    //----------------constructor
    public Note(String note, Date date, Priority priority, Bitmap photo, Date pendingDate) {
        this.mNote = note;
        this.mDate = date;
        this.mPriority = priority;
        this.mPhoto = photo;
        this.mPendingDate = pendingDate;
    }

    protected Note(Parcel in) {
        mNote = in.readString();
        mChecked = in.readByte() != 0;
        mDate = (Date) in.readSerializable();
        mPendingDate = (Date) in.readSerializable();
        mPhoto = in.readParcelable(Bitmap.class.getClassLoader());
        mPriority = Priority.values()[in.readInt()];


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

    public Date getPendingDate() {
        return mPendingDate;
    }

    public void setPendingDate(Date mPendingDate) {
        this.mPendingDate = mPendingDate;
    }

    @NonNull
    public Bitmap getPhoto() {
        return mPhoto;
    }

    public void setPhoto(@NonNull Bitmap mPhoto) {
        this.mPhoto = mPhoto;
    }

    @NonNull
    public Date getDate() {
        return mDate;
    }



    public void setDate(@NonNull Date mDate) {
        this.mDate = mDate;
    }

    @NonNull
    public Priority getPriority() {
        return mPriority;
    }

    public void setPriority(@NonNull Priority mPriority) {
        this.mPriority = mPriority;
    }



    @NonNull
    public String getNote() {
        return mNote;
    }

    public void setNote(@NonNull String mNote) {
        this.mNote = mNote;
    }

    @NonNull
    public boolean isChecked() {
        return mChecked;
    }

    public void setChecked(@NonNull boolean mChecked) {
        this.mChecked = mChecked;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(mNote);
        dest.writeInt(mChecked ? 1 : 0);
        dest.writeSerializable(mDate);
        dest.writeSerializable(mPendingDate);
        dest.writeParcelable(mPhoto, flags);
        dest.writeInt(mPriority.ordinal());
    }

    public enum Priority {
        HIGH(0),
        MED(1),
        SMALL(2);

        private int code;

        Priority(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }
}










