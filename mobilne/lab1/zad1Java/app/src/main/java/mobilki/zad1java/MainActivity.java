package mobilki.zad1java;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    int random_left;
    int random_right;
    int points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setListeners();
        roll();
    }


    protected void roll(){
        Button left_button = (Button) findViewById(R.id.leftButton);
        Button right_button = (Button) findViewById(R.id.rightButton);
        Random rng = new Random();
        random_left = rng.nextInt(10);
        random_right = rng.nextInt(10);
        left_button.setText(String.valueOf(random_left));
        right_button.setText(String.valueOf(random_right));
    }

    protected void checkGuess(int answer, int other ){
        if(answer > other){
            Toast.makeText(this, "Correct!",Toast.LENGTH_SHORT).show();
            points++;
            TextView pointText = findViewById(R.id.Points);
            pointText.setText(getString(R.string.gamePoints, points));
        }else{
            Toast.makeText(this, "Wrong!",Toast.LENGTH_SHORT).show();
        }
        roll();
    }
    protected void setListeners(){
        Button left_button = findViewById(R.id.leftButton);
        Button right_button = findViewById(R.id.rightButton);

        findViewById(R.id.leftButton).setOnClickListener(v -> checkGuess(random_left,random_right));
        findViewById(R.id.rightButton).setOnClickListener(v -> checkGuess(random_right,random_left));

    }


}