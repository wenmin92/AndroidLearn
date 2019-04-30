package cc.wenmin92.androidlearn.graphics.paint.shader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import androidx.annotation.IntDef;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.blankj.utilcode.util.SizeUtils;

import java.lang.annotation.Retention;

import cc.wenmin92.androidlearn.R;

import static java.lang.annotation.RetentionPolicy.SOURCE;

@SuppressWarnings("WeakerAccess")
public class ShaderView extends View {

    public static final int TYPE_NONE = 0;
    public static final int TYPE_BITMAP_SHADER = 1;
    public static final int TYPE_COMPOSE_SHADER = 2;
    public static final int TYPE_LINEAR_GRADIENT = 3;
    public static final int TYPE_SWEEP_GRADIENT = 4;
    public static final int TYPE_RADIAL_GRADIENT = 5;

    @Retention(SOURCE)
    @IntDef({TYPE_BITMAP_SHADER, TYPE_COMPOSE_SHADER, TYPE_LINEAR_GRADIENT, TYPE_SWEEP_GRADIENT, TYPE_RADIAL_GRADIENT})
    public @interface ShaderType {}

    private int mShaderType = TYPE_NONE;
    private Shader.TileMode mTileMode = Shader.TileMode.CLAMP;
    private final Paint mPaint;
    private final Paint mHintPaint;

    public ShaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(0xFF8CD3EC);

        mHintPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mHintPaint.setStyle(Paint.Style.STROKE);
        mHintPaint.setStrokeWidth(SizeUtils.dp2px(2f));
        mHintPaint.setColor(0xFFE98758);
        mHintPaint.setPathEffect(new DashPathEffect(new float[]{SizeUtils.dp2px(4f), SizeUtils.dp2px(2f)}, 0f));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
        if (mShaderType == TYPE_LINEAR_GRADIENT) {
            canvas.drawRect(getWidth() / 4f, getHeight() / 4f, getWidth() / 4f * 3f, getHeight() / 4f * 3f, mHintPaint);
        }
        if (mShaderType == TYPE_RADIAL_GRADIENT) {
            canvas.drawCircle(getWidth() / 2f, getHeight() / 2f, getWidth() / 3f, mHintPaint);
        }
        super.onDraw(canvas);
    }

    public void setShader(@ShaderType int shaderType) {
        mShaderType = shaderType;
        Shader shader = null;
        switch (shaderType) {
            case TYPE_NONE:
                break;
            case TYPE_BITMAP_SHADER:
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.avatar);
                shader = new BitmapShader(bitmap, mTileMode, mTileMode);
                break;
            case TYPE_COMPOSE_SHADER:
                Shader shader1 = new BitmapShader(BitmapFactory.decodeResource(getResources(), R.drawable.windmill), mTileMode, mTileMode);
                Shader shader2 = new LinearGradient(0f, 0f, getWidth(), getHeight(), 0xFF86B850, 0xFFF5B751, mTileMode);
                shader = new ComposeShader(shader1, shader2, PorterDuff.Mode.MULTIPLY);
                break;
            case TYPE_LINEAR_GRADIENT:
                shader = new LinearGradient(getWidth() / 4f, getHeight() / 4f, getWidth() / 4f * 3f, getHeight() / 4f * 3f, new int[]{Color.BLUE, Color.YELLOW, Color.GREEN}, null, mTileMode);
                break;
            case TYPE_SWEEP_GRADIENT:
                shader = new SweepGradient(getWidth() / 2f, getHeight() / 2f, new int[]{Color.BLUE, Color.YELLOW, Color.GREEN}, null);
                break;
            case TYPE_RADIAL_GRADIENT:
                shader = new RadialGradient(getWidth() / 2f, getHeight() / 2f, getWidth() / 3f, new int[]{Color.BLUE, Color.YELLOW, Color.GREEN}, null, mTileMode);
                break;
        }
        mPaint.setShader(shader);
        invalidate();
    }

    public void setTileMode(Shader.TileMode tileMode) {
        mTileMode = tileMode;
        setShader(mShaderType);
    }
}
