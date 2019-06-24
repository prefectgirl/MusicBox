package com.example.a8_9baidumusicbox;

/**
 * Created by 双双 on 2018/6/26.
 */

public interface Iservice {
    //把我们想要暴露的方法放在接口中
    public String callPlayMusic();
    public void callPauseMusic();
    public void callrePlayMusic();
    public String callrePlayNextMusic();
    public String callrePlayPreMusic();

}
