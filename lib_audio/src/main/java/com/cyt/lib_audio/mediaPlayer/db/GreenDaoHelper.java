package com.cyt.lib_audio.mediaPlayer.db;

import android.database.sqlite.SQLiteDatabase;

import com.cyt.lib_audio.mediaPlayer.AudioHelper;
import com.cyt.lib_audio.mediaPlayer.model.AudioBean;
import com.cyt.lib_audio.mediaPlayer.model.Favourite;
import com.cyt.lib_audio.mediaplayer.db.DaoMaster;
import com.cyt.lib_audio.mediaplayer.db.DaoSession;
import com.cyt.lib_audio.mediaplayer.db.FavouriteDao;


/**
 * 操作greenDao数据库帮助类
 */
public class GreenDaoHelper {

  private static final String DB_BAME = "music_db";

  private static DaoMaster.DevOpenHelper mHelper;
  private static SQLiteDatabase mDb;
  //管理数据库
  private static DaoMaster mDaoMaster;
  //管理各种实体Dao,不让业务层拿到session直接去操作数据库，统一由此类提供方法
  private static DaoSession mDaoSession;

  /**
   * 设置greenDao
   */
  public static void initDatabase() {
    mHelper = new DaoMaster.DevOpenHelper(AudioHelper.getContext(), DB_BAME, null);
    mDb = mHelper.getWritableDatabase();
    mDaoMaster = new DaoMaster(mDb);
    mDaoSession = mDaoMaster.newSession();
  }

  /**
   * 添加感兴趣
   */
  public static void addFavourite(AudioBean audioBean) {
    FavouriteDao dao = mDaoSession.getFavouriteDao();
    Favourite favourite = new Favourite();
    favourite.setAudioId(audioBean.id);
    favourite.setAudioBean(audioBean);
    dao.insertOrReplace(favourite);
  }

  /**
   * 移除感兴趣
   */
  public static void removeFavourite(AudioBean audioBean) {
    FavouriteDao dao = mDaoSession.getFavouriteDao();
    Favourite favourite =
        dao.queryBuilder().where(FavouriteDao.Properties.AudioId.eq(audioBean.id)).unique();
    dao.delete(favourite);
  }

  /**
   * 查找感兴趣
   */
  public static Favourite selectFavourite(AudioBean audioBean) {
    FavouriteDao dao = mDaoSession.getFavouriteDao();
    Favourite favourite =
        dao.queryBuilder().where(FavouriteDao.Properties.AudioId.eq(audioBean.id)).unique();
    return favourite;
  }
}
