package uk.ac.brighton.uni.modern1.warmupfitnessapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Warmup extends AppCompatActivity
{
    public TextView warmupTitleText;
    public TextView warmupAimText;
    public TextView warmupCountdownText;

    private int warmupAimValue = 10;
    //Create array that stores the titles

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warmup);

        warmupTitleText = findViewById(R.id.warmupTitleText);
        warmupAimText = findViewById(R.id.warmupAimText);
        warmupCountdownText = findViewById(R.id.warmupCountdownText);

        Intent userInputData = getIntent();
        int intensityValue = userInputData.getIntExtra("intensityValue", 0);
        //Use this to make new countdown times after the exercise ends.

        warmupTitleText.setText("Jumping Jacks");
        //Depending on the title that determins the image
        warmupAimText.setText("Aim: " + warmupAimValue * intensityValue);
    }
}
