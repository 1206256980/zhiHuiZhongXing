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
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.frj.zhihuizhongxing.Base.BaseMvpFragment;
import com.frj.zhihuizhongxing.Contract.Main1undoneContract;
import com.frj.zhihuizhongxing.CurrentUser;
import com.frj.zhihuizhongxing.Data.BaseBean;
import com.frj.zhihuizhongxing.Data.DaiBanTaskBean;
import com.frj.zhihuizhongxing.NavMapActivity;
import com.frj.zhihuizhongxing.Presenter.Main1undonePresenter;
import com.frj.zhihuizhongxing.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class Main1undoneFragment extends BaseMvpFragment<Main1undonePresenter> implements Main1undoneContract.View {


    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.tv_hint)
    TextView tvHint;

    public Main1undoneFragment() {
        // Required empty public constructor
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main1undone;
    }

    @Override
    protected void initView(View view) {
        presenter = new Main1undonePresenter(this);
        initAdapter();
        initRefresh();
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
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new Adapter();
        adapter.setOnLoadMoreListener(this::loadMore, recycler);
        adapter.addFooterView(LayoutInflater.from(getContext()).inflate(R.layout.item_task_into_black, (ViewGroup) recycler.getParent(), false));
        recycler.setAdapter(adapter);
        /*List list = new ArrayList();
        list.add(null);
        adapter.setNewData(list);*/
    }

    public void loadMore() {
        page++;
        llHintShow(false);
        presenter.accident(page, length, "distribute");
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    private void refresh() {
        page = 0;
        llHintShow(false);
        presenter.accident(page, length, "distribute");
    }

    @Override
    public void onSuccessAccident(DaiBanTaskBean bean) {
        refreshLayout.setRefreshing(false);
        if (bean.getIsErr() == 0) {
            List<DaiBanTaskBean.DataBean> resultDatas = bean.getData();
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
            toast(bean.getMsg());
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

    @Override
    public void onErrorAccident(Throwable throwable) {
        refreshLayout.setRefreshing(false);
        throwable.printStackTrace();
    }

    @Override
    public void onSuccessAccidentSendmsg(BaseBean baseBean) {
        try {
            if (baseBean.getIsErr() == 0) {
                toast("接受成功");
                adapter.remove(postition);
                /*MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.setSelectedItem(1);
                mainActivity.fastcat.selectedItem = 1;
                mainActivity.fastcat.invalidate();*/
                startActivity(new Intent(getContext(), NavMapActivity.class)
                        .putExtra("latitude", dataBean.getLatitude())
                        .putExtra("longitude", dataBean.getLongitude()));
            } else {
                toast(baseBean.getMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(Tag, e.getMessage());
        }

    }

    @Override
    public void onErroraccidentSendmsg(Throwable throwable) {
        throwable.printStackTrace();
        Log.e(Tag, throwable.getMessage());
    }


    private String Tag = "Main1undoneFragment";
    DaiBanTaskBean.DataBean dataBean;
    int postition;


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
                helper.tvAccept.setOnClickListener(v -> sendAccept(item, helper.getLayoutPosition()));
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


    /**
     * 接受任务
     *
     * @param item
     */
    private void sendAccept(DaiBanTaskBean.DataBean item, int postition) {
        this.postition = postition;
        this.dataBean = item;
        presenter.accidentSendmsg(item.getDam_id(), "accept", CurrentUser.getLatitude(), CurrentUser.getLongitude());
    }


    /**
     * 获取定位
     */

    private AMapLocationClient mLocationClient;
    private AMapLocationClientOption mLocationOption;

    public void startLocaion() {

        mLocationClient = new AMapLocationClient(getContext());
        mLocationClient.setLocationListener(mLocationListener);

        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //获取一次定位结果：
        //该方法默认为false。
        mLocationOption.setOnceLocation(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);

        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    private String TAG = "Main1undoneFragment";
    //double latitude ;//经度
    //double longitude ;//纬度

    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    //定位成功回调信息，设置相关消息
                    Log.i(TAG, "当前定位结果来源-----" + amapLocation.getLocationType());//获取当前定位结果来源，如网络定位结果，详见定位类型表
                    Log.i(TAG, "纬度 ----------------" + amapLocation.getLatitude());//获取纬度
                    Log.i(TAG, "经度-----------------" + amapLocation.getLongitude());//获取经度
                    Log.i(TAG, "精度信息-------------" + amapLocation.getAccuracy());//获取精度信息
                    Log.i(TAG, "地址-----------------" + amapLocation.getAddress());//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                    Log.i(TAG, "国家信息-------------" + amapLocation.getCountry());//国家信息
                    Log.i(TAG, "省信息---------------" + amapLocation.getProvince());//省信息
                    Log.i(TAG, "城市信息-------------" + amapLocation.getCity());//城市信息
                    Log.i(TAG, "城区信息-------------" + amapLocation.getDistrict());//城区信息
                    Log.i(TAG, "街道信息-------------" + amapLocation.getStreet());//街道信息
                    Log.i(TAG, "街道门牌号信息-------" + amapLocation.getStreetNum());//街道门牌号信息
                    Log.i(TAG, "城市编码-------------" + amapLocation.getCityCode());//城市编码
                    Log.i(TAG, "地区编码-------------" + amapLocation.getAdCode());//地区编码
                    Log.i(TAG, "当前定位点的信息-----" + amapLocation.getAoiName());//获取当前定位点的AOI信息
                    // Toast.makeText(getContext(), "定位成功", Toast.LENGTH_LONG).show();
                    /*latitude =amapLocation.getLatitude();
                    longitude = amapLocation.getLongitude();*/
                    /*presenter.addCoordinate(amapLocation.getLatitude() + "," + amapLocation.getLongitude());*/
                } else {
                    //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                    Toast.makeText(getContext(), "定位失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    };
}
