package uk.ac.brighton.uni.modern1.warmupfitnessapp;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Warmup extends AppCompatActivity
{
    private TextView warmupTitleText;
    private TextView warmupAimText;
    private TextView warmupCountdownText;
    private ImageView imageToDisplay;

    private boolean exerciseButtonSelected = false;
    private boolean stretchingButtonSelected = false;
    private int selecetedWarmupValue;

    private int warmupAimValue = 10;

    private int durationValue;
    private int intensityValue;

    private CountDownTimer warmupTimer;
    private long timeLeftInMilliseconds = 10000;
    private long recoveryTimerValue = 10000;
    private boolean recoveryTimerActive = true;

    private Intent changeActivity;

    ArrayList<String> chooseExercise;
    ArrayList<String> chooseStretch;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warmup);

        changeActivity = new Intent(this, CountdownTimer.class);

        //Receive user input from countdownTimer class
        Intent userInputData = getIntent();
        durationValue = userInputData.getIntExtra("durationValue", 0);
        intensityValue = userInputData.getIntExtra("intensityValue", 0);
        exerciseButtonSelected = userInputData.getBooleanExtra("exerciseButtonSelected", false);
        stretchingButtonSelected = userInputData.getBooleanExtra("stretchingButtonSelected", false);

        warmupTitleText = findViewById(R.id.warmupTitleText);
        warmupAimText = findViewById(R.id.warmupAimText);
        warmupCountdownText = findViewById(R.id.warmupCountdownText);
        imageToDisplay = (ImageView) findViewById(R.id.imageToDisplay);

        //If there was a database you would sub it in here
        chooseExercise = new ArrayList<>();
        chooseExercise.add("Jumping Jack");
        chooseExercise.add("Sit Up");
        chooseExercise.add("Push Up");

        chooseStretch = new ArrayList<>();
        chooseStretch.add("Leg");
        chooseStretch.add("Arm");
        chooseStretch.add("Touch Toes");

        //Use this to make new countdown times after the exercise ends.


        //Depending on the title that determins the image

        if(exerciseButtonSelected == true && stretchingButtonSelected == true)
        {
            selecetedWarmupValue = (int) Math.round(Math.random());
        }
        else if (exerciseButtonSelected == true)
        {
            selecetedWarmupValue = 0;
        }
        else //if(exerciseButtonSelected == false && stretchingButtonSelected == true)
        {
            selecetedWarmupValue = 1;
        }


        if(selecetedWarmupValue == 0)
        {
            warmupAimText.setText("Aim: " + warmupAimValue * intensityValue);

            switch (chooseRandomExerciseOrStretch(chooseExercise))
            {
                case 0:
                    warmupTitleText.setText(chooseExercise.get(0));
                    imageToDisplay.setImageResource(R.drawable.jumping_jack);
                    break;
                case 1:
                    warmupTitleText.setText(chooseExercise.get(1));
                    break;
                case 2:
                    warmupTitleText.setText(chooseExercise.get(2));
                    break;
            }
        }
        else
        {
            warmupAimText.setText("Hold: " + warmupAimValue * intensityValue);

            switch (chooseRandomExerciseOrStretch(chooseStretch))
            {
                case 0:
                    warmupTitleText.setText(chooseStretch.get(0));
                    imageToDisplay.setImageResource(R.drawable.jumping_jack);
                    break;
                case 1:
                    warmupTitleText.setText(chooseStretch.get(1));
                    break;
                case 2:
                    warmupTitleText.setText(chooseStretch.get(2));
                    break;
            }
        }
        startWarmupTimer();
    }

    private int chooseRandomExerciseOrStretch(ArrayList<String> list)
    {
        int arrayElement = (int) (Math.random() * list.size());
        return  arrayElement;
    }

    public void startWarmupTimer()
    {
        warmupTimer = new CountDownTimer(timeLeftInMilliseconds, 100)
        {
            @Override
            public void onTick(long millisUntilFinished)
            {
                timeLeftInMilliseconds = millisUntilFinished;
                warmupCountdownText.setText("" + timeLeftInMilliseconds / 1000);
            }

            @Override
            public void onFinish()
            {
                changeActivity.putExtra("recoveryTimerActive", recoveryTimerActive);
                changeActivity.putExtra("recoveryTimerValue", recoveryTimerValue * intensityValue);
                startActivity(changeActivity);
            }
        }.start();
    }
}
