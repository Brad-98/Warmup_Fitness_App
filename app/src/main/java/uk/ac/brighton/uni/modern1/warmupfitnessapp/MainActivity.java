package uk.ac.brighton.uni.modern1.warmupfitnessapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity
{
    private Button startButton;

    private TextView durationValue;
    private TextView intensityText;
    private SeekBar durationBar;
    private SeekBar intensityBar;

    private ToggleButton exerciseButton;
    private ToggleButton stretchingButton;

    private int duration = 0;
    private int intensity = 1;

    private boolean exerciseButtonSelected = false;
    private boolean stretchingButtonSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        exerciseButton = (ToggleButton) findViewById(R.id.exerciseButton);

        exerciseButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked)
                {
                    exerciseButtonSelected = true;
                }
                else
                {
                    exerciseButtonSelected = false;
                }
            }
        });

        stretchingButton = (ToggleButton) findViewById(R.id.stretchingButton);

        stretchingButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked)
                {
                    stretchingButtonSelected = true;
                }
                else
                {
                    stretchingButtonSelected = false;
                }
            }
        });

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
                if(exerciseButtonSelected == true || stretchingButtonSelected == true)
                {
                    goToCountdownTimerActivity();
                }
            }
        });
    }

    public void goToCountdownTimerActivity()
    {
        Intent changeActivity = new Intent(this, CountdownTimer.class);

        //Sending the user input values to the countdown class
        changeActivity.putExtra("intensityValue", intensity);
        changeActivity.putExtra("durationValue", duration);
        changeActivity.putExtra("exerciseButtonSelected", exerciseButtonSelected);
        changeActivity.putExtra("stretchingButtonSelected", stretchingButtonSelected);

        startActivity(changeActivity);
    }
}
