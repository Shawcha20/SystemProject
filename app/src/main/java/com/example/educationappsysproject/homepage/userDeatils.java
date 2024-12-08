package com.example.educationappsysproject.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.educationappsysproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class userDeatils extends AppCompatActivity {

    private TextView userEmail, userName, userRoll, welcome;
    private Button backToHomePage;
    private FirebaseUser firebaseUser;
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_deatils);

        // Initialize Firebase instances
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        // Initialize views
        welcome = findViewById(R.id.welcome);
        userEmail = findViewById(R.id.fEmail);
        userName = findViewById(R.id.fName);
        userRoll = findViewById(R.id.fRoll);
        backToHomePage = findViewById(R.id.backToHomePage);

        // Get current user
        firebaseUser = auth.getCurrentUser();
        if (firebaseUser == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            finish(); // Close activity if no user is logged in
            return;
        }

        // Get user ID
        String userId = firebaseUser.getUid();

        // Fetch data from Firestore
        DocumentReference docRef = firestore.collection("users").document(userId);
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Toast.makeText(userDeatils.this, "Error in loading data", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (value != null && value.exists()) {
                    String name = value.getString("Name");
                    String email = value.getString("Email");
                    String roll = value.getString("studentId");

                    // Update UI
                    welcome.setText("Hi " + name);
                    userEmail.setText(email);
                    userName.setText(name);
                    userRoll.setText(roll);
                } else {
                    Toast.makeText(userDeatils.this, "No data found", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Button click listener
        backToHomePage.setOnClickListener(v -> startActivity(new Intent(userDeatils.this, homeScreen.class)));
    }
}
