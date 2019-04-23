package cc.wenmin92.androidlearn.graphics.paint.other;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import cc.wenmin92.androidlearn.R;

import static com.blankj.utilcode.util.SizeUtils.dp2px;
import static com.blankj.utilcode.util.SizeUtils.sp2px;

public class ShadowLayerView extends View {

    private Paint mPaint;

    public ShadowLayerView(Context context) {
        super(context);
        init();
    }

    public ShadowLayerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    @SuppressLint("DrawAllocation")
    protected void onDraw(Canvas canvas) {
        mPaint.setColor(Color.BLUE);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mPaint.setShadowLayer(dp2px(10), dp2px(4), dp2px(4), 0x88000000);

        // 实心矩形
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(dp2px(8), dp2px(8), dp2px(58), dp2px(58), mPaint);

        // 空心圆(圆环)
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(dp2px(4));
        canvas.drawCircle(dp2px(58 + 25 + 16), dp2px(25 + 8), dp2px(23), mPaint);

        // 文字
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(sp2px(14));
        mPaint.setFakeBoldText(true);
        canvas.drawText("程序员  programmer", dp2px(8), dp2px(68) + sp2px(14), mPaint);

        // 图片1
        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.avatar);
        canvas.drawBitmap(bitmap1, getWidth() - bitmap1.getWidth() - dp2px(16), dp2px(8), mPaint);

        // 透明图片2
        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.windmill);
        canvas.drawBitmap(bitmap2, getWidth() - bitmap1.getWidth() - bitmap2.getWidth() - dp2px(24), dp2px(8), mPaint);
    }
}
