package cc.wenmin92.androidlearn.view.style;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import cc.wenmin92.androidlearn.R;
import timber.log.Timber;

/**
 * [深入理解Android 自定义attr Style styleable以及其应用 - 简书](https://www.jianshu.com/p/61b79e7f88fc)
 * <p>
 * 数据来源一共有4个，set, defStyleAttr, NULL, defStyleRes, 如果一个属性在多个地方都被定义,
 * 优先级如下：
 * set > defStyleAttr(主题可配置样式) > defStyleRes(默认样式) > NULL(主题中直接指定)
 */
public class CustomView extends View {

    public CustomView(Context context) {
        this(context, null);
        Timber.d("CustomView: [1] 参数");
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, R.attr.customView);
        Timber.d("CustomView: [2] 参数");
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, R.style.CustomView);
        Timber.d("CustomView: [3] 参数");
    }

    /**
     * @param attrs        XML tag 显式指定的属性
     * @param defStyleAttr 在 theme 中设置的, 指向一个样式, 用来提供默认值
     * @param defStyleRes  只有在 defStyleAttr 为0 或者 theme 中未设置指定的 defStyleAttr 时, 才会使用该默认配置
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        Timber.d("CustomView: [4] 参数");

        int[] attrsDesired = R.styleable.CustomView; // 需要获取的属性的id合集
        TypedArray a = context.obtainStyledAttributes(attrs, attrsDesired, defStyleAttr, defStyleRes);

        int color1 = a.getColor(R.styleable.CustomView_color1, 0xff000000);
        int color2 = a.getColor(R.styleable.CustomView_color2, 0xff000000);
        int color3 = a.getColor(R.styleable.CustomView_color3, 0xff000000);
        int color4 = a.getColor(R.styleable.CustomView_color4, 0xff000000);
        int color5 = a.getColor(R.styleable.CustomView_color5, 0xff000000);

        Timber.d("color1 = %s", Integer.toHexString(color1)); // 
        Timber.d("color2 = %s", Integer.toHexString(color2)); // 
        Timber.d("color3 = %s", Integer.toHexString(color3)); // 
        Timber.d("color4 = %s", Integer.toHexString(color4)); // 
        Timber.d("color5 = %s", Integer.toHexString(color5)); // 

        a.recycle();
    }
}
