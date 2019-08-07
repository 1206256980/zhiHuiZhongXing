package com.frj.zhihuizhongxing.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.frj.zhihuizhongxing.Base.BaseMvpFragment;
import com.frj.zhihuizhongxing.Contract.Main2Contract;
import com.frj.zhihuizhongxing.Data.DaiBanTaskBean;
import com.frj.zhihuizhongxing.Presenter.Main2Presenter;
import com.frj.zhihuizhongxing.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class Main3Fragment extends BaseMvpFragment<Main2Presenter> implements Main2Contract.View {


    @BindView(R.id.recycler)
    RecyclerView recycler;
    Unbinder unbinder;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.tv_hint)
    TextView tvHint;
    Unbinder unbinder1;

    public Main3Fragment() {
        // Required empty public constructor
    }


    int page = 0;
    int length = 10;

    @Override
    protected void initView(View view) {
        presenter = new Main2Presenter(this);
        initAdapter();
        initRefresh();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main3;
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
        refreshLayout.setRefreshing(false);
    }

    private void initRefresh() {
        refreshLayout.setOnRefreshListener(this::refresh);
        refreshLayout.setRefreshing(true);
        refresh();
    }


    private void refresh() {
        llHintShow(false);
        page = 0;
        presenter.currentTaskList(page, length, "complete");
    }

    Adapter adapter;

    private void initAdapter() {
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new Adapter();
        adapter.setOnLoadMoreListener(this::loadMore, recycler);
        recycler.setAdapter(adapter);
    }

    private void loadMore() {
        llHintShow(false);
        page++;
        presenter.currentTaskList(page, length, "complete");
    }

    /**
     * 提示是否显示
     *
     * @param isShow
     */
    public void llHintShow(boolean isShow) {
        recycler.setVisibility(isShow ? View.GONE : View.VISIBLE);
        tvHint.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onSuccessCurrentTaskList(DaiBanTaskBean daiBanTaskBean) {
        refreshLayout.setRefreshing(false);
        if (daiBanTaskBean.getIsErr() == 0) {
            List<DaiBanTaskBean.DataBean> resultDatas = daiBanTaskBean.getData();
            if (page == 0) {

                adapter.setNewData(resultDatas);
                if (resultDatas.size() <= 0) {
                    llHintShow(true);
                }
            } else {
                adapter.addData(resultDatas);
            }
            if (resultDatas.size() >= length) {
                adapter.loadMoreComplete();
            } else {
                adapter.loadMoreEnd();
            }
        } else {
            toast(daiBanTaskBean.getMsg());
        }
    }



    class Adapter extends BaseQuickAdapter<DaiBanTaskBean.DataBean, Adapter.ViewHolder> {


        public Adapter() {
            super(R.layout.item_main3);
        }

        @Override
        protected void convert(ViewHolder helper, DaiBanTaskBean.DataBean item) {

            // helper.llMain.addView(LayoutInflater.from(getContext()).inflate(R.layout.item_main3_item, null));
            if (!TextUtils.isEmpty(item.getCreate_time())) {
                helper.tvTime.setText(item.getCreate_time() + "");
            }
            helper.tvStauts.setText("已完成");

            if (!TextUtils.isEmpty(item.getTitle())) {
                helper.tvRoad.setText(item.getTitle() + "");
            }
        }

        class ViewHolder extends BaseViewHolder {
            @BindView(R.id.tv_time)
            TextView tvTime;
            @BindView(R.id.tv_road)
            TextView tvRoad;
            @BindView(R.id.tv_stauts)
            TextView tvStauts;
            @BindView(R.id.ll_main)
            LinearLayout llMain;

            ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }
        }
    }

}
