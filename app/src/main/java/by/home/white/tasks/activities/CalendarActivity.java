package by.home.white.tasks.activities;

import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.format.DateFormatDayFormatter;
import com.prolificinteractive.materialcalendarview.format.DayFormatter;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import by.home.white.tasks.MainActivityInside;
import by.home.white.tasks.R;
import by.home.white.tasks.entities.Note;
import by.home.white.tasks.retrofit.NetworkService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CalendarActivity extends AppCompatActivity {

    List<Note> nts = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);


        final MaterialCalendarView materialCalendarView = findViewById(R.id.calendarView);
        int user = getIntent().getIntExtra("calendUser",0);

        NetworkService.getInstance().getJSONApiGetNotes().getNotes(user)
                .enqueue(new Callback<List<Note>>() {
                    @Override
                    public void onResponse(Call<List<Note>> call, Response<List<Note>> response) {

                        nts = response.body();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMddHHmmss");

                        Date medDate = null;
                        for (int i = 0; i < nts.size(); i++)
                        {
                            try {
                                medDate = simpleDateFormat.parse(nts.get(i).getPendingDate());

                            } catch (ParseException e) {
                               Log.d("Cause", e.getLocalizedMessage());
                            }
                            if (medDate != null) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(medDate);

                                CalendarDay calndarDay = CalendarDay.from(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
                                materialCalendarView.setDateSelected(calndarDay, true);
                            }
                        }


                    }

                    @Override
                    public void onFailure(Call<List<Note>> call, Throwable t) {

                        Log.d("Cause", t.getMessage());
                    }
                });









    }
}
