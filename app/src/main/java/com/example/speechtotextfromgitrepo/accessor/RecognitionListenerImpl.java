package com.example.speechtotextfromgitrepo.accessor;

import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.util.Log;

import com.example.speechtotextfromgitrepo.interfaces.ListenerCallback;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class RecognitionListenerImpl implements RecognitionListener {

    @NonNull
    private final ListenerCallback listenerCallback;

    private static final String TAG = "RecognitionListenerImpl";

    public void onError(int error)
    {
        Log.d(TAG,  "error " +  error);
        listenerCallback.onError(error);
    }
    public void onResults(Bundle results)
    {
        Log.d(TAG, "onResults " + results);
        ArrayList<String> data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        listenerCallback.onResult(data.get(0));
    }

    public void onReadyForSpeech(Bundle params)
    {
        Log.d(TAG, "onReadyForSpeech");
    }
    public void onBeginningOfSpeech()
    {
        Log.d(TAG, "onBeginningOfSpeech");
    }
    public void onRmsChanged(float rmsdB)
    {
        Log.d(TAG, "onRmsChanged");
    }
    public void onBufferReceived(byte[] buffer)
    {
        Log.d(TAG, "onBufferReceived");
    }
    public void onEndOfSpeech()
    {
        Log.d(TAG, "onEndofSpeech");
    }
    public void onPartialResults(Bundle partialResults)
    {
        Log.d(TAG, "onPartialResults");
    }
    public void onEvent(int eventType, Bundle params)
    {
        Log.d(TAG, "onEvent " + eventType);
    }
}
