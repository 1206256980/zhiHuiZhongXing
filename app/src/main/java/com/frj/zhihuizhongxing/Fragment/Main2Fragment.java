package com.frj.zhihuizhongxing.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.frj.zhihuizhongxing.Activity.TaskIntoActivity;
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
public class Main2Fragment extends BaseMvpFragment<Main2Presenter> implements Main2Contract.View {


    @BindView(R.id.ll_head)
    LinearLayout llHead;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    Unbinder unbinder;
    @BindView(R.id.tv_hint)
    TextView tvHint;
    Unbinder unbinder1;

    @Override
    protected void initView(View view) {
        presenter = new Main2Presenter(this);
        initAdapter();
        initRefresh();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main2;
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

    int page = 0;
    int length = 10;
    Adapter adapter;

    private void initAdapter() {
        try {
            recycler.setLayoutManager(new LinearLayoutManager(getContext()));
            adapter = new Adapter();
            recycler.setAdapter(adapter);
            adapter.setOnLoadMoreListener(this::loadmore, recycler);
            adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    DaiBanTaskBean.DataBean dataBean = (DaiBanTaskBean.DataBean) adapter.getData().get(position);
                    if (dataBean != null) {
                        startActivity(new Intent(getContext(), TaskIntoActivity.class).putExtra("dataBean", dataBean));
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    private void loadmore() {
        llHintShow(false);
        page++;
        presenter.currentTaskList(page, length, "accept");
    }

    private void refresh() {
        page = 0;
        llHintShow(false);
        presenter.currentTaskList(page, length, "accept");
    }

    private String Tag = "Main2Fragment";

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
            super(R.layout.item_main1_undone);
        }

        @Override
        protected void convert(ViewHolder helper, DaiBanTaskBean.DataBean item) {
            try {
                if (!TextUtils.isEmpty(item.getCreate_time())) {
                    helper.tvTime.setText(item.getCreate_time() + "");
                }
                if (!TextUtils.isEmpty(item.getName())) {
                    helper.tvName.setText(item.getName() + "");
                }
                if (!TextUtils.isEmpty(item.getPhone())) {
                    helper.tvPhone.setText(item.getPhone() + "");
                }
                if (!TextUtils.isEmpty(item.getTitle())) {
                    helper.tvTitle.setText(item.getTitle() + "");
                }
                helper.tvAccept.setVisibility(View.GONE);
                //helper.tvAccept.setOnClickListener(v -> sendAccept(item,helper.getLayoutPosition()));
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(Tag, e.getMessage());
            }
        }


        class ViewHolder extends BaseViewHolder {
            @BindView(R.id.tv_title)
            TextView tvTitle;
            @BindView(R.id.tv_name)
            TextView tvName;
            @BindView(R.id.tv_phone)
            TextView tvPhone;
            @BindView(R.id.tv_time)
            TextView tvTime;
            @BindView(R.id.tv_accept)
            TextView tvAccept;

            ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }
        }
    }


}
