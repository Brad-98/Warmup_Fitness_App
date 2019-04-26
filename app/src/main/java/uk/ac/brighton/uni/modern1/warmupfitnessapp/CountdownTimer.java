package uk.ac.brighton.uni.modern1.warmupfitnessapp;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class CountdownTimer extends AppCompatActivity
{
    private TextView countdownText;
    private CountDownTimer countDownTimer;

    private long timeLeftInMilliseconds;
    private int count = 0;

    public long countdownValue = 11000;

    private Intent changeActivity;
    private int durationValue;
    private int intensityValue;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown_timer);

        //Receive user input from mainActivity class
        Intent userInputData = getIntent();
        durationValue = userInputData.getIntExtra("durationValue", 0);
        intensityValue = userInputData.getIntExtra("intensityValue", 0);

        countdownText = (TextView) findViewById(R.id.countdownText);
        changeActivity = new Intent(this, Warmup.class);



        startCountdownTimer(countdownValue);
    }

    public void startCountdownTimer(long countdownValue)
    {
        timeLeftInMilliseconds = countdownValue;

        countDownTimer = new CountDownTimer(timeLeftInMilliseconds, 1000)
        {
            @Override
            public void onTick(long millisUntilFinished)
            {
                timeLeftInMilliseconds = millisUntilFinished;
                countdownText.setText("" + timeLeftInMilliseconds / 1000);

                count++;

                if(count == 10)
                {
                    changeActivity.putExtra("durationValue", durationValue);
                    changeActivity.putExtra("intensityValue", intensityValue);
                    startActivity(changeActivity);
                    count = 0;
                }
            }

            @Override
            public void onFinish()
            {
            }
        }.start();
    }
}
