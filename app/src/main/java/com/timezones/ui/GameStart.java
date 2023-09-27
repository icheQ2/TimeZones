package com.timezones.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.timezones.R;
import com.timezones.entity.City;
import com.timezones.utils.AnswerBase;
import com.timezones.utils.TimerDisplay;

import java.util.ArrayList;


public class GameStart extends AppCompatActivity {

    private TextView counterText;
    private CountDownTimer countDownTimer;
    private boolean hardMode;
    private ArrayList<Integer> gameSet;

    private static final String HARD_MODE = "hardMode";
    private static final String GAME_SET = "gameSet";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamestart);

        Bundle bundle = getIntent().getExtras();
        hardMode = false;
        if (bundle != null) {
            hardMode = bundle.getBoolean(HARD_MODE);
        }

        gameSet = new ArrayList<>(AnswerBase.getGameSet());

        for (int i = 0; i < gameSet.size(); i++) {
            City city = AnswerBase.cities.get(gameSet.get(i));
            int cityTextId = getResources().getIdentifier(
                    "start_city" + i, "id", getPackageName());
            TextView cityText = findViewById(cityTextId);
            cityText.setText(city.getName());
            int timeZoneImageId = getResources().getIdentifier(
                    "start_timezone" + i, "id", getPackageName());
            ImageView timeZoneImage = findViewById(timeZoneImageId);
            timeZoneImage.setImageResource(getResources().getIdentifier(
                    city.getTimeZone(), "drawable", getPackageName()));
        }

        counterText = findViewById(R.id.start_counter);

        countDownTimer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millis) {
                String formatted = TimerDisplay.getDisplay(millis);
                counterText.setText(formatted);
            }

            @Override
            public void onFinish() {
                counterText.setText(R.string.counter_zero);
            startGame(null);
            }
        }.start();
    }

    public void startGame(View view) {
        Intent intent = new Intent(this, Game.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean(HARD_MODE, hardMode);
        bundle.putIntegerArrayList(GAME_SET, gameSet);
        intent.putExtras(bundle);
        startActivity(intent);
        countDownTimer.cancel();
        finish();
    }

    @Override
    public void onBackPressed() {
        countDownTimer.cancel();
        finish();
    }
}