package com.jinxun.hunting_goods.presentation.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;

import com.jinxun.hunting_goods.R;
import com.jinxun.hunting_goods.base.BaseActivity;
import com.jinxun.hunting_goods.databinding.ActivityShoppingCarBinding;
import com.jinxun.hunting_goods.network.HttpSubscriber;
import com.jinxun.hunting_goods.network.api.shoppingCar.usercase.DeleteShoppingCarCase;
import com.jinxun.hunting_goods.network.api.shoppingCar.usercase.ShoppingCarListCase;
import com.jinxun.hunting_goods.network.api.shoppingCar.usercase.ToPay;
import com.jinxun.hunting_goods.network.bean.Response;
import com.jinxun.hunting_goods.network.bean.shopping.ConfirmedOrderEntity;
import com.jinxun.hunting_goods.network.bean.shopping.ShoppingCarInfo;
import com.jinxun.hunting_goods.network.bean.shopping.createOrder;
import com.jinxun.hunting_goods.presentation.adapter.ShoppingCarListAdapter;
import com.jinxun.hunting_goods.util.ToastUtil;
import com.jinxun.hunting_goods.weight.NavigationTopBar;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCarActivity extends BaseActivity implements NavigationTopBar.NavigationTopBarClickListener {

    private ActivityShoppingCarBinding binding;
    private ShoppingCarListAdapter mAdapter;
    private List<ShoppingCarInfo> shoppingCarInfoLists;

    private BigDecimal totalPrice;//商品总价格
    private int totalCount;//商品总数量

    private String[] code;

    @Override
    protected void initComponent() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_shopping_car);
        binding.setPresenter(new Presenter());
    }

    @Override
    protected void loadData(Bundle savedInstanceState) {
        getShoppingCarList();
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        binding.shoppingCarTopBar.setLeftImageResource(R.mipmap.back_icon);
        binding.shoppingCarTopBar.setCenterTitleText("我的购物车");
        binding.shoppingCarTopBar.setViewGone();
        binding.shoppingCarTopBar.setNavigationTopBarClickListener(this);
    }

    @Override
    protected void initListeners() {

    }

    /**
     * 结算
     */
    private void statistics() {
        totalPrice = new BigDecimal(0.00);
        totalCount = 0;
        code = new String[shoppingCarInfoLists.size()];

        for (int i = 0; i < shoppingCarInfoLists.size(); i++) {
            ShoppingCarInfo shoppingCarInfo = shoppingCarInfoLists.get(i);
            if (shoppingCarInfo.isChoosed()) {
                totalCount++;
                totalPrice = totalPrice.add(shoppingCarInfo.getCost());
            }
        }
        for (int j = 0; j < code.length; j++) {
            if (shoppingCarInfoLists.get(j).isChoosed()) {
                code[j] = shoppingCarInfoLists.get(j).getUniqueCode();
            }
        }

        Log.i("ShoppingCarActivity", "statistics: " + code.toString());
        binding.shoppingCarPrice.setText(totalPrice + "");
        binding.paySum.setText("(" + totalCount + ")");
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

    private void getShoppingCarList() {
        new ShoppingCarListCase(88l).execute(new HttpSubscriber<ArrayList<ShoppingCarInfo>>(ShoppingCarActivity.this) {
            @Override
            public void onSuccess(Response<ArrayList<ShoppingCarInfo>> response) {
                shoppingCarList(response.getData());
                shoppingCarInfoLists = new ArrayList<>();
                shoppingCarInfoLists = response.getData();
            }

            @Override
            public void onFailure(String errorMsg, Response<ArrayList<ShoppingCarInfo>> response) {
                ToastUtil.showShortToast(getApplicationContext(), response.getMessage());

            }
        });
    }

    private void shoppingCarList(final ArrayList<ShoppingCarInfo> list) {
        mAdapter = new ShoppingCarListAdapter();
        binding.shoppingCarRv.setLayoutManager(new LinearLayoutManager(this));
        binding.shoppingCarRv.setAdapter(mAdapter);
        mAdapter.setList(list);
        mAdapter.setListener(new ShoppingCarListAdapter.onSwipeListener() {
            @Override
            public void onDelete(int position, String code) {
                deleteShoppingCar(list.get(position).getUniqueCode());
                mAdapter.getList().remove(position);
                mAdapter.notifyItemRemoved(position);
            }

            @Override
            public void onClick(View view, int position) {
//                String uniqueCode = list.get(position).getUniqueCode();
//                Intent intent = new Intent(ShoppingCarActivity.this, ConfirmedOrderActivity.class);
//                intent.putExtra("uniqueCode", uniqueCode);
//                startActivity(intent);
            }

            @Override
            public void onCheckGroup(int position, boolean isChecked) {
                list.get(position).setChoosed(isChecked);
                if (isAllCheck())
                    binding.allChooseCheckbox.setChecked(true);
                else
                    binding.allChooseCheckbox.setChecked(false);
                mAdapter.notifyDataSetChanged();
                statistics();
            }
        });
    }

    /**
     * 遍历结合
     */
    private boolean isAllCheck() {
        for (ShoppingCarInfo info : shoppingCarInfoLists) {
            if (!info.isChoosed())
                return false;
        }
        return true;
    }

    /**
     * 删除购物车
     */
    private void deleteShoppingCar(String code) {
        new DeleteShoppingCarCase(code).execute(new HttpSubscriber(ShoppingCarActivity.this) {
            @Override
            public void onSuccess(Response response) {
                ToastUtil.showShortToast(getApplicationContext(), "删除成功");
            }

            @Override
            public void onFailure(String errorMsg, Response response) {
                ToastUtil.showShortToast(getApplicationContext(), response.getMessage());
            }
        });
    }


    public class Presenter {
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.pay_btn:
                    if (code == null) {
                        ToastUtil.showShortToast(ShoppingCarActivity.this,"购物车里空空如也");
                    } else {
                        Intent intent = new Intent(ShoppingCarActivity.this, ConfirmedOrderActivity.class);
                        createOrder order = new createOrder(88l, code);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("order", order);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }

                    break;
                default:
                    break;
            }
        }

        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (shoppingCarInfoLists.size() != 0 && shoppingCarInfoLists != null)
                if (isChecked) {
                    for (int i = 0; i < shoppingCarInfoLists.size(); i++) {
                        shoppingCarInfoLists.get(i).setChoosed(true);
                    }
                    mAdapter.notifyDataSetChanged();
                } else {
                    for (int i = 0; i < shoppingCarInfoLists.size(); i++) {
                        shoppingCarInfoLists.get(i).setChoosed(false);
                    }
                    mAdapter.notifyDataSetChanged();
                }
            statistics();
        }
    }
}
