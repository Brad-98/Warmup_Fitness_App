package uk.ac.brighton.uni.modern1.warmupfitnessapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class Warmup extends AppCompatActivity
{
    public TextView warmupTitleText;
    public TextView warmupAimText;
    public TextView warmupCountdownText;

    private int warmupAimValue = 10;

    private int durationValue;
    private int intensityValue;
    //Create array that stores the titles

    ArrayList<String> chooseExercise;
    ArrayList<String> chooseStretch;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warmup);

        //Receive user input from countdownTimer class
        Intent userInputData = getIntent();
        durationValue = userInputData.getIntExtra("durationValue", 0);
        intensityValue = userInputData.getIntExtra("intensityValue", 0);

        warmupTitleText = findViewById(R.id.warmupTitleText);
        warmupAimText = findViewById(R.id.warmupAimText);
        warmupCountdownText = findViewById(R.id.warmupCountdownText);

        chooseExercise = new ArrayList<>();
        chooseExercise.add("Jumping Jack");
        chooseExercise.add("Sit Up");
        chooseExercise.add("Push Up");

        chooseStretch = new ArrayList<>();
        chooseStretch.add("Leg");
        chooseStretch.add("Arm");
        chooseStretch.add("Touch Toes");

        //Use this to make new countdown times after the exercise ends.

        warmupTitleText.setText(chooseExercise.get(chooseRandomExerciseOrStretch(chooseExercise)));
        //Depending on the title that determins the image



       // if(1 > 6)
        //{
        warmupAimText.setText("Aim: " + warmupAimValue * intensityValue);
            switch (chooseRandomExerciseOrStretch(chooseExercise))
            {

                //Change images
                case 0:
                    break;
                case 1:

                    break;
                case 2:

                    break;
            }
        //}
    }

    private int chooseRandomExerciseOrStretch(ArrayList<String> list)
    {
        int arrayElement = (int) (Math.random() * list.size());
        return  arrayElement;
    }
}
