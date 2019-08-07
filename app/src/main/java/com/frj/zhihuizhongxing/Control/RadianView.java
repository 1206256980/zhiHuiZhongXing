package com.frj.zhihuizhongxing.Control;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import com.frj.zhihuizhongxing.R;

public class RadianView extends View {
    public RadianView(Context context) {
        super(context);
        init();
    }

    public RadianView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RadianView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public RadianView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    Paint mPaint;

    private void init() {
        mPaint = new Paint();
    }

    private int colorBottonRadio = getResources().getColor(R.color.blue);

    public void setColorBottonRadio(int colorBottonRadio) {
        this.colorBottonRadio = colorBottonRadio;
        invalidate();
    }

    int x;
    int radio;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        x = getMeasuredWidth() / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setAntiAlias(true);
        mPaint.setColor(colorBottonRadio);
        canvas.drawCircle(x, x, x, mPaint);
    }
}
