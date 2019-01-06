package by.home.white.tasks;

import android.arch.persistence.room.TypeConverter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;


public class BitmapConverter {
    @TypeConverter
    public static Bitmap toBitmap(byte[] source){
        Bitmap bmp = BitmapFactory.decodeByteArray(source, 0, source.length);
        return bmp;
    }

    @TypeConverter
    public static byte[] fromBitmap(Bitmap source){

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        if (source != null) {
            source.compress(Bitmap.CompressFormat.PNG, 100, stream);
        }
        byte[] byteArray = stream.toByteArray();
        return byteArray;


    }




}
