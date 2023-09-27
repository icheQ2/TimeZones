package com.timezones.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.timezones.R;

public class GameEnd extends AppCompatActivity {

    private static final String SCORE = "score";
    private static final String TOTAL_QUESTIONS = "totalQuestions";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameend);

        Bundle bundle = getIntent().getExtras();
        int score = 0;
        int totalQuestions = 0;
        if (bundle != null) {
            score = bundle.getInt(SCORE);
            totalQuestions = bundle.getInt(TOTAL_QUESTIONS);
        }

        TextView finalScore = findViewById(R.id.end_score);
        @SuppressLint("DefaultLocale") String finalScoreString =
                String.format("%d/%d", score, totalQuestions);
        finalScore.setText(finalScoreString);
    }

    public void openMainMenu(View view) {
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }
}