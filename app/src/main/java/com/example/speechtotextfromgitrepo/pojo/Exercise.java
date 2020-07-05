package com.example.speechtotextfromgitrepo.pojo;

import android.text.SpannableStringBuilder;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;

@Builder
@Data
public class Exercise {
    @NonNull
    private final String exerciseText;
    @NonNull
    private final String[] wordlist;
    @NonNull
    private SpannableStringBuilder processedExercise;
    @NonNull
    private SpannableStringBuilder unprocessedExercise;
    @NonNull
    private Integer currentIndex;
}
