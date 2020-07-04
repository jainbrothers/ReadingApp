package com.example.speechtotextfromgitrepo;



import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.speech.RecognizerIntent;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView txvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("basic logging");
        getSpeechInput();
        txvResult = (TextView) findViewById(R.id.txvResult);
    }

    public void getSpeechInput() {

        System.out.println("inside getSpeechInput");
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        try {
            if (intent.resolveActivity(getPackageManager()) != null) {
                System.out.println("before starting error logging");
                startActivityForResult(intent, 10);
                System.out.println("after starting error logging");
            } else {
                Toast.makeText(this, "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception ex) {
            System.out.println("exception received while starting activity. " + ex.getMessage());
        }

        System.out.println("exiting getSpeechInput");
    }

//    public void getSpeechInput(View view) {
//
//        Intent intent = new Intent(RecognizerIntent.ACTION_VOICE_SEARCH_HANDS_FREE);
//        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
//        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
//
//        try {
//            System.out.println("basic logging");
//            if (intent.resolveActivity(getPackageManager()) != null) {
//                System.out.println("before starting error logging");
//                startActivityForResult(intent, 10);
//            } else {
//                Toast.makeText(this, "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
//            }
//        } catch (Exception ex) {
//            System.out.println("exception received while starting activity. " + ex.getMessage());
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println(" requestCode " + requestCode + " resultCode " + resultCode + " data " + data);
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK && data != null) {
                    System.out.println("Received result from callback. data %s " + data.toString());
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    txvResult.setText(result.get(0));
                    getSpeechInput();
                    System.out.println("call getSpeechInput afer receiving result");
                }
                break;
        }
    }
}