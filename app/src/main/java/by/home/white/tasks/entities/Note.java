package by.home.white.tasks.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

import by.home.white.tasks.converters.DateConverter;
import by.home.white.tasks.converters.PriorityConverter;

@Entity
public class Note implements Parcelable {




    //----------------field
    @SerializedName("Id")
    @Expose
    private int mId;

    //----------------field
    @SerializedName("note")
    @Expose
    private String mNote;

    //----------------field
    @SerializedName("isChecked")
    @Expose
    private boolean mChecked;

    //----------------field
    @SerializedName("priority")
    @Expose
    private String mPriority;

    //----------------field
    @SerializedName("date")
    @Expose
    private String mDate;

    //----------------field
    @SerializedName("pendDate")
    @Expose
    private String mPendingDate;

    //----------------field
    @SerializedName("userId")
    @Expose
    private int mUserId;


    //----------------constructor
    public Note(int id, String note, boolean isChecked, String date, String priority, String pendingDate, int userId) {
        this.mId = id;
        this.mNote = note;
        this.mChecked = isChecked;
        this.mDate = date;
        this.mPriority = priority;
        //this.mPhoto = photo;
        this.mPendingDate = pendingDate;
        this.mUserId = userId;
    }

    protected Note(Parcel in) {
        mId = in.readInt();
        mNote = in.readString();
        mChecked = in.readByte() != 0;
        mDate = in.readString();
        mPendingDate = in.readString();
        //mPhoto = in.readParcelable(Bitmap.class.getClassLoader());
        mPriority = in.readString();
        mUserId = in.readInt();


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

    public String getNote() {
        return mNote;
    }

    public void setNote(String mNote) {
        this.mNote = mNote;
    }

    public boolean isChecked() {
        return mChecked;
    }

    public void setChecked(boolean mChecked) {
        this.mChecked = mChecked;
    }

    @NonNull
    public String getPriority() {
        return mPriority;
    }

    public void setPriority(@NonNull String mPriority) {
        this.mPriority = mPriority;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String mDate) {
        this.mDate = mDate;
    }

    public String getPendingDate() {
        return mPendingDate;
    }

    public void setPendingDate(String mPendingDate) {
        this.mPendingDate = mPendingDate;
    }

    public int getUserId() {
        return mUserId;
    }

    public void setUserId(int mUserId) {
        this.mUserId = mUserId;
    }

    @NonNull
    public int getId() {
        return mId;
    }

    public void setId(@NonNull int mId) {
        this.mId = mId;
    }



    /*@NonNull
    //public Bitmap getPhoto() {
       return mPhoto;
    }

    //public void setPhoto(@NonNull Bitmap mPhoto) {
        this.mPhoto = mPhoto;
    }*/


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mNote);
        dest.writeInt(mChecked ? 1 : 0);
        dest.writeString(mDate);
        dest.writeString(mPendingDate);
        //dest.writeParcelable(mPhoto, flags);
        dest.writeString(mPriority);
        dest.writeInt(mUserId);
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










