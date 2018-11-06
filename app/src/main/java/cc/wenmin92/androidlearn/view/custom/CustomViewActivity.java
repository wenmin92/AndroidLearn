package cc.wenmin92.androidlearn.view.custom;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import cc.wenmin92.androidlearn.R;

public class CustomViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);

        // findViewById(R.id.view_custom).animate().setDuration(3000).rotation(3600f).start();
        // findViewById(R.id.view_custom).setAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));

        // Choreographer.getInstance().postFrameCallback(frameTimeNanos -> Timber.d("frameTimeNanos: %s",
        // frameTimeNanos));
    }
}
