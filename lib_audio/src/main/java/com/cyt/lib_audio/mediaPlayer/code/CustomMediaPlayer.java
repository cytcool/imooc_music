package com.cyt.lib_audio.mediaPlayer.code;

import android.media.MediaPlayer;

import java.io.IOException;

public class CustomMediaPlayer extends MediaPlayer implements MediaPlayer.OnCompletionListener{

    public enum Status{
        IDEL,INITALIZED,STARTED,PAUSED,STOPPED,COMPLETE
    }

    private Status mState;
    private OnCompletionListener mCompletionListener;

    public CustomMediaPlayer(){
        super();
        mState = Status.IDEL;
        super.setOnCompletionListener(this);
    }

    @Override
    public void reset() {
        super.reset();
        mState = Status.IDEL;
    }

    @Override
    public void setDataSource(String path) throws IOException, IllegalArgumentException, IllegalStateException, SecurityException {
        super.setDataSource(path);
        mState = Status.INITALIZED;
    }

    @Override
    public void start() throws IllegalStateException {
        super.start();
        mState = Status.STARTED;
    }

    @Override
    public void pause() throws IllegalStateException {
        super.pause();
        mState = Status.PAUSED;
    }

    @Override
    public void stop() throws IllegalStateException {
        super.stop();
        mState = Status.STOPPED;
    }



    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        mState = Status.COMPLETE;
    }

    public Status getState(){
        return mState;
    }

    public boolean isComplete(){
        return mState == Status.COMPLETE;
    }

    public void setCompleteListener(OnCompletionListener listener){
        mCompletionListener = listener;
    }
}
