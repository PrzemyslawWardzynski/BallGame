package com.example.ballgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class GameOverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        TextView tv1 = findViewById(R.id.textView);
        float score = getIntent().getExtras().getFloat("timeScore");
        tv1.setText(Float.toString(score));

        writeFile(Float.toString(score));

    }

    public void restart(View v)
    {
        this.finish();
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    public void goToMenu(View v)
    {
        this.finish();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void writeFile(String textToSave) {

        try {
            FileOutputStream fileOutputStream = openFileOutput("Times.txt", MODE_APPEND);
            fileOutputStream.write(textToSave.getBytes());
            fileOutputStream.write(" ".getBytes());
            fileOutputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
