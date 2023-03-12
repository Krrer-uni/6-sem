package Mobilki.zad2java;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    public static enum gameState {
        playing,
        ended;
    }

    private final long game_legth = (long) 10000;
    private int points;
    private gameState stateOfGame;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        this.setupListeners();
        this.reset();
    }

    public MainActivity() {
        this.stateOfGame = MainActivity.gameState.ended;
    }


    public final long getGame_legth() {
        return this.game_legth;
    }

    public final int getPoints() {
        return this.points;
    }

    public final void setPoints(int var1) {
        this.points = var1;
    }

    private void startGame() {
        Button startButton = findViewById(R.id.startButton);
        startButton.setVisibility(View.GONE);
        reset();
        this.stateOfGame = gameState.playing;
        new CountDownTimer(game_legth, 100) {

            public void onTick(long millisUntilFinished) {
                TextView timerText = findViewById(R.id.timer);
                timerText.setText(getString(R.string.timerFormat, (millisUntilFinished / 1000.0)));
            }

            public void onFinish() {
                TextView timerText = findViewById(R.id.timer);
                timerText.setText(getString(R.string.timerFormat, .0));
                Button startButton = findViewById(R.id.startButton);
                startButton.setVisibility(View.VISIBLE);
                stateOfGame = gameState.ended;
            }
        }.start();

    }

    private final void reset() {
        TextView points_view = this.findViewById(R.id.points);
        points_view.setText("");
        TextView timer_view = this.findViewById(R.id.timer);
        timer_view.setText(getString(R.string.timerFormat, 10.0));
        this.points = 0;
    }

    private void setupListeners() {
        Button resetButton = findViewById(R.id.startButton);
        Button tapButton = findViewById(R.id.tapButton);

        resetButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startGame();
                    }
                }
        );
        tapButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (stateOfGame == gameState.playing) {
                            points++;
                            TextView pointsText = findViewById(R.id.points);
                            if (points < 50) {
                                pointsText.setText(getString(R.string.points_text, points));
                            } else if (points < 100) {
                                pointsText.setText(getString(R.string.points_over_50_text, points));
                            } else {
                                pointsText.setText(getString(R.string.points_over_100_text, points));
                            }
                            pointsText.setTextSize((float) (30.0 + (float) points / 2.0));
                        }
                    }
                }
        );
    }
}