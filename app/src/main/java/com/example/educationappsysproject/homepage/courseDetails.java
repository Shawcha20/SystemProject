package com.example.educationappsysproject.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.educationappsysproject.R;

public class courseDetails extends AppCompatActivity {


    CardView video , exam , pdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        video = findViewById(R.id.videoCard);
        exam = findViewById(R.id.examCard);
        pdf = findViewById(R.id.pdfCard);

        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(courseDetails.this , courseDetails.class);
                startActivity(i);
            }
        });
        exam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(courseDetails.this , courseDetails.class);
                startActivity(i);
            }
        });

        pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(courseDetails.this , courseDetails.class);
                startActivity(i);
            }
        });
    }
}