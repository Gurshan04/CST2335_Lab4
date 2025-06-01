package algonquin.cst2335.gurshan; // Use your actual package name

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

import algonquin.cst2335.gurshan.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize view binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Optionally, set initial flag visibility and alpha
        binding.flagImage.setVisibility(View.VISIBLE); // or View.INVISIBLE if you want it hidden initially
        binding.flagImage.setAlpha(1.0f); // 1.0 = fully visible, 0.0 = invisible

        // Set listener for the Switch to animate the flag
        binding.switchFlag.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Fade in the flag
                binding.flagImage.setVisibility(View.VISIBLE);
                binding.flagImage.animate().alpha(1.0f).setDuration(500).start();
            } else {
                // Fade out the flag
                binding.flagImage.animate().alpha(0.0f).setDuration(500)
                        .withEndAction(() -> binding.flagImage.setVisibility(View.INVISIBLE))
                        .start();
            }
        });
    }
}
