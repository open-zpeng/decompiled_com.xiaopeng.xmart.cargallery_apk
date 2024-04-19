package com.xiaopeng.lib.utils.view.anim;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;
import androidx.constraintlayout.motion.widget.Key;
import com.xiaopeng.lib.utils.view.UIUtils;
/* loaded from: classes.dex */
public class BaseAnimation {
    private static final String ANIM_ALPHA = "alpha";
    protected int mDelay = 100;
    protected int mDuration = 200;

    /* loaded from: classes.dex */
    public enum AnimationType {
        ALPHA,
        ROTATE,
        ROTATE_CENTER,
        HORIZON_LEFT,
        HORIZON_RIGHT,
        HORIZON_CROSS,
        SCALE,
        FLIP_HORIZON,
        FLIP_VERTICAL
    }

    /* renamed from: com.xiaopeng.lib.utils.view.anim.BaseAnimation$1  reason: invalid class name */
    /* loaded from: classes.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$lib$utils$view$anim$BaseAnimation$AnimationType;

        static {
            int[] iArr = new int[AnimationType.values().length];
            $SwitchMap$com$xiaopeng$lib$utils$view$anim$BaseAnimation$AnimationType = iArr;
            try {
                iArr[AnimationType.ROTATE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$xiaopeng$lib$utils$view$anim$BaseAnimation$AnimationType[AnimationType.ALPHA.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$xiaopeng$lib$utils$view$anim$BaseAnimation$AnimationType[AnimationType.HORIZON_LEFT.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$xiaopeng$lib$utils$view$anim$BaseAnimation$AnimationType[AnimationType.HORIZON_RIGHT.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$xiaopeng$lib$utils$view$anim$BaseAnimation$AnimationType[AnimationType.HORIZON_CROSS.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$xiaopeng$lib$utils$view$anim$BaseAnimation$AnimationType[AnimationType.SCALE.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$xiaopeng$lib$utils$view$anim$BaseAnimation$AnimationType[AnimationType.FLIP_HORIZON.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$com$xiaopeng$lib$utils$view$anim$BaseAnimation$AnimationType[AnimationType.FLIP_VERTICAL.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
        }
    }

    public void runAnimation(View view, long delay, AnimationType type) {
        switch (AnonymousClass1.$SwitchMap$com$xiaopeng$lib$utils$view$anim$BaseAnimation$AnimationType[type.ordinal()]) {
            case 1:
                runRotateAnimation(view, delay);
                return;
            case 2:
                runAlphaAnimation(view, delay);
                return;
            case 3:
                runHorizonLeftAnimation(view, delay);
                return;
            case 4:
                runHorizonRightAnimation(view, delay);
                return;
            case 5:
            default:
                return;
            case 6:
                runScaleAnimation(view, delay);
                return;
            case 7:
                runFlipHorizonAnimation(view, delay);
                return;
            case 8:
                runFlipVerticalAnimation(view, delay);
                return;
        }
    }

    private void runHorizonLeftAnimation(View view, long delay) {
        view.setAlpha(0.0f);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "translationX", -UIUtils.getScreenWidth(), 0.0f);
        objectAnimator.setInterpolator(new LinearInterpolator());
        ObjectAnimator objectAnimatorAlpha = ObjectAnimator.ofFloat(view, "alpha", 0.0f, 1.0f);
        AnimatorSet set = new AnimatorSet();
        set.setDuration(this.mDuration);
        set.setStartDelay(delay);
        set.playTogether(objectAnimator, objectAnimatorAlpha);
        set.addListener(new AnimatorListener(view));
        set.start();
    }

    private void runHorizonRightAnimation(View view, long delay) {
        view.setAlpha(0.0f);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "translationX", UIUtils.getScreenWidth(), 0.0f);
        objectAnimator.setInterpolator(new LinearInterpolator());
        ObjectAnimator objectAnimatorAlpha = ObjectAnimator.ofFloat(view, "alpha", 0.0f, 1.0f);
        AnimatorSet set = new AnimatorSet();
        set.setStartDelay(delay);
        set.setDuration(this.mDuration);
        set.playTogether(objectAnimator, objectAnimatorAlpha);
        set.addListener(new AnimatorListener(view));
        set.start();
    }

    private void runAlphaAnimation(View view, long delay) {
        view.setAlpha(0.0f);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "alpha", 0.0f, 1.0f);
        objectAnimator.setStartDelay(delay);
        objectAnimator.setDuration(this.mDuration);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.addListener(new AnimatorListener(view));
        objectAnimator.start();
    }

    private void runRotateAnimation(View view, long delay) {
        view.setAlpha(0.0f);
        AnimatorSet set = new AnimatorSet();
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, Key.ROTATION, 0.0f, 360.0f);
        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(view, "scaleX", 0.0f, 1.0f);
        ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(view, "scaleY", 0.0f, 1.0f);
        ObjectAnimator objectAnimator4 = ObjectAnimator.ofFloat(view, "alpha", 0.0f, 1.0f);
        objectAnimator2.setInterpolator(new AccelerateInterpolator(1.0f));
        objectAnimator3.setInterpolator(new AccelerateInterpolator(1.0f));
        set.setDuration(this.mDuration);
        set.playTogether(objectAnimator, objectAnimator2, objectAnimator3, objectAnimator4);
        set.setStartDelay(delay);
        set.addListener(new AnimatorListener(view));
        set.start();
    }

    private void runScaleAnimation(View view, long delay) {
        view.setAlpha(0.0f);
        AnimatorSet set = new AnimatorSet();
        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(view, "scaleX", 0.0f, 1.0f);
        ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(view, "scaleY", 0.0f, 1.0f);
        ObjectAnimator objectAnimator4 = ObjectAnimator.ofFloat(view, "alpha", 0.0f, 1.0f);
        set.setDuration(this.mDuration);
        set.playTogether(objectAnimator2, objectAnimator3, objectAnimator4);
        set.setStartDelay(delay);
        set.addListener(new AnimatorListener(view));
        set.start();
    }

    private void runFlipVerticalAnimation(View view, long delay) {
        view.setAlpha(0.0f);
        AnimatorSet set = new AnimatorSet();
        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(view, "rotationX", -180.0f, 0.0f);
        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(view, "alpha", 0.0f, 1.0f);
        set.setDuration(this.mDuration);
        set.playTogether(objectAnimator1, objectAnimator2);
        set.setStartDelay(delay);
        set.addListener(new AnimatorListener(view));
        set.start();
    }

    private void runFlipHorizonAnimation(View view, long delay) {
        view.setAlpha(0.0f);
        AnimatorSet set = new AnimatorSet();
        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(view, "rotationY", -180.0f, 0.0f);
        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(view, "alpha", 0.0f, 1.0f);
        set.setDuration(this.mDuration);
        set.playTogether(objectAnimator1, objectAnimator2);
        set.setStartDelay(delay);
        set.addListener(new AnimatorListener(view));
        set.start();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class AnimatorListener implements Animator.AnimatorListener {
        View mmView;

        public AnimatorListener(View view) {
            this.mmView = view;
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationStart(Animator animation) {
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animation) {
            this.mmView.setLayerType(0, null);
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationCancel(Animator animation) {
            this.mmView.setLayerType(0, null);
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationRepeat(Animator animation) {
        }
    }
}
