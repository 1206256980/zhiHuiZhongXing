package com.frj.zhihuizhongxing.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.frj.zhihuizhongxing.Base.BaseMvpActivity;
import com.frj.zhihuizhongxing.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectMapActivity extends BaseMvpActivity implements AMap.OnMapClickListener, GeocodeSearch.OnGeocodeSearchListener {


    @BindView(R.id.btn_location)
    Button btnLocation;
    @BindView(R.id.map)
    MapView map;

    @Override
    public int getLayoutId() {
        return R.layout.activity_select_map;
    }

    @Override
    public void initView() {

    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        map.onCreate(savedInstanceState);
        init();
    }

    private AMap aMap = null;
    double latitude;
    double longtitude;
    String TAG = "SelectMapActivity";

    @OnClick(R.id.btn_location)
    public void onViewClicked() {
        Log.e("提示定位", latitude + "///" + longtitude);
        setResult(RESULT_OK,new Intent().putExtra("latitude",latitude)
                .putExtra("longtitude",longtitude));
        finish();
    }

    GeocodeSearch geocodeSearch;

    private void init() {
        if (aMap == null) {
            aMap = map.getMap();
            //地理搜索类
            geocodeSearch = new GeocodeSearch(this);
            geocodeSearch.setOnGeocodeSearchListener(this);
            //setUpMap();
            startLocaion();
        }
    }

    private void setUpMap() {
        LatLng latLng = new LatLng(39.92448, 116.518295);
        aMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));//设置中心点
        aMap.moveCamera(CameraUpdateFactory.zoomTo(18)); // 设置地图可视缩放大小
        aMap.setOnMapClickListener(this);
    }

    /**
     * 获取定位
     */


    private AMapLocationClient mLocationClient;
    private AMapLocationClientOption mLocationOption;

    public void startLocaion() {

        aMap.showIndoorMap(true);
        mLocationClient = new AMapLocationClient(getApplicationContext());
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
                    LatLng latLng = new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude());
                    aMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));//设置中心点
                    aMap.moveCamera(CameraUpdateFactory.zoomTo(18)); // 设置地图可视缩放大小
                    aMap.setOnMapClickListener(SelectMapActivity.this);
                    onMapClick(latLng);
                    //Toast.makeText(SelectMapActivity.this, "定位成功", Toast.LENGTH_LONG).show();

                    //presenter.addCoordinate(amapLocation.getLatitude() + "," + amapLocation.getLongitude());
                } else {
                    //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                    Toast.makeText(SelectMapActivity.this, "定位失败,请打开GPS", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    @Override
    protected void onDestroy() {
        if (map != null) {
            map.onDestroy();
        }
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图

    }

    @Override
    protected void onResume() {
        if (map != null) {
            map.onResume();
        }
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
    }

    @Override
    protected void onPause() {
        if (map != null) {
            map.onPause();
        }
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (map != null) {
            map.onSaveInstanceState(outState);
        }
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
    }

    @Override
    public void onMapClick(LatLng latLng) {
        aMap.clear();
        latitude = latLng.latitude;
        longtitude = latLng.longitude;
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ico_select_location));
        markerOptions.position(latLng);
        getAddressByLatlng(latLng);
        aMap.addMarker(markerOptions);
        aMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));
    }

    private void getAddressByLatlng(LatLng latLng) {
        //逆地理编码查询条件：逆地理编码查询的地理坐标点、查询范围、坐标类型。
        LatLonPoint latLonPoint = new LatLonPoint(latLng.latitude, latLng.longitude);
        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 500f, GeocodeSearch.AMAP);
        //异步查询
        geocodeSearch.getFromLocationAsyn(query);
    }

    /**
     * 得到逆地理编码异步查询结果
     */
    String simpleAddress;

    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
        RegeocodeAddress regeocodeAddress = regeocodeResult.getRegeocodeAddress();
        String formatAddress = regeocodeAddress.getFormatAddress();
        simpleAddress = formatAddress.substring(9);
        //Log.e("提示",formatAddress+"///"+simpleAddress);
        //tvChoseAddress.setText("查询经纬度对应详细地址：\n" + simpleAddress);
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }

}
