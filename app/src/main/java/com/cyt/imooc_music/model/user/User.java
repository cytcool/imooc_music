package com.cyt.imooc_music.model.user;

import com.cyt.imooc_music.model.BaseModel;
import com.cyt.imooc_music.model.user.UserContent;

/**
 * 用户数据协议
 */
public class User extends BaseModel {
  public int ecode;
  public String emsg;
  public UserContent data;
}
