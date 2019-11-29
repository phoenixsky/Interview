package cn.phoenixsky.recyclerview2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new Adapter());
        recyclerView.addItemDecoration(new Divider());
        recyclerView.addItemDecoration(new StickerHeader());
    }
}


class ViewHolder extends RecyclerView.ViewHolder {

    TextView textView;

    ViewHolder(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.text_view);
    }
}

class Adapter extends RecyclerView.Adapter<ViewHolder> {

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText("position:" + position);
    }


    @Override
    public int getItemCount() {
        return 30;
    }
}

class StickerHeader extends RecyclerView.ItemDecoration {


    private Paint paint;
    private static final int HEIGHT = 100;

    StickerHeader() {
        paint = new Paint();
        paint.setColor(Color.RED);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildAdapterPosition(view);
        if (position % 5 == 0) {//每组第一个
            outRect.top = HEIGHT;
        }
    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(child);
            int groupNo = position / 5 + 1;

            int left = child.getLeft();
            int right = child.getRight();
            paint.setColor(groupNo % 2 == 0 ? Color.RED : Color.YELLOW);
            if (i == 0) {// 页面的第一个
                boolean isLastOne = position % 5 == 4;
                int bottom = HEIGHT;
                if (isLastOne) {
                    bottom = Math.min(child.getBottom(), bottom);
                }
                c.drawRect(left, 0, right, bottom, paint);

            } else {
                if (position % 5 == 0) { //每组的第一个
                    int bottom = child.getTop();
                    int top = bottom - HEIGHT;
                    c.drawRect(left, top, right, bottom, paint);
                }
            }
        }
    }

}

class Divider extends RecyclerView.ItemDecoration {

    private static final int HEIGHT = 20;
    private static final int LEFT = 20;
    private static final int RIGHT = 20;

    private Paint paint;

    Divider() {
        paint = new Paint();
        paint.setColor(Color.GRAY);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = 20;
        outRect.left = 20;
        outRect.right = 20;
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);

            int left = child.getLeft() - LEFT;
            int right = child.getRight() + RIGHT;
            int top = child.getBottom();
            c.drawRect(left, top, right, top + HEIGHT, paint);
        }

    }
}