package cc.wenmin92.androidlearn.graphics.paint.path_effect;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposePathEffect;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.graphics.SumPathEffect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.SizeUtils;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.Locale;

import cc.wenmin92.androidlearn.R;

@SuppressWarnings("SuspiciousNameCombination")
@SuppressLint("SetTextI18n")
public class PathEffectTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.hide();
        }

        setContentView(R.layout.activity_path_effect);

        PathEffectView pathEffectNone = findViewById(R.id.path_effect_none);
        TextView tvNone = findViewById(R.id.tv_none);
        pathEffectNone.post(() -> tvNone.setText(
                String.format(Locale.CHINA, "NONE (W:%d, H:%d, Stroke:%d)",
                        SizeUtils.px2dp(pathEffectNone.getWidth()),
                        SizeUtils.px2dp(pathEffectNone.getHeight()),
                        SizeUtils.px2dp(PathEffectView.STROKE_WIDTH))));

        setPathEffectCorner();
        setPathEffectDiscrete();
        setPathEffectDash();
        setPathEffectPathDash();
        setPathEffectConstruct();
    }

    private void setPathEffectCorner() {
        PathEffectView pathEffectCorner = findViewById(R.id.path_effect_corner);
        SeekBar seekRadius = findViewById(R.id.seek_radius);
        TextView tvRadius = findViewById(R.id.tv_radius);
        seekRadius.setMax(200);
        seekRadius.setProgress(100);
        tvRadius.setText("radius(dp): " + seekRadius.getProgress() / 10.0f);

        Paint paint = pathEffectCorner.getPaint();
        paint.setPathEffect(new CornerPathEffect(SizeUtils.dp2px(seekRadius.getProgress() / 10.0f)));

        seekRadius.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvRadius.setText("radius(dp): " + progress / 10.0f);
                paint.setPathEffect(new CornerPathEffect(SizeUtils.dp2px(progress / 10.0f)));
                pathEffectCorner.invalidate();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
    }

    private void setPathEffectDiscrete() {
        PathEffectView pathEffectDiscrete = findViewById(R.id.path_effect_discrete);
        SeekBar seekSegmentLength = findViewById(R.id.seek_segmentLength);
        SeekBar seekDeviation = findViewById(R.id.seek_deviation);
        TextView tvSegmentLength = findViewById(R.id.tv_segmentLength);
        TextView tvDeviation = findViewById(R.id.tv_deviation);
        seekSegmentLength.setMax(200);
        seekSegmentLength.setProgress(100);
        seekDeviation.setMax(200);
        seekDeviation.setProgress(100);
        tvSegmentLength.setText("segmentLength(dp): " + seekSegmentLength.getProgress() / 10.0f);
        tvDeviation.setText("deviation: " + seekDeviation.getProgress() / 10.0f);

        Paint paint = pathEffectDiscrete.getPaint();
        paint.setPathEffect(new DiscretePathEffect(SizeUtils.dp2px(seekSegmentLength.getProgress() / 10.0f), seekDeviation.getProgress() / 10.0f));

        seekSegmentLength.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvSegmentLength.setText("segmentLength(dp): " + seekSegmentLength.getProgress() / 10.0f);
                paint.setPathEffect(new DiscretePathEffect(SizeUtils.dp2px(seekSegmentLength.getProgress() / 10.0f), seekDeviation.getProgress() / 10.0f));
                pathEffectDiscrete.invalidate();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
        seekDeviation.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvDeviation.setText("deviation: " + seekDeviation.getProgress() / 10.0f);
                paint.setPathEffect(new DiscretePathEffect(SizeUtils.dp2px(seekSegmentLength.getProgress() / 10.0f), seekDeviation.getProgress() / 10.0f));
                pathEffectDiscrete.invalidate();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
    }

    private void setPathEffectDash() {
        PathEffectView pathEffectDash = findViewById(R.id.path_effect_dash);
        SeekBar seekPhase = findViewById(R.id.seek_phase);
        TextView tvRadius = findViewById(R.id.tv_phase);
        EditText etIntervals = findViewById(R.id.et_intervals);
        seekPhase.setMax(200);
        seekPhase.setProgress(100);
        tvRadius.setText("phase(dp): " + seekPhase.getProgress());

        Paint paint = pathEffectDash.getPaint();
        paint.setPathEffect(new DashPathEffect(getFloats(etIntervals), SizeUtils.dp2px(seekPhase.getProgress())));

        seekPhase.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvRadius.setText("phase(dp): " + progress);
                paint.setPathEffect(new DashPathEffect(getFloats(etIntervals), SizeUtils.dp2px(seekPhase.getProgress())));
                pathEffectDash.invalidate();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
        etIntervals.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().split(",").length < 2) {
                    return;
                }
                paint.setPathEffect(new DashPathEffect(getFloats(etIntervals), SizeUtils.dp2px(seekPhase.getProgress())));
                pathEffectDash.invalidate();
            }
        });
    }

    private float[] getFloats(EditText etIntervals) {
        String[] rawIntervalStrs = etIntervals.getText().toString().trim().split(",");

        String[] intervalStrs = rawIntervalStrs;
        if (rawIntervalStrs.length % 2 != 0) {
            intervalStrs = Arrays.copyOf(rawIntervalStrs, rawIntervalStrs.length - 1);
        }
        float[] intervals = new float[intervalStrs.length];
        for (int i = 0; i < intervalStrs.length; i++) {
            float interval = 0;
            try {
                interval = Float.parseFloat(intervalStrs[i]);
            } catch (Exception ignored) { }
            intervals[i] = SizeUtils.dp2px(interval);
        }
        return intervals;
    }

    private void setPathEffectPathDash() {
        PathEffectView pathEffectPathDash = findViewById(R.id.path_effect_path_dash);
        SeekBar seekAdvance = findViewById(R.id.seek_advance);
        SeekBar seekPhase = findViewById(R.id.seek_phase_path);
        TextView tvPhase = findViewById(R.id.tv_phase_path);
        TextView tvAdvance = findViewById(R.id.tv_advance);
        RadioGroup rgStyle = findViewById(R.id.rg_style);
        seekAdvance.setMax(200);
        seekAdvance.setProgress(100);
        seekPhase.setMax(200);
        seekPhase.setProgress(100);
        tvAdvance.setText("advance(dp): " + seekAdvance.getProgress() / 10.0f);
        tvPhase.setText("phase(dp): " + seekPhase.getProgress());

        Paint paint = pathEffectPathDash.getPaint();
        Path path = new Path();
        // path.addCircle(0, 0, SizeUtils.dp2px(2f), Path.Direction.CW);
        int height = SizeUtils.dp2px(2f);
        int width = SizeUtils.dp2px(8f);
        path.moveTo(height, -height);
        path.lineTo(width + height, -height);
        path.lineTo(width, 0);
        path.lineTo(width + height, height);
        path.lineTo(height, height);
        path.lineTo(0, 0);
        path.close();
        paint.setPathEffect(new PathDashPathEffect(path, SizeUtils.dp2px(seekAdvance.getProgress() / 10.0f), SizeUtils.dp2px(seekPhase.getProgress()), getPathDashStyle()));

        seekAdvance.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvAdvance.setText("advance(dp): " + progress / 10.0f);
                paint.setPathEffect(new PathDashPathEffect(path, SizeUtils.dp2px(seekAdvance.getProgress() / 10.0f), SizeUtils.dp2px(seekPhase.getProgress()), getPathDashStyle()));
                pathEffectPathDash.invalidate();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
        seekPhase.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvPhase.setText("phase(dp): " + progress);
                paint.setPathEffect(new PathDashPathEffect(path, SizeUtils.dp2px(seekAdvance.getProgress() / 10.0f), SizeUtils.dp2px(seekPhase.getProgress()), getPathDashStyle()));
                pathEffectPathDash.invalidate();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
        rgStyle.setOnCheckedChangeListener((group, checkedId) -> {
            paint.setPathEffect(new PathDashPathEffect(path, SizeUtils.dp2px(seekAdvance.getProgress() / 10.0f), SizeUtils.dp2px(seekPhase.getProgress()), getPathDashStyle()));
            pathEffectPathDash.invalidate();
        });
    }

    @NotNull
    private PathDashPathEffect.Style getPathDashStyle() {
        RadioGroup rgStyle = findViewById(R.id.rg_style);
        switch (rgStyle.getCheckedRadioButtonId()) {
            case R.id.rb_morph:
                return PathDashPathEffect.Style.MORPH;
            case R.id.rb_rotate:
                return PathDashPathEffect.Style.ROTATE;
            case R.id.rb_translate:
                return PathDashPathEffect.Style.TRANSLATE;
            default:
                return PathDashPathEffect.Style.MORPH;
        }
    }

    private void setPathEffectConstruct() {
        PathEffectView pathEffectConstruct = findViewById(R.id.path_effect_construct);
        RadioGroup rgConstruct = findViewById(R.id.rg_construct);
        CheckBox cbReverse = findViewById(R.id.btn_reverse);
        TextView tvOrder = findViewById(R.id.tv_order);

        Paint paint = pathEffectConstruct.getPaint();
        CornerPathEffect first = new CornerPathEffect(SizeUtils.dp2px(10f));
        DashPathEffect second = new DashPathEffect(new float[]{SizeUtils.dp2px(8f), SizeUtils.dp2px(4f)}, 0f);
        paint.setPathEffect(getConstructEffect(first, second));

        rgConstruct.setOnCheckedChangeListener((group, checkedId) -> {
            paint.setPathEffect(getConstructEffect(first, second));
            pathEffectConstruct.invalidate();
        });
        cbReverse.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!isChecked) {
                tvOrder.setText("1. CornerPathEffect\n2. DashPathEffect");
            } else {
                tvOrder.setText("1. DashPathEffect\n2. CornerPathEffect");
            }
            paint.setPathEffect(getConstructEffect(first, second));
            pathEffectConstruct.invalidate();
        });
    }

    @NotNull
    private PathEffect getConstructEffect(CornerPathEffect first, DashPathEffect second) {
        RadioGroup rgConstruct = findViewById(R.id.rg_construct);
        CheckBox cbReverse = findViewById(R.id.btn_reverse);
        boolean isReverse = cbReverse.isChecked();
        switch (rgConstruct.getCheckedRadioButtonId()) {
            case R.id.rb_sum:
                if (!isReverse) {
                    return new SumPathEffect(first, second);
                } else {
                    return new SumPathEffect(second, first);
                }
            case R.id.rb_compose:
                if (!isReverse) {
                    return new ComposePathEffect(first, second);
                } else {
                    return new ComposePathEffect(second, first);
                }
            default:
                return new SumPathEffect(first, second);
        }
    }

    public static void view2png(View view, File file) {
        //Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        else
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.TRANSPARENT);
        // draw the view on the canvas
        view.draw(canvas);
        //return the bitmap
        try {
            returnedBitmap.compress(Bitmap.CompressFormat.PNG, 95, new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void onClick(View view) {
        File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), view.getId() + ".png");
        view2png(view, file);
        Toast.makeText(this, "File saved in " + file.getPath(), Toast.LENGTH_SHORT).show();
    }
}
