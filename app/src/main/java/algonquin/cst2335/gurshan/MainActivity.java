package algonquin.cst2335.gurshan;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * MainActivity for the Password Checker app.
 * Prompts the user for a password and checks its complexity.
 * @author Gurshan
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {

    /** Displays prompts and results to the user. */
    private TextView textView;
    /** Receives password input from the user. */
    private EditText editText;
    /** Button to submit password for checking. */
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Link UI elements to variables
        textView = findViewById(R.id.textView);
        editText = findViewById(R.id.editText);
        button = findViewById(R.id.button);

        button.setOnClickListener(v -> {
            String password = editText.getText().toString();
            if (checkPasswordComplexity(password)) {
                textView.setText("Your password meets the requirements");
            } else {
                textView.setText("You shall not pass!");
            }
        });
    }

    /**
     * Checks if a password string meets complexity requirements:
     * - at least one upper case letter,
     * - at least one lower case letter,
     * - at least one digit,
     * - at least one special character.
     * Shows a Toast message for any missing requirement.
     * @param pw The password string to check.
     * @return true if password is complex enough, false otherwise.
     */
    public boolean checkPasswordComplexity(String pw) {
        boolean foundUpper = false, foundLower = false, foundNumber = false, foundSpecial = false;
        for (char c : pw.toCharArray()) {
            if (Character.isUpperCase(c)) foundUpper = true;
            else if (Character.isLowerCase(c)) foundLower = true;
            else if (Character.isDigit(c)) foundNumber = true;
            else if (isSpecialCharacter(c)) foundSpecial = true;
        }

        if (!foundUpper) {
            Toast.makeText(this, "Missing upper case letter", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!foundLower) {
            Toast.makeText(this, "Missing lower case letter", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!foundNumber) {
            Toast.makeText(this, "Missing a digit", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!foundSpecial) {
            Toast.makeText(this, "Missing special character (#$%^&*!@?)", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    /**
     * Checks if a character is a special character required for password complexity.
     * @param c The character to check.
     * @return true if c is one of #$%^&*!@?, false otherwise.
     */
    public boolean isSpecialCharacter(char c) {
        switch (c) {
            case '#': case '$': case '%': case '^': case '&':
            case '*': case '!': case '@': case '?':
                return true;
            default:
                return false;
        }
    }
}
