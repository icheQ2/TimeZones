package com.timezones.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.timezones.R;

public class MainMenu extends AppCompatActivity {

    private static final String HARD_MODE = "hardMode";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);
    }

    public void playCasualGame(View view) {
        Intent intent = new Intent(this, GameStart.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean(HARD_MODE, false);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void playTimeGame(View view) {
        Intent intent = new Intent(this, GameStart.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean(HARD_MODE, true);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
