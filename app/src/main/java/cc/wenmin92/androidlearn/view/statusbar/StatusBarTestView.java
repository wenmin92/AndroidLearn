package cc.wenmin92.androidlearn.view.statusbar;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import android.util.AttributeSet;

import timber.log.Timber;

public class StatusBarTestView extends AppCompatTextView {

    public StatusBarTestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void dispatchSystemUiVisibilityChanged(int visibility) {
        super.dispatchSystemUiVisibilityChanged(visibility);
        Timber.d("dispatchSystemUiVisibilityChanged, visibility: %d", visibility);
    }

    @Override
    public void dispatchWindowSystemUiVisiblityChanged(int visibility) {
        super.dispatchWindowSystemUiVisiblityChanged(visibility);
        Timber.d("dispatchWindowSystemUiVisiblityChanged, visibility: %d", visibility);
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        Timber.d("onWindowVisibilityChanged, visibility: %d", visibility);
    }
}
