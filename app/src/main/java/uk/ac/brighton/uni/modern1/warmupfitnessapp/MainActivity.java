package uk.ac.brighton.uni.modern1.warmupfitnessapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    private Button startButton;

    private TextView durationValue;
    private TextView intensityText;
    private SeekBar durationBar;
    private SeekBar intensityBar;

    public int duration;
    private int intensity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        durationValue = (TextView) findViewById(R.id.durationValue);
        durationBar = (SeekBar) findViewById(R.id.durationBar);

        durationBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                durationValue.setText("" + progress + " Minutes");
                duration = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
            }
        });

        intensityText = (TextView) findViewById(R.id.intensityText);
        intensityBar = (SeekBar) findViewById(R.id.intensityBar);

        intensityBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                switch (progress)
                {
                    case 0:
                        intensityText.setText("Low");
                        intensity = 1;
                        break;
                    case 1:
                        intensityText.setText("Medium");
                        intensity = 2;
                        break;
                    case 2:
                        intensityText.setText("High");
                        intensity = 3;
                        break;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
            }
        });

        startButton = (Button) findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                goToCountdownTimerActivity();
            }
        });
    }

    public void goToCountdownTimerActivity()
    {
        Intent sendDataToWarmupClass = new Intent(this, Warmup.class);
        sendDataToWarmupClass.putExtra("intensityValue", intensity);

        Intent changeActivity = new Intent(this, CountdownTimer.class);
        changeActivity.putExtra("intensityValue", intensity);
        startActivity(changeActivity);
    }
}
