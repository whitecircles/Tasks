package by.home.white.tasks.entities;

import android.app.PendingIntent;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "pend_table")
public class PendInt {




    //----------------field
        @PrimaryKey
        @NonNull
        @ColumnInfo(name = "pendInt")
        private PendingIntent pInt;

        //----------------field
        @NonNull
        @ColumnInfo(name = "noteId")
        private int noteId;

        @NonNull
        public PendingIntent getpInt() {
            return pInt;
        }

        public void setpInt(@NonNull PendingIntent pInt) {
            this.pInt = pInt;
        }

        public int getNoteId() {
            return noteId;
        }

        public void setNoteId(int noteId) {
            this.noteId = noteId;
        }

        public PendInt(@NonNull PendingIntent pInt, int noteId) {
                this.pInt = pInt;
                this.noteId = noteId;
            }
}
