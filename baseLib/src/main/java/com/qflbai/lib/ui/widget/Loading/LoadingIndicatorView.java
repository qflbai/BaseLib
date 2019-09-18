package com.qflbai.lib.ui.widget.Loading;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.qflbai.lib.R;
import com.qflbai.lib.utils.DensityUtil;

import java.util.ArrayList;
import java.util.HashMap;

public class LoadingIndicatorView extends View {
    public static final float SCALE = 1.0f;

    public static final int ALPHA = 255;

    float[] scaleFloats = new float[]{SCALE,
            SCALE,
            SCALE,
            SCALE,
            SCALE,
            SCALE,
            SCALE,
            SCALE};

    int[] alphas = new int[]{ALPHA,
            ALPHA,
            ALPHA,
            ALPHA,
            ALPHA,
            ALPHA,
            ALPHA,
            ALPHA};
    private Paint mPaint;
    private int w;
    private int h;
    private int cy;
    private int cx;
    private float mRadius;

    public LoadingIndicatorView(Context context) {
        this(context, null);
    }

    public LoadingIndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public LoadingIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.w = w;
        this.h = h;
        cx = w / 2;
        cy = h / 2;
    }

    private void init(Context context, AttributeSet attrs, int i) {
        mPaint = new Paint();
        int colorAccent = getResources().getColor(R.color.colorAccent);
        mPaint.setColor(colorAccent);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        mRadius = DensityUtil.dip2px(context, 3);
    }


    @Override
    protected void onDraw(Canvas canvas) {

        for (int i = 0; i < 8; i++) {
            canvas.save();
            Point point = circleAt(getWidth(), getHeight(), getWidth() / 3 - mRadius, i * (Math.PI / 4));
            canvas.translate(point.x, point.y);
            //canvas.rotate(45);
            canvas.scale(scaleFloats[i], scaleFloats[i]);
            mPaint.setAlpha(alphas[i]);
            canvas.drawCircle(0, 0, mRadius, mPaint);
            canvas.restore();
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startAnimators();
    }

    @Override
    protected void onDetachedFromWindow() {
        stopAnimators();
        super.onDetachedFromWindow();
    }

    /**
     * 圆O的圆心为(a,b),半径为R,点A与到X轴的为角α.
     * 则点A的坐标为(a+R*cosα,b+R*sinα)
     *
     * @param width
     * @param height
     * @param radius
     * @param angle
     * @return
     */
    private Point circleAt(int width, int height, float radius, double angle) {
        float x = (float) (width / 2 + radius * (Math.cos(angle)));
        float y = (float) (height / 2 + radius * (Math.sin(angle)));
        return new Point(x, y);
    }

    final class Point {
        public float x;
        public float y;

        public Point(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }

    public ArrayList<ValueAnimator> onCreateAnimators() {
        ArrayList<ValueAnimator> animators = new ArrayList<>();
        int[] delays = {0, 120, 240, 360, 480, 600, 720, 780, 840};
        for (int i = 0; i < 8; i++) {
            final int index = i;
            ValueAnimator scaleAnim = ValueAnimator.ofFloat(1, 0.4f, 1);
            scaleAnim.setDuration(1000);
            scaleAnim.setRepeatCount(-1);
            scaleAnim.setStartDelay(delays[i]);
            addUpdateListener(scaleAnim, new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    scaleFloats[index] = (float) animation.getAnimatedValue();
                    postInvalidate();
                }
            });

            ValueAnimator alphaAnim = ValueAnimator.ofInt(255, 77, 255);
            alphaAnim.setDuration(1000);
            alphaAnim.setRepeatCount(-1);
            alphaAnim.setStartDelay(delays[i]);
            addUpdateListener(alphaAnim, new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    alphas[index] = (int) animation.getAnimatedValue();
                    postInvalidate();
                }
            });
            animators.add(scaleAnim);
            animators.add(alphaAnim);
        }
        return animators;
    }

    public void addUpdateListener(ValueAnimator animator, ValueAnimator.AnimatorUpdateListener updateListener) {
        mUpdateListeners.put(animator, updateListener);
    }

    private HashMap<ValueAnimator, ValueAnimator.AnimatorUpdateListener> mUpdateListeners = new HashMap<>();

    private ArrayList<ValueAnimator> mAnimators;

    public void startAnimators() {
        ensureAnimators();

        if (isStarted()) {
            return;
        }

        for (int i = 0; i < mAnimators.size(); i++) {
            ValueAnimator animator = mAnimators.get(i);
            ValueAnimator.AnimatorUpdateListener updateListener = mUpdateListeners.get(animator);
            if (updateListener != null) {
                animator.addUpdateListener(updateListener);
            }

            animator.start();
        }
    }

    private boolean isStarted() {
        for (ValueAnimator animator : mAnimators) {
            return animator.isStarted();
        }
        return false;
    }

    private boolean mHasAnimators;

    private void ensureAnimators() {
        if (!mHasAnimators) {
            mAnimators = onCreateAnimators();
            mHasAnimators = true;
        }
    }

    public void stopAnimators() {
        if (mAnimators != null) {
            for (ValueAnimator animator : mAnimators) {
                if (animator != null && animator.isStarted()) {
                    animator.removeAllUpdateListeners();
                    animator.end();
                }
            }
        }
    }

}
