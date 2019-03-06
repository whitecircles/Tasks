package by.home.white.tasks.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import net.gotev.uploadservice.BinaryUploadRequest;
import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;
import by.home.white.tasks.fragments.DateFragment;
import by.home.white.tasks.entities.Note;
import by.home.white.tasks.R;
import by.home.white.tasks.fragments.TimeFragment;
import by.home.white.tasks.retrofit.NetworkService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityForNoteBuild extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";
    public static final String EXTRA_REPLY_DESC = "com.example.android.wordlistsql.REPLY_DESC";
    public static final String EXTRA_REPLY_PRIORITY = "com.example.android.wordlistsql.REPLY_FOR_PRIORITY";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 1;
    public boolean isEdit = false;

    Uri photoURI;

    EditText editNoteView;
    EditText editDesc;
    String item;
    String pendDate;
    Calendar calendar;
    Note note;
    TextView tv;

    String mCurrentPhotoPath;

    private Button btnForPendDate;
    private Button btnForPhotoTaken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_note_build);

        editNoteView = findViewById(R.id.editText);
        editDesc = findViewById(R.id.editTextForDesc);
        tv = findViewById(R.id.textViewForDateDisplay);

        Intent intent = getIntent();
        if (intent.hasExtra("noteForEdit")) {
            note = intent.getExtras().getParcelable("noteForEdit");
            isEdit = true;
        }


        btnForPendDate = findViewById(R.id.buttonForDatePick);
        //btnForPhotoTaken = findViewById(R.id.buttonForPhoto);

        //---------spinner
        final Spinner spinner = findViewById(R.id.spinner);

        spinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Note.Priority.values()));

        if (note != null) {
            ArrayAdapter myAdap = (ArrayAdapter) spinner.getAdapter();
            int spinnerPosition = myAdap.getPosition(note.getPriority());
            spinner.setSelection(spinnerPosition);
            editNoteView.setText(note.getNote());
            editDesc.setText(note.getDescription());
            pendDate = note.getPendingDate();

        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {
                item = spinner.getSelectedItem().toString();
                ;
            }

            public void onNothingSelected(AdapterView<?> parent) {
                item = "HIGH";
            }
        });


        final FloatingActionButton button = findViewById(R.id.saveButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {


                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(editNoteView.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String smallnote = editNoteView.getText().toString();
                    replyIntent.putExtra(EXTRA_REPLY, smallnote);

                    String desc = editDesc.getText().toString();
                    replyIntent.putExtra(EXTRA_REPLY_DESC, desc);

                    replyIntent.putExtra(EXTRA_REPLY_PRIORITY, item);

                    replyIntent.putExtra("date", pendDate);

                    replyIntent.putExtra("isEdit", isEdit);

                    replyIntent.putExtra("user2", getIntent().getIntExtra("uIdent", 1));
                    if (isEdit == true) {
                        replyIntent.putExtra("EditFor", note.getId());
                    }

                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });


        btnForPendDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DateFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
                DialogFragment timePicker = new TimeFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
                calendar = Calendar.getInstance();
            }
        });


        /*btnForPhotoTaken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });*/


    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);

            }
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {

            uploadMultipart(this);



        }
    }



    public void uploadMultipart(final Context context) {
        try {
            String uploadId =
                    new MultipartUploadRequest(context, "https://mveronicatest2.azurewebsites.net")
                            // starting from 3.1+, you can also use content:// URI string instead of absolute file
                            .addFileToUpload(photoURI.toString(), "img")
                            .setNotificationConfig(new UploadNotificationConfig())
                            .setMaxRetries(2)
                            .startUpload();
        } catch (Exception exc) {
            Log.d("AndroidUploadService", exc.getMessage(), exc);
        }
    }



    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir);

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }



    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);


        SimpleDateFormat formatsimple = new SimpleDateFormat("yyMMddHHmmss");
        pendDate = formatsimple.format(calendar.getTime());
        SimpleDateFormat simpleDate =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String simpledate = simpleDate.format(calendar.getTime());
        tv.setText(simpledate);
    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);


    }
}
