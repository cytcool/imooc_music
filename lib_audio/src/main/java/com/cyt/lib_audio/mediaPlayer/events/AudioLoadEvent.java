package com.cyt.lib_audio.mediaPlayer.events;

import com.cyt.lib_audio.mediaPlayer.model.AudioBean;

public class AudioLoadEvent {
  public AudioBean mAudioBean;

  public AudioLoadEvent(AudioBean audioBean) {
    this.mAudioBean = audioBean;
  }
}
