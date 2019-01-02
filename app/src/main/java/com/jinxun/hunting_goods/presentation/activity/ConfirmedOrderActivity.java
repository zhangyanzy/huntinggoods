package com.jinxun.hunting_goods.presentation.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.CollapsibleActionView;
import android.view.View;

import com.alipay.sdk.app.PayTask;
import com.bigkoo.pickerview.OptionsPickerView;
import com.jinxun.hunting_goods.R;
import com.jinxun.hunting_goods.base.BaseActivity;
import com.jinxun.hunting_goods.databinding.ActivityConfirmedOrderBinding;
import com.jinxun.hunting_goods.network.HttpSubscriber;
import com.jinxun.hunting_goods.network.api.shoppingCar.usercase.PayCase;
import com.jinxun.hunting_goods.network.api.shoppingCar.usercase.PayOrderCase;
import com.jinxun.hunting_goods.network.api.shoppingCar.usercase.ToPay;
import com.jinxun.hunting_goods.network.bean.Response;
import com.jinxun.hunting_goods.network.bean.address.AddressEntity;
import com.jinxun.hunting_goods.network.bean.shopping.ConfirmedOrderEntity;
import com.jinxun.hunting_goods.network.bean.shopping.Day;
import com.jinxun.hunting_goods.network.bean.shopping.PayOrderEntity;
import com.jinxun.hunting_goods.network.bean.shopping.PayResult;
import com.jinxun.hunting_goods.network.bean.shopping.ProductEntity;
import com.jinxun.hunting_goods.network.bean.shopping.Times;
import com.jinxun.hunting_goods.network.bean.shopping.createOrder;
import com.jinxun.hunting_goods.presentation.adapter.ConfirmedOrderAdapter;
import com.jinxun.hunting_goods.util.CalendarUtil;
import com.jinxun.hunting_goods.util.ToastUtil;
import com.jinxun.hunting_goods.weight.NavigationTopBar;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class ConfirmedOrderActivity extends BaseActivity implements NavigationTopBar.NavigationTopBarClickListener {


    private static final String TAG = "ConfirmedOrderActivity";
    private static final int SDK_PAY_FLAG = 1001;

    private ActivityConfirmedOrderBinding binding;

    private OptionsPickerView mPickView;
    private ConfirmedOrderAdapter mAdapter;

    private ArrayList<Day> days = new ArrayList<>();
    private ArrayList<ArrayList<Times>> timesLists = new ArrayList<>();

    private createOrder order;
    private AddressEntity entity;


    private long timeInMillis;
    private long afterTimeMillis;
    private Long receiveAddId;//收货地址id
    private Long deliveryAddId;//取货地址

    private ConfirmedOrderEntity confirmedOrderEntity;


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG:
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    String resultInfo = payResult.getResult();
                    String resultStatus = payResult.getResultStatus();
                    if (TextUtils.equals(resultStatus, "9000")) {
                        ToastUtil.showShortToast(getApplicationContext(), "成功");
                    } else {
                        ToastUtil.showShortToast(getApplicationContext(), "失败");
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void initComponent() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_confirmed_order);
        binding.setPresenter(new Presenter());
    }

    @Override
    protected void loadData(Bundle savedInstanceState) {
        order = new createOrder();
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            order = (createOrder) bundle.getSerializable("order");
            getOrderList(order.getUserId(), order.getCode());
        }
        createPicker();

    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        binding.confirmOrderTopBar.setLeftImageResource(R.mipmap.back_icon);
        binding.confirmOrderTopBar.setCenterTitleText("确认订单");
        binding.confirmOrderTopBar.setViewGone();
        binding.confirmOrderTopBar.setNavigationTopBarClickListener(this);
    }

    @Override
    protected void initListeners() {

    }

    private void pickViewShow() {
        mPickView = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String name = days.get(options1).getName();
                int code = days.get(options1).getCode();
                String time = timesLists.get(options1).get(options2).getName();
                int id = timesLists.get(options1).get(options2).getId();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                if (code == 0 || id == 0) {
                    timeInMillis = calendar.getTimeInMillis() / 1000;
                    afterTimeMillis = CalendarUtil.getAfterOneHour(calendar, 2);
                } else {
                    timeInMillis = CalendarUtil.getAfterTime(calendar, new Date(), code, id);
                    afterTimeMillis = CalendarUtil.getAfterOneHour(calendar, 1);
                }
                String appointmentTime = name + time;
                binding.appointmentTime.setText(appointmentTime);
                Log.i(TAG, "getCode: " + code);
                Log.i(TAG, "getId: " + id);
                Log.i(TAG, "timeInMillis: " + timeInMillis);
                Log.i(TAG, "afterTimeInMillis: " + afterTimeMillis);

            }
        }).setTitleText("预约时间选择").build();
        mPickView.setPicker(days, timesLists);
        mPickView.show();
    }

    @Override
    public void leftImageClick() {
        finish();
    }

    @Override
    public void rightImageClick() {

    }

    @Override
    public void alignRightLeftImageClick() {

    }

    private void getOrderList(Long userId, String[] code) {
        new ToPay(userId, code).execute(new HttpSubscriber<ConfirmedOrderEntity>(ConfirmedOrderActivity.this) {
            @Override
            public void onSuccess(Response<ConfirmedOrderEntity> response) {
                confirmedOrderEntity = new ConfirmedOrderEntity();
                confirmedOrderEntity = response.getData();
                initAdapter(response.getData().getProductList());
                binding.indentNum.setText(response.getData().getOrderNO());
                if (response.getData().getAddressEntity() != null) {
                    AddressEntity data = response.getData().getAddressEntity();
                    String address = data.getProvince() + data.getCity() + data.getDistrict() + data.getAddress();
                    binding.pickUpAddress.setText(address);
                } else {
                    binding.pickUpAddress.setText("请选择地址");
                }
            }

            @Override
            public void onFailure(String errorMsg, Response<ConfirmedOrderEntity> response) {
                ToastUtil.showShortToast(getApplicationContext(), response.getMessage());
            }
        });
    }

    private void getAlipayCode() {
        new PayCase(88l).execute(new HttpSubscriber<String>(ConfirmedOrderActivity.this) {
            @Override
            public void onSuccess(Response<String> response) {
                final String code = response.getData();
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        PayTask payTask = new PayTask(ConfirmedOrderActivity.this);
                        Map<String, String> map = payTask.payV2(code, true);
                        Message message = new Message();
                        message.what = SDK_PAY_FLAG;
                        message.obj = map;
                        handler.sendMessage(message);
                    }
                };
                Thread thread = new Thread(runnable);
                thread.start();
            }

            @Override
            public void onFailure(String errorMsg, Response<String> response) {
            }
        });
    }

    private void initAdapter(ArrayList<ProductEntity> lists) {
        mAdapter = new ConfirmedOrderAdapter();
        binding.indentInfoRv.setLayoutManager(new LinearLayoutManager(this));
        binding.indentInfoRv.setAdapter(mAdapter);
        mAdapter.setList(lists);
    }


    private void createPicker() {
        Day today = new Day("今天", 0);
        Day tomorrow = new Day("明天", 1);
        Day afterDay = new Day("后天", 2);
        days.add(today);
        days.add(tomorrow);
        days.add(afterDay);


        Times times = new Times("两小时内", 0);
        Times times10 = new Times("10点-11点", 10);
        Times times11 = new Times("11点-12点", 11);
        Times times12 = new Times("12点-13点", 12);
        Times times13 = new Times("13点-14点", 13);
        Times times14 = new Times("14点-15点", 14);
        Times times15 = new Times("15点-16点", 15);
        Times times16 = new Times("16点-17点", 16);

        ArrayList<Times> todayLists = new ArrayList<>();
        todayLists.add(times);
        todayLists.add(times10);
        todayLists.add(times11);
        todayLists.add(times12);
        todayLists.add(times13);
        todayLists.add(times14);
        todayLists.add(times15);
        todayLists.add(times16);
        ArrayList<Times> tomorrowLists = new ArrayList<>();
        tomorrowLists.add(times10);
        tomorrowLists.add(times11);
        tomorrowLists.add(times12);
        tomorrowLists.add(times13);
        tomorrowLists.add(times14);
        tomorrowLists.add(times15);
        tomorrowLists.add(times16);
        ArrayList<Times> afterTomorrow = new ArrayList<>();
        afterTomorrow.add(times10);
        afterTomorrow.add(times11);
        afterTomorrow.add(times12);
        afterTomorrow.add(times13);
        afterTomorrow.add(times14);
        afterTomorrow.add(times15);
        afterTomorrow.add(times16);
        timesLists.add(todayLists);
        timesLists.add(tomorrowLists);
        timesLists.add(afterTomorrow);
    }

    private void pay() {
//        if (binding.pickUpAddress.getText().toString().contains("请选择地址")) {
//            ToastUtil.showShortToast(getApplicationContext(), "请选择收货地址");
//            return;
//        } else if (binding.appointmentTime.getText().toString().contains("预约取件时间（两小时内）")) {
//            ToastUtil.showShortToast(getApplicationContext(), "请选择预约取件时间");
//            return;
//        }
//
//        new PayOrderCase(88l, confirmedOrderEntity.getOrderNO(), receiveAddId, deliveryAddId, timeInMillis, afterTimeMillis)
//                .execute(new HttpSubscriber<PayOrderEntity>(ConfirmedOrderActivity.this) {
//                    @Override
//                    public void onSuccess(Response<PayOrderEntity> response) {
//                        Intent intent = new Intent(ConfirmedOrderActivity.this, PaySuccessActivity.class);
//                        Bundle bundle = new Bundle();
//                        bundle.putSerializable("PayOrderEntity", response.getData());
//                        intent.putExtras(bundle);
//                        startActivity(intent);
//                        finish();
//                    }
//
//                    @Override
//                    public void onFailure(String errorMsg, Response<PayOrderEntity> response) {
//                        ToastUtil.showShortToast(getApplicationContext(), response.getMessage());
//                    }
//                });
        getAlipayCode();
    }


    public class Presenter {
        public void onClick(View view) {
            Intent intent = null;
            switch (view.getId()) {
                case R.id.appointment_time_root:
                    pickViewShow();
                    break;
                case R.id.pay_root:
                    pay();
                    break;
                case R.id.recipients_info_root:
                    break;
                case R.id.pick_up_address:
                    intent = new Intent(ConfirmedOrderActivity.this, DistributionAddressActivity.class);
                    startActivityForResult(intent, 1);
                    break;
                case R.id.delivery_address_info:
                    intent = new Intent(ConfirmedOrderActivity.this, DistributionAddressActivity.class);
                    startActivityForResult(intent, 2);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                entity = (AddressEntity) data.getSerializableExtra("addressReturn");
                if (entity != null) {
                    String address = entity.getProvince() + entity.getCity() + entity.getDistrict() + entity.getAddress();
                    binding.pickUpAddress.setText(address);
                    receiveAddId = entity.getId();
                }
                break;
            case 2:
                entity = (AddressEntity) data.getSerializableExtra("addressReturn");
                if (entity != null) {
                    String address = entity.getProvince() + entity.getCity() + entity.getDistrict() + entity.getAddress();
                    binding.deliveryAddressInfo.setText(address);
                    deliveryAddId = entity.getId();
                }
                break;
            default:
                break;
        }
    }
}
