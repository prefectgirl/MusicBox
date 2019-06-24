package com.example.a8_9baidumusicbox;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.a8_9baidumusicbox.Utils.AnimationUtils;
import com.example.a8_9baidumusicbox.Utils.FileUtils;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {
    private final String TAG = "MainActivity";
    private Iservice iservice;
    private MyConn conn;
    private ImageView star;
    private ImageView backpic;

    private AnimationUtils animationUtils;
    public List<Map<String, Object>> list;
    private TextView musicName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        star = findViewById(R.id.star);
        backpic = findViewById(R.id.backpic);
        musicName = findViewById(R.id.musicName);

        animationUtils = new AnimationUtils(this);
        initDataSetting();
        animationUtils.glideLoadingPic(backpic);

    }

    // 当Activity销毁的时候调用
    @Override
    protected void onDestroy() {
        // 在Activity销毁的时候 取消绑定服务
        unbindService(conn);
        super.onDestroy();
    }

    /**
     * 按返回键退出时不销毁activity
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initDataSetting() {
        Toast.makeText(this, "欢迎使用音乐播放器", Toast.LENGTH_LONG).show();
        //[0]先调用startservice 方法开启服务 保证服务在后台长期运行
        Intent intent = new Intent(this, MusicService.class);
        startService(intent);

        //1 调用binderService 目的是获取我们定义的Ibinder对象
        conn = new MyConn();
        // 连接MusicService 服务 获取我们定义的中间人对象
        bindService(intent, conn, BIND_AUTO_CREATE);
    }

    public void clickPlay(View view) {
        animationUtils.initAnimationDemo();
        String s = iservice.callPlayMusic();
        musicName.setText(s);
        animationUtils.AnimationStar(star, backpic);
    }

    public void clickPause(View view) {
        iservice.callPauseMusic();
        animationUtils.AnimationPause(star, backpic);
    }

    public void clickReplay(View view) {
           iservice.callrePlayMusic();
           animationUtils.AnimationStar(star, backpic);

    }

    public void clickNextMusic(View view) {
        animationUtils.initAnimationDemo();
        String s = iservice.callrePlayNextMusic();
        musicName.setText(s);
        animationUtils.AnimationStar(star, backpic);
    }

    public void clickPreMusic(View view) {
        animationUtils.initAnimationDemo();
        String s = iservice.callrePlayPreMusic();
        musicName.setText(s);
        animationUtils.AnimationStar(star, backpic);
    }

    private class MyConn implements ServiceConnection {
        // 当连接成功时候调用
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // 获取我们定义的中间人Ibinder 对象
            iservice = (Iservice) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    }

}

