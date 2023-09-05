package com.example.myapplication;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import com.example.myapplication.ResultActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ButtonAdapter extends BaseAdapter {
    private Context context;
    private Random random = new Random();
    private OrderCells orderCells;
    private List<String> buttonTextList = new ArrayList<>();
    private List<Boolean> isButtonHiddenList = new ArrayList<>();
    private ArrayList<Long> highScores;
    private long elapsedTimeMillis;


    private int expectedNumber = 1;
    private int incorrectAttempts = 0;
    private final int maxIncorrectAttempts = 3;


    public ButtonAdapter(Context context, List list, ArrayList<Long> highScores, long elapsedTimeMillis) {
        this.context = context;
        this.orderCells = new OrderCells(list);
        this.highScores = highScores;
        this.elapsedTimeMillis = elapsedTimeMillis;

        for (int i = 0; i < list.size(); i++) {
            isButtonHiddenList.add(false);
        }
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    public void updateElapsedTime(long elapsedTimeMillis) {
        this.elapsedTimeMillis = elapsedTimeMillis;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Button button;
        if (convertView == null) {
            button = new Button(context);
            button.setLayoutParams(new GridView.LayoutParams(250,250));
            button.setPadding(8, 8, 8, 8);

            int color = Color.rgb(random.nextInt(250)+6, random.nextInt(250)+6, random.nextInt(250)+6);
            button.setBackgroundColor(color);
        } else {
            button = (Button) convertView;
        }

        if (position < buttonTextList.size()) {
            String buttonText = buttonTextList.get(position);
            button.setText(buttonText);
        } else {
            String value = Integer.toString(orderCells.getNumber());
            buttonTextList.add(value);
            button.setText(value);
        }

        if (isButtonHiddenList.get(position)) {
            button.setVisibility(View.INVISIBLE);
        } else {
            String buttonText = buttonTextList.get(position);
            button.setText(buttonText);
            button.setVisibility(View.VISIBLE);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String buttonText = buttonTextList.get(position);
                int buttonNumber = Integer.parseInt(buttonText);

                if (buttonNumber == expectedNumber) {
                    expectedNumber++;
                    isButtonHiddenList.set(position, true);

                    //Làm sau khi thắng
                    if (expectedNumber > 10) {
                        long playerRunTime = elapsedTimeMillis;
                        highScores.add(playerRunTime);
                        ArrayList<String> listScores = new ArrayList<>();

                        for (Long score : highScores) {
                            String scoreString = String.valueOf(score);
                            listScores.add(scoreString);
                        }

                        Intent intent = new Intent(context, Result1Activity.class);
                        intent.putExtra("playerRunTime", playerRunTime);
                        intent.putStringArrayListExtra("highScores", listScores);
                        context.startActivity(intent);

                    }
                } else {
                    incorrectAttempts++;
                    if (incorrectAttempts >= maxIncorrectAttempts) {
                        Intent intent = new Intent(context, ResultActivity.class);
                        context.startActivity(intent);
                    }
                }
                notifyDataSetChanged();
            }
        });

        return button;
    }
}
