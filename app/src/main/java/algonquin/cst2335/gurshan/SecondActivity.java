package algonquin.cst2335.gurshan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class SecondActivity extends AppCompatActivity {

    private static final String TAG = "SecondActivity";
    private EditText editTextPhone;
    private ImageView imageViewProfile;
    private SharedPreferences prefs;
    private ActivityResultLauncher<Intent> cameraLauncher;
    private Bitmap profileBitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        TextView textViewWelcome = findViewById(R.id.textViewWelcome);
        editTextPhone = findViewById(R.id.editTextPhone);
        imageViewProfile = findViewById(R.id.imageViewProfile);
        Button buttonCall = findViewById(R.id.buttonCall);
        Button buttonChangePicture = findViewById(R.id.buttonChangePicture);

        // Get email from Intent and show
        String email = getIntent().getStringExtra("EmailAddress");
        if (email != null)
            textViewWelcome.setText("Welcome back " + email);

        // Load phone number from SharedPreferences
        prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String savedPhone = prefs.getString("PhoneNumber", "");
        editTextPhone.setText(savedPhone);

        // Load image from file if exists
        File file = new File(getFilesDir(), "Picture.png");
        if (file.exists()) {
            profileBitmap = android.graphics.BitmapFactory.decodeFile(file.getAbsolutePath());
            imageViewProfile.setImageBitmap(profileBitmap);
        }

        // Setup camera launcher
        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null && data.getExtras() != null) {
                            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                            imageViewProfile.setImageBitmap(thumbnail);
                            profileBitmap = thumbnail;
                            saveBitmapToFile(thumbnail, "Picture.png");
                        }
                    }
                });

        // Call button click handler
        buttonCall.setOnClickListener(v -> {
            String phone = editTextPhone.getText().toString();
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + phone));
            startActivity(callIntent);
        });

        // Change picture button handler
        buttonChangePicture.setOnClickListener(v -> {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraLauncher.launch(cameraIntent);
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.w(TAG, "In onPause() - Saving phone number");
        // Save phone number to SharedPreferences
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("PhoneNumber", editTextPhone.getText().toString());
        editor.apply();
    }

    // Helper: Save bitmap to internal storage
    private void saveBitmapToFile(Bitmap bitmap, String filename) {
        try (FileOutputStream fos = openFileOutput(filename, Context.MODE_PRIVATE)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
