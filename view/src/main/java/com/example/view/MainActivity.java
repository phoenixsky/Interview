package com.example.view;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.graphics.Interpolator;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.example.view.anim.PointView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        PointView view = findViewById(R.id.view);

        // 直接操作view,类型较少.使用的是硬件加速方案
        /*view.animate()
                .alpha(0.5f)
                .rotation(45)
                .translationX(100)
                .setStartDelay(1000)
                .start();*/


        // 顺序播放或同时播放
       /*AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator bottomFlip = ObjectAnimator.ofFloat(view, "bottomFlip", 45);
        ObjectAnimator flipRotation = ObjectAnimator.ofFloat(view, "flipRotation", 180);
        ObjectAnimator topFlip = ObjectAnimator.ofFloat(view, "topFlip", -45);
        animatorSet.playSequentially(bottomFlip,flipRotation,topFlip);
        // animatorSet.playTogether(bottomFlip,flipRotation,topFlip);
        animatorSet.setStartDelay(1000);
        animatorSet.setDuration(1400);
        animatorSet.start();*/


        // PropertyValuesHolder同时播放
        /*PropertyValuesHolder bottomFlip = PropertyValuesHolder.ofFloat("bottomFlip", 45);
        PropertyValuesHolder flipRotation = PropertyValuesHolder.ofFloat("flipRotation", 180);
        PropertyValuesHolder topFlip = PropertyValuesHolder.ofFloat("topFlip", -45);
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(view, bottomFlip, flipRotation, topFlip);
        objectAnimator.setStartDelay(1000);
        objectAnimator.setDuration(5000);
        objectAnimator.start();*/

        // 关键帧
        /*int scale = 2;
        Keyframe keyframe1 = Keyframe.ofFloat(0, 1);
        Keyframe keyframe2 = Keyframe.ofFloat(.8f, .2f * scale);
        Keyframe keyframe4 = Keyframe.ofFloat(.98f, 1.5f*scale);
        Keyframe keyframe5 = Keyframe.ofFloat(1f, scale);
        PropertyValuesHolder scaleX = PropertyValuesHolder.ofKeyframe("scaleX", keyframe1, keyframe2,  keyframe4,keyframe5);
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(view, scaleX);
        objectAnimator.setStartDelay(0);
        objectAnimator.setDuration(3000);
        objectAnimator.start();*/

        // TypeEvaluator
        /*ObjectAnimator animator = ObjectAnimator.ofObject(view, "point", new TypeEvaluator<PointF>() {
            @Override
            public PointF evaluate(float fraction, PointF startValue, PointF endValue) {

                float x = startValue.x + (endValue.x - startValue.x) * fraction;
                float y = startValue.y + (endValue.y - startValue.y) * fraction;

                return new PointF(x,y);
            }
        }, new PointF(300, 300),new PointF(1000, 1000));
        animator.setStartDelay(1000);
        animator.setDuration(3000);
        animator.start();*/
    }
}
