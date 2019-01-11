package by.home.white.tasks;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //List<User> users = new ArrayList<User>();

        Button loginBtn = findViewById(R.id.buttonForLogin);
        final EditText eForName = findViewById(R.id.editTextForName);
        final EditText eForPass = findViewById(R.id.editTextForPass);

        //---------
        NetworkService.getInstance().getJSONApi().getUsers().enqueue(new Callback<List<User>>() {
                @Override
                public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                    TextView textView = findViewById(R.id.textViewForRaw);
                    textView.setText(response.raw().toString());
                }
                @Override
                public void onFailure(Call<List<User>> call, Throwable t) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "failure", Toast.LENGTH_SHORT);
                    toast.show();
                }
            });


        /*TextView textView = findViewById(R.id.textViewForRaw);
        textView.setText(users.toString());
        final List<User> users2 = users;

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                for (int i = 0; i < users2.size(); i++) {
                    if ((users2.get(i).getName().equals(eForName.getText().toString())) && (users2.get(i).getPassword().equals(eForPass.getText().toString()))) {
                        Intent intent = new Intent(MainActivity.this, MainActivityInside.class);
                        intent.putExtra("user", users2.get(i).getId());
                        startActivity(intent);
                    }
                }
            }
        });*/
    }
}
























