package by.home.white.tasks.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
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

import by.home.white.tasks.fragments.DateFragment;
import by.home.white.tasks.entities.Note;
import by.home.white.tasks.R;
import by.home.white.tasks.fragments.TimeFragment;

public class ActivityForNoteBuild extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";
    public static final String EXTRA_REPLY_DESC = "com.example.android.wordlistsql.REPLY_DESC";
    public static final String EXTRA_REPLY_PRIORITY = "com.example.android.wordlistsql.REPLY_FOR_PRIORITY";
    public boolean isEdit = false;


    EditText editNoteView;
    EditText editDesc;
    String item;
    String pendDate;
    Calendar calendar;
    Note note;
    TextView tv;


    private Button btnForPendDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_note_build);

        editNoteView = findViewById(R.id.editText);
        editDesc = findViewById(R.id.editTextForDesc);
        tv = findViewById(R.id.textViewForDateDisplay);

        Intent intent = getIntent();
        if (intent.hasExtra("noteForEdit"))
        {
            note = intent.getExtras().getParcelable("noteForEdit");
            isEdit = true;
        }


        btnForPendDate = findViewById(R.id.buttonForDatePick);

        //---------spinner
        final Spinner spinner = findViewById(R.id.spinner);

        spinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Note.Priority.values()));

        if (note != null)
        {
            ArrayAdapter myAdap = (ArrayAdapter) spinner.getAdapter();
            int spinnerPosition = myAdap.getPosition(note.getPriority());
            spinner.setSelection(spinnerPosition);
            editNoteView.setText(note.getNote());
            pendDate = note.getPendingDate();

        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {
                item = spinner.getSelectedItem().toString();;
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
