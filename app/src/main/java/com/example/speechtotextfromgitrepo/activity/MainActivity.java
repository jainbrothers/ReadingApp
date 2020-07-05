package com.example.speechtotextfromgitrepo.activity;


import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.speechtotextfromgitrepo.R;
import com.example.speechtotextfromgitrepo.accessor.RecognitionListenerImpl;
import com.example.speechtotextfromgitrepo.builder.ListenBuilder;
import com.example.speechtotextfromgitrepo.interfaces.ListenerCallback;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private TextView txvResult;
    private ListenBuilder listenBuilder;
    private ListnerCallbackImpl listnerCallback;
    private static String TAG = "MainActivityLoggging";

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
            Log.d(TAG, "onResult " + result);
            txvResult.setText(result);
            listenBuilder.startListening();
        }

        public void onError(final int errorCode) {

            Log.d(TAG, "onError " + errorCode);
            txvResult.setText("Error occurred: " + errorCode);
            if (isRetryableError(errorCode)) {
                Log.d(TAG, "retrying after errorcode " + errorCode);
                listenBuilder.startListening();
            }
        }

        private boolean isRetryableError(final int errorCode) {
            if (errorCode == SpeechRecognizer.ERROR_SPEECH_TIMEOUT || errorCode == SpeechRecognizer.ERROR_NO_MATCH) {
                return true;
            }
            return false;
        }
    }
}