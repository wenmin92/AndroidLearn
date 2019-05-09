package cc.wenmin92.androidlearn.view.popupwindow;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import androidx.appcompat.app.AppCompatActivity;
import cc.wenmin92.androidlearn.R;
import timber.log.Timber;

/**
 * popupWindow 文档: https://www.yuque.com/wenmin92/android/adggog
 */
public class PopupWindowActivity extends AppCompatActivity {

    private PopupWindow popupBasic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_window);

        View layout = LayoutInflater.from(this).inflate(R.layout.layout_popup_window, null);
        popupBasic = new PopupWindow(layout, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupBasic.setAnimationStyle(android.R.style.Animation_Dialog);
        popupBasic.setFocusable(true);
        // popupBasic.setClippingEnabled(false); 默认true, 是否允许剪裁 (设成false后, 可以超出屏幕范围, 如, 显示在底部导航栏上)
        popupBasic.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupBasic.setOutsideTouchable(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            popupBasic.setOverlapAnchor(false);
        }
        Timber.d("popupBasic.getMaxAvailableHeight()=%s", popupBasic.getMaxAvailableHeight(findViewById(R.id.btn_basic)));
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_basic:
                popupBasic.showAtLocation(findViewById(R.id.tv_bottom), Gravity.BOTTOM, 0, 0);
                view.postDelayed(() -> Timber.d("popupBasic.isAboveAnchor()=%b", popupBasic.isAboveAnchor()), 500);
                view.postDelayed(() -> {
                    Timber.d("popupBasic.getMaxAvailableHeight()=%s", popupBasic.getMaxAvailableHeight(findViewById(R.id.btn_basic)));
                    Timber.d("popupBasic.getHeight()=%s", popupBasic.getHeight());
                    Timber.d("view.getY()=%s", view.getY());
                    Timber.d("view.getY()+view.getHeight()=%s", view.getY() + view.getHeight());
                }, 500);
                break;
        }
    }
}
