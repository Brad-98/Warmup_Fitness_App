package uk.ac.brighton.uni.modern1.warmupfitnessapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class MainActivity extends AppCompatActivity
{
    private Button startButton;
    private Button databaseStartButton;

    private TextView durationValue;
    private TextView intensityText;
    private SeekBar durationBar;
    private SeekBar intensityBar;

    private ToggleButton exerciseButton;
    private ToggleButton stretchingButton;

    private int duration = 1;
    private int intensity = 1;

    private boolean exerciseButtonSelected = false;
    private boolean stretchingButtonSelected = false;

    //Firebase Database
    private FirebaseDatabase myDatabase;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_main);

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("User Input");


        exerciseButton = (ToggleButton) findViewById(R.id.exerciseButton);
        exerciseButton.setBackgroundColor(Color.LTGRAY);

        exerciseButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked)
                {
                    exerciseButtonSelected = true;
                    exerciseButton.setBackgroundColor(Color.parseColor("#77abff"));
                }
                else
                {
                    exerciseButtonSelected = false;
                    exerciseButton.setBackgroundColor(Color.LTGRAY);
                }
            }
        });

        stretchingButton = (ToggleButton) findViewById(R.id.stretchingButton);
        stretchingButton.setBackgroundColor(Color.LTGRAY);
        stretchingButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked)
                {
                    stretchingButtonSelected = true;
                    stretchingButton.setBackgroundColor(Color.parseColor("#77abff"));
                }
                else
                {
                    stretchingButtonSelected = false;
                    stretchingButton.setBackgroundColor(Color.LTGRAY);
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
                switch (progress)
                {
                    case 0:
                        duration = 1;
                        durationValue.setText("" + duration + " Minute");
                        break;

                        default:
                            duration = progress + 1;
                            durationValue.setText("" + (progress + 1) + " Minutes");
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
                    //Firebase Database
                    myRef.child("Exercise Button Pressed").setValue(Boolean.toString(exerciseButtonSelected));
                    myRef.child("Stretching Button Pressed").setValue(Boolean.toString(stretchingButtonSelected));
                    myRef.child("Duration").setValue(Integer.toString(duration));
                    myRef.child("Intensity").setValue(Integer.toString(intensity));

                    goToCountdownTimerActivity();
                }
            }
        });

        databaseStartButton = (Button) findViewById(R.id.databaseStartButton);
        databaseStartButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                myRef.addValueEventListener(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        //Storing the firebase database data in a map
                        Map<String, String> userInput = (Map) dataSnapshot.getValue();

                        //Retrieving the data in string form then converting it
                        String exerciseButtonString = userInput.get("Exercise Button Pressed");
                        exerciseButtonSelected = Boolean.parseBoolean(exerciseButtonString);

                        String stretchingButtonString = userInput.get("Stretching Button Pressed");
                        stretchingButtonSelected = Boolean.parseBoolean(stretchingButtonString);

                        String durationString = userInput.get("Duration");
                        duration = Integer.parseInt(durationString);

                        String intensityString = userInput.get("Intensity");
                        intensity = Integer.parseInt(intensityString);
                        goToCountdownTimerActivity();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
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