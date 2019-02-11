package by.home.white.tasks.converters;

import android.arch.persistence.room.TypeConverter;

import by.home.white.tasks.entities.Note;

import static by.home.white.tasks.entities.Note.Priority.HIGH;
import static by.home.white.tasks.entities.Note.Priority.MED;
import static by.home.white.tasks.entities.Note.Priority.LOW;

public class PriorityConverter {
    @TypeConverter
    public static Note.Priority toPriority(int priority) {
        if (priority == HIGH.getCode()) {
            return HIGH;
        } else if (priority == MED.getCode()) {
            return MED;
        } else if (priority == LOW.getCode()) {
            return LOW;
        } else {
            throw new IllegalArgumentException("Could not recognize status");
        }
    }

    @TypeConverter
    public static Integer toInteger(Note.Priority priority) {
        return priority.getCode();
    }
}
