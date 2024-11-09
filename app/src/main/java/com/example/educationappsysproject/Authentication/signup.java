package com.example.educationappsysproject.Authentication;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signup extends AppCompatActivity {

    TextView goToSignIn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    TextInputEditText email, sid , password ,sName;
    Button register;
    Drawable drawable;
    FirebaseUser firebaseUser;
    FirebaseDatabase database;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        goToSignIn =findViewById(R.id.toSignIn);
        progressBar=findViewById(R.id.progressSignUp);
        email=findViewById(R.id.signUpEmail);
        sid=findViewById(R.id.studentId);
        password=findViewById(R.id.signUpPassword);
        sName=findViewById(R.id.studentName);
        fAuth= FirebaseAuth.getInstance();
        register=findViewById(R.id.signUpBtn);

        if(fAuth.getCurrentUser() !=null)
        {
            startActivity(new Intent(getApplicationContext(), homeScreen.class));
            finish();
        }
        register.setOnClickListener(v-> {
            String Remail = email.getText().toString().trim();
            String Rpassword = password.getText().toString().trim();
            String Rsid= sid.getText().toString().trim();
            String RsName= sName.getText().toString().trim();
            if (TextUtils.isEmpty(Remail)) {

                Toast.makeText(signup.this, "Email is required", Toast.LENGTH_SHORT).show();
                return;
            }
            if(TextUtils.isEmpty(Rsid))
            {
                Toast.makeText(signup.this, "student id must be filled", Toast.LENGTH_SHORT).show();
                return;
            }
            if(TextUtils.isEmpty(RsName))
            {
                Toast.makeText(signup.this,"student name must be filled", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(Rpassword)) {
//                Snackbar.make(findViewById(android.R.id.content), "Password field is required", Snackbar.LENGTH_SHORT).show();

//                Rpassword.setError("Password field is required");
                Toast.makeText(signup.this, "password filed is required", Toast.LENGTH_SHORT).show();
                return;
            }
            if (Rpassword.length() < 6)
            {
                Toast.makeText(signup.this, "email must be at least 6 charecter", Toast.LENGTH_SHORT).show();
                return;
            }
            progressBar.setVisibility(View.VISIBLE);
            fAuth.createUserWithEmailAndPassword(Remail, Rpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(signup.this,"Signed up", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), homeScreen.class));
                    }
                    else{
                        Toast.makeText(signup.this, "Error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });
        goToSignIn.setOnClickListener(v->{
            startActivity(new Intent(getApplicationContext(), login.class));
            finish();
        });
    }
}