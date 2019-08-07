package com.frj.zhihuizhongxing.Control;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;


import com.frj.zhihuizhongxing.R;
import com.frj.zhihuizhongxing.Tools.SystemTools;

public class BottomRadian extends View {
    private static final float BOTTOM_HEIGHT = 50;
    private int mRadius;
    private int mRx;
    private int mRy;

    private Paint mPaint;

    public BottomRadian(Context context) {
        super(context);
        init();
    }

    public BottomRadian(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BottomRadian(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BottomRadian(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mPaint = new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int a = getMeasuredWidth() / 2;
        int b = SystemTools.dip2px(BOTTOM_HEIGHT);
        mRx = a;
        mRadius = (a * a + b * b) / (2 * b);
        mRy = getMeasuredHeight() - mRadius;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setAntiAlias(true);
        mPaint.setColor(getResources().getColor(R.color.bg_gray));
        RectF rectF = new RectF(SystemTools.dip2px(-20), getMeasuredHeight() / 2, getMeasuredWidth()+SystemTools.dip2px(20), getMeasuredHeight());
        canvas.drawOval(rectF, mPaint);
        mPaint.setColor(getResources().getColor(R.color.bg_status));
        canvas.drawCircle(mRx, mRy, mRadius, mPaint);
    }
}
