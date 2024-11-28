package com.example.educationappsysproject.Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.educationappsysproject.R;
import com.example.educationappsysproject.homepage.homeScreen;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.android.material.textfield.TextInputEditText;

public class login extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    TextView goSignUp;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    TextInputEditText email, password;
    Button signIn;
    GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth
        fAuth = FirebaseAuth.getInstance();

        // Initialize UI components
        progressBar = findViewById(R.id.progressSignIn);
        email = findViewById(R.id.signInEmail);
        password = findViewById(R.id.signInPassword);
        signIn = findViewById(R.id.signIn);
        goSignUp = findViewById(R.id.toSignUP);

        // Find Google Sign-In button
        SignInButton googleSignInButton = findViewById(R.id.signInwGoogle);

        // Set up Google Sign-In options
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.google_client_id)) // Replace with your Google Client ID
                .requestEmail()
                .build();

        // Build GoogleSignInClient
        googleSignInClient = GoogleSignIn.getClient(this, gso);

        // Set onClickListener for Google Sign-In button
        googleSignInButton.setOnClickListener(view -> {
            Intent signInIntent = googleSignInClient.getSignInIntent();
            activityResultLauncher.launch(signInIntent);
        });

        // Sign in with email and password
        signIn.setOnClickListener(v -> {
            String Remail = email.getText().toString().trim();
            String Rpassword = password.getText().toString().trim();

            if (TextUtils.isEmpty(Remail)) {
                Toast.makeText(login.this, "Email is required", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(Rpassword)) {
                Toast.makeText(login.this, "Password field is required", Toast.LENGTH_SHORT).show();
                return;
            }
            if (Rpassword.length() < 6) {
                Toast.makeText(login.this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                return;
            }

            progressBar.setVisibility(View.VISIBLE);
            fAuth.signInWithEmailAndPassword(Remail, Rpassword).addOnCompleteListener(task -> {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    Toast.makeText(login.this, "User logged in", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), homeScreen.class));
                    finish();
                } else {
                    Toast.makeText(login.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Login failed: ", task.getException());
                }
            });
        });

        // Navigate to Sign-Up screen
        goSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(login.this, signup.class);
            startActivity(intent);
            finish();
        });
    }

    private final ActivityResultLauncher<Intent> activityResultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                    try {
                        GoogleSignInAccount account = task.getResult(ApiException.class);
                        if (account != null) {
                            // Get Google ID token and exchange it for a Firebase credential
                            AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                            fAuth.signInWithCredential(credential).addOnCompleteListener(this, task1 -> {
                                if (task1.isSuccessful()) {
                                    // Sign-in successful, navigate to home screen
                                    Toast.makeText(login.this, "Sign-In successful", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(login.this, homeScreen.class));
                                    finish();
                                } else {
                                    // Sign-in failed
                                    Toast.makeText(login.this, "Authentication failed: " + task1.getException(), Toast.LENGTH_SHORT).show();
                                    Log.e(TAG, "Google sign-in failed: ", task1.getException());
                                }
                            });
                        }
                    } catch (ApiException e) {
                        Toast.makeText(login.this, "Google Sign-In failed", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "Google sign-in error: ", e);
                    }
                }
            });
}
