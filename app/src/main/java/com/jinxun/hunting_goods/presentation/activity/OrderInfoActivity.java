package com.jinxun.hunting_goods.presentation.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bigkoo.pickerview.OptionsPickerView;
import com.jinxun.hunting_goods.R;
import com.jinxun.hunting_goods.base.BaseActivity;
import com.jinxun.hunting_goods.databinding.ActivityOrderInfoBinding;
import com.jinxun.hunting_goods.network.HttpSubscriber;
import com.jinxun.hunting_goods.network.api.order.usercase.OrderDetailCase;
import com.jinxun.hunting_goods.network.bean.Response;
import com.jinxun.hunting_goods.network.bean.address.AddressEntity;
import com.jinxun.hunting_goods.network.bean.order.OrderInfoEntity;
import com.jinxun.hunting_goods.network.bean.shopping.Day;
import com.jinxun.hunting_goods.network.bean.shopping.Times;
import com.jinxun.hunting_goods.util.CalendarUtil;
import com.jinxun.hunting_goods.util.ToastUtil;
import com.jinxun.hunting_goods.weight.NavigationTopBar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.http.POST;

public class OrderInfoActivity extends BaseActivity {


    private static final String TAG = "OrderInfoActivity";

    private ActivityOrderInfoBinding binding;

    private String deliveryAdd;//取货地址
    private String receiveAdd;//收货地址
    private AddressEntity entity;
    private OrderInfoEntity orderInfoEntity;

    private Long receiveAddId;//收货地址id
    private Long deliveryAddId;//取货地址

    private long timeInMillis;
    private long afterTimeMillis;

    private ArrayList<Day> days = new ArrayList<>();
    private ArrayList<ArrayList<Times>> timesLists = new ArrayList<>();


    @Override
    protected void initComponent() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_info);
        binding.setPresenter(new Presenter());
    }

    @Override
    protected void loadData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        String orderNO = intent.getStringExtra("orderNO");//订单号
        if (orderNO == null)
            return;
        getOrderDetail(orderNO);
    }

    private void getOrderDetail(String orderNO) {
        new OrderDetailCase(orderNO).execute(new HttpSubscriber<OrderInfoEntity>(OrderInfoActivity.this) {
            @Override
            public void onSuccess(Response<OrderInfoEntity> response) {
                createView(response.getData());
                orderInfoEntity = response.getData();
            }

            @Override
            public void onFailure(String errorMsg, Response<OrderInfoEntity> response) {
                ToastUtil.showShortToast(getApplicationContext(), response.getMessage());
            }
        });
    }

    private void pickViewShow() {
        createPicker();
        OptionsPickerView mPickView = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String name = days.get(options1).getName();
                int code = days.get(options1).getCode();
                String time = timesLists.get(options1).get(options2).getName();
                int id = timesLists.get(options1).get(options2).getId();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());
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
            }
        }).setTitleText("预约时间选择").build();
        mPickView.setPicker(days, timesLists);
        mPickView.show();
    }


    private void createView(OrderInfoEntity data) {
        if (null == data)
            return;
        String time = String.valueOf(data.getCreateTime());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CANADA);
        binding.orderTime.setText(sdf.format(new Date(Long.valueOf(time))));
        binding.orderNum.setText(data.getOrderNO());
        if (data.getOrderStatus() == 0 && data.getOrderStatus() == 1) {
            binding.orderInfoStates.setText("待付款");
            binding.orderInfoStatesMessage.setText("请尽快完成付款");
            binding.orderInfoStatesIcon.setImageResource(R.mipmap.wait_pay);
            binding.appointmentTimeRoot.setVisibility(View.VISIBLE);
        } else if (data.getOrderStatus() == 2) {
            binding.orderInfoStates.setText("待取件");
            binding.orderInfoStatesIcon.setImageResource(R.mipmap.wait_pick_up);
            binding.orderInfoStatesMessage.setText("取件码是*****,快递将于**-**上门");
            binding.orderInfoBottomBg.setVisibility(View.GONE);
        } else if (data.getOrderStatus() == 3) {
            binding.orderInfoStates.setText("待发货");
            binding.orderInfoStatesIcon.setImageResource(R.mipmap.wait_sending);
            binding.orderInfoStatesMessage.setText("鞋子正在加工中,预计发货时间为*天，请耐心等待");
            binding.orderInfoBottomBg.setVisibility(View.GONE);
        } else if (data.getOrderStatus() == 4) {
            binding.orderInfoStates.setText("待收货");
            binding.orderInfoStatesIcon.setImageResource(R.mipmap.wait_receiving);
            binding.orderInfoStatesMessage.setText("鞋子已经在派送途中了");
            binding.orderInfoBottomBg.setBackgroundResource(R.mipmap.check_the_logistics);
            binding.continuePayBtn.setVisibility(View.GONE);
            binding.cancelOrderBtn.setVisibility(View.GONE);
        } else if (data.getOrderStatus() == 5) {
            binding.orderInfoStates.setText("已完成");
            binding.orderInfoStatesIcon.setImageResource(R.mipmap.is_complete);
            binding.orderInfoStatesMessage.setText("您已经成为平台专属定制vip了");
            binding.orderInfoBottomBg.setBackgroundResource(R.mipmap.apply_for_after);
            binding.continuePayBtn.setVisibility(View.GONE);
            binding.cancelOrderBtn.setVisibility(View.GONE);
        } else if (data.getOrderStatus() == 6) {
            binding.orderInfoStates.setText("售后");
        }


        //取货地址
        if (data.getDeliveryAdd() == null) {
            binding.pickUpAddress.setText("请选择地址");
        } else {
            AddressEntity deliveryAddData = data.getDeliveryAdd();
            deliveryAdd = deliveryAddData.getProvince() + deliveryAddData.getCity() + deliveryAddData.getDistrict() + deliveryAddData.getAddress();
            binding.pickUpAddress.setText(deliveryAdd);
        }
        //收货地址
        if (data.getReceiveAdd() == null) {
            binding.deliveryAddressInfo.setText("默认和取件地址相同");
        } else {
            AddressEntity receiveAddData = data.getReceiveAdd();
            receiveAdd = receiveAddData.getProvince() + receiveAddData.getCity() + receiveAddData.getDistrict() + receiveAddData.getDistrict() + receiveAddData.getAddress();
        }
        if (deliveryAdd != null || receiveAdd != null)
            if (deliveryAdd.equals(receiveAdd))
                binding.deliveryAddressInfo.setText("默认和取件地址相同");
            else
                binding.deliveryAddressInfo.setText(receiveAdd);

    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        binding.orderInfoTopBar.setLeftImageResource(R.mipmap.back_icon);
        binding.orderInfoTopBar.setCenterTitleText("订单详情");
        binding.orderInfoTopBar.setViewGone();
    }

    @Override
    protected void initListeners() {
        binding.orderInfoTopBar.setNavigationTopBarClickListener(new NavigationTopBar.NavigationTopBarClickListener() {
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
        });
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (data.getSerializableExtra("addressReturn") != null)
                    entity = (AddressEntity) data.getSerializableExtra("addressReturn");
                if (null != entity) {
                    String address = entity.getProvince() + entity.getCity() + entity.getDistrict() + entity.getAddress();
                    binding.pickUpAddress.setText(address);
                    receiveAddId = entity.getId();
                }
                break;
            case 2:
                if (data.getSerializableExtra("addressReturn") != null)
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

    private void bottomClick() {
        if (orderInfoEntity.getOrderStatus() == 4) {
            ToastUtil.showShortToast(getApplicationContext(), "查看物流");
        } else if (orderInfoEntity.getOrderStatus() == 5) {
            ToastUtil.showShortToast(getApplicationContext(), "申请售后");
            Intent intent = new Intent(getApplicationContext(), ApplyAfterActivity.class);
            if (orderInfoEntity != null) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("orderInfoEntity", orderInfoEntity);
                intent.putExtras(bundle);
                startActivity(intent);
            }

        }
    }

    public class Presenter {
        public void onClick(View view) {
            Intent intent = null;
            switch (view.getId()) {
                case R.id.pick_up_address://取货地址
                    intent = new Intent(OrderInfoActivity.this, DistributionAddressActivity.class);
                    startActivityForResult(intent, 1);
                    break;
                case R.id.delivery_address_info://收货地址
                    intent = new Intent(OrderInfoActivity.this, DistributionAddressActivity.class);
                    startActivityForResult(intent, 2);
                    break;
                case R.id.appointment_time_root:
                    pickViewShow();
                    break;
                case R.id.order_info_bottom_bg:
                    bottomClick();
                    break;
                case R.id.cancel_order_btn:
                    if (orderInfoEntity.getOrderStatus() != 1) {
                        binding.cancelOrderBtn.setEnabled(false);
                    } else {
                        binding.cancelOrderBtn.setEnabled(true);
                        ToastUtil.showShortToast(getApplicationContext(), "取消订单");
                    }
                    break;
                case R.id.continue_pay_btn:
                    if (orderInfoEntity.getOrderStatus() != 1) {
                        binding.continuePayBtn.setEnabled(false);
                    } else {
                        binding.continuePayBtn.setEnabled(true);
                        ToastUtil.showShortToast(getApplicationContext(), "继续付款");
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
