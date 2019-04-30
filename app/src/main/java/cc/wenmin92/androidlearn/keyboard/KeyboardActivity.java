package cc.wenmin92.androidlearn.keyboard;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cc.wenmin92.androidlearn.R;
import timber.log.Timber;

/**
 * KeyboardActivity that initialises the keyboardheight
 * provider and observer.
 */
public final class KeyboardActivity extends AppCompatActivity implements KeyboardHeightObserver {

    /**
     * Tag for logging
     */
    private final static String TAG = "sample_MainActivity";

    /**
     * The keyboard height provider
     */
    private KeyboardHeightProvider keyboardHeightProvider;

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);

        keyboardHeightProvider = new KeyboardHeightProvider(this);

        // make sure to start the keyboard height provider after the onResume
        // of this activity. This is because a popup window must be initialised
        // and attached to the activity root view. 
        View view = findViewById(R.id.activitylayout);
        view.post(() -> keyboardHeightProvider.start());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onPause() {
        super.onPause();
        keyboardHeightProvider.setKeyboardHeightObserver(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onResume() {
        super.onResume();
        keyboardHeightProvider.setKeyboardHeightObserver(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        keyboardHeightProvider.close();
    }

    /**
     * {@inheritDoc}
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void onKeyboardHeightChanged(int height, int orientation) {

        String or = orientation == Configuration.ORIENTATION_PORTRAIT ? "portrait" : "landscape";
        Timber.i("onKeyboardHeightChanged in pixels: " + height + " " + or);

        TextView tv = findViewById(R.id.height_text);
        tv.setText(height + " " + or);

        // color the keyboard height view, this will stay when you close the keyboard
        View view = findViewById(R.id.keyboard);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
        params.height = height;
    }
}