package com.frj.zhihuizhongxing.Control;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.frj.zhihuizhongxing.R;
import com.frj.zhihuizhongxing.Tools.SystemTools;

/**
 * 底部导航栏组件
 */

public class FastCatBar extends View {
    public final static int FCB_ITEM_VIDEO = 0;     //视频
    public final static int FCB_ITEM_MOVIE_CATEGORY = 5;      //电影
    public final static int FCB_ITEM_UPLOAD = 2;   //上传
    public final static int FCB_ITEM_APP = 3;   //应用
    public final static int FCB_ITEM_MINE = 4;     //我的
    public final static int FCB_ITEM_FOLLOW = 1;
    private static final long FAST_CLICK_TIME = 600;

    private long lastClick = 0;
    public int selectedItem = FCB_ITEM_VIDEO;

    private OnSelectedItemChanged onSelectedItemChanged;

    public void setOnSelectedItemChanged(OnSelectedItemChanged onSelectedItemChanged) {
        this.onSelectedItemChanged = onSelectedItemChanged;
    }


    public FastCatBar(Context context) {
        super(context);
        init();
    }

    public FastCatBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    Paint paint;
    Bitmap bpVideo;
    //    Bitmap bpMovie;
    Bitmap bpFollow;
    Bitmap bpUpload;
    Bitmap bpApp;
    Bitmap bpMine;

    Bitmap bpVideoSelected;
    //    Bitmap bpMovieSelected;
    Bitmap bpFollowSelected;
    Bitmap bpUploadSelected;
    Bitmap bpAppSelected;
    Bitmap bpMineSelected;
    Bitmap bgCircle;

    Resources res;

    RectF rectF;

    protected void init() {
        res = getResources();
        paint = new Paint();

        //载入菜单图片
        bpVideo = BitmapFactory.decodeResource(res, R.drawable.ico_index);
//        bpMovie = BitmapFactory.decodeResource(res, R.drawable.fcb_movie);
        bpFollow = BitmapFactory.decodeResource(res, R.drawable.ico_task);
        bpUpload = BitmapFactory.decodeResource(res, R.drawable.ico_history_task);
        bpApp = BitmapFactory.decodeResource(res, R.drawable.ico_me);

        bpVideoSelected = BitmapFactory.decodeResource(res, R.drawable.ico_select_index);
//        bpMovieSelected = BitmapFactory.decodeResource(res, R.drawable.fcb_movie_slt);
        bpFollowSelected = BitmapFactory.decodeResource(res, R.drawable.ico_select_task);
        bpUploadSelected = BitmapFactory.decodeResource(res, R.drawable.ico_select_history_task);
        bpAppSelected = BitmapFactory.decodeResource(res, R.drawable.ico_select_me);

        bgCircle=BitmapFactory.decodeResource(res, R.drawable.ico_circle);

        rectF = new RectF();
    }


    @SuppressLint("ResourceType")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = getMeasuredHeight();
        int width = getMeasuredWidth();

        //绘制背景
        //paint.setColor(res.getColor(R.color.black));
        paint.setColor(res.getColor(R.color.white));
        paint.setAntiAlias(true);
        //canvas.drawCircle(width / 2, height / 2, height / 2, paint);
        canvas.drawRect(0, SystemTools.dip2px(15), width, height, paint);
        paint.setColor(res.getColor(R.color.white));


        //计算菜单项目坐标和大小
        float itemSize = (float) (height * 0.43);//每个tab高度
        float itemSpace = (float) ((width - SystemTools.dip2px(40)) / 4);//每个tab宽度
        float itemY = (float) (height * 0.25);//top
        float itemY1 = itemY + itemSize;//botton
        float itemX = (itemSpace - itemSize) / 2 + SystemTools.dip2px(20);//tab两边边距
        float itemX1 = itemX + itemSize;
        float textY = (float) (itemY1 + height * 0.23);
        float textSize = (float) (itemSize * 0.4);
        float itemCSizeX = (float) (height * 0.85);
        float itemCSizeY = (float) (height * 0.6);
        float itemCY = (float) (height * 0.3);
        float itemCY1 = itemCY + itemCSizeY;
        float itemCX = (width - itemCSizeX) / 2;
        float itemCX1 = itemCX + itemCSizeX;


        //视频
        paint.setTextSize(textSize);
        paint.setColor(res.getColor(R.color.color_gray));
        if (selectedItem == FCB_ITEM_VIDEO) {
            paint.setColor(res.getColor(R.color.white));
            drawOval(itemSpace, itemSpace*0, canvas);
            paint.setColor(res.getColor(R.color.blue));
            rectF.set(itemX-SystemTools.dip2px(2), itemY-SystemTools.dip2px(2), itemX1+SystemTools.dip2px(2), itemY1+SystemTools.dip2px(2));

        }else{

            rectF.set(itemX+SystemTools.dip2px(2), itemY+SystemTools.dip2px(2), itemX1-SystemTools.dip2px(2), itemY1-SystemTools.dip2px(2));
        }
        //paint.setColor(res.getColor(selectedItem == FCB_ITEM_VIDEO ? R.color.tab_selected : R.color.fcbItem));
        canvas.drawBitmap(selectedItem == FCB_ITEM_VIDEO ? bpVideoSelected : bpVideo, null, rectF, paint);
        canvas.drawText("首页", itemX+SystemTools.dip2px(3), textY, paint);

        //关注
        itemX += itemSpace;
        itemX1 = itemX + itemSize;
        paint.setColor(res.getColor(R.color.color_gray));
        if (selectedItem == FCB_ITEM_FOLLOW) {
            paint.setColor(res.getColor(R.color.white));
            drawOval(itemSpace, itemSpace*1, canvas);
            paint.setColor(res.getColor(R.color.blue));
            rectF.set(itemX-SystemTools.dip2px(2), itemY-SystemTools.dip2px(2), itemX1+SystemTools.dip2px(2), itemY1+SystemTools.dip2px(2));

        }else{

            rectF.set(itemX+SystemTools.dip2px(2), itemY+SystemTools.dip2px(2), itemX1-SystemTools.dip2px(2), itemY1-SystemTools.dip2px(2));
        }
        //paint.setColor(res.getColor(selectedItem == FCB_ITEM_FOLLOW ? R.color.tab_selected : R.color.fcbItem));
        canvas.drawBitmap(selectedItem == FCB_ITEM_FOLLOW ? bpFollowSelected : bpFollow, null, rectF, paint);
        canvas.drawText("当前任务", itemX-SystemTools.dip2px(8), textY, paint);

        //分类
        itemX += itemSpace;
        itemX1 = itemX + itemSize;
        paint.setColor(res.getColor(R.color.color_gray));
        if (selectedItem == FCB_ITEM_UPLOAD) {
            paint.setColor(res.getColor(R.color.white));
            drawOval(itemSpace, itemSpace*2, canvas);
            paint.setColor(res.getColor(R.color.blue));
            rectF.set(itemX-SystemTools.dip2px(2), itemY-SystemTools.dip2px(2), itemX1+SystemTools.dip2px(2), itemY1+SystemTools.dip2px(2));

        }else{

            rectF.set(itemX+SystemTools.dip2px(2), itemY+SystemTools.dip2px(2), itemX1-SystemTools.dip2px(2), itemY1-SystemTools.dip2px(2));
        }
        //paint.setColor(res.getColor(selectedItem == FCB_ITEM_UPLOAD ? R.color.tab_selected : R.color.fcbItem));
        canvas.drawBitmap(selectedItem == FCB_ITEM_UPLOAD ? bpUploadSelected : bpUpload, null, rectF, paint);
        canvas.drawText("历史任务", itemX-SystemTools.dip2px(8), textY, paint);

//        canvas.drawText(res.getString(R.string.fcb_item_upload), itemX, textY, paint);
        //应用
        //电影
        itemX += itemSpace;
        itemX1 = itemX + itemSize;
        paint.setColor(res.getColor(R.color.color_gray));
        if (selectedItem == FCB_ITEM_APP) {
            paint.setColor(res.getColor(R.color.white));
            drawOval(itemSpace, itemSpace*3, canvas);
            paint.setColor(res.getColor(R.color.blue));
            rectF.set(itemX-SystemTools.dip2px(2), itemY-SystemTools.dip2px(2), itemX1+SystemTools.dip2px(2), itemY1+SystemTools.dip2px(2));

        }else{

            rectF.set(itemX+SystemTools.dip2px(2), itemY+SystemTools.dip2px(2), itemX1-SystemTools.dip2px(2), itemY1-SystemTools.dip2px(2));
        }
        // paint.setColor(res.getColor(selectedItem == FCB_ITEM_APP ? R.color.tab_selected : R.color.fcbItem));
        canvas.drawBitmap(selectedItem == FCB_ITEM_APP ? bpAppSelected : bpApp, null, rectF, paint);
        canvas.drawText("我的", itemX+SystemTools.dip2px(3), textY, paint);


    }

    private void drawOval(float itemSpace, float itemSpaceAll, Canvas canvas) {
        int ovalLeft = (int) (SystemTools.dip2px(4)+itemSpaceAll+ (itemSpace/2-SystemTools.dip2px(30)/2));
        int ovalBotttom = SystemTools.dip2px(15);
        int ovalRight = ovalLeft + SystemTools.dip2px(30) * 2;
        RectF rectF = new RectF(ovalLeft, 0, ovalRight, ovalBotttom);
        //canvas.drawOval(rectF, paint);//画椭圆
        canvas.drawBitmap(bgCircle, null, rectF, paint);
    }


    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            return true;
        }
        if (action == MotionEvent.ACTION_UP) {
            if (System.currentTimeMillis() - lastClick > FAST_CLICK_TIME) {
                lastClick = System.currentTimeMillis();
                int i = getHitItem(event.getX(), event.getY());
                if (i >= 0 && selectedItem != i) {
                    performClick();
                    /*if (i != FCB_ITEM_UPLOAD) {*/
                    selectedItem = i;
                    invalidate();
                    /*}*/
                    onSelectedItemChanged.onChange(i);
                }
            }
        }
        return super.onTouchEvent(event);
    }

    /* 判断哪个项目被点击 */
    private int getHitItem(float x, float y) {
        int height = getMeasuredHeight();
        int width = getMeasuredWidth();
        float itemSize = (float) (height * 0.8);
        float itemSpace = (float) (width / 4);
        float itemY = (float) (height * 0.2);
        float itemY1 = (float) (height);
        float itemX = (itemSpace - itemSize) / 2;
        float itemX1 = itemX + itemSize;

        if (x > itemX && x < itemX1 && y > itemY && y < itemY1) {
            return FCB_ITEM_VIDEO;
        }

        itemX += itemSpace;
        itemX1 = itemX + itemSize;
        if (x > itemX && x < itemX1 && y > itemY && y < itemY1) {
            return FCB_ITEM_FOLLOW;
        }
        itemX += itemSpace;
        itemX1 = itemX + itemSize;
        if (x > itemX && x < itemX1 && y > itemY && y < itemY1) {
            return FCB_ITEM_UPLOAD;
        }
        itemX += itemSpace;
        itemX1 = itemX + itemSize;
        if (x > itemX && x < itemX1 && y > itemY && y < itemY1) {
            return FCB_ITEM_APP;
        }

        itemX += itemSpace;
        itemX1 = itemX + itemSize;
        if (x > itemX && x < itemX1 && y > itemY && y < itemY1) {
            return FCB_ITEM_MINE;
        }

        /*float rx = width / 2 - x;
        float ry = height / 2 - y;
        float r = (float) Math.pow(rx * rx + ry * ry, 0.5);
        if (r < height / 2) {
            return FCB_ITEM_UPLOAD;
        }*/

        return -1;
    }

    public interface OnSelectedItemChanged {
        void onChange(int itemIndex);
    }
}