package com.example.speechtotextfromgitrepo.pojo;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Builder
@Data
public class ExerciseWord {
    @NonNull
    String text;
    String graphemes;
}
