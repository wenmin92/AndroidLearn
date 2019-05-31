package cc.wenmin92.androidlearn;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cc.wenmin92.androidlearn.graphics.canvas.CanvasTestActivity;
import cc.wenmin92.androidlearn.graphics.paint.other.ShadowLayerActivity;
import cc.wenmin92.androidlearn.graphics.paint.path_effect.PathEffectTestActivity;
import cc.wenmin92.androidlearn.graphics.paint.shader.ShaderTestActivity;
import cc.wenmin92.androidlearn.keyboard.KeyboardActivity;
import cc.wenmin92.androidlearn.service.location.LocationActivity;
import cc.wenmin92.androidlearn.storage.StorageTestActivity;
import cc.wenmin92.androidlearn.text.TextTestActivity;
import cc.wenmin92.androidlearn.view.anim.AddViewAnimActivity;
import cc.wenmin92.androidlearn.view.anim.AnimActivity;
import cc.wenmin92.androidlearn.view.constraint.ConstraintActivity;
import cc.wenmin92.androidlearn.view.custom.CustomViewActivity;
import cc.wenmin92.androidlearn.view.popupwindow.PopupWindowActivity;
import cc.wenmin92.androidlearn.view.statusbar.StatusBarTestActivity;
import cc.wenmin92.androidlearn.view.style.StyleActivity;
import cc.wenmin92.androidlearn.view.viewpager.ViewPagerRvActivity;
import cc.wenmin92.androidlearn.widget.NestRecyclerActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        MyAdapter adapter = new MyAdapter(getTestItems());
        adapter.setOnItemClickListener(this::startActivity);
        rv.setAdapter(adapter);
    }

    private void startActivity(TestItem item) {
        startActivity(new Intent(MainActivity.this, item.activityClass));
    }

    @NonNull
    private ArrayList<TestItem> getTestItems() {
        ArrayList<TestItem> testItems = new ArrayList<>();
        testItems.add(new TestItem(LocationActivity.class, "位置服务"));
        testItems.add(new TestItem(ConstraintActivity.class, "ConstraintLayout"));
        testItems.add(new TestItem(KeyboardActivity.class, "监听键盘弹起"));
        testItems.add(new TestItem(StyleActivity.class, "Style 样式"));
        testItems.add(new TestItem(AnimActivity.class, "anim"));
        testItems.add(new TestItem(AddViewAnimActivity.class, "add view anim"));
        testItems.add(new TestItem(CustomViewActivity.class, "custom view"));
        testItems.add(new TestItem(PathEffectTestActivity.class, "Paint-Path Effect"));
        testItems.add(new TestItem(ShaderTestActivity.class, "Paint-Shader"));
        testItems.add(new TestItem(StatusBarTestActivity.class, "Status Bar"));
        testItems.add(new TestItem(StorageTestActivity.class, "Storage"));
        testItems.add(new TestItem(PopupWindowActivity.class, "Popup Window"));
        testItems.add(new TestItem(ShadowLayerActivity.class, "Paint-ShadowLayer"));
        testItems.add(new TestItem(CanvasTestActivity.class, "Canvas"));
        testItems.add(new TestItem(ViewPagerRvActivity.class, "ViewPagerRv"));
        testItems.add(new TestItem(TextTestActivity.class, "Text"));
        testItems.add(new TestItem(NestRecyclerActivity.class, "NestRecyclerView"));
        return testItems;
    }

    @SuppressWarnings("WeakerAccess")
    static class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        private List<TestItem> testItems;
        private OnItemClickListener listener;

        public MyAdapter(List<TestItem> testItems) {
            this.testItems = testItems;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_test_item, parent, false);
            return new MyViewHolder(view, listener);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            TestItem testItem = testItems.get(position);
            holder.bind(testItem);
            holder.tvName.setText(testItem.name);
        }

        @Override
        public int getItemCount() {
            return testItems.size();
        }

        public void setOnItemClickListener(OnItemClickListener listener) {
            this.listener = listener;
        }
    }

    @SuppressWarnings("WeakerAccess")
    static class MyViewHolder extends RecyclerView.ViewHolder {

        TestItem testItem;
        TextView tvName;

        public MyViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            if (listener != null)
                itemView.setOnClickListener(v -> listener.onClickListener(testItem));
        }

        public void bind(TestItem testItem) {
            this.testItem = testItem;
        }
    }

    interface OnItemClickListener {
        void onClickListener(TestItem testItem);
    }

    @SuppressWarnings("WeakerAccess")
    static class TestItem {
        public Class activityClass;
        public String name;

        public TestItem(Class activityClass, String name) {
            this.activityClass = activityClass;
            this.name = name;
        }
    }
}
