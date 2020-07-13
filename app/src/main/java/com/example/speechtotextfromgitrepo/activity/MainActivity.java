package com.example.speechtotextfromgitrepo.activity;


import android.os.Bundle;
import android.speech.SpeechRecognizer;
import android.text.SpannableStringBuilder;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import com.example.speechtotextfromgitrepo.R;
import com.example.speechtotextfromgitrepo.component.ReadComponent;
import com.example.speechtotextfromgitrepo.interfaces.UpdateTextView;

import lombok.NonNull;

public class MainActivity extends AppCompatActivity{

    private TextView txvResult;
    private ReadComponent readComponent;
    private static String TAG = "MainActivityLogging";
    private GestureDetectorCompat gDetect;
    private Boolean fullScreenFlag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txvResult = (TextView) findViewById(R.id.txvResult);
        gDetect = new GestureDetectorCompat(this, new GestureListener());

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

    @Override public boolean onTouchEvent(MotionEvent event) {
        gDetect.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    public class GestureListener extends GestureDetector.SimpleOnGestureListener {
        private float flingMin = 100;
        private float velocityMin = 100;

        //user will move forward through messages on fling up or left
        boolean forward = false;
        //user will move backward through messages on fling down or right
        boolean backward = false;

        @Override public boolean onDoubleTap(MotionEvent e) {
            System.out.println("On Double Tap");
            return super.onDoubleTap(e);
        }

        @Override public boolean onDown(MotionEvent e) {
            System.out.println("On Down");
            return super.onDown(e);
        }

        @Override public void onLongPress(MotionEvent e) {
            System.out.println("On Long Press");
            super.onLongPress(e);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            //calculate the change in X position within the fling gesture
            float horizontalDiff = e2.getX() - e1.getX();
            //calculate the change in Y position within the fling gesture
            float verticalDiff = e2.getY() - e1.getY();

            float absHDiff = Math.abs(horizontalDiff);
            float absVDiff = Math.abs(verticalDiff);
            float absVelocityX = Math.abs(velocityX);
            float absVelocityY = Math.abs(velocityY);

            if (absHDiff > absVDiff && absHDiff > flingMin && absVelocityX > velocityMin) {
                //move forward or backward
                if (horizontalDiff > 0) {
                    backward = true;
                } else if (absVDiff > flingMin && absVelocityY > velocityMin) {
                    if (verticalDiff > 0) {
                        backward = true;
                    } else {
                        forward = true;
                    }
                }
            }

            //user is cycling forward through messages
            if (forward) {
                System.out.println("user is cycling forward through messages");
            }
            //user is cycling backwards through messages
            else if (backward) {
                System.out.println("user is cycling backwards through messages");
            }

            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            System.out.println("Scrolling");
            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        @Override public boolean onSingleTapConfirmed(MotionEvent e) {
            System.out.println("On Single TapConfirmed");
            toggleSystemUI();
            return super.onSingleTapConfirmed(e);
        }

        @Override public boolean onSingleTapUp(MotionEvent e) {
            System.out.println("On Single Tap Up");
            return super.onSingleTapUp(e);
        }
    }

    private void toggleSystemUI(){
        if (fullScreenFlag){
            showSystemUI();
            fullScreenFlag = false;
        }
        else{
            hideSystemUI();
            fullScreenFlag = true;
        }

    }
    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }


    // Shows the system bars by removing all the flags
    // except for the ones that make the content appear under the system bars.
    private void showSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }
}