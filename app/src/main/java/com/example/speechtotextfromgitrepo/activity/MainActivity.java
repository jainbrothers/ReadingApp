package com.example.speechtotextfromgitrepo.activity;



import android.content.Intent;
import android.os.Bundle;

import com.example.speechtotextfromgitrepo.R;
import com.example.speechtotextfromgitrepo.builder.ListenBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView txvResult;
    private SpeechRecognizer sr;
    private ListenBuilder listenBuilder;
    private String TAG = "MainActivityLoggging";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txvResult = (TextView) findViewById(R.id.txvResult);

        sr = SpeechRecognizer.createSpeechRecognizer(this);
        listenBuilder = new ListenBuilder(sr, new ListnerBuilderImpl());
        listenBuilder.startListening();
    }

    private class ListnerBuilderImpl implements ListenBuilder.ListenerCallback {
        public void onResult(final String result) {
            txvResult.setText(result);
            listenBuilder.startListening();
        }

        public void onError(final Integer errorCode) {
            txvResult.setText("Error occurred: " + errorCode .toString());
            listenBuilder.startListening();
        }
    }
}