package com.frj.zhihuizhongxing.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.model.NaviLatLng;
import com.frj.zhihuizhongxing.Base.BaseMvpActivity;
import com.frj.zhihuizhongxing.Presenter.MainPresenter;
import com.frj.zhihuizhongxing.R;
import com.frj.zhihuizhongxing.NavMapActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//AMapNaviViewListener
public class Main1Activity extends BaseMvpActivity<MainPresenter>  {


    @BindView(R.id.mapView)
    MapView mapView;
    @BindView(R.id.btn)
    Button btn;
    private AMap aMap;
    private TextView offlineMap, record;
    private AMapLocationClient mLocationClient;
    private AMapLocationClientOption mLocationOption;
    private LocationSource.OnLocationChangedListener mListener;
    private AMapLocation privLocation;
    private double distance;

    //算路终点坐标
    protected NaviLatLng mEndLatlng = new NaviLatLng(22.652, 113.966);
    //算路起点坐标
    protected NaviLatLng mStartLatlng = new NaviLatLng(22.540332, 113.939961);
    //存储算路起点的列表
    protected final List<NaviLatLng> sList = new ArrayList<NaviLatLng>();
    //存储算路终点的列表
    protected final List<NaviLatLng> eList = new ArrayList<NaviLatLng>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_main1;
    }

    @Override
    public void initView() {
        //startActivity(new Intent(this,NavMapActivity.class));
        /*presenter = new MainPresenter(this);
        Observable.interval(10, TimeUnit.SECONDS)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.d("提示", aLong.intValue() + "///");//打印时间
                        startLocaion();
                    }
                });*/


    }

    AMapNavi mAMapNavi;

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        mapView.onCreate(savedInstanceState);// 调用地图所必须重写

        if (aMap == null) {
            aMap = mapView.getMap();
        }
        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
//aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。


        List<LatLng> latLngs = new ArrayList<LatLng>();
        latLngs.add(new LatLng(39.999391, 116.135972));
        latLngs.add(new LatLng(25.830174, 116.057694));
        latLngs.add(new LatLng(39.900430, 110.265061));
        latLngs.add(new LatLng(39.955192, 112.140092));
        /*aMap.addPolyline(new PolylineOptions().
                addAll(latLngs).width(10).color(Color.argb(255, 1, 1, 1)));*/
        aMap.addPolyline((new PolylineOptions())
                .addAll(latLngs)
                .width(10)
                .setDottedLine(false) //关闭虚线
                .color(Color.RED));



    }

    /**
     * 必须重写的方法
     */
    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    /**
     * 必须重写的方法
     */
    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }

    /**
     * 必须重写的方法
     */
    @Override
    protected void onResume() {
        mapView.onResume();
        super.onResume();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mapView.onSaveInstanceState(outState);
    }

    /*@Override
    public void onSuccessCoordinate(MainBean mainBean) {
    }*/

    @Override
    public void onError(Throwable throwable) {

    }

    private static final String TAG = "Main1Activity";


    /**
     * 获取定位
     */


    public void startLocaion() {

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
                    Toast.makeText(Main1Activity.this, "定位成功", Toast.LENGTH_LONG).show();

                    presenter.addCoordinate(amapLocation.getLatitude() + "," + amapLocation.getLongitude());
                } else {
                    //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                    Toast.makeText(Main1Activity.this, "定位失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }



    @OnClick(R.id.btn)
    public void onViewClicked() {
        startActivity(new Intent(this, NavMapActivity.class));
    }
}
