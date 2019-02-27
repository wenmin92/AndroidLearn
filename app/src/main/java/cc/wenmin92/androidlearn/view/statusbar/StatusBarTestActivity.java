package cc.wenmin92.androidlearn.view.statusbar;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;

import cc.wenmin92.androidlearn.R;
import timber.log.Timber;

public class StatusBarTestActivity extends Activity {

    private View decorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_bar_test);

        decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(visibility -> {
            ToastUtils.showShort("system ui visibility has changed: " + visibility);
            Timber.d("system ui visibility has changed: %s", visibility);
        });
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_clear:
                decorView.setSystemUiVisibility(0);
                break;
            case R.id.btn_dim:
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
                break;
            case R.id.btn_hide:
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
                break;
            case R.id.btn_behind:
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
                break;
            case R.id.btn_behind_hide:
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
                break;
            case R.id.btn_behind_hide_auto:
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
                break;

            // 相关资料实现在语雀文档中
            case R.id.btn_translucent:
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
                break;
            case R.id.btn_transparent:
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
                break;
        }
    }

}
