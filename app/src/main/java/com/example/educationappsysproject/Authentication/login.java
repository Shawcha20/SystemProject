package com.example.educationappsysproject.Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.educationappsysproject.R;
import com.example.educationappsysproject.homepage.homeScreen;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

import java.util.EventListener;

public class login extends AppCompatActivity {

    TextView goSignUp;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    TextInputEditText email, password;
    Button signIn;
    TextView frgtPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        fAuth=FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.progressSignIn);
        email=findViewById(R.id.signInEmail);
        password=findViewById(R.id.signInPassword);
        signIn=findViewById(R.id.signIn);
        goSignUp=findViewById(R.id.toSignUP);
        signIn.setOnClickListener(v->{
            String Remail = email.getText().toString().trim();
            String Rpassword = password.getText().toString().trim();

            if (TextUtils.isEmpty(Remail)) {

                Toast.makeText(login.this, "Email is required", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(Rpassword)) {
//                Snackbar.make(findViewById(android.R.id.content), "Password field is required", Snackbar.LENGTH_SHORT).show();

//                Rpassword.setError("Password field is required");
                Toast.makeText(login.this, "password filed is required", Toast.LENGTH_SHORT).show();
                return;
            }
            if (Rpassword.length() < 6)
            {
                Toast.makeText(login.this, "email must be at least 6 charecter", Toast.LENGTH_SHORT).show();
                return;
            }
            progressBar.setVisibility(View.VISIBLE);
            fAuth.signInWithEmailAndPassword(Remail,Rpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(login.this, "user logged in",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), homeScreen.class));
                        finish();
                    }
                    else {
                        Toast.makeText(login.this, "error"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });
        goSignUp.setOnClickListener(v->{
            Intent intent= new Intent(login.this, signup.class);
            startActivity(intent);
            finish();
        });



    }
}