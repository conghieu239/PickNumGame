package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Result1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result1);

        Button btnPlayAgain = findViewById(R.id.button);

        TextView txtPlayerTime = findViewById(R.id.txtResult);
        long playerRunTime = getIntent().getLongExtra("playerRunTime", 0);
        txtPlayerTime.setText("Thời gian của bạn: " + formatTime(playerRunTime));

        List<String> listScores = getIntent().getStringArrayListExtra("highScores");
        List<Long> highScores = new ArrayList<>();
        for (String scoreString : listScores) {
            try {
                long score = Long.parseLong(scoreString);
                highScores.add(score);
            } catch (NumberFormatException e) {
            }
        }

        Collections.sort(highScores);

        // Hiển thị kết quả cho người chơi
        int playerRank = highScores.indexOf(playerRunTime) + 1;
        TextView txtResult = findViewById(R.id.txtResult);

        txtResult.setText("Chúc mừng bạn!\nBạn ở vị trí thứ " + playerRank);

        StringBuilder topScores = new StringBuilder("Kỉ lục\n");
        int maxDisplayCount = Math.min(highScores.size(), 3);
        for (int i = 0; i < maxDisplayCount; i++) {
            long score = highScores.get(i);
            String time = formatTime(score);
            topScores.append(time).append("\n");
        }
        TextView txtTopScores = findViewById(R.id.txtTopScores);
        txtTopScores.setText(topScores.toString());

        btnPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveHighScoresToSharedPreferences(highScores);

                Intent intent = new Intent(Result1Activity.this, MainActivity.class);
                startActivity(intent);

                finish();
            }
        });
    }

    private void saveHighScoresToSharedPreferences(List<Long> highScores) {
        JSONArray jsonArray = new JSONArray();
        for (Long score : highScores) {
            jsonArray.put(score);
        }
        String jsonString = jsonArray.toString();

        SharedPreferences preferences = getSharedPreferences("HighScores", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("high_scores", jsonString);
        editor.apply();
    }
    private String formatTime(long score) {
        long seconds = score / 1000;
        long minutes = seconds / 60;
        seconds = seconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}
