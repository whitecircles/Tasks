package by.home.white.tasks.activities;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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



public class MainActivity extends AppCompatActivity {

    Intent intent;
    boolean isDone = false;
    List<User> users = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        Button loginBtn = findViewById(R.id.buttonForLogin);
        final EditText eForName = findViewById(R.id.editTextForName);
        final EditText eForPass = findViewById(R.id.editTextForPass);
        final TextView textView = findViewById(R.id.textViewForRaw);
        Button btnForReg = findViewById(R.id.BtnForRegistr);

        btnForReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentForReg = new Intent(MainActivity.this, ActivityForRegistration.class);
            }
        });

        final Intent intent = new Intent(MainActivity.this, MainActivityInside.class);

        //---------
        NetworkService.getInstance().getJSONApiGetUsers().getUsers()
                .enqueue(new Callback<List<User>>() {
                             @Override
                             public void onResponse(Call<List<User>> call, Response<List<User>> response) {

                                 textView.setText(response.body().toString());
                                 users = response.body();
                                 if (users != null) {
                                     isDone = true;
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
                    for (int i = 0; i < users.size(); i++) {
                        if ((users.get(i).getName().equals(eForName.getText().toString())) && (users.get(i).getPasswrd().equals(eForPass.getText().toString()))) {

                            intent.putExtra("user", 1);
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
























