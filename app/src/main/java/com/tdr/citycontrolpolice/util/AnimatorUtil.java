package com.tdr.citycontrolpolice.util;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.OvershootInterpolator;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/5/5 14:50
 * 修改备注：
 */
public class AnimatorUtil {
    private ValueAnimator animator;
    private static final long ANIMATOR_TIME = 400;
    private static AnimatorUtil mAnimatorUtil;

    private AnimatorUtil() {

    }

    public static AnimatorUtil getInstance() {
        if (mAnimatorUtil == null) {
            synchronized (AnimatorUtil.class) {
                if (mAnimatorUtil == null) {
                    mAnimatorUtil = new AnimatorUtil();
                }
            }
        }
        return mAnimatorUtil;
    }

    public void doExplandAnimator(final View view, int height, boolean isExpland) {
        if (isExpland) {
            closeAnimator(view, height);
        } else {
            explandAnimator(view, height);
        }
    }

    public void explandAnimator(final View view, int height) {
        view.setVisibility(View.VISIBLE);
        animator = ValueAnimator.ofFloat(0, height);
        animator.setInterpolator(new OvershootInterpolator());
        animator.setDuration(ANIMATOR_TIME).start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                view.setTranslationY((Float) animation.getAnimatedValue());
            }
        });
    }

    public void closeAnimator(final View view, int height) {
        animator = ValueAnimator.ofFloat(height, 0);
        animator.setDuration(ANIMATOR_TIME).start();
        animator.setInterpolator(new AnticipateInterpolator());

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                view.setTranslationY((Float) animation.getAnimatedValue());
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    public void setRotationAnimator(View view, boolean isExpland, float angle) {
        if (isExpland) {
            ObjectAnimator.ofFloat(view, "rotation", angle, 0.0F)
                    .setDuration(ANIMATOR_TIME)
                    .start();
        } else {
            ObjectAnimator.ofFloat(view, "rotation", 0.0F, angle)
                    .setDuration(ANIMATOR_TIME)
                    .start();
        }
    }

}
