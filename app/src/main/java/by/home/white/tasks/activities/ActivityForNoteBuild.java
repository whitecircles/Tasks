package by.home.white.tasks.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import by.home.white.tasks.fragments.DateFragment;
import by.home.white.tasks.entities.Note;
import by.home.white.tasks.R;
import by.home.white.tasks.fragments.TimeFragment;

public class ActivityForNoteBuild extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";
    public static final String EXTRA_REPLY_PRIORITY = "com.example.android.wordlistsql.REPLY_FOR_PRIORITY";
    //public static final String EXTRA_REPLY_PHOTO = "com.example.android.wordlistsql.REPLY_FOR_PHOTO";
    //static final int REQUEST_IMAGE_CAPTURE = 1;
    public boolean isEdit = false;


    EditText mEditNoteView;
    String item;
    //Bitmap photo;
    TextView tvForPhoto;
    String pendDate;
    Calendar calendar;
    Note note;


    private Button btnForPendDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_note_build);

        mEditNoteView = (EditText) findViewById(R.id.editText);

        Intent intent = getIntent();
        if (intent.hasExtra("noteForEdit"))
        {
            note = (Note) intent.getExtras().getParcelable("noteForEdit");
            isEdit = true;


        }





        btnForPendDate = findViewById(R.id.buttonForDatePick);
        TextView tvForPhoto = findViewById(R.id.textViewforPhotoTaken);
        tvForPhoto.setText("");



        //---------spinner

        final Spinner spinner = (Spinner) findViewById(R.id.spinner);

        spinner.setAdapter(new ArrayAdapter<Note.Priority>(this, android.R.layout.simple_spinner_item, Note.Priority.values()));

        if (note != null)
        {
            ArrayAdapter myAdap = (ArrayAdapter) spinner.getAdapter();
            int spinnerPosition = myAdap.getPosition(note.getPriority());
            spinner.setSelection(spinnerPosition);
            mEditNoteView.setText(note.getNote());
            //photo = note.getPhoto();
            pendDate = note.getPendingDate();

        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {
                item = spinner.getSelectedItem().toString();;
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /*Button photoBtn = (Button) findViewById(R.id.buttonForPhoto);
        photoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        }); */


        final FloatingActionButton button = findViewById(R.id.saveButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (item == null) {
                    item = "SMALL";
                }

                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(mEditNoteView.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String word = mEditNoteView.getText().toString();
                    replyIntent.putExtra(EXTRA_REPLY, word);

                    replyIntent.putExtra(EXTRA_REPLY_PRIORITY, item);

                    //replyIntent.putExtra(EXTRA_REPLY_PHOTO, photo);

                    replyIntent.putExtra("date", pendDate);

                    replyIntent.putExtra("isEdit", isEdit);

                    replyIntent.putExtra("user2", getIntent().getIntExtra("uIdent",1));
                    if (isEdit == true)
                    {
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
                timePicker.show(getSupportFragmentManager(),"time picker");


                calendar = Calendar.getInstance();


            }
        });
    }



    /*private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            tvForPhoto = findViewById(R.id.textViewforPhotoTaken);


            if(imageBitmap != null)
            {
                tvForPhoto.setText("Photo has been taken");
            }
            photo = imageBitmap;
        }
    }*/


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);


        SimpleDateFormat formatsimple = new SimpleDateFormat("yyMMddHHmmss");
        pendDate = formatsimple.format(calendar.getTime());
    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);


    }
}
