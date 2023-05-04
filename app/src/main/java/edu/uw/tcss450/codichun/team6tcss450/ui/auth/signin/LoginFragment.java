package edu.uw.tcss450.codichun.team6tcss450.ui.auth.signin;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import edu.uw.tcss450.codichun.team6tcss450.R;

public class LoginFragment extends AppCompatActivity {

    private EditText mUsernameEditText;
    private EditText mPasswordEditText;
    private Button mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);

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
                Toast.makeText(LoginFragment.this, "Login button clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
/*
package edu.uw.tcss450.codichun.team6tcss450.ui.auth.signin;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import edu.uw.tcss450.codichun.team6tcss450.R;
import edu.uw.tcss450.codichun.team6tcss450.databinding.Fragment

public class LoginFragmenttemp extends Fragment {

    private Fragment binding;

    private LoginViewModel mViewModel;

    //public static


    private EditText mUsernameEditText;
    private EditText mPasswordEditText;
    private Button mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);

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
                Toast.makeText(LoginFragmenttemp.this, "Login button clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

 */
