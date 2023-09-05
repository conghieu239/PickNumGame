package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.GridView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button btnStart;
    private TextView txtTime;
    private GridView gridView;
    private ButtonAdapter adapter;
    private boolean gameStarted = false;
    private long startTimeMillis;
    private Handler handler = new Handler();
    private Runnable timerRunnable;
    private ArrayList<Long> highScores = new ArrayList<>();
    public long elapsedTimeMillis;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Integer> list = new ArrayList(){};
        for (int i=1; i<= 10; i++)
            list.add(i);

        btnStart = findViewById(R.id.btnStart);
        txtTime = findViewById(R.id.txtTime);
        gridView = findViewById(R.id.griView);

        ArrayList<Long> highScores = loadHighScoresFromSharedPreferences();

        adapter = new ButtonAdapter(this, list, highScores, elapsedTimeMillis);
        //gridView.setAdapter(adapter);

        //Ấn nút Start để chơi game
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gridView.setAdapter(adapter);
                startGame();
            }
        });
    }

    private ArrayList<Long> loadHighScoresFromSharedPreferences() {
        ArrayList<Long> highScores = new ArrayList<>();
        SharedPreferences preferences = getSharedPreferences("HighScores", Context.MODE_PRIVATE);
        String jsonString = preferences.getString("high_scores", "");

        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                long score = jsonArray.getLong(i);
                highScores.add(score);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return highScores;
    }

    private void startGame() {
        btnStart.setVisibility(View.INVISIBLE);
        startTimeMillis = System.currentTimeMillis();
        startTimer();
    }

    private void startTimer() {
        timerRunnable = new Runnable() {
            @Override
            public void run() {
                elapsedTimeMillis = System.currentTimeMillis() - startTimeMillis;
                int seconds = (int) (elapsedTimeMillis / 1000) % 60;
                int minutes = (int) ((elapsedTimeMillis / (1000 * 60)) % 60);

                String timeText = String.format("%02d:%02d", minutes, seconds);

                txtTime.setText("Thời gian: " + timeText);

                adapter.updateElapsedTime(elapsedTimeMillis);

                handler.postDelayed(this, 1000);
            }
        };
        handler.post(timerRunnable);
    }
}