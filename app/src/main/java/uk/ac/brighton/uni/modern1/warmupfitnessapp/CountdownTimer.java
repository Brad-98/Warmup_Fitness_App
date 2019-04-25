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
    private int intensityValue;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown_timer);

        countdownText = (TextView) findViewById(R.id.countdownText);
        changeActivity = new Intent(this, Warmup.class);

        Intent userInputData = getIntent();
        intensityValue = userInputData.getIntExtra("intensityValue", 0);

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
