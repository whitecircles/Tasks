package by.home.white.tasks;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Parcelable;
import android.os.SystemClock;

import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import by.home.white.tasks.activities.ActivityForNoteBuild;

import by.home.white.tasks.activities.CalendarActivity;
import by.home.white.tasks.retrofit.NetworkService;
import by.home.white.tasks.reclrView.ReclrAdapter;
import by.home.white.tasks.reclrView.ReclrItemClickListener;
import by.home.white.tasks.entities.Note;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivityInside extends AppCompatActivity {

    public static final int NEW_NOTE_ACTIVITY_REQUEST_CODE = 1;
    private static final String CHANNEL_ID = "101";
    public boolean isEdit = false;



    RecyclerView rv;
    ReclrAdapter adapter;
    String pendDate;
    List<Note> nts;

    int user;
    int editFor;
    long timediff;



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.calendar:
                Intent calendarIntent = new Intent(MainActivityInside.this, CalendarActivity.class);
                calendarIntent.putExtra("calendUser", user);
                startActivity(calendarIntent);
                return true;
            default:
                return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_inside);

        Intent mainintent = getIntent();
        user = mainintent.getIntExtra("user", 0);

        rv = findViewById(R.id.reclr);
        adapter = new ReclrAdapter(this);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        rv.setLayoutManager(lm);
        rv.addOnItemTouchListener(new ReclrItemClickListener(this, rv, new ReclrItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
                Note myNoteForIsDone = adapter.getNoteAtPosition(position);
                myNoteForIsDone.setChecked(!myNoteForIsDone.isChecked());

                nts.set(position, myNoteForIsDone);
                adapter.notifyDataSetChanged();

                NetworkService.getInstance().getJSONApiEditNote().editNote(myNoteForIsDone.getId(),
                        myNoteForIsDone.getNote(), myNoteForIsDone.isChecked(), myNoteForIsDone.getPriority(), myNoteForIsDone.getDate().toString(),
                        myNoteForIsDone.getPendingDate(), myNoteForIsDone.getUserId(), myNoteForIsDone.getDescription())
                        .enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {


                                Toast toast = Toast.makeText(getApplicationContext(),
                                        "successful", Toast.LENGTH_SHORT);
                                toast.show();

                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Log.d("cause:", t.getMessage());
                            }
                        });


            }

            @Override
            public void onLongItemClick(View view, int position) {
                showPopupMenu(view, position);
            }

        }));

        RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rv.addItemDecoration(dividerItemDecoration);
        rv.setAdapter(adapter);


        NetworkService.getInstance().getJSONApiGetNotes().getNotes(user)
                .enqueue(new Callback<List<Note>>() {
                    @Override
                    public void onResponse(Call<List<Note>> call, Response<List<Note>> response) {

                        nts = response.body();
                        adapter.setNotes(nts);

                    }

                    @Override
                    public void onFailure(Call<List<Note>> call, Throwable t) {

                        Log.d("Cause", t.getMessage());
                    }
                });


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivityInside.this, ActivityForNoteBuild.class);
                intent.putExtra("uIdent", user);
                startActivityForResult(intent, NEW_NOTE_ACTIVITY_REQUEST_CODE);
            }
        });


    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_NOTE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            isEdit = data.getBooleanExtra("isEdit", false);
            if (isEdit) {
                editFor = data.getIntExtra("EditFor", 0);
            }


            SimpleDateFormat formatsimple2 = new SimpleDateFormat("yyMMddHHmmss");
            String now = formatsimple2.format(Calendar.getInstance().getTime());
            String priority = data.getStringExtra(ActivityForNoteBuild.EXTRA_REPLY_PRIORITY);
            pendDate = data.getStringExtra("date");
            if (pendDate == null)
                pendDate = formatsimple2.format(Calendar.getInstance().getTime());

            String onenote = data.getStringExtra(ActivityForNoteBuild.EXTRA_REPLY);
            String desc = data.getStringExtra(ActivityForNoteBuild.EXTRA_REPLY_DESC);
            final Note note = new Note(editFor, onenote, false, now, priority, pendDate, user, desc);

            if (isEdit) {
                 for (int i = 0; i < nts.size(); i++) {
                    if (nts.get(i).getId() == editFor) {
                        nts.set(i, note);
                        adapter.notifyDataSetChanged();
                    }
                }


                NetworkService.getInstance().getJSONApiEditNote().editNote(editFor,
                        note.getNote(), note.isChecked(), note.getPriority(), note.getDate(),
                        note.getPendingDate(), user, note.getDescription())
                        .enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                Toast toast = Toast.makeText(getApplicationContext(),
                                        "successful", Toast.LENGTH_SHORT);
                                toast.show();


                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Log.d("cause:", t.getMessage());
                            }
                        });

            } else {

                SimpleDateFormat format = new SimpleDateFormat("yyMMddHHmmss");
                Date pdate = null;
                try {
                    if (note.getPendingDate() != null) {
                        pdate = format.parse(note.getPendingDate());

                    } else {
                        pdate = Calendar.getInstance().getTime();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                long timeInMilliseconds = pdate.getTime();
                long timeInMillisecondsNow = new Date().getTime();
                timediff = timeInMilliseconds - timeInMillisecondsNow;


                NetworkService.getInstance().getJSONApiInsertNote().insertNote(note.getNote(), note.isChecked(), note.getPriority(), note.getDate(),
                        note.getPendingDate(), note.getUserId(), note.getDescription())
                        .enqueue(new Callback<Integer>() {
                            @Override
                            public void onResponse(Call<Integer> call, Response<Integer> response) {

                                Toast toast = Toast.makeText(getApplicationContext(),
                                        "successful", Toast.LENGTH_SHORT);
                                toast.show();

                                note.setId(response.body());
                                if (timediff > 0) {


                                    scheduleNotification(getNotification(note.getNote()), (int) timediff, note.getId());
                                    Log.d("timinmillis", String.valueOf(timediff));
                                }

                                nts.add(note);
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onFailure(Call<Integer> call, Throwable t) {
                                Log.d("cause:", t.getMessage());
                            }
                        });

            }





        }
    }

    private void showPopupMenu(View v, final int position) {
        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.inflate(R.menu.popup_menu);

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {

                    case R.id.setDone:

                        final Note myNoteForIsDone = adapter.getNoteAtPosition(position);
                        myNoteForIsDone.setChecked(!myNoteForIsDone.isChecked());

                        nts.set(position, myNoteForIsDone);
                        adapter.notifyDataSetChanged();


                        NetworkService.getInstance().getJSONApiEditNote().editNote(myNoteForIsDone.getId(),
                                myNoteForIsDone.getNote(), myNoteForIsDone.isChecked(), myNoteForIsDone.getPriority(), myNoteForIsDone.getDate().toString(),
                                myNoteForIsDone.getPendingDate(), myNoteForIsDone.getUserId(), myNoteForIsDone.getDescription())
                                .enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {


                                        Toast toast = Toast.makeText(getApplicationContext(),
                                                "successful", Toast.LENGTH_SHORT);
                                        toast.show();

                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {
                                        Log.d("cause:", t.getMessage());
                                    }
                                });


                        return true;
                    case R.id.delete:
                        final Note myNoteForDel = adapter.getNoteAtPosition(position);
                          NetworkService.getInstance().getJSONApiDeleteNote().deleteNote(myNoteForDel.getId())
                                .enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        Toast toast = Toast.makeText(getApplicationContext(),
                                                "successful", Toast.LENGTH_SHORT);
                                        toast.show();
                                        for (int i = 0; i < nts.size(); i++) {
                                            if (nts.get(i).getId() == myNoteForDel.getId()) {
                                                nts.remove(nts.get(i));
                                                adapter.notifyDataSetChanged();

                                            }
                                        }
                                        cancelNotification(getNotification(myNoteForDel.getNote()), myNoteForDel.getId());


                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {
                                        Log.d("cause:", t.getMessage());
                                    }
                                });




                        return true;
                    case R.id.edit:
                        Note myNoteForEdit = adapter.getNoteAtPosition(position);
                        Intent intent = new Intent(MainActivityInside.this, ActivityForNoteBuild.class);
                        intent.putExtra("noteForEdit", (Parcelable) myNoteForEdit);
                        startActivityForResult(intent, NEW_NOTE_ACTIVITY_REQUEST_CODE);
                        return true;
                    default:
                        return false;
                }
            }
        });
        popupMenu.show();
    }

    private void scheduleNotification(Notification notification, int delay, int identifier) {

        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, identifier, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager) getSystemService(this.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);




    }


    private void cancelNotification(Notification notification, int id) {

        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendIntent = PendingIntent.getBroadcast(getApplicationContext(), id, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(this.ALARM_SERVICE);
        alarmManager.cancel(pendIntent);

    }

    private Notification getNotification(String content) {


        NotificationManager notificationManager =
                (NotificationManager) getSystemService(this.NOTIFICATION_SERVICE);

        //if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "My channel",
                NotificationManager.IMPORTANCE_HIGH);
        channel.setDescription("My channel description");
        channel.enableLights(true);
        channel.enableVibration(false);

        notificationManager.createNotificationChannel(channel);


        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("task")
                        // 3 hours
                        .setTimeoutAfter(10800000)
                        .setContentText(content);


        return builder.build();

    }

}



















