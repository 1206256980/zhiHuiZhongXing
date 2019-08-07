package com.frj.zhihuizhongxing.Fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.frj.zhihuizhongxing.Activity.SelectMapActivity;
import com.frj.zhihuizhongxing.Base.BaseMvpFragment;
import com.frj.zhihuizhongxing.Contract.Main1FinishContract;
import com.frj.zhihuizhongxing.CurrentUser;
import com.frj.zhihuizhongxing.Data.BaseBean;
import com.frj.zhihuizhongxing.Presenter.Main1FinishPresenter;
import com.frj.zhihuizhongxing.R;
import com.frj.zhihuizhongxing.Tools.DiaLogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class Main1FinishFragment extends BaseMvpFragment<Main1FinishPresenter> implements Main1FinishContract.View, GeocodeSearch.OnGeocodeSearchListener {


    @BindView(R.id.iv_location)
    ImageView ivLocation;
    @BindView(R.id.et_into)
    EditText etInto;
    @BindView(R.id.et_address)
    TextView etAddress;
    @BindView(R.id.ll_location)
    LinearLayout llLocation;
    /*@BindView(R.id.et_linkman)
    EditText etLinkman;
    @BindView(R.id.et_phone)
    EditText etPhone;*/
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    Main1Fragment main1Fragment;

    public Main1FinishFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public Main1FinishFragment(Main1Fragment main1Fragment) {
        // Required empty public constructor
        this.main1Fragment = main1Fragment;
    }


    GeocodeSearch geocodeSearch;

    @Override
    protected void initView(View view) {
        presenter = new Main1FinishPresenter(this);
        geocodeSearch = new GeocodeSearch(getContext());
        geocodeSearch.setOnGeocodeSearchListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main1_finish;
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    final int LOCATION = 1;

    @OnClick({R.id.ll_location, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_location:
                startActivityForResult(new Intent(getContext(), SelectMapActivity.class), LOCATION);
                break;
            case R.id.tv_submit:
                if (!validateInput(getInto(), getAddress())) {
                    return;
                }
                DiaLogUtils.getInstance().show(getContext(), false);

                Log.d("提示",latitude+"//"+longtitude);
                presenter.createAccident(simpleAddress, getInto(), latitude, longtitude);
                break;
        }
    }

    private void getAddressByLatlng(LatLng latLng) {
        //逆地理编码查询条件：逆地理编码查询的地理坐标点、查询范围、坐标类型。
        LatLonPoint latLonPoint = new LatLonPoint(latLng.latitude, latLng.longitude);
        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 500f, GeocodeSearch.AMAP);
        //异步查询
        geocodeSearch.getFromLocationAsyn(query);
    }

    Double latitude;//纬度
    Double longtitude;//经度

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == LOCATION) {
                latitude = data.getDoubleExtra("latitude", 0f);
                longtitude = data.getDoubleExtra("longtitude", 0f);
                getAddressByLatlng(new LatLng(latitude, longtitude));
            }
        }
    }

    String simpleAddress="";
    private String TAG = "Main1FinishFragment";

    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
        RegeocodeAddress regeocodeAddress = regeocodeResult.getRegeocodeAddress();
        String formatAddress = regeocodeAddress.getFormatAddress();
        simpleAddress = formatAddress.substring(9);
        Log.e(TAG, formatAddress + "///" + simpleAddress);
        etAddress.setText(simpleAddress + "");
        //tvChoseAddress.setText("查询经纬度对应详细地址：\n" + simpleAddress);

    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }


    private boolean validateInput(String msg, String address) {
        if (TextUtils.isEmpty(msg)) {
            toast("请输入案发信息");
            return false;
        } else if (TextUtils.isEmpty(address)) {
            toast("请选择地址");
            return false;
        }
        return true;
    }


    @Override
    public void onSuccessCreateAccident(BaseBean baseBean) {

        DiaLogUtils.getInstance().cancel();
        if (baseBean.getIsErr() == 0) {
            toast("创建成功");
            if (main1Fragment != null) {
                main1Fragment.setViewPageItem(0);
                etAddress.setText("");
                etInto.setText("");
            }
        } else {
            toast(baseBean.getMsg());
        }
    }

    @Override
    public void onErrorCreateAccident(Throwable throwable) {
        throwable.printStackTrace();
        DiaLogUtils.getInstance().cancel();
        toast("出现异常");
    }

    public String getInto() {
        return etInto.getText().toString();
    }

    public String getAddress() {
        return etAddress.getText().toString();
    }

    /*public String getLinkMan() {
        return etLinkman.getText().toString();
    }

    public String getPhone() {
        return etPhone.getText().toString();
    }*/
}
