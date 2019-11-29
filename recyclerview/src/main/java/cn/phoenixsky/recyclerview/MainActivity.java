package cn.phoenixsky.recyclerview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new RecyclerAdapter());
        recyclerView.addItemDecoration(new StickyHeader());
        recyclerView.addItemDecoration(new Divider());
    }

}


class ContentViewHolder extends RecyclerView.ViewHolder {

    TextView textView;

    ContentViewHolder(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.item_text_view);
    }
}

class HeaderViewHolder extends RecyclerView.ViewHolder {

    TextView textView;

    HeaderViewHolder(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.header_title);
    }
}

class FooterViewHolder extends RecyclerView.ViewHolder {

    TextView textView;

    FooterViewHolder(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.footer_title);
    }
}

class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "RecyclerAdapter";

    private static final int HEADER = 0;
    private static final int CONTENT = 1;
    private static final int FOOTER = 2;


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
//            case HEADER:
//                return new HeaderViewHolder(inflater.inflate(R.layout.header, parent, false));
//            case FOOTER:
//                return new FooterViewHolder(inflater.inflate(R.layout.footer, parent, false));
            default:
                return new ContentViewHolder(inflater.inflate(R.layout.item_view, parent, false));
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            ((HeaderViewHolder) holder).textView.setText("header");
        } else if (holder instanceof FooterViewHolder) {
            ((FooterViewHolder) holder).textView.setText("Footer");
        } else if (holder instanceof ContentViewHolder) {
            ((ContentViewHolder) holder).textView.setText("position--" + position);
        }
    }

//    @Override
//    public int getItemViewType(int position) {
//        if (position == 0) {
//            return HEADER;
//        } else if (position >= getItemCount() - 1) {
//            return FOOTER;
//        } else {
//            return CONTENT;
//        }
//    }

    @Override
    public int getItemCount() {
        return 30 + 1 + 1;
    }
}


class StickyHeader extends RecyclerView.ItemDecoration {

    private static final String TAG = "StickyHeader";
    static int HEIGHT = 100;
    private Paint paint;
    private Paint textPaint;


    public StickyHeader() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);
        paint.setColor(Color.parseColor("#ff0000"));

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(60);
        textPaint.setTypeface(Typeface.SANS_SERIF);
        textPaint.setFakeBoldText(true);

    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildAdapterPosition(view);
        boolean isGroupFirst = position % 5 == 0;
        if (isGroupFirst) {
            outRect.top = HEIGHT;
        }
    }

//    @Override
//    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
//        super.onDrawOver(c, parent, state);
//    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

        Log.i(TAG, "onDraw: " + parent.getChildCount());

        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);

            int position = parent.getChildAdapterPosition(child);
            int groupNo = position / 5 + 1;
            boolean isGroupFirst = position % 5 == 0;
            boolean isGroupLast = position % 5 == 4;


            if (groupNo % 2 == 0) {
                paint.setColor(Color.GREEN);
            } else {
                paint.setColor(Color.RED);
            }

            int left = child.getLeft();
            int right = child.getRight();

            if (i == 0) {
                int top = parent.getPaddingTop();
                int bottom = top+ HEIGHT;
                if (isGroupLast) {
                    int supposedTop = child.getBottom() - HEIGHT;
                    top = Math.min(supposedTop,top);
                    Log.i(TAG, "supposedTop: " + supposedTop);
                    Log.i(TAG, "top: " + top);

                    bottom = Math.min(child.getBottom(),HEIGHT);

                }
                c.drawRect(left, 0, right, bottom , paint);
                c.drawText("Sticker Header " + groupNo, left + 30, top + 60, textPaint);
                textPaint.setTextSize(30);
                c.drawText("bottom " + bottom , left + 530, top + 60, textPaint);
            } else {
                if (isGroupFirst) {
                    int top = child.getTop() - HEIGHT;
                    Log.i(TAG, "onDraw: left:" + left + " top:" + child.getTop() + " right:" + right + " bottom:" + child.getTop());
                    c.drawRect(left, top, right, child.getTop() - 10, paint);
                    c.drawText("Sticker Header " + groupNo, left + 30, top + 60, textPaint);
                }
            }
        }
    }
}

class Divider extends RecyclerView.ItemDecoration {

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }


    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }


    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = 10;
        outRect.left = 15;
        outRect.right = 15;
    }
}