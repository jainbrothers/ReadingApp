package com.example.speechtotextfromgitrepo.builder;

import android.text.SpannableStringBuilder;
import android.util.Log;

import com.example.speechtotextfromgitrepo.pojo.Exercise;
import com.example.speechtotextfromgitrepo.pojo.ExerciseWord;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class ExerciseBuilder {
    private static final String TAG = "ExerciseBuilder";

    public Exercise getNextExercise() {
        String exerciseJsonText = "{\"exerciseText\": \"this is simple exercise.\", \"currentIndex\": 0, \"processedExercise\": \"\", \"unprocessedExercise\": \"this is simple exercise.\", \"exerciseWords\": [{\"text\": \"this\", \"graphemes\": \"DH IH1 S\"}, {\"text\": \"is\", \"graphemes\": \"IH1 Z\"}, {\"text\": \"simple\", \"graphemes\": \"S IH1 M P AH0 L\"}, {\"text\": \"exercise\", \"graphemes\": \"EH1 K S ER0 S AY2 Z\"}]}";
        Exercise exercise = null;
        try {
            GsonBuilder gsonBldr = new GsonBuilder();
            gsonBldr.registerTypeAdapter(Exercise.class, new ExerciseDeserializer());
            exercise = gsonBldr.create().fromJson(exerciseJsonText, Exercise.class);
            Log.d(TAG, "getNextExercise: exercise " + exercise);
        } catch (Exception ex) {
            Log.d(TAG, "getNextExercise: ", ex);
            throw new RuntimeException("Deserilization failed. " + ex.getMessage(), ex);
        }

        return exercise;
    }

    public class ExerciseDeserializer implements JsonDeserializer<Exercise> {

        @Override
        public Exercise deserialize(JsonElement jElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jObject = jElement.getAsJsonObject();
            return Exercise.builder().unprocessedExercise(new SpannableStringBuilder(jObject.get("unprocessedExercise").getAsString()))
                    .processedExercise(new SpannableStringBuilder(jObject.get("processedExercise").getAsString()))
                    .exerciseText(jObject.get("exerciseText").getAsString())
                    .currentIndex(jObject.get("currentIndex").getAsInt())
                    .exerciseWords(new Gson().fromJson(jObject.get("exerciseWords").getAsJsonArray(), ExerciseWord[].class))
                    .build();
        }
    }
}
