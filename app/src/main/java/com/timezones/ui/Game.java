package com.timezones.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.timezones.R;
import com.timezones.entity.City;
import com.timezones.utils.AnswerBase;
import com.timezones.utils.TimerDisplay;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game extends AppCompatActivity implements View.OnClickListener {

    private TextView counterText;
    private CountDownTimer countDownTimer;
    private Button selectorButton;
    private boolean hardMode;
    private List<Integer> gameSet;
    private final Map<String, Integer> answerOptions = new HashMap<>();


    private int score = 0;
    private int question = 0;
    private int totalQuestions = 0;
    private int selectedAnswer = -1;

    private static final String HARD_MODE = "hardMode";
    private static final String GAME_SET = "gameSet";
    private static final String SCORE = "score";
    private static final String TOTAL_QUESTIONS = "totalQuestions";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Bundle bundle = getIntent().getExtras();
        hardMode = false;
        gameSet = new ArrayList<>();
        if (bundle != null) {
            hardMode = bundle.getBoolean(HARD_MODE);
            gameSet = bundle.getIntegerArrayList(GAME_SET);
            if (gameSet != null) {
                Collections.shuffle(gameSet);
                totalQuestions = gameSet.size();
                gameSet.forEach(index -> answerOptions
                        .put(AnswerBase.cities.get(index).getName(), index));
            }
        }

        counterText = findViewById(R.id.game_counter);

        selectorButton = findViewById(R.id.selector_button);
        Button submitButton = findViewById(R.id.submit_button);

        selectorButton.setOnClickListener(this);
        submitButton.setOnClickListener(this);

        loadNewQuestion();
    }

    @Override
    public void onClick(View v) {
        Button clickedButton = (Button) v;

        if (clickedButton.getId() == R.id.selector_button) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Game.this);
            builder.setTitle("Select the answer");
            String[] answers = answerOptions.keySet().toArray(new String[0]);
            builder.setItems(answers, (dialog, which) -> {
                String selectedCity = answers[which];
                try {
                    selectedAnswer = answerOptions.get(selectedCity);
                } catch (NullPointerException e) {
                    selectedAnswer = -1;
                }
                selectorButton.setText(selectedCity);
            });

            builder.show();
        } else if (clickedButton.getId() == R.id.submit_button && selectedAnswer != -1) {
            processAnswer();
        }
    }

    private void loadNewQuestion() {
        if (question < totalQuestions) {
            ImageView timeZoneImage = findViewById(R.id.game_timezone);
            City city = AnswerBase.cities.get(gameSet.get(question));
            timeZoneImage.setImageResource(getResources().getIdentifier(
                    city.getTimeZone(), "drawable", getPackageName()));
            TextView currentScore = findViewById(R.id.game_score);
            @SuppressLint("DefaultLocale") String currentScoreString =
                    String.format("Current score: %d/%d", score, totalQuestions);
            currentScore.setText(currentScoreString);
            TextView currentQuestion = findViewById(R.id.game_current);
            @SuppressLint("DefaultLocale") String currentQuestionString =
                    String.format("Question №%d", question + 1);
            currentQuestion.setText(currentQuestionString);

            if (hardMode) {
                countDownTimer = new CountDownTimer(10000, 1000) {
                    @Override
                    public void onTick(long millis) {
                        String formatted = TimerDisplay.getDisplay(millis);
                        counterText.setText(formatted);
                    }

                    @Override
                    public void onFinish() {
                        counterText.setText(R.string.counter_zero);
                        processAnswer();
                    }
                }.start();
            }
        } else {
            endGame();
        }
    }

    private void processAnswer() {
        if (selectedAnswer == gameSet.get(question)) {
            score++;
        }
        question++;
        if (hardMode) {
            countDownTimer.cancel();
        }
        loadNewQuestion();
        selectorButton.setText(R.string.game_selector);
        selectedAnswer = -1;
    }

    private void endGame() {
        Intent intent = new Intent(this, GameEnd.class);
        Bundle bundle = new Bundle();
        bundle.putInt(SCORE, score);
        bundle.putInt(TOTAL_QUESTIONS, totalQuestions);
        intent.putExtras(bundle);
        startActivity(intent);
        if (hardMode) {
            countDownTimer.cancel();
        }
        finish();
    }

    @Override
    public void onBackPressed() {
        countDownTimer.cancel();
        finish();
    }
}