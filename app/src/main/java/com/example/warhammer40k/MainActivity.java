package com.example.warhammer40k;

import static android.view.View.GONE;
import static java.lang.Boolean.TRUE;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {



    //Declaring variables for Dark/Light Mode
    private RadioGroup radioGroup;
    private TextView select;


    private boolean isCheckedValue;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        configureNextButton();
       // configureCheckButton();

        /*
        CheckBox checkBox = (CheckBox)findViewById(R.id.quickToggle);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isCheckedValue = isChecked;
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("yourBoolName", isCheckedValue);
                startActivity(intent);

            }

        });

*/






        radioGroup = findViewById(R.id.darkLightMode);
        select = findViewById(R.id.textView9);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // on radio button check change
                switch (checkedId) {
                    case R.id.lightMode:
                        // checking the radio button with id.
                        // setting the text to text view as light mode.
                        select.setText("Light Theme");
                        // changing the theme to light mode.
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        break;
                    case R.id.darkMode:
                        // dark radio button is selected
                        // setting dark theme text to our text view.
                        select.setText("Dark Theme");
                        // changing the theme to dark mode.
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        break;
                }
            }
        });



    }


    /*private void configureCheckButton() {
        CheckBox quickToggle = (CheckBox) findViewById(R.id.quickToggle);
        Button buttonHitting = (Button) findViewById(R.id.buttonHitting);
        Button buttonWounding = (Button) findViewById(R.id.buttonWounding);
        Button buttonSaves = (Button) findViewById(R.id.buttonSaves);

        if(quickToggle.isChecked()){
            buttonHitting.setVisibility(View.VISIBLE);
            buttonWounding.setVisibility(View.VISIBLE);
            buttonSaves.setVisibility(View.VISIBLE);
        }
        else{
            buttonHitting.setVisibility(View.GONE);
            buttonWounding.setVisibility(View.GONE);
            buttonSaves.setVisibility(View.GONE);
        }

    }

     */





  /*public void isToggled(View view){

        boolean toggleSwitch = ((CheckBox)view).isChecked();

        if (toggleSwitch){
            buttonHitting.setVisibility(View.VISIBLE);
            buttonWounding.setVisibility(View.VISIBLE);
            buttonSaves.setVisibility(View.VISIBLE);
        }
        else {
            buttonHitting.setVisibility(GONE);
            buttonWounding.setVisibility(GONE);
            buttonSaves.setVisibility(GONE);
        }
    }
*/
    private void configureNextButton(){
        Button nextButton = (Button) findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, CalculatorActivity.class));
                    Log.i("configureNextButton", "9th edition selected...");
            }
        });

        Button termButton = (Button) findViewById(R.id.termButton);
        termButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TermsPage.class));
            }
        });
        }

    }

