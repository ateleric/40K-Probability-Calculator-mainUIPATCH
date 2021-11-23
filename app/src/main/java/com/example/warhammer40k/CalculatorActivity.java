package com.example.warhammer40k;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.util.Arrays;

public class CalculatorActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // Text box variables
    EditText attacksInput;
    EditText skillInput;
    EditText strengthInput;
    EditText toughnessInput;
    EditText armPenInput;
    EditText armSaveInput;
    EditText invulnSaveInput;
    EditText feelNoPainInput;

    // Button variables
    CheckBox toHitPlusOneCheckBox;
    CheckBox toHitMinusOneCheckBox;
    CheckBox toHitRerollOnesCheckBox;
    CheckBox toWoundPlusOneCheckBox;
    CheckBox toWoundMinusOneCheckBox;
    CheckBox toWoundRerollOnesCheckBox;
    CheckBox invulnSaveCheckBox;
    CheckBox feelNoPainCheckBox;
    Button calculateButton;
    Button backButton;

    // Spinner variables
    Spinner damageSpinner;
    Spinner damageModSpinner;


    //Declaring variables for popup window
    private AlertDialog.Builder DB;
    private AlertDialog dialog;
    private TextView based, result;
    private Button saveResult, resetFields;

    // Declaring variables for popup quick tips
    private AlertDialog.Builder DB2;
    private AlertDialog dialogPop;
    private TextView tipsPopup;
    private Button buttonHitting, buttonWounding, buttonSaves;



    Session session = new Session();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primary_calc);

        // Text inputs & checkboxes initialization
        configureInputs();
        configureCheckBoxes();

        // Button Initializations & Listeners
        configureCalculateButton();
        configureBackButton();

        // Spinner initializations
        configureSpinners();

        Log.i("onCreate", "initializations complete...");

        // Tips Button initializations
        configureButtonHitting();
        configureButtonWounding();
        configureButtonSaves();

       /*
        Intent intent = getIntent();
        boolean yourBool = intent.getBooleanExtra("yourBoolName", false);

            Button buttonHitting = (Button) findViewById(R.id.buttonHitting);
            Button buttonWounding = (Button) findViewById(R.id.buttonWounding);
            Button buttonSaves = (Button) findViewById(R.id.buttonSaves);

            if (yourBool) {
                //For Displaying Button
                buttonHitting.setVisibility(View.VISIBLE);
                buttonWounding.setVisibility(View.VISIBLE);
                buttonSaves.setVisibility(View.VISIBLE);
            } else {
                buttonHitting.setVisibility(View.GONE);
                buttonWounding.setVisibility(View.GONE);
                buttonSaves.setVisibility(View.GONE);
            }
*/

        }



    private void configureInputs(){
        // Text box initializations
        attacksInput = (EditText) findViewById(R.id.attacksInput);
        skillInput = (EditText) findViewById(R.id.skillInput);
        strengthInput = (EditText) findViewById(R.id.strengthInput);
        toughnessInput = (EditText) findViewById(R.id.toughnessInput);
        armPenInput = (EditText) findViewById(R.id.armPenInput);
        armSaveInput = (EditText) findViewById(R.id.armSaveInput);
        invulnSaveInput = (EditText) findViewById(R.id.invulnSaveInput);
        feelNoPainInput = (EditText) findViewById(R.id.feelNoPainInput);
        Log.i("configureInputs", "text boxes initialized...");
    }

    private void configureCheckBoxes(){
        toHitPlusOneCheckBox = (CheckBox) findViewById(R.id.toHitPlusOneCheckBox);
        toHitMinusOneCheckBox = (CheckBox) findViewById(R.id.toHitMinusOneCheckBox);
        toHitRerollOnesCheckBox = (CheckBox) findViewById(R.id.toHitRerollOnesCheckBox);
        toWoundPlusOneCheckBox = (CheckBox) findViewById(R.id.toWoundPlusOneCheckBox);
        toWoundMinusOneCheckBox = (CheckBox) findViewById(R.id.toWoundMinusOneCheckBox);
        toWoundRerollOnesCheckBox = (CheckBox) findViewById(R.id.toWoundRerollOnesCheckBox);
        invulnSaveCheckBox = (CheckBox) findViewById(R.id.invulnSaveCheckBox);
        feelNoPainCheckBox = (CheckBox) findViewById(R.id.feelNoPainCheckBox);
        Log.i("configureCheckBoxes", "checkboxes initialized...");
    }

    private void configureSpinners(){
        //Spinner for damage
        damageSpinner = findViewById(R.id.damageSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.damageNum, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        damageSpinner.setAdapter(adapter);
        damageSpinner.setOnItemSelectedListener(this);
        Log.i("configureSpinners", "damageSpinner initialized...");


        //Spinner for damageMod
        damageModSpinner = findViewById(R.id.damageModSpinner);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.damageModNum, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        damageModSpinner.setAdapter(adapter2);
        damageModSpinner.setOnItemSelectedListener(this);
        Log.i("configureSpinners", "damageModSpinner initialized...");

    }

    private void configureBackButton(){
        backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Log.i("configureBackButton", "backButton initialized...");
        }

    private void configureCalculateButton(){
        calculateButton = (Button) findViewById(R.id.calculateButton);
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("configCalculateButton", "\"calculate\" button pressed...");

                //////////////////////////////////////////////////////////////////////////////
                //////////////////////////////MAIN FUNCTIONALITY//////////////////////////////
                //////////////////////////////////////////////////////////////////////////////

                // Declare new session object to hold user input and results
                //Session session = new Session();

                // Read attacks & skill user inputs, and calculate number of hits
                session.attacks = Integer.valueOf(attacksInput.getText().toString());
                session.skill = Integer.valueOf(skillInput.getText().toString());
                session.hits = ToHit(session.attacks, session.skill);

                // Read stength & toughness user inputs, and calculate number of wounds
                session.strength = Integer.valueOf(strengthInput.getText().toString());
                session.toughness = Integer.valueOf(toughnessInput.getText().toString());
                session.wounds = ToWound(session.strength, session.toughness, session.hits);

                // Reads in indexes of damage spinners, and calculates initial damage
                session.damage = DamageInput();

                // Read saves & damage user inputs, and calculate final damage
                session.armPen = Integer.valueOf(armPenInput.getText().toString());
                session.armSave = Integer.valueOf(armSaveInput.getText().toString());
                if(invulnSaveCheckBox.isChecked())
                    session.invulnSave = Integer.valueOf(invulnSaveInput.getText().toString());
                if(feelNoPainCheckBox.isChecked())
                    session.feelNoPain = Integer.valueOf(feelNoPainInput.getText().toString());
                session.finalDamage = FinalDamage(session.damage, session.armPen, session.armSave,
                                        session.invulnSave, (int)session.wounds, session.feelNoPain);

                //////////////////////////////////////////////////////////////////////////////
                //////////////////////////////MAIN FUNCTIONALITY//////////////////////////////
                //////////////////////////////////////////////////////////////////////////////

                //TODO: DISPLAY RESULT DONZZO
                //Toast.makeText(getApplicationContext(), "" + session.finalDamage, Toast.LENGTH_LONG).show();

                createNewContactDialog();

                TextView t = (TextView) findViewById(R.id.result);
                result.setText("Result: " + session.finalDamage);


            }
        });
        Log.i("configCalculateButton", "calculateButton initialized...");
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        return;
    }

    // takes the number of attacks and skill (user input), and calculates the number of hits
    public double ToHit(int attacks, int skill) {
        // make sure skill is in correct range
        if (skill < 2 || skill > 6) {
            Log.e("ToHit","SKILL NOT IN RANGE");
        }

        // check if +1/-1 modifier radios are ticked
        if (toHitPlusOneCheckBox.isChecked() && toHitMinusOneCheckBox.isChecked()) {
            //no modification takes place
        } else if (toHitPlusOneCheckBox.isChecked()) {
            skill--;
        } else if (toHitMinusOneCheckBox.isChecked()) {
            skill++;
        }
        double result = ModifierConvert(skill);

        // check if reroll ones modifier radio is ticked
        if (toHitRerollOnesCheckBox.isChecked()) {
            result += 0.16;
        }

        result *= attacks;
        Log.i("ToHit","ToHit calculates: hits = " + result);
        return result;
    }

    public double ToWound(int strength, int toughness, double hits){
        double result = StrvTgh(strength, toughness);

        if(toWoundPlusOneCheckBox.isChecked() && toWoundMinusOneCheckBox.isChecked()){
            //no modification takes place
        } else if(toWoundPlusOneCheckBox.isChecked()){
            result--;
        } else if(toWoundMinusOneCheckBox.isChecked()){
            result++;
        }
        result = ModifierConvert((int)result);

        // check if reroll ones modifier radio is ticked
        if(toWoundRerollOnesCheckBox.isChecked()) {
            result += 0.16;
        }

        result *= hits;
        Log.i("ToWound","ToWound calculates: wounds = " + result);
        return result;
    }

    // Takes user input on damage spinner and damage modifier spinner, and returns the average
    // damage based on those selections.
    public int DamageInput(){
        int result = -1;

        // Spinner selected array indexes
        int damageCase = damageSpinner.getSelectedItemPosition();
        int damageModCase = damageModSpinner.getSelectedItemPosition();
        Log.i("damage case = ","" + damageCase);
        Log.i("damage mod case = ","" + damageModCase);

        // Map damageSpinner index onto actual damage value
        if(damageCase > -1 && damageCase < 5){
            result = damageCase + 1;
        }else if (damageCase == 5){
            result = 2;
        }else if (damageCase == 6 || damageCase == 7){
            result = 4;
        }else if (damageCase == 8){
            result = 7;
        }else{
            Log.e("DamageInput","damageCase out of range!" + damageCase);
        }
        Log.i("DamageInput","damageCase returns: " + result);

        // Map damageModSpinner index onto actual damage value
        if(damageModCase == -1){
            result += -1;
        }else if(damageModCase > -1 && damageModCase < 9){
            result += damageModCase - 1;
        }else{
            Log.e("DamageInput","damageModCase out of range!" + damageModCase);
        }
        Log.i("DamageInput","damageModCase returns: " + result);

        return result;
    }

    // Calculates final damage statistic
    public double FinalDamage(int damage, int armPen, int armSave, int invulnSave, int wounds, int feelNoPain) {
        double result = -1;

        // damage cannot be 0, map to 1
        if(damage == 0){
            damage = 1;
        }

        armPen *= -1;
        armSave += armPen;

        if(invulnSaveCheckBox.isChecked() && armSave >= invulnSave) {
            invulnSave = Integer.valueOf(invulnSaveInput.getText().toString());
            wounds *= 1 - ModifierConvert(invulnSave);
        }else{
            wounds *= 1- ModifierConvert(armSave);
        }

        damage *= wounds;

        if(feelNoPainCheckBox.isChecked()){
            Log.i("Damage","FNP checked");
            feelNoPain = Integer.valueOf(feelNoPainInput.getText().toString());
            damage *= 1 - ModifierConvert(feelNoPain);
        }
        result = damage;
        Log.i("Damage", "Damage calculates: finalDamage = " + result);

        //TODO: REMOVE TOAST
        Toast.makeText(this,"" + result,Toast.LENGTH_LONG).show();
        return result;
    }


    // compares strength vs. toughness. Returns the wounds value.
    public static int StrvTgh(int s, int t) {
        int result;

        if (s == t) {
            result = 4;
        } else if (s >= t * 2) {
            result =  2;
        } else if (s > t) {
            result = 3;
        } else if (s <= t / 2) {
            result =  6;
        } else if (s < t) {
            result = 5;
        } else {
            Log.e("StrvTgh", "COMPARISON FAILURE: s = " + s + "t = " + t);
            return -1;
        }

        Log.i("StrvTgh", "StrvTgh returns: " + result);
        return result;
    }

    //ModifierConvert
    public double ModifierConvert(int mod) {
        double result;

        switch (mod) {
            case 1:
            case 2:
                result = .83;
                break;
            case 3:
                result = .66;
                break;
            case 4:
                result = .5;
                break;
            case 5:
                result = .33;
                break;
            case 6:
            case 7:
                result = .16;
                break;
            default:
                Log.e("ModifierConvert", "FAILURE: mod = " + mod);
                return -1;
        }
        Log.i("ModifierConvert", "ModifierConvert returns: " + result);
        return result;
    }

    public void createNewContactDialog(){
        DB = new AlertDialog.Builder(this);
        final View contactPopupView = getLayoutInflater().inflate(R.layout.popup, null);
        based = (TextView) contactPopupView.findViewById(R.id.definitionPopup);
        result = (TextView) contactPopupView.findViewById(R.id.result);


        saveResult = (Button) contactPopupView.findViewById(R.id.saveResult);
        resetFields = (Button) contactPopupView.findViewById(R.id.resetFields);

        DB.setView(contactPopupView);
        dialog = DB.create();
        dialog.show();


        saveResult.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //define save button to save into SAVED RESULTS
            }
        });
        resetFields.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //define reset fields button to reset ALL FIELDS in calculator
            }
        });
        //result = "Result "+ result.setText(toString(session.finalDamage));

    }

    public void quickTipsPopup(){
        DB2 = new AlertDialog.Builder(this);
        final View tipsPopupView = getLayoutInflater().inflate(R.layout.tipspop, null);
        tipsPopup = (TextView) tipsPopupView.findViewById(R.id.tipsPopup);


        DB2.setView(tipsPopupView);
        dialogPop = DB2.create();
        dialogPop.show();

    }

    private void configureButtonHitting(){
        buttonHitting = (Button) findViewById(R.id.buttonHitting);
        buttonHitting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start popup
                quickTipsPopup();

                //Editing popup TextView
                String hitting = getString(R.string.hitting);
                TextView t = (TextView) findViewById(R.id.tipsPopup);
                tipsPopup.setText(hitting);
            }
        });}

    private void configureButtonWounding(){
        buttonWounding = (Button) findViewById(R.id.buttonWounding);
        buttonWounding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                quickTipsPopup();

                String wounding = getString(R.string.wounding);
                TextView t = (TextView) findViewById(R.id.tipsPopup);
                tipsPopup.setText(wounding);


            }
        });}
    private void configureButtonSaves(){
        buttonSaves = (Button) findViewById(R.id.buttonSaves);
        buttonSaves.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                quickTipsPopup();

                String saves = getString(R.string.saves);
                TextView t = (TextView) findViewById(R.id.tipsPopup);
                tipsPopup.setText(saves);

            }
        });}



}



