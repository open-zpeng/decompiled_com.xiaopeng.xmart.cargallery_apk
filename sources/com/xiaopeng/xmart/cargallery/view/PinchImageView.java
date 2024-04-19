package com.xiaopeng.xmart.cargallery.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.widget.AppCompatImageView;
import com.xiaopeng.xmart.cargallery.view.DetailActivity;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
/* loaded from: classes17.dex */
public class PinchImageView extends AppCompatImageView {
    public static final float FLING_DAMPING_FACTOR = 0.9f;
    private static final float MAX_SCALE = 4.0f;
    public static final int PINCH_MODE_FREE = 0;
    public static final int PINCH_MODE_SCALE = 2;
    public static final int PINCH_MODE_SCROLL = 1;
    public static final int SCALE_ANIMATOR_DURATION = 200;
    private DetailActivity.OnTopViewChangeListener listener;
    private int mDispatchOuterMatrixChangedLock;
    private FlingAnimator mFlingAnimator;
    private GestureDetector mGestureDetector;
    private PointF mLastMovePoint;
    private RectF mMask;
    private MaskAnimator mMaskAnimator;
    private View.OnClickListener mOnClickListener;
    private View.OnLongClickListener mOnLongClickListener;
    private Matrix mOuterMatrix;
    private List<OuterMatrixChangedListener> mOuterMatrixChangedListeners;
    private List<OuterMatrixChangedListener> mOuterMatrixChangedListenersCopy;
    private int mPinchMode;
    private ScaleAnimator mScaleAnimator;
    private float mScaleBase;
    private PointF mScaleCenter;

    /* loaded from: classes17.dex */
    public interface OuterMatrixChangedListener {
        void onOuterMatrixChanged(PinchImageView pinchImageView);
    }

    @Override // android.view.View
    public void setOnClickListener(View.OnClickListener l) {
        this.mOnClickListener = l;
    }

    @Override // android.view.View
    public void setOnLongClickListener(View.OnLongClickListener l) {
        this.mOnLongClickListener = l;
    }

    public Matrix getOuterMatrix(Matrix matrix) {
        if (matrix == null) {
            return new Matrix(this.mOuterMatrix);
        }
        matrix.set(this.mOuterMatrix);
        return matrix;
    }

    public Matrix getInnerMatrix(Matrix matrix) {
        if (matrix == null) {
            matrix = new Matrix();
        } else {
            matrix.reset();
        }
        if (isReady()) {
            RectF tempSrc = MathUtils.rectFTake(0.0f, 0.0f, getDrawable().getIntrinsicWidth(), getDrawable().getIntrinsicHeight());
            RectF tempDst = MathUtils.rectFTake(0.0f, 0.0f, getWidth(), getHeight());
            matrix.setRectToRect(tempSrc, tempDst, Matrix.ScaleToFit.CENTER);
            MathUtils.rectFGiven(tempDst);
            MathUtils.rectFGiven(tempSrc);
        }
        return matrix;
    }

    public Matrix getCurrentImageMatrix(Matrix matrix) {
        Matrix matrix2 = getInnerMatrix(matrix);
        matrix2.postConcat(this.mOuterMatrix);
        return matrix2;
    }

    public RectF getImageBound(RectF rectF) {
        if (rectF == null) {
            rectF = new RectF();
        } else {
            rectF.setEmpty();
        }
        if (!isReady()) {
            return rectF;
        }
        Matrix matrix = MathUtils.matrixTake();
        getCurrentImageMatrix(matrix);
        rectF.set(0.0f, 0.0f, getDrawable().getIntrinsicWidth(), getDrawable().getIntrinsicHeight());
        matrix.mapRect(rectF);
        MathUtils.matrixGiven(matrix);
        return rectF;
    }

    public RectF getMask() {
        if (this.mMask != null) {
            return new RectF(this.mMask);
        }
        return null;
    }

    public int getPinchMode() {
        return this.mPinchMode;
    }

    @Override // android.view.View
    public boolean canScrollHorizontally(int direction) {
        if (this.mPinchMode == 2) {
            return true;
        }
        RectF bound = getImageBound(null);
        if (bound == null || bound.isEmpty()) {
            return false;
        }
        return direction > 0 ? bound.right > ((float) getWidth()) : bound.left < 0.0f;
    }

    @Override // android.view.View
    public boolean canScrollVertically(int direction) {
        if (this.mPinchMode == 2) {
            return true;
        }
        RectF bound = getImageBound(null);
        if (bound == null || bound.isEmpty()) {
            return false;
        }
        return direction > 0 ? bound.bottom > ((float) getHeight()) : bound.top < 0.0f;
    }

    public void outerMatrixTo(Matrix endMatrix, long duration) {
        if (endMatrix == null) {
            return;
        }
        this.mPinchMode = 0;
        cancelAllAnimator();
        if (duration <= 0) {
            this.mOuterMatrix.set(endMatrix);
            dispatchOuterMatrixChanged();
            invalidate();
            return;
        }
        ScaleAnimator scaleAnimator = new ScaleAnimator(this.mOuterMatrix, endMatrix, duration);
        this.mScaleAnimator = scaleAnimator;
        scaleAnimator.start();
    }

    public void zoomMaskTo(RectF mask, long duration) {
        if (mask == null) {
            return;
        }
        MaskAnimator maskAnimator = this.mMaskAnimator;
        if (maskAnimator != null) {
            maskAnimator.cancel();
            this.mMaskAnimator = null;
        }
        if (duration <= 0 || this.mMask == null) {
            if (this.mMask == null) {
                this.mMask = new RectF();
            }
            this.mMask.set(mask);
            invalidate();
            return;
        }
        MaskAnimator maskAnimator2 = new MaskAnimator(this.mMask, mask, duration);
        this.mMaskAnimator = maskAnimator2;
        maskAnimator2.start();
    }

    public void reset() {
        this.mOuterMatrix.reset();
        dispatchOuterMatrixChanged();
        this.mMask = null;
        this.mPinchMode = 0;
        this.mLastMovePoint.set(0.0f, 0.0f);
        this.mScaleCenter.set(0.0f, 0.0f);
        this.mScaleBase = 0.0f;
        MaskAnimator maskAnimator = this.mMaskAnimator;
        if (maskAnimator != null) {
            maskAnimator.cancel();
            this.mMaskAnimator = null;
        }
        cancelAllAnimator();
        invalidate();
    }

    public void setOnTopViewChangeListener(DetailActivity.OnTopViewChangeListener listener) {
        this.listener = listener;
    }

    public void addOuterMatrixChangedListener(OuterMatrixChangedListener listener) {
        if (listener == null) {
            return;
        }
        if (this.mDispatchOuterMatrixChangedLock == 0) {
            if (this.mOuterMatrixChangedListeners == null) {
                this.mOuterMatrixChangedListeners = new ArrayList();
            }
            this.mOuterMatrixChangedListeners.add(listener);
            return;
        }
        if (this.mOuterMatrixChangedListenersCopy == null) {
            if (this.mOuterMatrixChangedListeners != null) {
                this.mOuterMatrixChangedListenersCopy = new ArrayList(this.mOuterMatrixChangedListeners);
            } else {
                this.mOuterMatrixChangedListenersCopy = new ArrayList();
            }
        }
        this.mOuterMatrixChangedListenersCopy.add(listener);
    }

    public void removeOuterMatrixChangedListener(OuterMatrixChangedListener listener) {
        if (listener == null) {
            return;
        }
        if (this.mDispatchOuterMatrixChangedLock == 0) {
            List<OuterMatrixChangedListener> list = this.mOuterMatrixChangedListeners;
            if (list != null) {
                list.remove(listener);
                return;
            }
            return;
        }
        if (this.mOuterMatrixChangedListenersCopy == null && this.mOuterMatrixChangedListeners != null) {
            this.mOuterMatrixChangedListenersCopy = new ArrayList(this.mOuterMatrixChangedListeners);
        }
        List<OuterMatrixChangedListener> list2 = this.mOuterMatrixChangedListenersCopy;
        if (list2 != null) {
            list2.remove(listener);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchOuterMatrixChanged() {
        List<OuterMatrixChangedListener> list;
        List<OuterMatrixChangedListener> list2 = this.mOuterMatrixChangedListeners;
        if (list2 == null) {
            return;
        }
        this.mDispatchOuterMatrixChangedLock++;
        for (OuterMatrixChangedListener listener : list2) {
            listener.onOuterMatrixChanged(this);
        }
        int i = this.mDispatchOuterMatrixChangedLock - 1;
        this.mDispatchOuterMatrixChangedLock = i;
        if (i == 0 && (list = this.mOuterMatrixChangedListenersCopy) != null) {
            this.mOuterMatrixChangedListeners = list;
            this.mOuterMatrixChangedListenersCopy = null;
        }
    }

    protected float getMaxScale() {
        return MAX_SCALE;
    }

    protected float calculateNextScale(float innerScale, float outerScale) {
        float currentScale = innerScale * outerScale;
        if (currentScale < MAX_SCALE) {
            return MAX_SCALE;
        }
        return innerScale;
    }

    public PinchImageView(Context context) {
        super(context);
        this.mOuterMatrix = new Matrix();
        this.mPinchMode = 0;
        this.mLastMovePoint = new PointF();
        this.mScaleCenter = new PointF();
        this.mScaleBase = 0.0f;
        this.mGestureDetector = new GestureDetector(getContext(), new GestureListener(this));
        initView();
    }

    public PinchImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mOuterMatrix = new Matrix();
        this.mPinchMode = 0;
        this.mLastMovePoint = new PointF();
        this.mScaleCenter = new PointF();
        this.mScaleBase = 0.0f;
        this.mGestureDetector = new GestureDetector(getContext(), new GestureListener(this));
        initView();
    }

    public PinchImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mOuterMatrix = new Matrix();
        this.mPinchMode = 0;
        this.mLastMovePoint = new PointF();
        this.mScaleCenter = new PointF();
        this.mScaleBase = 0.0f;
        this.mGestureDetector = new GestureDetector(getContext(), new GestureListener(this));
        initView();
    }

    private void initView() {
        super.setScaleType(ImageView.ScaleType.MATRIX);
    }

    @Override // android.widget.ImageView
    public void setScaleType(ImageView.ScaleType scaleType) {
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onDraw(Canvas canvas) {
        if (isReady()) {
            Matrix matrix = MathUtils.matrixTake();
            setImageMatrix(getCurrentImageMatrix(matrix));
            MathUtils.matrixGiven(matrix);
        }
        if (this.mMask != null) {
            canvas.save();
            canvas.clipRect(this.mMask);
            super.onDraw(canvas);
            canvas.restore();
            return;
        }
        super.onDraw(canvas);
    }

    private boolean isReady() {
        return getDrawable() != null && getDrawable().getIntrinsicWidth() > 0 && getDrawable().getIntrinsicHeight() > 0 && getWidth() > 0 && getHeight() > 0;
    }

    /* loaded from: classes17.dex */
    private class MaskAnimator extends ValueAnimator implements ValueAnimator.AnimatorUpdateListener {
        private float[] mStart = new float[4];
        private float[] mEnd = new float[4];
        private float[] mResult = new float[4];

        public MaskAnimator(RectF start, RectF end, long duration) {
            setFloatValues(0.0f, 1.0f);
            setDuration(duration);
            addUpdateListener(this);
            this.mStart[0] = start.left;
            this.mStart[1] = start.top;
            this.mStart[2] = start.right;
            this.mStart[3] = start.bottom;
            this.mEnd[0] = end.left;
            this.mEnd[1] = end.top;
            this.mEnd[2] = end.right;
            this.mEnd[3] = end.bottom;
        }

        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public void onAnimationUpdate(ValueAnimator animation) {
            float value = ((Float) animation.getAnimatedValue()).floatValue();
            for (int i = 0; i < 4; i++) {
                float[] fArr = this.mResult;
                float[] fArr2 = this.mStart;
                fArr[i] = fArr2[i] + ((this.mEnd[i] - fArr2[i]) * value);
            }
            if (PinchImageView.this.mMask == null) {
                PinchImageView.this.mMask = new RectF();
            }
            RectF rectF = PinchImageView.this.mMask;
            float[] fArr3 = this.mResult;
            rectF.set(fArr3[0], fArr3[1], fArr3[2], fArr3[3]);
            PinchImageView.this.invalidate();
        }
    }

    /* loaded from: classes17.dex */
    private static class GestureListener extends GestureDetector.SimpleOnGestureListener {
        private WeakReference<PinchImageView> mRef;

        public GestureListener(PinchImageView imageView) {
            this.mRef = new WeakReference<>(imageView);
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            PinchImageView imageView = this.mRef.get();
            if (imageView != null && imageView.mPinchMode == 0) {
                if (imageView.mScaleAnimator == null || !imageView.mScaleAnimator.isRunning()) {
                    imageView.fling(velocityX, velocityY);
                    return true;
                }
                return true;
            }
            return true;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public void onLongPress(MotionEvent e) {
            PinchImageView imageView = this.mRef.get();
            if (imageView != null && imageView.mOnLongClickListener != null) {
                imageView.mOnLongClickListener.onLongClick(imageView);
            }
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
        public boolean onDoubleTap(MotionEvent e) {
            PinchImageView imageView = this.mRef.get();
            if (imageView != null && imageView.mPinchMode == 1 && (imageView.mScaleAnimator == null || !imageView.mScaleAnimator.isRunning())) {
                imageView.doubleTap(e.getX(), e.getY());
            }
            return true;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
        public boolean onSingleTapConfirmed(MotionEvent e) {
            PinchImageView imageView = this.mRef.get();
            if (imageView != null && imageView.listener != null) {
                imageView.listener.onTopChange(false, 0);
                return true;
            }
            return true;
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        ScaleAnimator scaleAnimator;
        super.onTouchEvent(event);
        int action = event.getAction() & 255;
        if (action == 1 || action == 3) {
            if (this.mPinchMode == 2) {
                scaleEnd();
            }
            this.mPinchMode = 0;
        } else if (action == 6) {
            if (this.mPinchMode == 2 && event.getPointerCount() > 2) {
                if ((event.getAction() >> 8) == 0) {
                    saveScaleContext(event.getX(1), event.getY(1), event.getX(2), event.getY(2));
                } else if ((event.getAction() >> 8) == 1) {
                    saveScaleContext(event.getX(0), event.getY(0), event.getX(2), event.getY(2));
                }
            }
            scaleEnd();
        } else if (action == 0) {
            ScaleAnimator scaleAnimator2 = this.mScaleAnimator;
            if (scaleAnimator2 == null || !scaleAnimator2.isRunning()) {
                cancelAllAnimator();
                this.mPinchMode = 1;
                this.mLastMovePoint.set(event.getX(), event.getY());
            }
        } else if (action == 5) {
            cancelAllAnimator();
            this.mPinchMode = 2;
            saveScaleContext(event.getX(0), event.getY(0), event.getX(1), event.getY(1));
        } else if (action == 2 && ((scaleAnimator = this.mScaleAnimator) == null || !scaleAnimator.isRunning())) {
            int i = this.mPinchMode;
            if (i == 1) {
                scrollBy(event.getX() - this.mLastMovePoint.x, event.getY() - this.mLastMovePoint.y);
                this.mLastMovePoint.set(event.getX(), event.getY());
            } else if (i == 2 && event.getPointerCount() > 1) {
                float distance = MathUtils.getDistance(event.getX(0), event.getY(0), event.getX(1), event.getY(1));
                float[] lineCenter = MathUtils.getCenterPoint(event.getX(0), event.getY(0), event.getX(1), event.getY(1));
                this.mLastMovePoint.set(lineCenter[0], lineCenter[1]);
                scale(this.mScaleCenter, this.mScaleBase, distance, this.mLastMovePoint);
            }
        }
        this.mGestureDetector.onTouchEvent(event);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean scrollBy(float xDiff, float yDiff) {
        if (isReady()) {
            RectF bound = MathUtils.rectFTake();
            getImageBound(bound);
            float displayWidth = getWidth();
            float displayHeight = getHeight();
            if (bound.right - bound.left < displayWidth) {
                xDiff = 0.0f;
            } else if (bound.left + xDiff > 0.0f) {
                if (bound.left < 0.0f) {
                    xDiff = -bound.left;
                } else {
                    xDiff = 0.0f;
                }
            } else if (bound.right + xDiff < displayWidth) {
                if (bound.right > displayWidth) {
                    xDiff = displayWidth - bound.right;
                } else {
                    xDiff = 0.0f;
                }
            }
            if (bound.bottom - bound.top < displayHeight) {
                yDiff = 0.0f;
            } else if (bound.top + yDiff > 0.0f) {
                if (bound.top < 0.0f) {
                    yDiff = -bound.top;
                } else {
                    yDiff = 0.0f;
                }
            } else if (bound.bottom + yDiff < displayHeight) {
                if (bound.bottom > displayHeight) {
                    yDiff = displayHeight - bound.bottom;
                } else {
                    yDiff = 0.0f;
                }
            }
            MathUtils.rectFGiven(bound);
            this.mOuterMatrix.postTranslate(xDiff, yDiff);
            dispatchOuterMatrixChanged();
            invalidate();
            return (xDiff == 0.0f && yDiff == 0.0f) ? false : true;
        }
        return false;
    }

    private void saveScaleContext(float x1, float y1, float x2, float y2) {
        this.mScaleBase = MathUtils.getMatrixScale(this.mOuterMatrix)[0] / MathUtils.getDistance(x1, y1, x2, y2);
        float[] center = MathUtils.inverseMatrixPoint(MathUtils.getCenterPoint(x1, y1, x2, y2), this.mOuterMatrix);
        this.mScaleCenter.set(center[0], center[1]);
    }

    private void scale(PointF scaleCenter, float scaleBase, float distance, PointF lineCenter) {
        if (!isReady()) {
            return;
        }
        float scale = scaleBase * distance;
        Matrix matrix = MathUtils.matrixTake();
        matrix.postScale(scale, scale, scaleCenter.x, scaleCenter.y);
        matrix.postTranslate(lineCenter.x - scaleCenter.x, lineCenter.y - scaleCenter.y);
        this.mOuterMatrix.set(matrix);
        MathUtils.matrixGiven(matrix);
        dispatchOuterMatrixChanged();
        invalidate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doubleTap(float x, float y) {
        float postY;
        if (!isReady()) {
            return;
        }
        Matrix innerMatrix = MathUtils.matrixTake();
        getInnerMatrix(innerMatrix);
        float innerScale = MathUtils.getMatrixScale(innerMatrix)[0];
        float outerScale = MathUtils.getMatrixScale(this.mOuterMatrix)[0];
        float currentScale = innerScale * outerScale;
        float displayWidth = getWidth();
        float displayHeight = getHeight();
        float maxScale = getMaxScale();
        float nextScale = calculateNextScale(innerScale, outerScale);
        if (nextScale > maxScale) {
            nextScale = maxScale;
        }
        if (nextScale < innerScale) {
            nextScale = innerScale;
        }
        Matrix animEnd = MathUtils.matrixTake(this.mOuterMatrix);
        animEnd.postScale(nextScale / currentScale, nextScale / currentScale, x, y);
        animEnd.postTranslate((displayWidth / 2.0f) - x, (displayHeight / 2.0f) - y);
        Matrix testMatrix = MathUtils.matrixTake(innerMatrix);
        testMatrix.postConcat(animEnd);
        RectF testBound = MathUtils.rectFTake(0.0f, 0.0f, getDrawable().getIntrinsicWidth(), getDrawable().getIntrinsicHeight());
        testMatrix.mapRect(testBound);
        float postX = 0.0f;
        if (testBound.right - testBound.left < displayWidth) {
            postX = (displayWidth / 2.0f) - ((testBound.right + testBound.left) / 2.0f);
        } else if (testBound.left > 0.0f) {
            postX = -testBound.left;
        } else if (testBound.right < displayWidth) {
            postX = displayWidth - testBound.right;
        }
        if (testBound.bottom - testBound.top < displayHeight) {
            float postY2 = (displayHeight / 2.0f) - ((testBound.bottom + testBound.top) / 2.0f);
            postY = postY2;
        } else if (testBound.top > 0.0f) {
            postY = -testBound.top;
        } else {
            float postY3 = testBound.bottom;
            if (postY3 >= displayHeight) {
                postY = 0.0f;
            } else {
                float postY4 = displayHeight - testBound.bottom;
                postY = postY4;
            }
        }
        animEnd.postTranslate(postX, postY);
        cancelAllAnimator();
        ScaleAnimator scaleAnimator = new ScaleAnimator(this, this.mOuterMatrix, animEnd);
        this.mScaleAnimator = scaleAnimator;
        scaleAnimator.start();
        MathUtils.rectFGiven(testBound);
        MathUtils.matrixGiven(testMatrix);
        MathUtils.matrixGiven(animEnd);
        MathUtils.matrixGiven(innerMatrix);
    }

    private void scaleEnd() {
        boolean change;
        if (!isReady()) {
            return;
        }
        boolean change2 = false;
        Matrix currentMatrix = MathUtils.matrixTake();
        getCurrentImageMatrix(currentMatrix);
        float currentScale = MathUtils.getMatrixScale(currentMatrix)[0];
        float outerScale = MathUtils.getMatrixScale(this.mOuterMatrix)[0];
        float displayWidth = getWidth();
        float displayHeight = getHeight();
        float maxScale = getMaxScale();
        float scalePost = 1.0f;
        float postX = 0.0f;
        float postY = 0.0f;
        if (currentScale > maxScale) {
            scalePost = maxScale / currentScale;
        }
        if (outerScale * scalePost < 1.0f) {
            scalePost = 1.0f / outerScale;
        }
        if (scalePost != 1.0f) {
            change2 = true;
        }
        Matrix testMatrix = MathUtils.matrixTake(currentMatrix);
        testMatrix.postScale(scalePost, scalePost, this.mLastMovePoint.x, this.mLastMovePoint.y);
        RectF testBound = MathUtils.rectFTake(0.0f, 0.0f, getDrawable().getIntrinsicWidth(), getDrawable().getIntrinsicHeight());
        testMatrix.mapRect(testBound);
        if (testBound.right - testBound.left < displayWidth) {
            change = change2;
            postX = (displayWidth / 2.0f) - ((testBound.right + testBound.left) / 2.0f);
        } else {
            change = change2;
            if (testBound.left > 0.0f) {
                postX = -testBound.left;
            } else if (testBound.right < displayWidth) {
                postX = displayWidth - testBound.right;
            }
        }
        if (testBound.bottom - testBound.top < displayHeight) {
            postY = (displayHeight / 2.0f) - ((testBound.bottom + testBound.top) / 2.0f);
        } else if (testBound.top > 0.0f) {
            postY = -testBound.top;
        } else if (testBound.bottom < displayHeight) {
            postY = displayHeight - testBound.bottom;
        }
        if ((postX == 0.0f && postY == 0.0f) ? change : true) {
            Matrix animEnd = MathUtils.matrixTake(this.mOuterMatrix);
            animEnd.postScale(scalePost, scalePost, this.mLastMovePoint.x, this.mLastMovePoint.y);
            animEnd.postTranslate(postX, postY);
            cancelAllAnimator();
            ScaleAnimator scaleAnimator = new ScaleAnimator(this, this.mOuterMatrix, animEnd);
            this.mScaleAnimator = scaleAnimator;
            scaleAnimator.start();
            MathUtils.matrixGiven(animEnd);
        }
        MathUtils.rectFGiven(testBound);
        MathUtils.matrixGiven(testMatrix);
        MathUtils.matrixGiven(currentMatrix);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void fling(float vx, float vy) {
        if (!isReady()) {
            return;
        }
        cancelAllAnimator();
        FlingAnimator flingAnimator = new FlingAnimator(vx / 60.0f, vy / 60.0f);
        this.mFlingAnimator = flingAnimator;
        flingAnimator.start();
    }

    private void cancelAllAnimator() {
        ScaleAnimator scaleAnimator = this.mScaleAnimator;
        if (scaleAnimator != null) {
            scaleAnimator.cancel();
            this.mScaleAnimator = null;
        }
        FlingAnimator flingAnimator = this.mFlingAnimator;
        if (flingAnimator != null) {
            flingAnimator.cancel();
            this.mFlingAnimator = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes17.dex */
    public class FlingAnimator extends ValueAnimator implements ValueAnimator.AnimatorUpdateListener {
        private float[] mVector;

        public FlingAnimator(float vectorX, float vectorY) {
            setFloatValues(0.0f, 1.0f);
            setDuration(1000000L);
            addUpdateListener(this);
            this.mVector = new float[]{vectorX, vectorY};
        }

        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public void onAnimationUpdate(ValueAnimator animation) {
            PinchImageView pinchImageView = PinchImageView.this;
            float[] fArr = this.mVector;
            boolean result = pinchImageView.scrollBy(fArr[0], fArr[1]);
            float[] fArr2 = this.mVector;
            fArr2[0] = fArr2[0] * 0.9f;
            fArr2[1] = fArr2[1] * 0.9f;
            if (!result || MathUtils.getDistance(0.0f, 0.0f, fArr2[0], fArr2[1]) < 1.0f) {
                animation.cancel();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes17.dex */
    public class ScaleAnimator extends ValueAnimator implements ValueAnimator.AnimatorUpdateListener {
        private float[] mEnd;
        private float[] mResult;
        private float[] mStart;

        public ScaleAnimator(final PinchImageView this$0, Matrix start, Matrix end) {
            this(start, end, 200L);
        }

        public ScaleAnimator(Matrix start, Matrix end, long duration) {
            this.mStart = new float[9];
            this.mEnd = new float[9];
            this.mResult = new float[9];
            setFloatValues(0.0f, 1.0f);
            setDuration(duration);
            addUpdateListener(this);
            start.getValues(this.mStart);
            end.getValues(this.mEnd);
        }

        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public void onAnimationUpdate(ValueAnimator animation) {
            float value = ((Float) animation.getAnimatedValue()).floatValue();
            for (int i = 0; i < 9; i++) {
                float[] fArr = this.mResult;
                float[] fArr2 = this.mStart;
                fArr[i] = fArr2[i] + ((this.mEnd[i] - fArr2[i]) * value);
            }
            PinchImageView.this.mOuterMatrix.setValues(this.mResult);
            PinchImageView.this.dispatchOuterMatrixChanged();
            PinchImageView.this.invalidate();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes17.dex */
    public static abstract class ObjectsPool<T> {
        private Queue<T> mQueue = new LinkedList();
        private int mSize;

        protected abstract T newInstance();

        protected abstract T resetInstance(T obj);

        public ObjectsPool(int size) {
            this.mSize = size;
        }

        public T take() {
            if (this.mQueue.size() == 0) {
                return newInstance();
            }
            return resetInstance(this.mQueue.poll());
        }

        public void given(T obj) {
            if (obj != null && this.mQueue.size() < this.mSize) {
                this.mQueue.offer(obj);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes17.dex */
    public static class MatrixPool extends ObjectsPool<Matrix> {
        public MatrixPool(int size) {
            super(size);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.xiaopeng.xmart.cargallery.view.PinchImageView.ObjectsPool
        public Matrix newInstance() {
            return new Matrix();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.xiaopeng.xmart.cargallery.view.PinchImageView.ObjectsPool
        public Matrix resetInstance(Matrix obj) {
            obj.reset();
            return obj;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes17.dex */
    public static class RectFPool extends ObjectsPool<RectF> {
        public RectFPool(int size) {
            super(size);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.xiaopeng.xmart.cargallery.view.PinchImageView.ObjectsPool
        public RectF newInstance() {
            return new RectF();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.xiaopeng.xmart.cargallery.view.PinchImageView.ObjectsPool
        public RectF resetInstance(RectF obj) {
            obj.setEmpty();
            return obj;
        }
    }

    /* loaded from: classes17.dex */
    public static class MathUtils {
        private static MatrixPool mMatrixPool = new MatrixPool(16);
        private static RectFPool mRectFPool = new RectFPool(16);

        public static Matrix matrixTake() {
            return mMatrixPool.take();
        }

        public static Matrix matrixTake(Matrix matrix) {
            Matrix result = mMatrixPool.take();
            if (matrix != null) {
                result.set(matrix);
            }
            return result;
        }

        public static void matrixGiven(Matrix matrix) {
            mMatrixPool.given(matrix);
        }

        public static RectF rectFTake() {
            return mRectFPool.take();
        }

        public static RectF rectFTake(float left, float top, float right, float bottom) {
            RectF result = mRectFPool.take();
            result.set(left, top, right, bottom);
            return result;
        }

        public static RectF rectFTake(RectF rectF) {
            RectF result = mRectFPool.take();
            if (rectF != null) {
                result.set(rectF);
            }
            return result;
        }

        public static void rectFGiven(RectF rectF) {
            mRectFPool.given(rectF);
        }

        public static float getDistance(float x1, float y1, float x2, float y2) {
            float x = x1 - x2;
            float y = y1 - y2;
            return (float) Math.sqrt((x * x) + (y * y));
        }

        public static float[] getCenterPoint(float x1, float y1, float x2, float y2) {
            return new float[]{(x1 + x2) / 2.0f, (y1 + y2) / 2.0f};
        }

        public static float[] getMatrixScale(Matrix matrix) {
            if (matrix != null) {
                float[] value = new float[9];
                matrix.getValues(value);
                return new float[]{value[0], value[4]};
            }
            return new float[2];
        }

        public static float[] inverseMatrixPoint(float[] point, Matrix matrix) {
            if (point != null && matrix != null) {
                float[] dst = new float[2];
                Matrix inverse = matrixTake();
                matrix.invert(inverse);
                inverse.mapPoints(dst, point);
                matrixGiven(inverse);
                return dst;
            }
            return new float[2];
        }

        public static void calculateRectTranslateMatrix(RectF from, RectF to, Matrix result) {
            if (from == null || to == null || result == null || from.width() == 0.0f || from.height() == 0.0f) {
                return;
            }
            result.reset();
            result.postTranslate(-from.left, -from.top);
            result.postScale(to.width() / from.width(), to.height() / from.height());
            result.postTranslate(to.left, to.top);
        }

        public static void calculateScaledRectInContainer(RectF container, float srcWidth, float srcHeight, ImageView.ScaleType scaleType, RectF result) {
            float scale;
            float scale2;
            if (container == null || result == null || srcWidth == 0.0f || srcHeight == 0.0f) {
                return;
            }
            if (scaleType == null) {
                scaleType = ImageView.ScaleType.FIT_CENTER;
            }
            result.setEmpty();
            if (ImageView.ScaleType.FIT_XY.equals(scaleType)) {
                result.set(container);
            } else if (ImageView.ScaleType.CENTER.equals(scaleType)) {
                Matrix matrix = matrixTake();
                RectF rect = rectFTake(0.0f, 0.0f, srcWidth, srcHeight);
                matrix.setTranslate((container.width() - srcWidth) * 0.5f, (container.height() - srcHeight) * 0.5f);
                matrix.mapRect(result, rect);
                rectFGiven(rect);
                matrixGiven(matrix);
                result.left += container.left;
                result.right += container.left;
                result.top += container.top;
                result.bottom += container.top;
            } else if (ImageView.ScaleType.CENTER_CROP.equals(scaleType)) {
                Matrix matrix2 = matrixTake();
                RectF rect2 = rectFTake(0.0f, 0.0f, srcWidth, srcHeight);
                float dx = 0.0f;
                float dy = 0.0f;
                if (container.height() * srcWidth > container.width() * srcHeight) {
                    scale2 = container.height() / srcHeight;
                    dx = (container.width() - (srcWidth * scale2)) * 0.5f;
                } else {
                    float scale3 = container.width();
                    scale2 = scale3 / srcWidth;
                    dy = (container.height() - (srcHeight * scale2)) * 0.5f;
                }
                matrix2.setScale(scale2, scale2);
                matrix2.postTranslate(dx, dy);
                matrix2.mapRect(result, rect2);
                rectFGiven(rect2);
                matrixGiven(matrix2);
                result.left += container.left;
                result.right += container.left;
                result.top += container.top;
                result.bottom += container.top;
            } else if (ImageView.ScaleType.CENTER_INSIDE.equals(scaleType)) {
                Matrix matrix3 = matrixTake();
                RectF rect3 = rectFTake(0.0f, 0.0f, srcWidth, srcHeight);
                if (srcWidth <= container.width() && srcHeight <= container.height()) {
                    scale = 1.0f;
                } else {
                    float scale4 = container.width();
                    scale = Math.min(scale4 / srcWidth, container.height() / srcHeight);
                }
                float dx2 = (container.width() - (srcWidth * scale)) * 0.5f;
                float dy2 = (container.height() - (srcHeight * scale)) * 0.5f;
                matrix3.setScale(scale, scale);
                matrix3.postTranslate(dx2, dy2);
                matrix3.mapRect(result, rect3);
                rectFGiven(rect3);
                matrixGiven(matrix3);
                result.left += container.left;
                result.right += container.left;
                result.top += container.top;
                result.bottom += container.top;
            } else if (ImageView.ScaleType.FIT_CENTER.equals(scaleType)) {
                Matrix matrix4 = matrixTake();
                RectF rect4 = rectFTake(0.0f, 0.0f, srcWidth, srcHeight);
                RectF tempSrc = rectFTake(0.0f, 0.0f, srcWidth, srcHeight);
                RectF tempDst = rectFTake(0.0f, 0.0f, container.width(), container.height());
                matrix4.setRectToRect(tempSrc, tempDst, Matrix.ScaleToFit.CENTER);
                matrix4.mapRect(result, rect4);
                rectFGiven(tempDst);
                rectFGiven(tempSrc);
                rectFGiven(rect4);
                matrixGiven(matrix4);
                result.left += container.left;
                result.right += container.left;
                result.top += container.top;
                result.bottom += container.top;
            } else if (ImageView.ScaleType.FIT_START.equals(scaleType)) {
                Matrix matrix5 = matrixTake();
                RectF rect5 = rectFTake(0.0f, 0.0f, srcWidth, srcHeight);
                RectF tempSrc2 = rectFTake(0.0f, 0.0f, srcWidth, srcHeight);
                RectF tempDst2 = rectFTake(0.0f, 0.0f, container.width(), container.height());
                matrix5.setRectToRect(tempSrc2, tempDst2, Matrix.ScaleToFit.START);
                matrix5.mapRect(result, rect5);
                rectFGiven(tempDst2);
                rectFGiven(tempSrc2);
                rectFGiven(rect5);
                matrixGiven(matrix5);
                result.left += container.left;
                result.right += container.left;
                result.top += container.top;
                result.bottom += container.top;
            } else if (ImageView.ScaleType.FIT_END.equals(scaleType)) {
                Matrix matrix6 = matrixTake();
                RectF rect6 = rectFTake(0.0f, 0.0f, srcWidth, srcHeight);
                RectF tempSrc3 = rectFTake(0.0f, 0.0f, srcWidth, srcHeight);
                RectF tempDst3 = rectFTake(0.0f, 0.0f, container.width(), container.height());
                matrix6.setRectToRect(tempSrc3, tempDst3, Matrix.ScaleToFit.END);
                matrix6.mapRect(result, rect6);
                rectFGiven(tempDst3);
                rectFGiven(tempSrc3);
                rectFGiven(rect6);
                matrixGiven(matrix6);
                result.left += container.left;
                result.right += container.left;
                result.top += container.top;
                result.bottom += container.top;
            } else {
                result.set(container);
            }
        }
    }
}
