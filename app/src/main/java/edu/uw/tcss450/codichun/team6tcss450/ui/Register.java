package edu.uw.tcss450.codichun.team6tcss450.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import edu.uw.tcss450.codichun.team6tcss450.R;

public class Register extends AppCompatActivity {

    private EditText mUsernameEditText, mEmailEditText, mPasswordEditText, mConfirmPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mUsernameEditText = findViewById(R.id.username);
        mEmailEditText = findViewById(R.id.email);
        mPasswordEditText = findViewById(R.id.password);
        mConfirmPasswordEditText = findViewById(R.id.repassword);

        Button registerButton = findViewById(R.id.signupbtn);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mUsernameEditText.getText().toString().trim();
                String email = mEmailEditText.getText().toString().trim();
                String password = mPasswordEditText.getText().toString().trim();
                String confirmPassword = mConfirmPasswordEditText.getText().toString().trim();

                if (username.isEmpty()) {
                    mUsernameEditText.setError("Username is required");
                    mUsernameEditText.requestFocus();
                    return;
                }

                if (email.isEmpty()) {
                    mEmailEditText.setError("Email is required");
                    mEmailEditText.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    mEmailEditText.setError("Enter a valid email address");
                    mEmailEditText.requestFocus();
                    return;
                }

                if (password.isEmpty()) {
                    mPasswordEditText.setError("Password is required");
                    mPasswordEditText.requestFocus();
                    return;
                }

                if (password.length() < 6) {
                    mPasswordEditText.setError("Password should be at least 6 characters long");
                    mPasswordEditText.requestFocus();
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    mConfirmPasswordEditText.setError("Passwords do not match");
                    mConfirmPasswordEditText.requestFocus();
                    return;
                }

                registerUser(username, email, password);
            }
        });
    }

    private void registerUser(final String username, final String email, final String password) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://team6tcss450backend-env.eba-w8dtq3qz.us-east-2.elasticbeanstalk.com/api/auth/register";

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("username", username);
            jsonBody.put("email", email);
            jsonBody.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    boolean success = response.getBoolean("success");
                    String message = response.getString("message");
                    if (success) {
                        Toast.makeText(Register.this, message, Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(Register.this, message, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Register.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(jsonObjectRequest);
    }
}