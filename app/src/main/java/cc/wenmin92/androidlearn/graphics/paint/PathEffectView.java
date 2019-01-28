package cc.wenmin92.androidlearn.graphics.paint;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.blankj.utilcode.util.SizeUtils;

import timber.log.Timber;

public class PathEffectView extends View {

    public static int HEIGHT = SizeUtils.dp2px(80f);
    public static int WIDTH = SizeUtils.dp2px(300f);
    public static final int STROKE_WIDTH = SizeUtils.dp2px(4f);
    private static Path mPath;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public PathEffectView(Context context) {
        super(context);
        init();
    }

    public PathEffectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint.setColor(0xff78c257);
        mPaint.setStrokeWidth(STROKE_WIDTH);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    private void generatePath() {
        mPath = new Path();
        mPath.moveTo(0, HEIGHT >> 1);

        float segWidth = (float) (WIDTH / 30.0);
        for (int i = 0; i < 30; i++) {
            mPath.lineTo(segWidth * (i + 1), (float) (Math.random() * HEIGHT));
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Timber.d("[onMeasure]");
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Timber.d("onSizeChanged w:%d, h:%d, oldw:%d, oldh:%d", w, h, oldw, oldh);
        WIDTH = w;
        HEIGHT = h;
        if (mPath == null) {
            generatePath();
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Timber.d("[onLayout] left:%d, top:%d, right:%d, bottom:%d", left, top, right, bottom);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Timber.d("[onDraw]");
        canvas.drawColor(0xffeffae7);
        canvas.drawPath(mPath, mPaint);
    }

    public Paint getPaint() {
        return mPaint;
    }
}
