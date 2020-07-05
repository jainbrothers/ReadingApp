package com.example.speechtotextfromgitrepo.component;

import android.graphics.Color;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;

import com.example.speechtotextfromgitrepo.accessor.RecognitionListenerImpl;
import com.example.speechtotextfromgitrepo.activity.MainActivity;
import com.example.speechtotextfromgitrepo.builder.ExerciseBuilder;
import com.example.speechtotextfromgitrepo.builder.ListenBuilder;
import com.example.speechtotextfromgitrepo.interfaces.ListenerCallback;
import com.example.speechtotextfromgitrepo.interfaces.UpdateTextView;
import com.example.speechtotextfromgitrepo.pojo.Exercise;

import lombok.NonNull;

public class ReadComponent {

    private final ListenBuilder listenBuilder;
    private final ExerciseBuilder exerciseBuilder;
    private final UpdateTextView updateTextView;
    private Exercise exercise;
    private static String TAG = "ReadComponentLogging";

    public ReadComponent(@NonNull final SpeechRecognizer speechRecognizer, @NonNull final UpdateTextView updateTextView) {

        setupSpeechRecognizerCallback(speechRecognizer);
        listenBuilder = new ListenBuilder(speechRecognizer);
        this.updateTextView = updateTextView;
        exerciseBuilder = new ExerciseBuilder();
    }

    public void getExercise() {
        Exercise exercise = exerciseBuilder.getNextExercise();
        this.exercise = exercise;
        SpannableStringBuilder visibleText = mergeCurrentWordToProcessedText(Color.BLUE);
        Log.d(TAG, "preprocessed text after appending corrent word " + visibleText.toString());
        updateTextView.updateText(visibleText);
        listenBuilder.startListening();
    }

    private void setupSpeechRecognizerCallback(final SpeechRecognizer speechRecognizer) {

        ListnerCallbackImpl listnerCallback = new ListnerCallbackImpl();
        RecognitionListener recognitionListener = new RecognitionListenerImpl(listnerCallback);
        speechRecognizer.setRecognitionListener(recognitionListener);
    }

    private SpannableStringBuilder mergeCurrentWordToProcessedText(int color) {
        if (exercise.getCurrentIndex() >= exercise.getWordlist().length) {
            return exercise.getProcessedExercise();
        }
        SpannableStringBuilder mergedText = new SpannableStringBuilder(exercise.getProcessedExercise());
        SpannableStringBuilder currentWord = new SpannableStringBuilder(exercise.getWordlist()[exercise.getCurrentIndex()]);
        currentWord.setSpan(new ForegroundColorSpan(color), 0, currentWord.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        Log.d(TAG, "preprocessed text " + mergedText.toString());
        Log.d(TAG, "current word " + currentWord.toString());
        mergedText.append(" ").append(currentWord);
        return mergedText;
    }

    private void modifyTextOnEvent(int color) {
        SpannableStringBuilder mergedText = mergeCurrentWordToProcessedText(color);
        exercise.setProcessedExercise(mergedText);
        exercise.setCurrentIndex(exercise.getCurrentIndex() + 1);
        mergedText = mergeCurrentWordToProcessedText(Color.BLUE);
        updateTextView.updateText(mergedText);
    }

    private void startListening() {
        if (exercise.getCurrentIndex() < exercise.getWordlist().length) {
            listenBuilder.startListening();
        }
    }

    public class ListnerCallbackImpl implements ListenerCallback {
        public void onResult(final String result) {
            Log.d(TAG, "onResult " + result + ", current word " + exercise.getWordlist()[exercise.getCurrentIndex()]);
            if (exercise.getWordlist()[exercise.getCurrentIndex()].equalsIgnoreCase(result)) {
                Log.d(TAG, "successful match");
                modifyTextOnEvent(Color.GREEN);
            } else {
                Log.d(TAG, "match failed");
                modifyTextOnEvent(Color.RED);
            }
            startListening();
        }

        public void onError(final int errorCode) {

            Log.d(TAG, "onError " + errorCode);
            if (isRetryableError(errorCode)) {
                Log.d(TAG, "retrying after errorcode " + errorCode);
                startListening();
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
