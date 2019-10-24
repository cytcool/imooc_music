package com.cyt.lib_audio.mediaPlayer.core;

import com.cyt.lib_audio.mediaPlayer.code.AudioPlayer;
import com.cyt.lib_audio.mediaPlayer.code.CustomMediaPlayer;
import com.cyt.lib_audio.mediaPlayer.exception.AudioQueueEmptyException;
import com.cyt.lib_audio.mediaPlayer.model.AudioBean;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * 控制播放逻辑类
 */
public class AudioController {
    /**
     * 播放方式
     */
    public enum PlayMode{
        LOOP,
        RANDOM,
        REPEAT
    }

    public static AudioController getInstance(){
        return SingletonHolder.instance;
    }

    private static class SingletonHolder{
        private static AudioController instance = new AudioController();
    }

    private AudioPlayer mAudioPlayer;// 核心播放器
    private ArrayList<AudioBean> mQueue;// 歌曲队列
    private int mQueueIndex;// 当前播放歌曲索引
    private PlayMode mPlayMode;// 播放模式

    private AudioController(){
        mAudioPlayer = new AudioPlayer();
        mQueue = new ArrayList<>();
        mQueueIndex = 0;
        mPlayMode = PlayMode.LOOP;
    }

    /*
     * 获取播放器当前状态
     */
    private CustomMediaPlayer.Status getStatus() {
        return mAudioPlayer.getStatus();
    }

    public ArrayList<AudioBean> getQueue(){
        return mQueue == null ? new ArrayList<AudioBean>() : mQueue;
    }

    public void setQueue(ArrayList<AudioBean> queue){
        this.setQueue(queue,0);
    }

    public void setQueue(ArrayList<AudioBean> queue,int queueIndex){
        mQueue.addAll(queue);
        mQueueIndex = queueIndex;
    }

    public void addAudio(AudioBean bean){
        this.addAudio(0,bean);
    }

    public void addAudio(int index,AudioBean bean){
        if (mQueue == null){
            throw  new AudioQueueEmptyException("当前播放队列为空");
        }
        int query = queryAudio(bean);
        if (query <= -1){
            // 没有添加过
            addCustomAudio(index,bean);
            setPlayIndex(index);
        }else {
            AudioBean currentBean = getNowPlaying();
            if (!currentBean.id.equals(bean.id)){
                // 已经添加过且不在播放中
                setPlayIndex(query);
            }
        }
    }

    private void addCustomAudio(int index, AudioBean bean) {

    }

    private int queryAudio(AudioBean bean) {
        return 0;
    }

    public PlayMode getPlayMode(){
        return mPlayMode;
    }

    public void setPlayMode(PlayMode playMode){
        mPlayMode = playMode;
    }

    public void setPlayIndex(int index){
        if (mQueue == null){
            throw new AudioQueueEmptyException("当前播放队列为空，请先设置播放队列");
        }
    }


    public int getPlayIndex(){
        return mQueueIndex;
    }

    private AudioBean getNowPlaying() {
        return getPlaying();
    }

    private AudioBean getPlaying(){
        if (mQueue != null && !mQueue.isEmpty() && mQueueIndex >= 0 && mQueueIndex < mQueue.size()){
            return mQueue.get(mQueueIndex);
        }else {
            throw new AudioQueueEmptyException("当前播放队列为空，请先设置播放队列");
        }
    }

    private AudioBean getNextPlaying() {
        return null;
    }

    private AudioBean getPreviousPlaying() {
        return null;
    }


    /**
     * 对外提供的play方法
     */
    public void play(){
        AudioBean bean = getNowPlaying();
        mAudioPlayer.load(bean);
    }

    public void pause(){
        mAudioPlayer.pause();
    }

    public void resume(){
        mAudioPlayer.resume();
    }

    public void release(){
        mAudioPlayer.release();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 播放下一首歌曲
     */
    public void next(){
        AudioBean bean = getNextPlaying();
        mAudioPlayer.load(bean);
    }


    /**
     * 播放上一首歌曲
     */
    public void previous(){
        AudioBean bean = getPreviousPlaying();
        mAudioPlayer.load(bean);
    }

    /**
     * 自动切换播放/暂停
     */
    public void playOrPause(){
        if (isStartState()){
            pause();
        }else if (isPauseState()){
            resume();
        }
    }


    /**
     * 对外提供是否播放中状态
     * @return
     */
    public boolean isStartState(){
        return CustomMediaPlayer.Status.STARTED == getStatus();
    }

    public boolean isPauseState(){
        return CustomMediaPlayer.Status.PAUSED == getStatus();
    }



}
