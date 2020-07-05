package com.example.speechtotextfromgitrepo.activity;


import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.speechtotextfromgitrepo.R;
import com.example.speechtotextfromgitrepo.accessor.RecognitionListenerImpl;
import com.example.speechtotextfromgitrepo.builder.ListenBuilder;
import com.example.speechtotextfromgitrepo.interfaces.ListenerCallback;

public class MainActivity extends AppCompatActivity {

    private TextView txvResult;
    private ListenBuilder listenBuilder;
    private ListnerCallbackImpl listnerCallback;
    private String TAG = "MainActivityLoggging";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txvResult = (TextView) findViewById(R.id.txvResult);

        listenBuilder = new ListenBuilder(getSpeechRecognizer());
        listenBuilder.startListening();
    }

    private SpeechRecognizer getSpeechRecognizer() {

        SpeechRecognizer speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        ListnerCallbackImpl listnerCallback = new ListnerCallbackImpl();
        RecognitionListener recognitionListener = new RecognitionListenerImpl(listnerCallback);
        speechRecognizer.setRecognitionListener(recognitionListener);
        return speechRecognizer;
    }

    public class ListnerCallbackImpl implements ListenerCallback {
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