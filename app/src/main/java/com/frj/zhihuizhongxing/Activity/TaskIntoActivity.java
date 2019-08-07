package com.frj.zhihuizhongxing.Activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.frj.zhihuizhongxing.Base.BaseMvpActivity;
import com.frj.zhihuizhongxing.Contract.TaskIntoContract;
import com.frj.zhihuizhongxing.Data.DaiBanTaskBean;
import com.frj.zhihuizhongxing.Data.TaskIntoBean;
import com.frj.zhihuizhongxing.NavMapActivity;
import com.frj.zhihuizhongxing.Presenter.TaskIntoPresenter;
import com.frj.zhihuizhongxing.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class TaskIntoActivity extends BaseMvpActivity<TaskIntoPresenter> implements TaskIntoContract.View {

    @BindView(R.id.ll_head)
    LinearLayout llHead;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_nav)
    ImageView ivNav;

    @Override
    public int getLayoutId() {
        return R.layout.activity_task_into;
    }

    @Override
    public void initView() {
        presenter = new TaskIntoPresenter(this);
        ivBack.setOnClickListener(v -> finish());
        initAdapter();
        getIntentData();
    }

    @Override
    public void onError(Throwable throwable) {

    }

    DaiBanTaskBean.DataBean dataBean;

    private void getIntentData() {
        try{
            dataBean = (DaiBanTaskBean.DataBean) getIntent().getSerializableExtra("dataBean");
            tvTime.setText(StringToNianYue(dataBean.getCreate_time()) + "");
            tvTitle.setText(dataBean.getTitle() + "");
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    Adapter adapter;

    private void initAdapter() {
        recycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(new ArrayList<>());
        adapter.addFooterView(LayoutInflater.from(this).inflate(R.layout.item_task_into_black, (ViewGroup) recycler.getParent(), false));
        recycler.setAdapter(adapter);
    }

    private String TAG = "TaskIntoActivity";

    @Override
    public void onSuccessAccidentInfo(TaskIntoBean bean) {
        try {
            if (bean.getIsErr() == 0) {
                List<TaskIntoBean.DataBean> resultDatas = bean.getData();
                for (TaskIntoBean.DataBean dataBean :
                        resultDatas) {
                    switch (dataBean.getType()) {
                        case "accept":
                            dataBean.setItemType(1);
                            break;
                        case "colockin":
                            dataBean.setItemType(2);
                            break;
                        case "waitcheck":
                            dataBean.setItemType(3);
                            break;
                        case "complete":
                            dataBean.setItemType(4);
                            break;
                        default:
                            dataBean.setItemType(5);
                            break;
                    }
                }
                adapter.setNewData(resultDatas);
            } else {
                toast(bean.getMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        }
    }

    private String StringToNianYue(String time) throws ParseException {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date;
        date = format.parse(time);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        String s = format1.format(date);
        return s;

    }

    private String StringToTime(String time) throws ParseException {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date;
        date = format.parse(time);
        SimpleDateFormat format1 = new SimpleDateFormat("HH:mm");
        String s = format1.format(date);
        return s;

    }

    @Override
    public void onErrorAccidentInfo(Throwable throwable) {
        throwable.printStackTrace();
    }


    @OnClick(R.id.iv_nav)
    public void onViewClicked() {
        startActivity(new Intent(this, NavMapActivity.class)
                .putExtra("latitude", dataBean.getLatitude())
                .putExtra("longitude", dataBean.getLongitude()));
    }


    class Adapter extends BaseMultiItemQuickAdapter<TaskIntoBean.DataBean, BaseViewHolder> {


        @BindView(R.id.view)
        View view;

        /**
         * Same as QuickAdapter#QuickAdapter(Context,int) but with
         * some initialization data.
         *
         * @param data A new list is created out of this one to avoid mutable list
         */
        public Adapter(List<TaskIntoBean.DataBean> data) {
            super(data);
            addItemType(1, R.layout.item_main2_accept);//接受
            addItemType(2, R.layout.item_main2_colockin);//抵达
            addItemType(3, R.layout.item_main2_waitcheck);//等待
            addItemType(4, R.layout.item_main2_complete);//完成
            addItemType(5, R.layout.item_main2_complete);//其它
        }


        @Override
        protected void convert(BaseViewHolder helper, TaskIntoBean.DataBean item) {
            //adapterInit(helper);
            switch (item.getItemType()) {
                case 1:
                    accept(helper, item);
                    break;
                case 2:
                    colockin(helper, item);
                    break;
                case 3:
                    waitcheck(helper, item);
                    break;
                case 4:
                    complete(helper, item);
                    break;
                default:
                    break;
            }
        }


        private void accept(BaseViewHolder helper, TaskIntoBean.DataBean item) {
            try{
                helper.setText(R.id.tv_stauts, "已接收任务");
                int lastPosition = getData().size() - 1;
                helper.getView(R.id.ll_finally).setVisibility(helper.getLayoutPosition() == lastPosition ? View.VISIBLE : View.GONE);
                helper.setText(R.id.tv_task_end, "抵达现场报告");
                helper.getView(R.id.tv_task_end).setOnClickListener(v -> arrive());

                if (!TextUtils.isEmpty(item.getCreate_time())) {
                    helper.setText(R.id.tv_time, StringToTime(item.getCreate_time()) + "");
                }
            }catch (Exception e){
                e.printStackTrace();
            }


        }

        private void colockin(BaseViewHolder helper, TaskIntoBean.DataBean item) {
            try {

                helper.setText(R.id.tv_stauts, "到达任务地点");
                if (!TextUtils.isEmpty(item.getMessage())) {
                    helper.setText(R.id.tv_center, item.getMessage() + "");
                }
                //helper.tvCenter.setText(item.getMessage() + "");
                int lastPosition = getData().size() - 1;
                helper.getView(R.id.ll_finally).setVisibility(helper.getLayoutPosition() == lastPosition ? View.VISIBLE : View.GONE);
                String pics = item.getPics();
                String[] images = null;
                if (!TextUtils.isEmpty(pics)) {
                    images = pics.split(",");
                }
                ((LinearLayout) helper.getView(R.id.ll_img)).removeAllViews();
                if (((LinearLayout) helper.getView(R.id.ll_img)).getChildCount() <= 0) {
                    if (images != null && images.length > 0) {
                        for (int i = 0; i < images.length; i++) {
                            Log.e(TAG, images[i]);
                            ImageView imageView = (ImageView) LayoutInflater.from(TaskIntoActivity.this).inflate(R.layout.layout_image, helper.getView(R.id.ll_img), false);
                            RequestOptions requestOptions = new RequestOptions();
                            requestOptions.centerCrop();

                            Glide.with(TaskIntoActivity.this).load(images[i]).apply(requestOptions).into(imageView);
                            ((LinearLayout) helper.getView(R.id.ll_img)).addView(imageView);
                        }
                    }
                }


                helper.setText(R.id.tv_task_end, "任务结束报告");
                helper.getView(R.id.tv_task_end).setOnClickListener(v -> complete());

                if (!TextUtils.isEmpty(item.getCreate_time())) {
                    helper.setText(R.id.tv_time, StringToTime(item.getCreate_time())  + "");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void waitcheck(BaseViewHolder helper, TaskIntoBean.DataBean item) {
            try {
                helper.setText(R.id.tv_stauts, "发起任务完成申请");
                if (!TextUtils.isEmpty(item.getMessage())) {
                    helper.setText(R.id.tv_center, item.getMessage() + "");
                }
                int lastPosition = getData().size() - 1;
                helper.getView(R.id.ll_finally).setVisibility(helper.getLayoutPosition() == lastPosition ? View.VISIBLE : View.GONE);

                String pics = item.getPics();
                String[] images = null;
                if (!TextUtils.isEmpty(pics)) {
                    images = pics.split(",");
                }
                ((LinearLayout) helper.getView(R.id.ll_img)).removeAllViews();
                if (((LinearLayout) helper.getView(R.id.ll_img)).getChildCount() <= 0) {
                    if (images != null && images.length > 0) {
                        for (int i = 0; i < images.length; i++) {
                            Log.e(TAG, images[i]);
                            ImageView imageView = (ImageView) LayoutInflater.from(TaskIntoActivity.this).inflate(R.layout.layout_image, helper.getView(R.id.ll_img), false);
                            RequestOptions requestOptions = new RequestOptions();
                            requestOptions.centerCrop();

                            Glide.with(TaskIntoActivity.this).load(images[i]).apply(requestOptions).into(imageView);
                            ((LinearLayout) helper.getView(R.id.ll_img)).addView(imageView);
                            //helper.llImg.addView(imageView);
                        }
                    }
                }

                if (!TextUtils.isEmpty(item.getCreate_time())) {
                    helper.setText(R.id.tv_time, StringToTime(item.getCreate_time()) + "");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void complete(BaseViewHolder helper, TaskIntoBean.DataBean item) {
            try{

                helper.setText(R.id.tv_stauts, "申请通过,任务完成");

                if (!TextUtils.isEmpty(item.getCreate_time())) {
                    helper.setText(R.id.tv_time, StringToTime(item.getCreate_time()) + "");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        /**
         * 进入抵达页面
         */
        private void arrive() {
            startActivity(new Intent(TaskIntoActivity.this, Main2ArriveActivity.class)
                    .putExtra("dataBean", dataBean));
        }

        /**
         * 进入完成页面
         */
        private void complete() {
            startActivity(new Intent(TaskIntoActivity.this, TaskCompleteActivity.class)
                    .putExtra("dataBean", dataBean));
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (dataBean != null) {
            presenter.accidentInfo(dataBean.getDam_id());
        }
    }
}
