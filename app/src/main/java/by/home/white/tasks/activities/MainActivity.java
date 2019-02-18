package by.home.white.tasks.activities;


import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import by.home.white.tasks.MainActivityInside;
import by.home.white.tasks.retrofit.NetworkService;
import by.home.white.tasks.R;
import by.home.white.tasks.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


//it works
public class MainActivity extends AppCompatActivity {

    Intent intent;
    boolean isDone = false;
    List<User> users = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        final CheckBox chbSave = findViewById(R.id.checkBoxForSave);
        final Button loginBtn = findViewById(R.id.buttonForLogin);
        loginBtn.setEnabled(false);
        final EditText eForName = findViewById(R.id.editTextForName);
        final EditText eForPass = findViewById(R.id.editTextForPass);

        Button btnForRegistr = findViewById(R.id.BtnForRegistr);


        btnForRegistr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentForReg = new Intent(MainActivity.this, ActivityForRegistration.class);
                startActivity(intentForReg);
            }
        });



        final SharedPreferences pref = getSharedPreferences("savedIdentifs", MODE_PRIVATE);
        boolean isSaved = pref.getBoolean("isSaved", false);
        if (isSaved) {
            String passPref = pref.getString("passToSave", "");
            String namePref = pref.getString("nameToSave", "");
            eForName.setText(namePref);
            eForPass.setText(passPref);

        }


        NetworkService.getInstance().getJSONApiGetUsers().getUsers()
                .enqueue(new Callback<List<User>>() {
                             @Override
                             public void onResponse(Call<List<User>> call, Response<List<User>> response) {

                                 users = response.body();
                                 if (users != null) {
                                     isDone = true;
                                     loginBtn.setEnabled(true);
                                 }

                             }

                             @Override
                             public void onFailure(Call<List<User>> call, Throwable t) {

                                 Log.d("Cause", t.getMessage());
                             }
                         });







        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDone) {
                    if (chbSave.isChecked())
                    {
                        SharedPreferences.Editor editor = pref.edit();

                        editor.putBoolean("isSaved", true);
                        editor.putString("passToSave", eForPass.getText().toString());
                        editor.putString("nameToSave", eForName.getText().toString());
                        editor.commit();
                    }




                    for (int i = 0; i < users.size(); i++) {
                        if ((users.get(i).getName().replaceAll("\\s+","").equals(eForName.getText().toString())) && (users.get(i).getPasswrd().replaceAll("\\s+","").equals(eForPass.getText().toString()))) {
                            Intent intent = new Intent(MainActivity.this, MainActivityInside.class);
                            int chosenUser = users.get(i).getId();
                            intent.putExtra("user", chosenUser);
                            startActivity(intent);


                        }
                    }
                }
                // for test
                else if ((eForName.getText().toString().equals("")) && (eForPass.getText().toString().equals("")))
                {
                    intent.putExtra("user", 2);
                    startActivity(intent);
                }



            }
        });
    }
}
























