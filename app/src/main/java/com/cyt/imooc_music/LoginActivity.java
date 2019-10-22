package com.cyt.imooc_music;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.cyt.imooc_music.api.RequestCenter;
import com.cyt.imooc_music.model.login.LoginEvent;
import com.cyt.imooc_music.model.user.User;
import com.cyt.imooc_music.view.login.manager.UserManager;
import com.cyt.lib_common_ui.base.BaseActivity;
import com.cyt.lib_network.okhttp.listener.DisposeDataListener;

import org.greenrobot.eventbus.EventBus;

public class LoginActivity extends BaseActivity {

    public static void start(Context context){
        Intent intent = new Intent(context,LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_layout);
        findViewById(R.id.login_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestCenter.login(new DisposeDataListener() {
                    @Override
                    public void onSuccess(Object responseObj) {
                        // 处理正常逻辑
                        User user = (User) responseObj;
                        UserManager.getInstance().saveUser(user);
                        EventBus.getDefault().post(new LoginEvent());
                        finish();
                    }

                    @Override
                    public void onFailure(Object reasonObj) {

                    }
                });
            }
        });
    }
}
