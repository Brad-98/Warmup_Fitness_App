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

    private float timePast;

    private int durationValue;
    private int intensityValue;

    private CountDownTimer warmupTimer;
    private long timeLeftInMilliseconds = 30000;
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
        timePast = userInputData.getFloatExtra("timePast", 0.0f);

        warmupTitleText = findViewById(R.id.warmupTitleText);
        warmupAimText = findViewById(R.id.warmupAimText);
        warmupCountdownText = findViewById(R.id.warmupCountdownText);
        imageToDisplay = (ImageView) findViewById(R.id.imageToDisplay);

        //Creating the exercises/stretchs
        chooseExercise = new ArrayList<>();
        chooseExercise.add("Star Jumps");
        chooseExercise.add("Squats");
        chooseExercise.add("Push Ups");

        chooseStretch = new ArrayList<>();
        chooseStretch.add("Standing Quad");
        chooseStretch.add("Cross-Body Shoulder");
        chooseStretch.add("Touch Toes");

        timePast += 0.5f;

        //Checking to see when the warmup ends
        if(timePast >= (float) durationValue + 0.5f)
        {
            Intent home = new Intent(this, MainActivity.class);
            timeLeftInMilliseconds = 999999999;
            timePast = 0;
            finish();
            startActivity(home);
        }

        //Sets either exercises or stretches to be shown

        if(exerciseButtonSelected == true && stretchingButtonSelected == true)
        {
            selecetedWarmupValue = (int) Math.round(Math.random());
        }
        else if (exerciseButtonSelected == true && stretchingButtonSelected == false)
        {
            selecetedWarmupValue = 0;
        }
        else if(exerciseButtonSelected == false && stretchingButtonSelected == true)
        {
            selecetedWarmupValue = 1;
        }

        //Sets up the warmup page with correct information
        if(selecetedWarmupValue == 0)
        {
            warmupAimText.setText("Aim: " + warmupAimValue * intensityValue);

            switch (chooseRandomExerciseOrStretch(chooseExercise))
            {
                case 0:
                    warmupTitleText.setText(chooseExercise.get(0));
                    imageToDisplay.setImageResource(R.drawable.star_jumps);
                    break;
                case 1:
                    warmupTitleText.setText(chooseExercise.get(1));
                    imageToDisplay.setImageResource(R.drawable.squats);
                    break;
                case 2:
                    warmupTitleText.setText(chooseExercise.get(2));
                    imageToDisplay.setImageResource(R.drawable.push_ups);
                    break;
            }
        }
        else
        {
            warmupAimText.setText("Hold");

            switch (chooseRandomExerciseOrStretch(chooseStretch))
            {
                case 0:
                    warmupTitleText.setText(chooseStretch.get(0));
                    imageToDisplay.setImageResource(R.drawable.standing_quad);
                    break;
                case 1:
                    warmupTitleText.setText(chooseStretch.get(1));
                    imageToDisplay.setImageResource(R.drawable.crossbody_shoulder);
                    break;
                case 2:
                    warmupTitleText.setText(chooseStretch.get(2));
                    imageToDisplay.setImageResource(R.drawable.touch_toes);
                    break;
            }
        }
        startWarmupTimer();
    }

    //Choose random element from the arraylists
    private int chooseRandomExerciseOrStretch(ArrayList<String> list)
    {
        int arrayElement = (int) (Math.random() * list.size());
        return  arrayElement;
    }

    //Warmup countdown timer
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
                changeActivity.putExtra("recoveryTimerValue", recoveryTimerValue * (4 - intensityValue));

                //Resending the data
                changeActivity.putExtra("resentDurationValue", durationValue);
                changeActivity.putExtra("resentIntensityValue", intensityValue);
                changeActivity.putExtra("resentExerciseButtonSelected", exerciseButtonSelected);
                changeActivity.putExtra("resentStretchingButtonSelected", stretchingButtonSelected);
                changeActivity.putExtra("timePast", timePast);

                startActivity(changeActivity);
                finish();
            }
        }.start();
    }
}
