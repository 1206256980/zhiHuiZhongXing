package com.frj.zhihuizhongxing;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.AMapNaviViewOptions;
import com.amap.api.navi.enums.NaviType;
import com.amap.api.navi.model.NaviLatLng;
import com.frj.zhihuizhongxing.Activity.Main2ArriveActivity;
import com.frj.zhihuizhongxing.Base.MapActivity;

import java.util.ArrayList;
import java.util.List;

public class NavMapActivity extends MapActivity {
    MapView mMapView = null;
    //初始化地图控制器对象
    AMap aMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_map);
        mAMapNaviView = (AMapNaviView) findViewById(R.id.navi_view);
        mAMapNaviView.onCreate(savedInstanceState);
        mAMapNaviView.setAMapNaviViewListener(this);
        findViewById(R.id.tv_affirm).setOnClickListener(v -> startActivity(new Intent(this, Main2ArriveActivity.class)));
        getIntentData();
        startLocaion();
    }

    double terminusLatitude;//终点经
    double terminusLongitude;//终点

    private void getIntentData() {
        try {
            terminusLatitude = Double.parseDouble(getIntent().getStringExtra("latitude"));
            terminusLongitude = Double.parseDouble(getIntent().getStringExtra("longitude"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 获取定位
     */

    private AMapLocationClient mLocationClient;
    private AMapLocationClientOption mLocationOption;

    public void startLocaion() {

        mLocationClient = new AMapLocationClient(this);
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

    private String TAG = "NavMapActivity";
    private double latitude;//当前位置经度
    private double longitude;//当前位置纬度
    private int hint = 0;
    protected List<NaviLatLng> mWayPointList = new ArrayList<NaviLatLng>();

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
                    //Toast.makeText(NavMapActivity.this, "定位成功", Toast.LENGTH_LONG).show();
                    latitude = amapLocation.getLatitude();
                    longitude = amapLocation.getLongitude();

                    /**
                     * 方法: int strategy=mAMapNavi.strategyConvert(congestion, avoidhightspeed, cost, hightspeed, multipleroute); 参数:
                     *
                     * @congestion 躲避拥堵
                     * @avoidhightspeed 不走高速
                     * @cost 避免收费
                     * @hightspeed 高速优先
                     * @multipleroute 多路径
                     *
                     *  说明: 以上参数都是boolean类型，其中multipleroute参数表示是否多条路线，如果为true则此策略会算出多条路线。
                     *  注意: 不走高速与高速优先不能同时为true 高速优先与避免收费不能同时为true
                     */
                    int strategy = 0;
                    try {
                        //再次强调，最后一个参数为true时代表多路径，否则代表单路径
                        strategy = mAMapNavi.strategyConvert(true, false, false, false, false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (terminusLatitude > 0 && terminusLongitude > 0) {
                        NaviLatLng mStartLatlng = new NaviLatLng(latitude, longitude);
                        NaviLatLng mEndLatlng = new NaviLatLng(terminusLatitude, terminusLongitude);
                        List<NaviLatLng> sList = new ArrayList<NaviLatLng>();
                        sList.add(mStartLatlng);
                        List<NaviLatLng> eList = new ArrayList<NaviLatLng>();
                        eList.add(mEndLatlng);
                        mAMapNavi.calculateDriveRoute(sList, eList, mWayPointList, strategy);
                        //mAMapNavi.calculateRideRoute(new NaviLatLng(latitude, longitude), new NaviLatLng(terminusLatitude, terminusLongitude));
                    } else {
                        Toast.makeText(NavMapActivity.this, "出现异常", Toast.LENGTH_SHORT).show();
                    }
                    /*presenter.addCoordinate(amapLocation.getLatitude() + "," + amapLocation.getLongitude());*/
                } else {
                    //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                    if (hint == 0) {
                        Toast.makeText(NavMapActivity.this, "定位失败,请检查权限", Toast.LENGTH_LONG).show();
                        hint++;
                    }
                }
            }
        }
    };


    @Override
    public void onInitNaviSuccess() {
        super.onInitNaviSuccess();
        /**
         * 方法: int strategy=mAMapNavi.strategyConvert(congestion, avoidhightspeed, cost, hightspeed, multipleroute); 参数:
         *
         * @congestion 躲避拥堵
         * @avoidhightspeed 不走高速
         * @cost 避免收费
         * @hightspeed 高速优先
         * @multipleroute 多路径
         *
         *  说明: 以上参数都是boolean类型，其中multipleroute参数表示是否多条路线，如果为true则此策略会算出多条路线。
         *  注意: 不走高速与高速优先不能同时为true 高速优先与避免收费不能同时为true
         *//*
        int strategy = 0;
        try {
            //再次强调，最后一个参数为true时代表多路径，否则代表单路径
            strategy = mAMapNavi.strategyConvert(true, false, false, false, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mAMapNavi.calculateDriveRoute(sList, eList, mWayPointList, strategy);*/

    }

    @Override
    public void onCalculateRouteSuccess(int[] ids) {
        super.onCalculateRouteSuccess(ids);
        mAMapNavi.startNavi(NaviType.GPS);
    }
}
