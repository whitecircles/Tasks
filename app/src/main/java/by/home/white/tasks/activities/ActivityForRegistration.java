package by.home.white.tasks.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import by.home.white.tasks.R;
import by.home.white.tasks.retrofit.NetworkService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityForRegistration extends AppCompatActivity {

    //it works
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_registration);

        final EditText editForName = findViewById(R.id.editTextForName2);
        final EditText editForPass = findViewById(R.id.editTextForPass2);


        Button btnToReg = (Button) findViewById(R.id.btnForRegistr2);
        btnToReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editForName.getText().toString().equals("") ||  editForPass.getText().toString().equals(""))
                {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "enter all the values", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    NetworkService.getInstance().getJSONApiInsertUser().insertUser(editForName.getText().toString(), editForPass.getText().toString())
                            .enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    Toast toast = Toast.makeText(getApplicationContext(),
                                            "successful", Toast.LENGTH_SHORT);
                                    toast.show();
                                    Intent intentToMain = new Intent(ActivityForRegistration.this, MainActivity.class);
                                    startActivity(intentToMain);

                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
                                    Log.d("cause:", t.getMessage());
                                }
                            });
                }
            }
        });
    }
}
