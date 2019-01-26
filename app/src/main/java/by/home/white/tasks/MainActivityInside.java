package by.home.white.tasks;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import by.home.white.tasks.activities.ActivityForNoteBuild;
import by.home.white.tasks.retrofit.NetworkService;
import by.home.white.tasks.reclrView.ReclrAdapter;
import by.home.white.tasks.reclrView.ReclrItemClickListener;
import by.home.white.tasks.entities.Note;
import by.home.white.tasks.room.NoteRoomDatabase;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivityInside extends AppCompatActivity {

    public static final int NEW_NOTE_ACTIVITY_REQUEST_CODE = 1;
    private static final String CHANNEL_ID = "101";
    public boolean isEdit = false;

    //private NoteViewModel mNoteViewModel;

    RecyclerView rv;
    ReclrAdapter adapter;
    Date pendDate;
    List<Note> nts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_inside);

        /*mNoteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);

        mNoteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable final List<Note> notes) {
                // Update the cached copy of the words in the adapter.
                adapter.setNotes(notes);

                //nts = notes;
            }
        });

        getAllAsyncTask task = new getAllAsyncTask();
        task.execute(); */


        rv = findViewById(R.id.reclr);
        adapter = new ReclrAdapter(this);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        rv.setLayoutManager(lm);
        rv.addOnItemTouchListener(new ReclrItemClickListener(this, rv, new ReclrItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
                final Note myNoteForIsDone = adapter.getNoteAtPosition(position);
                myNoteForIsDone.setChecked(!myNoteForIsDone.isChecked());
                //mNoteViewModel.update(myNoteForIsDone);
                NetworkService.getInstance().getJSONApiEditNote().editNote(myNoteForIsDone.getId(),
                        myNoteForIsDone.getNote(),myNoteForIsDone.isChecked(),myNoteForIsDone.getPriority().toString(),myNoteForIsDone.getDate().toString(),
                        myNoteForIsDone.getPendingDate().toString(),myNoteForIsDone.getUserId())
                        .enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                Toast toast = Toast.makeText(getApplicationContext(),
                                        "successful", Toast.LENGTH_SHORT);
                                toast.show();
                                nts.set(myNoteForIsDone.getId(), myNoteForIsDone);
                                adapter.notifyDataSetChanged();
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
        //((DividerItemDecoration) dividerItemDecoration).setDrawable(ContextCompat.getDrawable(this, R.drawable.divider));
        rv.addItemDecoration(dividerItemDecoration);

        rv.setAdapter(adapter);

        NetworkService.getInstance().getJSONApiGetNotes().getNotes(getIntent().getIntExtra("user",1))
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


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivityInside.this, ActivityForNoteBuild.class);
                startActivityForResult(intent, NEW_NOTE_ACTIVITY_REQUEST_CODE);
            }
        });


    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_NOTE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            isEdit = data.getBooleanExtra("isEdit", false);



            SharedPreferences pref = getSharedPreferences("identifiers", MODE_PRIVATE);
            int id = pref.getInt("id", 0);
            SharedPreferences.Editor editor = pref.edit();


            editor.putInt("id", id + 1);
            editor.commit();



            Date date = Calendar.getInstance().getTime();
            Note.Priority priority = (Note.Priority) data.getSerializableExtra(ActivityForNoteBuild.EXTRA_REPLY_PRIORITY);
            //Bitmap btm = data.getParcelableExtra(ActivityForNoteBuild.EXTRA_REPLY_PHOTO);
            Date pendDate = (Date) data.getSerializableExtra("date");
            /*if (btm == null) {
                Bitmap photo = BitmapFactory.decodeResource(this.getResources(),
                        R.drawable.nophoto);
                btm = photo;
            }*/

            final Note note = new Note(id, data.getStringExtra(ActivityForNoteBuild.EXTRA_REPLY), date, priority, pendDate, 1);


            if (isEdit)
            {
                //mNoteViewModel.update(note);
                NetworkService.getInstance().getJSONApiEditNote().editNote(id,
                        note.getNote(),note.isChecked(),note.getPriority().toString(),note.getDate().toString(),
                        note.getPendingDate().toString(),getIntent().getIntExtra("user",1))
                        .enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                Toast toast = Toast.makeText(getApplicationContext(),
                                        "successful", Toast.LENGTH_SHORT);
                                toast.show();
                                for ( int i = 0; i < nts.size(); i++)
                                {
                                    if (nts.get(i).getId() == note.getId())
                                    {
                                        nts.set(nts.get(i).getId(), note);
                                        adapter.notifyDataSetChanged();
                                        break;
                                    }
                                }

                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Log.d("cause:", t.getMessage());
                            }
                        });

            }
            else {
                //mNoteViewModel.insert(note);
                NetworkService.getInstance().getJSONApiInsertNote().insertNote(note.getNote(),note.isChecked(),note.getPriority().toString(),note.getDate().toString(),
                        note.getPendingDate().toString(),getIntent().getIntExtra("user",1))
                        .enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                Toast toast = Toast.makeText(getApplicationContext(),
                                        "successful", Toast.LENGTH_SHORT);
                                toast.show();
                                nts.add(note);
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Log.d("cause:", t.getMessage());
                            }
                        });

            }



            long timeInMilliseconds = note.getPendingDate().getTime();
            long timeInMillisecondsNow = (long) new Date().getTime();
            long timediff = timeInMilliseconds - timeInMillisecondsNow;

            if (timediff > 0) {
                scheduleNotification(getNotification(note.getNote(), String.valueOf(id)), (int) timediff, id);
                Log.d("timinmillis", String.valueOf(timediff));
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
                    /*case R.id.showPhoto:
                        Intent imgIntent = new Intent(MainActivityInside.this, GalleryActivity.class);
                        Note myNote = adapter.getNoteAtPosition(position);
                        imgIntent.putExtra("photo", myNote.getPhoto());
                        startActivity(imgIntent);
                        return true;*/
                    case R.id.setDone:
                        final Note myNoteForIsDone = adapter.getNoteAtPosition(position);
                        myNoteForIsDone.setChecked(!myNoteForIsDone.isChecked());
                        //mNoteViewModel.update(myNoteForIsDone);
                        NetworkService.getInstance().getJSONApiEditNote().editNote(myNoteForIsDone.getId(),
                                myNoteForIsDone.getNote(),myNoteForIsDone.isChecked(),myNoteForIsDone.getPriority().toString(),myNoteForIsDone.getDate().toString(),
                                myNoteForIsDone.getPendingDate().toString(),myNoteForIsDone.getUserId())
                                .enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        Toast toast = Toast.makeText(getApplicationContext(),
                                                "successful", Toast.LENGTH_SHORT);
                                        toast.show();
                                        for ( int i = 0; i < nts.size(); i++)
                                        {
                                            if (nts.get(i).getId() == myNoteForIsDone.getId())
                                            {
                                                nts.set(nts.get(i).getId(), myNoteForIsDone);
                                                adapter.notifyDataSetChanged();
                                                break;
                                            }
                                        }


                                    }


                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {
                                        Log.d("cause:", t.getMessage());
                                    }
                                });

                        return true;
                    case R.id.delete:
                        final Note myNoteForDel = adapter.getNoteAtPosition(position);
                        //mNoteViewModel.delete(myNoteForDel);
                        NetworkService.getInstance().getJSONApiDeleteNote().deleteNote(myNoteForDel.getId())
                                .enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        Toast toast = Toast.makeText(getApplicationContext(),
                                                "successful", Toast.LENGTH_SHORT);
                                        toast.show();
                                        for ( int i = 0; i < nts.size(); i++)
                                        {
                                            if (nts.get(i).getId() == myNoteForDel.getId())
                                            {
                                                nts.remove(nts.get(i));
                                                adapter.notifyDataSetChanged();
                                            }
                                        }

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
                        /*
                        intent.putExtra("note", myNoteForEdit.getNote());
                        intent.putExtra("photo", myNoteForEdit.getPhoto());
                        intent.putExtra("pdate", myNoteForEdit.getPendingDate());
                        intent.putExtra("date", myNoteForEdit.getDate());
                        intent.putExtra("isChecked", myNoteForEdit.isChecked());
                        intent.putExtra("priority", myNoteForEdit.getPriority());
                        */
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

    private Notification getNotification(String content, String id) {


        NotificationManager notificationManager =
                (NotificationManager) getSystemService(this.NOTIFICATION_SERVICE);

        //if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "My channel",
                NotificationManager.IMPORTANCE_HIGH);
        channel.setDescription("My channel description");
        channel.enableLights(true);
        //channel.setLightColor(Color.RED);
        channel.enableVibration(false);

        notificationManager.createNotificationChannel(channel);



        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("task")
                        .setContentText(content);


        return builder.build();
        //---------------------

    }



    private class getAllAsyncTask extends AsyncTask<Void, Void, List<Note>> {
        //private NoteDao mAsyncTaskDao;
        NoteRoomDatabase db = NoteRoomDatabase.getDatabase(MainActivityInside.this);


        @Override
        protected List<Note> doInBackground(Void... voids) {
            List<Note> result = db.noteDao().getAll();
            return result;

        }

        @Override
        protected void onPostExecute(List<Note> notes) {
            super.onPostExecute(notes);
            nts = new ArrayList<Note>();
            nts = notes;
            //for (int i = 0; i < nts.size(); i++) {
            //if ((nts.get(i).getPendingDate() != null) && (nts.get(i).getPendingDate().after(Calendar.getInstance().getTime()))) {






            Log.d("isDone","Data loaded");

        }
    }
}













