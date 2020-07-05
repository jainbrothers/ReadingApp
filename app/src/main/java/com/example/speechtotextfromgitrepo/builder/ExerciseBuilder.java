package com.example.speechtotextfromgitrepo.builder;

import android.text.SpannableStringBuilder;

import com.example.speechtotextfromgitrepo.pojo.Exercise;

public class ExerciseBuilder {

    public Exercise getNextExercise() {

        String text = "This is basic exercise. Though it is not simple, accept everything except this thing.";
        SpannableStringBuilder unprocessedExercise = new SpannableStringBuilder(text);
        String[] wordList = text.split(" ");
        int index = 0;
        String currentWord = wordList[index];
        unprocessedExercise = unprocessedExercise.delete(0, currentWord.length());
        return Exercise.builder()
                .currentIndex(index)
                .exerciseText(text)
                .wordlist(wordList)
                .processedExercise(new SpannableStringBuilder(""))
                .unprocessedExercise(unprocessedExercise)
                .build();
    }
}
