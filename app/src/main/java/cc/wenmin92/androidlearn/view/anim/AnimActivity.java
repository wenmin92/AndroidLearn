package cc.wenmin92.androidlearn.view.anim;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import cc.wenmin92.androidlearn.R;

public class AnimActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim);
        View tv = findViewById(R.id.tv);

        findViewById(R.id.btn).setOnClickListener(v -> {
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.scale_anim);
            animation.reset();
            tv.startAnimation(animation);
        });
    }
}
