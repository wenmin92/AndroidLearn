package cc.wenmin92.androidlearn.graphics.paint.shader;

import android.graphics.Bitmap;
import android.graphics.Shader;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;

import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.PathUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.ToastUtils;

import java.io.File;

import cc.wenmin92.androidlearn.R;
import timber.log.Timber;

public class ShaderTestActivity extends AppCompatActivity {

    private ShaderView viewShader;
    private CheckBox lastShader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shader_test);
        viewShader = findViewById(R.id.view_shader);
        viewShader.setOnClickListener(v -> screenshot());
    }

    public void changeShader(View view) {
        if (lastShader != null) {
            lastShader.setChecked(false);
        }
        lastShader = (CheckBox) view;
        lastShader.setChecked(true);

        switch (view.getId()) {
            case R.id.btn_none:
                viewShader.setShader(ShaderView.TYPE_NONE);
                break;
            case R.id.btn_bitmap_shader:
                viewShader.setShader(ShaderView.TYPE_BITMAP_SHADER);
                break;
            case R.id.btn_compose_shader:
                viewShader.setShader(ShaderView.TYPE_COMPOSE_SHADER);
                break;
            case R.id.btn_linear_gradient:
                viewShader.setShader(ShaderView.TYPE_LINEAR_GRADIENT);
                break;
            case R.id.btn_radial_gradient:
                viewShader.setShader(ShaderView.TYPE_RADIAL_GRADIENT);
                break;
            case R.id.btn_sweep_gradient:
                viewShader.setShader(ShaderView.TYPE_SWEEP_GRADIENT);
                break;
        }

    }

    public void changeTileMode(View view) {
        switch (view.getId()) {
            case R.id.btn_clamp:
                viewShader.setTileMode(Shader.TileMode.CLAMP);
                break;
            case R.id.btn_repeat:
                viewShader.setTileMode(Shader.TileMode.REPEAT);
                break;
            case R.id.btn_mirror:
                viewShader.setTileMode(Shader.TileMode.MIRROR);
                break;
        }
    }

    private void screenshot() {
        String fileName = "ShaderTest_" + System.currentTimeMillis() + ".png";
        String filePath = PathUtils.getExternalAppPicturesPath() + File.separator + fileName;
        ImageUtils.save(ScreenUtils.screenShot(this, true), filePath, Bitmap.CompressFormat.PNG);
        Timber.d("screenshot, filePaht: %s", filePath);
        ToastUtils.showShort("screenshot");
    }
}
