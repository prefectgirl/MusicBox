package com.example.a8_9baidumusicbox.Utils;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.a8_9baidumusicbox.R;

public class AnimationUtils  {
    private Animation scaleAnimation;
    private Animation rotateAnimation;
    private Animation backgroundRotateAnimation;
    private AnimationSet animationSet;
    private Context context;

    private String mUrl;//在线图片的地址

    public AnimationUtils(Context context) {
        this.context = context;
    }

    /**
     * 动画实例之前的初始化
     */
    public void initAnimationDemo() {
        //旋转和缩放动画
        scaleAnimation = android.view.animation.AnimationUtils.loadAnimation(
                context, R.anim.scale_alpha_anim);
        rotateAnimation = android.view.animation.AnimationUtils.loadAnimation(
                context, R.anim.rotate);
        backgroundRotateAnimation = android.view.animation.AnimationUtils.loadAnimation(
                context, R.anim.rotate_background);

        backgroundRotateAnimation.setInterpolator(new LinearInterpolator());
        animationSet = new AnimationSet(true);
        animationSet.setInterpolator(new LinearInterpolator());
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(rotateAnimation);
    }

    /**
     * 开始动画
     * @param image1 上层星星圆，不会动
     * @param image2 下层旋转的专辑图片
     */
    public void AnimationStar(ImageView image1, ImageView image2) {
//        image1.startAnimation(animationSet);
        image2.startAnimation(backgroundRotateAnimation);
    }

    /**
     * 暂停动画
     */
    public void AnimationPause(ImageView image1, ImageView image2) {
        if (image1 != null) {
            image1.clearAnimation();
        }
        if (image2 != null) {
            image2.clearAnimation();
        }
    }

    //使用Glide加载图片
    public void glideLoadingPic(ImageView imageView) {
        mUrl = "https://5b0988e595225.cdn.sohucs.com/images/20190330/582d0bf270cb4d368c8bf6fc053e2cad.jpeg";
        Glide.with(context)
                .load(mUrl)
                .thumbnail(0.2f)//缩略图
                .placeholder(R.drawable.star)//图片加载出来前，显示的图片
                .error(R.drawable.pic3)//图片加载失败后，显示的图片
                .into(imageView);
    }

}
