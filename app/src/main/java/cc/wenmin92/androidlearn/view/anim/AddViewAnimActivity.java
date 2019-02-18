package cc.wenmin92.androidlearn.view.anim;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.SizeUtils;

import cc.wenmin92.androidlearn.R;

/**
 * 直接在 layout 布局中使用 animateLayoutChanges 属性，就会有下滑效果
 */
public class AddViewAnimActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_view_anim);

        ViewGroup layout = findViewById(R.id.layout_content);
        View viewA = findViewById(R.id.view_a);
        // View viewB = findViewById(R.id.view_b);

        TextView textView = new TextView(this);
        textView.setText("我爱你");
        textView.setBackgroundColor(0xFFBEBE8A);
        textView.setTextSize(SizeUtils.sp2px(16f));
        int padding = SizeUtils.dp2px(8f);
        textView.setPadding(padding, padding, padding, padding);
        textView.setGravity(Gravity.CENTER);
        ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(SizeUtils.dp2px(200f), ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, SizeUtils.dp2px(10f), 0, 0);
        textView.setLayoutParams(params);

        viewA.setOnClickListener(v -> switchAdd(layout, textView));
    }

    private void switchAdd(ViewGroup layout, TextView textView) {
        if (layout.indexOfChild(textView) >= 0) {
            layout.removeView(textView);
        } else {
            layout.addView(textView, 1);
        }
    }
}
