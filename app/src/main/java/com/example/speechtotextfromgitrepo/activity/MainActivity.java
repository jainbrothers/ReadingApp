package com.example.speechtotextfromgitrepo.activity;


import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;

import com.example.speechtotextfromgitrepo.R;
import com.example.speechtotextfromgitrepo.accessor.RecognitionListenerImpl;
import com.example.speechtotextfromgitrepo.builder.ListenBuilder;
import com.example.speechtotextfromgitrepo.component.ReadComponent;
import com.example.speechtotextfromgitrepo.interfaces.ListenerCallback;
import com.example.speechtotextfromgitrepo.interfaces.UpdateTextView;

import java.util.HashSet;
import java.util.Set;

import lombok.NonNull;

public class MainActivity extends AppCompatActivity {

    private TextView txvResult;
    private ReadComponent readComponent;
    private static String TAG = "MainActivityLogging";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txvResult = (TextView) findViewById(R.id.txvResult);

        readComponent = new ReadComponent(getSpeechRecognizer(), new UpdateTextViewImpl());
        readComponent.getExercise();
    }

    private SpeechRecognizer getSpeechRecognizer() {

        return SpeechRecognizer.createSpeechRecognizer(this);
    }

    public class UpdateTextViewImpl implements UpdateTextView {

        public void updateText(@NonNull final SpannableStringBuilder text) {
            txvResult.setText(text);
        }
    }
}