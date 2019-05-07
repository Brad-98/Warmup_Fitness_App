package uk.ac.brighton.uni.modern1.warmupfitnessapp;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class CountdownTimer extends AppCompatActivity
{
    private TextView countdownText;
    private CountDownTimer countDownTimer;
    private TextView recoverText;

    private long timeLeftInMilliseconds;
    private int count = 0;

    public long countdownValue = 10000;
    private long recoveryTimerValue;
    private boolean recoveryTimerActive = false;

    private Intent changeActivity;
    private int durationValue;
    private int intensityValue;

    private boolean exerciseButtonSelected = false;
    private boolean stretchingButtonSelected = false;

    private int resentDurationValue;
    private int resentIntensityValue;
    private boolean resentExerciseButtonSelected;
    private boolean resentStretchingButtonSelected;
    private Intent resentUserInputData;

    private Vibrator vibrator;

    private float timePast;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown_timer);

        //Receive user input from mainActivity class
        Intent userInputData = getIntent();
        durationValue = userInputData.getIntExtra("durationValue", 0);
        intensityValue = userInputData.getIntExtra("intensityValue", 0);
        exerciseButtonSelected = userInputData.getBooleanExtra("exerciseButtonSelected", false);
        stretchingButtonSelected = userInputData.getBooleanExtra("stretchingButtonSelected", false);


        recoveryTimerActive = userInputData.getBooleanExtra("recoveryTimerActive", false);
        recoveryTimerValue = userInputData.getLongExtra("recoveryTimerValue", 0);

        //Resent values
        Intent resentUserInputData = getIntent();
        resentDurationValue = resentUserInputData.getIntExtra("resentDurationValue", 0);
        resentIntensityValue = resentUserInputData.getIntExtra("resentIntensityValue", 0);
        resentExerciseButtonSelected = resentUserInputData.getBooleanExtra("resentExerciseButtonSelected", false);
        resentStretchingButtonSelected = resentUserInputData.getBooleanExtra("resentStretchingButtonSelected", false);

        timePast = resentUserInputData.getFloatExtra("timePast", 0.0f);

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        countdownText = (TextView) findViewById(R.id.countdownText);
        changeActivity = new Intent(this, Warmup.class);

        recoverText = (TextView) findViewById(R.id.recoverText);

            if (recoveryTimerActive == false)
            {
                startCountdownTimer(countdownValue);

            }
            else {
                startCountdownTimer(recoveryTimerValue);
                recoverText.setText("Recovery Time");
            }
    }

    public void startCountdownTimer(long countdownValue)
    {
        timeLeftInMilliseconds = countdownValue;

        countDownTimer = new CountDownTimer(timeLeftInMilliseconds, 100)
        {
            @Override
            public void onTick(long millisUntilFinished)
            {
                timeLeftInMilliseconds = millisUntilFinished;
                countdownText.setText("" + timeLeftInMilliseconds / 1000);

                if(timeLeftInMilliseconds <= 4000 && timeLeftInMilliseconds >= 3500)
                {
                    vibrator.vibrate(50);
                }
                else if(timeLeftInMilliseconds <= 3000 && timeLeftInMilliseconds >= 2500)
                {
                    vibrator.vibrate(50);
                }
                else if(timeLeftInMilliseconds <= 2000 && timeLeftInMilliseconds >= 1500)
                {
                    vibrator.vibrate(50);
                }
                else if(timeLeftInMilliseconds <= 1000 && timeLeftInMilliseconds >= 0)
                {
                    vibrator.vibrate(50);
                }
            }

            @Override
            public void onFinish()
            {
                    if(recoveryTimerActive == false)
                    {
                        changeActivity.putExtra("durationValue", durationValue);
                        changeActivity.putExtra("intensityValue", intensityValue);
                        changeActivity.putExtra("exerciseButtonSelected", exerciseButtonSelected);
                        changeActivity.putExtra("stretchingButtonSelected", stretchingButtonSelected);
                    }
                    else
                    {
                        changeActivity.putExtra("durationValue", resentDurationValue);
                        changeActivity.putExtra("intensityValue", resentIntensityValue);
                        changeActivity.putExtra("exerciseButtonSelected", resentExerciseButtonSelected);
                        changeActivity.putExtra("stretchingButtonSelected", resentStretchingButtonSelected);
                        changeActivity.putExtra("timePast", timePast);
                    }
                startActivity(changeActivity);
                    finish();
            }
        }.start();
    }
}
