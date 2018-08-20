package cc.wenmin92.androidlearn;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cc.wenmin92.androidlearn.service.location.LocationActivity;

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
