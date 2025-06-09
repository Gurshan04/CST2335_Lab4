package algonquin.cst2335.gurshan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private EditText editTextEmail, editTextPassword;
    private Button buttonLogin;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.w(TAG, "In onCreate() - Loading Widgets");

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);

        // Load email from SharedPreferences
        prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String savedEmail = prefs.getString("LoginName", "");
        editTextEmail.setText(savedEmail);

        buttonLogin.setOnClickListener(v -> {
            // Save email to SharedPreferences
            String email = editTextEmail.getText().toString();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("LoginName", email);
            editor.apply();

            // Start SecondActivity and pass email
            Intent nextPage = new Intent(MainActivity.this, SecondActivity.class);
            nextPage.putExtra("EmailAddress", email);
            startActivity(nextPage);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.w(TAG, "In onStart() - Activity is about to become visible");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.w(TAG, "In onResume() - Activity has become visible (it is now resumed)");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.w(TAG, "In onPause() - Another activity is taking focus");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.w(TAG, "In onStop() - Activity is no longer visible");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.w(TAG, "In onDestroy() - Activity is about to be destroyed");
    }
}
