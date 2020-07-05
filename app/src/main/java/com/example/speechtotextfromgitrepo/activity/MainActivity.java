package com.example.speechtotextfromgitrepo.activity;


import android.graphics.Color;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.speechtotextfromgitrepo.R;
import com.example.speechtotextfromgitrepo.accessor.RecognitionListenerImpl;
import com.example.speechtotextfromgitrepo.builder.ListenBuilder;
import com.example.speechtotextfromgitrepo.interfaces.ListenerCallback;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    protected void onClick(View v) {
        String colorfulText = "Once, there was a boy who became bored when he watched over the village sheep grazing on the hillside. To entertain himself, he sang out, “Wolf! Wolf! The wolf is chasing the sheep!”\n" +
                "When the villagers heard the cry, they came running up the hill to drive the wolf away. But, when they arrived, they saw no wolf. The boy was amused when seeing their angry faces.\n" +
                "“Don’t scream wolf, boy,” warned the villagers, “when there is no wolf!” They angrily went back down the hill.\n" +
                "Later, the shepherd boy cried out once again, “Wolf! Wolf! The wolf is chasing the sheep!” To his amusement, he looked on as the villagers came running up the hill to scare the wolf away.\n" +
                "As they saw there was no wolf, they said strictly, “Save your frightened cry for when there really is a wolf! Don’t cry ‘wolf’ when there is no wolf!” But the boy grinned at their words while they walked grumbling down the hill once more.\n";
        String[] wordList = colorfulText.split(" ");
        Spannable span = new SpannableString(colorfulText);
        ArrayList<Integer> indexList = new ArrayList();
        int index = colorfulText.indexOf(' ');
        indexList.add(0);
        indexList.add(index);
        while (index >= 0) {
            System.out.println(index);
            index = colorfulText.indexOf(' ', index + 1);
            indexList.add(index);
        }

        int firstIndex = 0;

        for(int j=0; j < indexList.size(); j++) {
            span.setSpan(new ForegroundColorSpan(getColorByFlag(j > 10)), firstIndex, indexList.get(j), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            firstIndex = indexList.get(j);

            ((TextView) findViewById(R.id.txvResult)).setText(span.subSequence(0, indexList.get(j)));
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException ex) {
//                Thread.currentThread().interrupt();
//            }
        }
    }

    private int getColorByFlag(boolean colorFlag){
//        Random rnd = new Random();
        if (colorFlag){
            return Color.argb(255, 255, 0, 0); //Red
        }
        return Color.argb(255, 0, 255, 0); //Green

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
//            txvResult.setText(result);
//            listenBuilder.startListening();
        }

        public void onError(final int errorCode) {

            Log.d(TAG, "onError " + errorCode);
//            txvResult.setText("Error occurred: " + errorCode);
            if (isRetryableError(errorCode)) {
                Log.d(TAG, "retrying after errorcode " + errorCode);
//                listenBuilder.startListening();
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