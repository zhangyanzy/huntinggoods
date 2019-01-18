package com.jinxun.hunting_goods.presentation.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.AMapLocationQualityReport;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.model.BitmapDescriptor;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.maps2d.overlay.DrivingRouteOverlay;
import com.amap.api.maps2d.overlay.WalkRouteOverlay;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;
import com.jinxun.hunting_goods.R;
import com.jinxun.hunting_goods.base.BaseActivity;
import com.jinxun.hunting_goods.databinding.ActivityMapBinding;
import com.jinxun.hunting_goods.network.bean.location.Gps;
import com.jinxun.hunting_goods.util.LocationUtil;
import com.jinxun.hunting_goods.util.ToastUtil;


public class MapActivity extends BaseActivity {

    private static final String TAG = "MapActivity";
    private ProgressDialog progDialog = null;// 搜索时进度条
    private ActivityMapBinding binding;

    private boolean isStartLocation = false;
    private Intent serviceIntent = null;
    private DriveRouteResult driveRouteResult;

    private Gps gps;


    private AMapLocationClient locationClient;
    private AMapLocationClientOption locationOption;

    private AMapLocationListener mapLocationListener = new MyAMapLocationListener();

    @Override
    protected void initComponent() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_map);
        binding.setPresenter(new Presenter());
    }


    /**
     * 初始化定位
     */
    private void init() {
        //初始化client
        locationClient = new AMapLocationClient(getApplicationContext());
        locationOption = getDefaultOption();
        //设置定位参数
        locationClient.setLocationOption(locationOption);
        // 设置定位监听
        locationClient.setLocationListener(mapLocationListener);
        locationClient.startLocation();
        gps = new Gps();
        gps.setOpen(false); //默认未定位

    }

    @Override
    protected void loadData(Bundle savedInstanceState) {
        binding.map.onCreate(savedInstanceState);
        binding.map.getMap().getUiSettings().getLogoPosition();
        binding.map.getMap().moveCamera(CameraUpdateFactory.zoomTo(18));
        LocationUtil.getGps();
        AMapLocationClientOption option = new AMapLocationClientOption();
        option.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);
        if (null != locationClient) {
            locationClient.setLocationOption(option);
            locationClient.stopLocation();
            locationClient.stopLocation();
        }
        initLocationStyle();
        init();
    }

    private void initLocationStyle() {
        BitmapDescriptor descriptor = BitmapDescriptorFactory.fromResource(R.mipmap.app_name_icon);//自定义蓝点图标
        MyLocationStyle myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.myLocationIcon(descriptor);
//        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW);//连续定位、且将视角移动到地图中心点，定位蓝点跟随设备移动。（1秒1次定位）
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);//定位一次，且将视角移动到地图中心点。
        myLocationStyle.strokeColor(getResources().getColor(R.color.colorPrimary));// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(getResources().getColor(R.color.colorPrimary));// 设置圆形的填充颜色
        binding.map.getMap().setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
//        mMapView.getMap().getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。
        binding.map.getMap().setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false
    }


    /**
     * 获取GPS状态的字符串
     *
     * @param statusCode GPS状态码
     * @return
     */
    private String getGPSStatusString(int statusCode) {
        String str = "";
        switch (statusCode) {
            case AMapLocationQualityReport.GPS_STATUS_OK:
                str = "GPS状态正常";
                break;
            case AMapLocationQualityReport.GPS_STATUS_NOGPSPROVIDER:
                str = "手机中没有GPS Provider，无法进行GPS定位";
                break;
            case AMapLocationQualityReport.GPS_STATUS_OFF:
                str = "GPS关闭，建议开启GPS，提高定位质量";
                break;
            case AMapLocationQualityReport.GPS_STATUS_MODE_SAVING:
                str = "选择的定位模式中不包含GPS定位，建议选择包含GPS定位的模式，提高定位质量";
                break;
            case AMapLocationQualityReport.GPS_STATUS_NOGPSPERMISSION:
                str = "没有GPS定位权限，建议开启gps定位权限";
                break;
        }
        return str;
    }


    /**
     * 销毁定位
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    public void destroyLocation() {
        if (null != locationClient) {
            /**
             * 如果AMapLocationClient是在当前Activity实例化的，
             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
             */
            locationClient.onDestroy();
            locationClient = null;
            locationOption = null;
        }
    }

    public Gps getGps() {
        return gps;
    }

    public void setGps(Gps gps) {
        this.gps = gps;
    }


    /**
     * 默认定位参数
     *
     * @return 定位参数
     */
    private static AMapLocationClientOption getDefaultOption() {
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(300000);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        mOption.setOnceLocation(false);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setLocationCacheEnable(true); //可选，设置是否使用缓存定位，默认为true
        return mOption;
    }


    @Override
    protected void initToolbar(Bundle savedInstanceState) {

    }

    @Override
    protected void initListeners() {

    }


    @Override
    protected void onPause() {
        super.onPause();
        binding.map.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding.map.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.map.onResume();
    }

    private class MyAMapLocationListener implements AMapLocationListener {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            Log.i(TAG, "onLocationChanged: " + aMapLocation);
            if (null != aMapLocation) {
                StringBuffer sb = new StringBuffer();
                if (aMapLocation.getErrorCode() == 0) {
                    gps.setOpen(true); //定位成功
                    gps.setLongitude(aMapLocation.getLongitude()); //经度
                    gps.setLatitude(aMapLocation.getLatitude()); //纬度
                    gps.setProvince(aMapLocation.getProvince()); //省
                    gps.setCity(aMapLocation.getCity()); //市
                    gps.setCityCode(aMapLocation.getCityCode()); //城市编码
                    gps.setDistrict(aMapLocation.getDistrict()); //区
                    gps.setAdCode(aMapLocation.getAdCode()); //区域 码
                    gps.setAddress(aMapLocation.getAddress()); //地址
                } else {
                    gps.setOpen(false); //定位失败
                }
            } else {
                gps.setOpen(false); //定位失败
                Log.e("AmapError", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
            setGps(gps);
            //当前经纬度
            //latitude 纬度
            //Longitude 经度
            LatLonPoint pointFrom = new LatLonPoint(aMapLocation.getLatitude(), aMapLocation.getLongitude());
            //目的地经纬度
            LatLonPoint pointTo = new LatLonPoint(39.5427, 116.2317);
            RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(pointFrom, pointTo);

            RouteSearch.DriveRouteQuery query = new RouteSearch.DriveRouteQuery(fromAndTo, RouteSearch.WALK_DEFAULT,
                    null, null, "");
//            RouteSearch.WalkRouteQuery query = new RouteSearch.WalkRouteQuery(fromAndTo, RouteSearch.WALK_DEFAULT);
            RouteSearch routeSearch = new RouteSearch(MapActivity.this);
            routeSearch.calculateDriveRouteAsyn(query);
            showProgressDialog();
            routeSearch.setRouteSearchListener(new RouteSearch.OnRouteSearchListener() {
                @Override
                public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {
                    Log.i(TAG, "onBusRouteSearched: " + busRouteResult);

                }

                @Override
                public void onDriveRouteSearched(DriveRouteResult result, int i) {
                    dissmissProgressDialog();
                    if (i == 1000) {
                        if (result != null && result.getPaths() != null && result.getPaths().size() > 0) {
                            driveRouteResult = result;
                            DrivePath drivePath = driveRouteResult.getPaths().get(0);
                            binding.map.getMap().clear();
                            DrivingRouteOverlay drivingRouteOverlay = new DrivingRouteOverlay(MapActivity.this, binding.map.getMap(), drivePath, driveRouteResult.getStartPos(), driveRouteResult.getTargetPos());
                            drivingRouteOverlay.removeFromMap();
                            drivingRouteOverlay.addToMap();
                            drivingRouteOverlay.zoomToSpan();
                        } else {
                            ToastUtil.showShortToast(getApplicationContext(), "路径规划失败");
                        }
                    } else if (i == 27) {
                        ToastUtil.showShortToast(getApplicationContext(), "网络异常");
                    } else if (i == 32) {
                        ToastUtil.showShortToast(getApplicationContext(),"key错误");
                    }

                }

                @Override
                public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) {
                    dissmissProgressDialog();
                    Log.i(TAG, "onWalkRouteSearched: " + i);
                    if (i == 1000) {
                        WalkRouteOverlay walkRouteOverlay = new WalkRouteOverlay(getApplicationContext(), binding.map.getMap(), walkRouteResult.getPaths().get(1), walkRouteResult.getStartPos(), walkRouteResult.getTargetPos());
                        walkRouteOverlay.setNodeIconVisibility(false);
                        walkRouteOverlay.removeFromMap();
                        walkRouteOverlay.addToMap();
                        walkRouteOverlay.zoomToSpan();

                    }
                }

                @Override
                public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {
                    Log.i(TAG, "onRideRouteSearched: " + rideRouteResult);

                }
            });

        }
    }


    /**
     * 显示进度框
     */
    private void showProgressDialog() {
        if (progDialog == null)
            progDialog = new ProgressDialog(this);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setIndeterminate(false);
        progDialog.setCancelable(true);
        progDialog.setMessage("正在搜索");
        progDialog.show();
    }

    /**
     * 隐藏进度框
     */
    private void dissmissProgressDialog() {
        if (progDialog != null) {
            progDialog.dismiss();
        }
    }


    public class Presenter {
        public void onClick(View view) {
            switch (view.getId()) {
                default:
                    break;
            }
        }
    }
}
