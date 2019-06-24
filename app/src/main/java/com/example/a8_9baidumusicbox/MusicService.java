package com.example.a8_9baidumusicbox;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.example.a8_9baidumusicbox.Utils.FileUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by 双双 on 2018/6/26.
 */

public class MusicService extends Service {
    private static String TAG = "MusicService";
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private int currIndex = 0;
    private FileUtils fileUtils;
    private List<Map<String, Object>> musiclist;  //用于显示播放列表的数据源
    private String musicUrl;
    private String path1 = "/storage/emulated/0/netease/cloudmusic/Music/";
    private String path2 = "/storage/emulated/0/music/";
    private String name;

    //返回Ibindr 对象
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    //服务开启的时候调用
    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }
    public void init() {
        currIndex = 0;
        fileUtils = new FileUtils();
        musiclist = fileUtils.refeshMusicList(path1);
        Log.i(TAG, "MusicService: " + musiclist.size());
        if (musiclist.size() == 0) {
            Toast.makeText(this, "你的文件夹中没有音乐！", Toast.LENGTH_LONG).show();
        }
    }

    //服务关闭的时候调用
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public String playMusic(int musicPo) {
        if (musiclist.size() > 0) {
            System.out.print("音乐播放了");
//        Toast.makeText(this, "音乐播放了", Toast.LENGTH_LONG).show();
            Map<String, Object> currMusic = musiclist.get(musicPo);
            musicUrl = (String) currMusic.get("musicAbPath");
            mediaPlayer.reset();
            try {
                mediaPlayer.setDataSource(musicUrl);
                mediaPlayer.prepare();
            } catch (IOException e) {
            }
//        mediaPlayer= MediaPlayer.create(this,R.raw.moonglow); //将需要播放的资源与之绑定
            mediaPlayer.start(); //开始播放
            currIndex = musicPo;
            setMusicNameTitle();
            //是否循环播放
            mediaPlayer.setLooping(true);
        }
        return name;
    }

    public void pauseMusic() {
        if (musiclist.size() > 0) {
            System.out.print("音乐停止播放");
//        Toast.makeText(this, "音乐停止播放", Toast.LENGTH_LONG).show();
            mediaPlayer.pause();
        }
    }

    public void replayMusic() {
        if (musiclist.size() > 0) {
            System.out.print("音乐继续播放了");
//        Toast.makeText(this, "音乐继续播放了", Toast.LENGTH_LONG).show();
            mediaPlayer.start();
        }
    }

    public String playNextMusic() {
        if (musiclist.size() > 0) {
            System.out.print("播放下一首音乐");
//        Toast.makeText(this, "播放下一首音乐", Toast.LENGTH_LONG).show();
            if (currIndex < musiclist.size() - 1) {
                currIndex += 1;
                Log.i(TAG, "playNextMusic: curent is " + currIndex);
                setMusicNameTitle();
                playMusic(currIndex);

            } else {
                currIndex = 0;
                playMusic(currIndex);
                Log.i(TAG, "playNextMusic: curent is " + currIndex);
            }
        }
        return name;
    }

    private void setMusicNameTitle() {
        String[] s = musiclist.get(currIndex).get("musicAbPath").toString().split(" ");
        name = s[s.length - 1];
    }

    public String playPreMusic() {
        if (musiclist.size() > 0) {
            System.out.print("播放上一首音乐");
//        Toast.makeText(this, "播放上一首音乐", Toast.LENGTH_LONG).show();
            if (currIndex > 0) {
                currIndex -= 1;
                Log.i(TAG, "playPreMusic: curent is " + currIndex);
                setMusicNameTitle();
                playMusic(currIndex);
            } else {
                currIndex = musiclist.size() - 1;
                playMusic(currIndex);
                Log.i(TAG, "playNextMusic: curent is " + currIndex);
            }
        }
        return name;
    }

    private class MyBinder extends Binder implements Iservice {
        @Override
        public String callPlayMusic() {
            String s = playMusic(currIndex);
            return s;
        }

        @Override
        public void callPauseMusic() {
            pauseMusic();
        }

        @Override
        public void callrePlayMusic() {
            replayMusic();
        }

        @Override
        public String callrePlayNextMusic() {
            String s = playNextMusic();
            return s;
        }

        @Override
        public String callrePlayPreMusic() {
            String s = playPreMusic();
            return s;
        }
    }

}
