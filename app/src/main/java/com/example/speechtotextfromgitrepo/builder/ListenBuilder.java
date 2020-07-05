package com.example.speechtotextfromgitrepo.builder;

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;

import lombok.NonNull;

public class ListenBuilder {

    @NonNull
    private final SpeechRecognizer speechRecognizer;
    private static String TAG = "ListenBuilderLoggging";

    public ListenBuilder(@NonNull final SpeechRecognizer speechRecognizer) {

        this.speechRecognizer = speechRecognizer;
    }

    public void startListening() {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,5);
        speechRecognizer.startListening(intent);
        speechRecognizer.stopListening();
    }
}
