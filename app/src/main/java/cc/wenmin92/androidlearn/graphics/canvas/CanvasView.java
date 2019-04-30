package cc.wenmin92.androidlearn.graphics.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class CanvasView extends View {

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public CanvasView(Context context) {
        super(context);
    }

    public CanvasView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setColor(0xFFE7FADB);
        canvas.drawPaint(mPaint);
    }
}
