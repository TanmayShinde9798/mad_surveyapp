package com.example.surveyapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class SurveyActivity extends AppCompatActivity {

    RadioGroup radioGroup;
    RadioButton r1, r2, r3;
    CheckBox cb1, cb2, cb3;
    ProgressBar progressBar;
    ImageButton btnReset;
    Button btnSubmit;
    Spinner subjectSpinner;
    TextView q1, q2, q3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        initViews();

        String survey = getIntent().getStringExtra("title");
        if (survey == null) survey = "Survey";

        setSurveyQuestions(survey);

        btnSubmit.setOnClickListener(v -> submitSurvey());
    }

    //  INIT
    private void initViews() {
        q1 = findViewById(R.id.q1);
        q2 = findViewById(R.id.q2);
        q3 = findViewById(R.id.q3);

        r1 = findViewById(R.id.r1);
        r2 = findViewById(R.id.r2);
        r3 = findViewById(R.id.r3);

        radioGroup = findViewById(R.id.radioGroup);

        cb1 = findViewById(R.id.cb1);
        cb2 = findViewById(R.id.cb2);
        cb3 = findViewById(R.id.cb3);
        btnReset = findViewById(R.id.btnReset);

        btnReset.setOnClickListener(v -> resetSurvey());

        subjectSpinner = findViewById(R.id.subjectSpinner);
        progressBar = findViewById(R.id.progressBar);
        btnSubmit = findViewById(R.id.btnSubmit);

        q3.setVisibility(View.GONE);
        subjectSpinner.setVisibility(View.GONE);
    }

    // QUESTIONS
    private void setSurveyQuestions(String survey) {

        if (survey.equals("Student Survey")) {

            q1.setText("How do you rate your studies?");
            r1.setText("Excellent");
            r2.setText("Good");
            r3.setText("Average");

            q2.setText("Which facilities do you use?");
            cb1.setText("Library");
            cb2.setText("Labs");
            cb3.setText("Sports");

            setupSpinner();

        } else if (survey.equals("College Survey")) {

            q1.setText("How is college infrastructure?");
            r1.setText("Very Good");
            r2.setText("Good");
            r3.setText("Poor");

            q2.setText("What needs improvement?");
            cb1.setText("Classrooms");
            cb2.setText("Canteen");
            cb3.setText("Parking");

        } else {

            q1.setText("How is this app?");
            r1.setText("Easy");
            r2.setText("Average");
            r3.setText("Hard");

            q2.setText("What do you like?");
            cb1.setText("UI");
            cb2.setText("Speed");
            cb3.setText("Simplicity");
        }
    }

    // SPINNER
    private void setupSpinner() {
        q3.setText("What is your favorite subject?");
        q3.setVisibility(View.VISIBLE);
        subjectSpinner.setVisibility(View.VISIBLE);

        String[] subjects = {
                "Select Subject",
                "Math",
                "Science",
                "History",
                "English"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                subjects
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subjectSpinner.setAdapter(adapter);
    }

    //  SUBMIT
    private void submitSurvey() {

        if (radioGroup.getCheckedRadioButtonId() == -1 ||
                (!cb1.isChecked() && !cb2.isChecked() && !cb3.isChecked())) {
            showCustomToast("Please answer all questions", false);
            return;
        }

        if (subjectSpinner.getVisibility() == View.VISIBLE &&
                subjectSpinner.getSelectedItemPosition() == 0) {
            showCustomToast("Please select a subject", false);
            return;
        }

        btnSubmit.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);

        new Handler().postDelayed(() -> {

            progressBar.setVisibility(View.GONE);
            btnSubmit.setEnabled(true);

            showCustomToast("Survey submitted successfully", true);
            startActivity(new Intent(this, SurveyListActivity.class));
            finish();

        }, 1500);
    }

    //  TOAST
    private void showCustomToast(String msg, boolean success) {
        View layout = LayoutInflater.from(this)
                .inflate(R.layout.toast_custom, findViewById(R.id.toastRoot));

        ImageView icon = layout.findViewById(R.id.toastIcon);
        TextView text = layout.findViewById(R.id.toastText);

        text.setText(msg);
        icon.setImageResource(success ? R.drawable.like : R.drawable.dislike);
        layout.setBackgroundResource(success
                ? R.drawable.toast_success_bg
                : R.drawable.toast_fail_bg);

        Toast toast = new Toast(this);
        toast.setView(layout);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }
    private void resetSurvey() {

        radioGroup.clearCheck();

        cb1.setChecked(false);
        cb2.setChecked(false);
        cb3.setChecked(false);

        if (subjectSpinner.getVisibility() == View.VISIBLE) {
            subjectSpinner.setSelection(0);
        }

        showCustomToast("Choices cleared", true);
    }

}
