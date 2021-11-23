package com.example.warhammer40k;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class TermsPage extends AppCompatActivity {

    ListView listView;

    private AlertDialog.Builder DB2;
    private AlertDialog dialog2;
    private TextView definitionPopup;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_page);


        listView = (ListView) findViewById(R.id.List);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.definitions, android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);

        configureBackButton();
        listViewTerms();


    }

    public void createNewContactDialog() {
        DB2 = new AlertDialog.Builder(this);
        final View contactPopupView = getLayoutInflater().inflate(R.layout.popupterms, null);
        definitionPopup = (TextView) contactPopupView.findViewById(R.id.definitionPopup);

        listView = (ListView) contactPopupView.findViewById(R.id.List);


        DB2.setView(contactPopupView);
        dialog2 = DB2.create();
        dialog2.show();


    }


public void listViewTerms(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                createNewContactDialog();

                if (position == 0){
                    definitionPopup.setText(getString(R.string.attack));
                }
                else if (position == 1){
                    definitionPopup.setText(getString(R.string.armorsavingthrow));
                }
                else if (position == 2){
                    definitionPopup.setText(getString(R.string.ap));
                }
                else if (position == 3){
                    definitionPopup.setText(getString(R.string.damageroll));
                }
                else if (position == 4){
                    definitionPopup.setText(getString(R.string.feelnopain));
                }
                else if (position == 5){
                    definitionPopup.setText(getString(R.string.hits));
                }
                else if (position == 6){
                    definitionPopup.setText(getString(R.string.invulnerable));
                }
                else if (position == 7){
                    definitionPopup.setText(getString(R.string.randomdamage));
                }
                else if (position == 8){
                    definitionPopup.setText(getString(R.string.skill));
                }
                else if (position == 9){
                    definitionPopup.setText(getString(R.string.strength));
                }
                else if (position == 10){
                    definitionPopup.setText(getString(R.string.toughness));
                }
                else if (position == 11){
                    definitionPopup.setText(getString(R.string.wounds));
                }

            }
        });


    }

    private void configureBackButton(){
        Button backButton = (Button) findViewById(R.id.backButtonTerms);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }



}

