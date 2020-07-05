package com.example.speechtotextfromgitrepo.interfaces;

public interface ListenerCallback {

    public void onResult(String result);
    public void onError(int errorCode);
}
