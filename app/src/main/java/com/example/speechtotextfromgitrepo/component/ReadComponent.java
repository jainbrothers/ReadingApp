package com.example.speechtotextfromgitrepo.component;

import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.util.Log;

import com.example.speechtotextfromgitrepo.accessor.RecognitionListenerImpl;
import com.example.speechtotextfromgitrepo.activity.MainActivity;
import com.example.speechtotextfromgitrepo.builder.ExerciseBuilder;
import com.example.speechtotextfromgitrepo.builder.ListenBuilder;
import com.example.speechtotextfromgitrepo.interfaces.ListenerCallback;
import com.example.speechtotextfromgitrepo.interfaces.UpdateTextView;

import lombok.NonNull;

public class ReadComponent {

    private final ListenBuilder listenBuilder;
    private final ExerciseBuilder exerciseBuilder;
    private final UpdateTextView updateTextView;
    private static String TAG = "ReadComponentLogging";

    public ReadComponent(@NonNull final SpeechRecognizer speechRecognizer, @NonNull final UpdateTextView updateTextView) {

        setupSpeechRecognizerCallback(speechRecognizer);
        listenBuilder = new ListenBuilder(speechRecognizer);
        this.updateTextView = updateTextView;
        exerciseBuilder = new ExerciseBuilder();

        //TODO: move it afer showing the paragraph
        listenBuilder.startListening();
    }

    public void getExercise() {
        String exercise = exerciseBuilder.getNextExercise();
        updateTextView.updateText(exercise);
    }

    private void setupSpeechRecognizerCallback(final SpeechRecognizer speechRecognizer) {

        ListnerCallbackImpl listnerCallback = new ListnerCallbackImpl();
        RecognitionListener recognitionListener = new RecognitionListenerImpl(listnerCallback);
        speechRecognizer.setRecognitionListener(recognitionListener);
    }

    public class ListnerCallbackImpl implements ListenerCallback {
        public void onResult(final String result) {
            Log.d(TAG, "onResult " + result);
            listenBuilder.startListening();
        }

        public void onError(final int errorCode) {

            Log.d(TAG, "onError " + errorCode);
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
