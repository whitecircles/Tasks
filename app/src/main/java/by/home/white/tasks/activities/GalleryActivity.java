package by.home.white.tasks.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import by.home.white.tasks.R;

public class GalleryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        ImageView iv = (ImageView) findViewById(R.id.gallery);
        Intent gIntent = getIntent();
        Bitmap bmp = gIntent.getParcelableExtra("photo");
        iv.setImageBitmap(bmp);
    }
}
