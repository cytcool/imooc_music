package com.cyt.lib_audio.mediaPlayer;

import android.content.Context;

import com.cyt.lib_audio.mediaPlayer.core.AudioController;
import com.cyt.lib_audio.mediaPlayer.db.GreenDaoHelper;


/**
 * Created by qndroid on 19/5/20.
 *
 * @function 唯一与外界通信的帮助类
 */
public final class AudioHelper {

  //SDK全局Context, 供子模块用
  private static Context mContext;

  public static void init(Context context) {
    mContext = context;
    //初始化本地数据库
    GreenDaoHelper.initDatabase();
  }

//  //外部启动MusicService方法
//  public static void startMusicService(ArrayList<AudioBean> audios) {
//    MusicService.startMusicService(audios);
//  }
//
//  public static void addAudio(Activity activity, AudioBean bean) {
//    AudioController.getInstance().addAudio(bean);
//    MusicPlayerActivity.start(activity);
//  }

  public static void pauseAudio() {
    AudioController.getInstance().pause();
  }

  public static void resumeAudio() {
    AudioController.getInstance().resume();
  }

  public static Context getContext() {
    return mContext;
  }
}
