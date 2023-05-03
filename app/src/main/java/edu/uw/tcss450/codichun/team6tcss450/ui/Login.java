package edu.uw.tcss450.codichun.team6tcss450.ui;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import edu.uw.tcss450.codichun.team6tcss450.R;

public class Login extends AppCompatActivity {

    private EditText mUsernameEditText;
    private EditText mPasswordEditText;
    private Button mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Find the views by their IDs
        mUsernameEditText = findViewById(R.id.username);
        mPasswordEditText = findViewById(R.id.password);
        mLoginButton = findViewById(R.id.loginbtn);

        // Set a click listener for the login button
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the input from the username and password fields
                String username = mUsernameEditText.getText().toString().trim();
                String password = mPasswordEditText.getText().toString().trim();

                // TODO: Send the user's credentials to the server to be verified

                // TODO: Handle the response from the server to determine whether the login was successful or not

                // For now, just display a toast message indicating that the login button was clicked
                Toast.makeText(Login.this, "Login button clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
