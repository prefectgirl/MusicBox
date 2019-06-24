package com.example.a8_9baidumusicbox.Utils;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileUtils {
    private static String TAG = "FileUtils";
    //用于显示播放列表的数据源
    private List<Map<String, Object>> musicList;



    /**
     * 找当前目录下的音乐文件
     * @param musicUrl 要找寻音乐文件的目录路径
     * @return
     */
    public List<Map<String, Object>> refeshMusicList(String musicUrl) {
        File musicDir = new File(musicUrl);
        if (musicDir != null && musicDir.isDirectory()) {
            File[] musicFile = musicDir.listFiles(new MusicFilter());
            if (musicFile != null) {
                musicList = new ArrayList<Map<String, Object>>();
                for (int i = 0; i < musicFile.length; i++) {
                    File currMusic = musicFile[i];
                    //获取当前目录的名称和绝对路径
                    String abPath = currMusic.getAbsolutePath();
                    String musicName = currMusic.getName();
                    Map<String, Object> currMusicMap = new HashMap<String, Object>();
                    currMusicMap.put("musicName", musicName);
                    currMusicMap.put("musicAbPath", abPath);
                    musicList.add(currMusicMap);
                }
            } else {
                musicList = new ArrayList<Map<String, Object>>();
            }
        } else {
            musicList = new ArrayList<Map<String, Object>>();
        }
        Log.i(TAG, "refeshMusicList: " + musicList);
        return musicList;
    }

    /**
     * 获取mp3资源名称
     */
    private class MusicFilter implements FileFilter {
        @Override
        public boolean accept(File pathname) {
            // TODO Auto-generated method stub
            if (pathname.isFile() == true) {
                String fileName = pathname.getName();
                String lowerName = fileName.toLowerCase();
                return lowerName.endsWith(".mp3");
            } else {
                return false;
            }
        }

    }

    /////////************************** 没有用*********************************************
    public static List<Song> getMusicData(Context context) {
        List<Song> list = new ArrayList<Song>();
//        //拼音首字母
//        for (int i = 'A'; i <= 'Z'; i++) {
//            Song songnum = new Song("" + (char) i, 0);
//            list.add(songnum);
//        }
        // 媒体库查询语句（写一个工具类MusicUtils）
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                null, null,null,
                MediaStore.Audio.AudioColumns.IS_MUSIC);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Song song = new Song();
                //歌曲
                song.name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME));
                //歌手
                song.singer = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
                //路径
                song.path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                //时长
                song.duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
                song.size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));
//                if (name.size > 1000 * 800) {
                    // 注释部分是切割标题，分离出歌曲名和歌手 （本地媒体库读取的歌曲信息不规范）
//                    if (name.name.contains("-")) {
//                        String[] str = name.name.split("-");
//                        name.singer = str[0].trim();
//                        name.name = str[1].trim();
//                    }
                    //设置拼音首字母
//                    name.setPys(ChineseToLetter.GetFirstPinyin(name.name));

//                if (song.name.contains("james")){
//                    Log.i(TAG, "getMusicData: " + song.path);
//                }
                    list.add(song);
//                }
            }
            // 释放资源
            cursor.close();
        }
        Log.i(TAG, "getMusicData: " + list.toString());
        return list;
    }
}
