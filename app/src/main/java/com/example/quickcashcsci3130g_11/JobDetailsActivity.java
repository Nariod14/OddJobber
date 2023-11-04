package com.example.quickcashcsci3130g_11;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class JobDetailsActivity extends AppCompatActivity {
    private TextView titleTextView;
    private TextView jobTypeTextView;
    private TextView dateTextView;
    private TextView durationTextView;
    private TextView urgencyTextView;
    private TextView salaryTextView;
    private TextView locationTextView;
    private TextView descriptionTextView;
    private Button applyButton;

    private Job job; // The job object for which details are displayed

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);

        // Initialize UI elements
        titleTextView = findViewById(R.id.titleTextView);
        jobTypeTextView = findViewById(R.id.jobTypeTextView);
        dateTextView = findViewById(R.id.dateTextView);
        durationTextView = findViewById(R.id.durationTextView);
        urgencyTextView = findViewById(R.id.urgencyTextView);
        salaryTextView = findViewById(R.id.salaryTextView);
        locationTextView = findViewById(R.id.locationTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        applyButton = findViewById(R.id.applyButton);

        // Get the job object from the intent
        job = (Job) getIntent().getSerializableExtra("job");

        // Display job details
        titleTextView.setText(job.getTitle());
        jobTypeTextView.setText(job.getJobType());
        dateTextView.setText(job.getDate());
        durationTextView.setText(job.getDuration() + " " + job.getDurationType());
        urgencyTextView.setText(job.getUrgencyType());
        salaryTextView.setText(job.getSalary() + " " + job.getSalaryType());
        locationTextView.setText(job.getLocation());
        descriptionTextView.setText(job.getDescription());

        // Set an OnClickListener for the "Apply" button
        applyButton.setOnClickListener(v -> applyForJob());

        // Add a click listener to the back button
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to the search results screen
                finish();
            }
        });
    }

    private void applyForJob() {
        String userUid;
        // Get the user's Firebase UID (replace with actual UID retrieval method)
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in, and you can get their UID
            userUid = user.getUid();
        } else {
            // User is not signed in; handle this case appropriately
            userUid = "TestUser";
        }
        // Check if the user has already applied for this job to avoid duplicate applications
        if (job.getApplicants() != null && job.getApplicants().contains(userUid)) {
            Toast.makeText(this, "You have already applied for this job.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Add the user's UID to the job's applicants list
        job.addApplicant(userUid);



        // Firebase database reference to update the job
        DatabaseReference jobRef = FirebaseDatabase.getInstance().getReference("jobs").child(job.getJobId());


        // Update the 'applicants' field in the job node with the user's UID
        jobRef.child("applicants").setValue(job.getApplicants(), (error, ref) -> {
            if (error == null) {
                Toast.makeText(this, "Applied for the job successfully.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Failed to apply for the job. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
