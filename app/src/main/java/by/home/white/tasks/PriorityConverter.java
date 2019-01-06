package by.home.white.tasks;

import android.arch.persistence.room.TypeConverter;

import static by.home.white.tasks.Note.Priority.HIGH;
import static by.home.white.tasks.Note.Priority.MED;
import static by.home.white.tasks.Note.Priority.SMALL;

public class PriorityConverter {
    @TypeConverter
    public static Note.Priority toPriority(int priority) {
        if (priority == HIGH.getCode()) {
            return HIGH;
        } else if (priority == MED.getCode()) {
            return MED;
        } else if (priority == SMALL.getCode()) {
            return SMALL;
        } else {
            throw new IllegalArgumentException("Could not recognize status");
        }
    }

    @TypeConverter
    public static Integer toInteger(Note.Priority priority) {
        return priority.getCode();
    }
}
