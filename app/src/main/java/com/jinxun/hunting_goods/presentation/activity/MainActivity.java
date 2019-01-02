package com.jinxun.hunting_goods.presentation.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.util.Log;
import android.view.View;

import com.jinxun.hunting_goods.R;
import com.jinxun.hunting_goods.base.recyclerview.BaseRecyclerViewAdapter;
import com.jinxun.hunting_goods.listener.OnViewPagerListener;
import com.jinxun.hunting_goods.network.HttpSubscriber;
import com.jinxun.hunting_goods.network.api.shoe.usercase.CompletePart;
import com.jinxun.hunting_goods.network.api.shoe.usercase.GetShoeColors;
import com.jinxun.hunting_goods.network.api.shoe.usercase.GetShoeMaterial;
import com.jinxun.hunting_goods.network.api.shoe.usercase.GetShoePart;
import com.jinxun.hunting_goods.network.api.shoe.usercase.GetShoes;
import com.jinxun.hunting_goods.network.api.shoppingCar.usercase.AddShoppingCarCase;
import com.jinxun.hunting_goods.network.bean.Response;
import com.jinxun.hunting_goods.network.bean.shoe.CompletePartEntity;
import com.jinxun.hunting_goods.network.bean.shoe.ShoeColorEntity;
import com.jinxun.hunting_goods.network.bean.shoe.ShoeMaterialEntity;
import com.jinxun.hunting_goods.network.bean.shoe.ShoePartEntity;
import com.jinxun.hunting_goods.presentation.adapter.ShoeColorInfoAdapter;
import com.jinxun.hunting_goods.presentation.adapter.ShoeColorsAdapter;
import com.jinxun.hunting_goods.presentation.adapter.ShoeInfoAdapter;
import com.jinxun.hunting_goods.presentation.adapter.ShoeMaterialAdapter;
import com.jinxun.hunting_goods.presentation.adapter.ShoeMaterialInfoAdapter;
import com.jinxun.hunting_goods.presentation.adapter.ShoePartAdapter;
import com.jinxun.hunting_goods.base.BaseActivity;
import com.jinxun.hunting_goods.databinding.ActivityMainBinding;
import com.jinxun.hunting_goods.manager.MyLinearLayoutManager;
import com.jinxun.hunting_goods.network.bean.shoe.ShoeInfoEntity;
import com.jinxun.hunting_goods.util.ToastUtil;
import com.jinxun.hunting_goods.weight.NavigationTopBar;

import java.util.ArrayList;

import retrofit2.http.POST;

/**
 * 切换商品主页  预估市值5000万
 */

public class MainActivity extends BaseActivity implements NavigationTopBar.NavigationTopBarClickListener,
        DrawerLayout.DrawerListener {

    public static final String TAG = "MainActivity";

    private ActivityMainBinding binding;

//    //上方适配器
//    private ShoeInfoAdapter mMainAdapter;//鞋子展示适配器
//    private ShoeMaterialInfoAdapter mMaterialInfoAdapter;//鞋子材质适配器
//    private ShoeColorInfoAdapter mColorInfoAdapter;
//
//    //下部适配器
//    private ShoePartAdapter mPartAdapter;//鞋子部位适配器
//    private ShoeMaterialAdapter mMaterialAdapter;//鞋子材质适配器
//    private ShoeColorsAdapter mColorAdapter;//材质颜色适配器


    private ArrayList<ShoeInfoEntity> entities;
    private ArrayList<ShoePartEntity> mPartLists;
    private ArrayList<ShoeMaterialEntity> mMaterialLists;
    private ArrayList<ShoeColorEntity> mColorLists;

    private CompletePartEntity mCompletePart = new CompletePartEntity();


    /**
     * 1 表示可以开始编辑
     * 2 表示完成
     * 3 退出与选中
     * <p>
     * 4。编辑完成
     */
    private int mBtnStatus = 1;

    /**
     * 底部栏
     * 1 表示材质
     * 2 表示颜色
     * 3 表示加入购物车 或者付款
     * 4。查看购物车
     */
    private int mBottomReturnStatus = 1;


    @Override
    protected void initComponent() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setPresenter(new Presenter());
        binding.bottomRoot.setBackgroundResource(R.mipmap.editorial_design_btn_bg);
    }

    private void initRecyclerView(ArrayList<ShoeInfoEntity> list) {
        MyLinearLayoutManager mManager = new MyLinearLayoutManager(this, OrientationHelper.VERTICAL);
        ShoeInfoAdapter mMainAdapter = new ShoeInfoAdapter();
        binding.mainRv.setLayoutManager(mManager);
        binding.mainRv.setAdapter(mMainAdapter);
        mMainAdapter.setList(list);

        mManager.setOnViewPagerListener(new OnViewPagerListener() {
            @Override
            public void onPageRelease(boolean isNext, int position) {
                Log.i(TAG, "释放位置:" + position + " 下一页:" + isNext);
            }

            @Override
            public void onPageSelected(int position, boolean isBottom) {
                Log.i(TAG, "选中位置:" + position + "  是否是滑动到底部:" + isBottom);

            }

            @Override
            public void onLayoutComplete() {

            }
        });
    }


    private void getShoeView() {
        new GetShoes().execute(new HttpSubscriber<ShoeInfoEntity>(MainActivity.this) {
            @Override
            public void onSuccess(Response<ShoeInfoEntity> response) {
                entities = new ArrayList<>();
                ShoeInfoEntity data = response.getData();
                entities.add(data);
                initRecyclerView(entities);
            }

            @Override
            public void onFailure(String errorMsg, Response<ShoeInfoEntity> response) {
                ToastUtil.showShortToast(getApplicationContext(), response.getMessage());
            }
        });
    }

    /**
     * 获取部位
     */
    private void getShoePart() {
        binding.shoePartRv.setVisibility(View.VISIBLE);
        new GetShoePart().execute(new HttpSubscriber<ArrayList<ShoePartEntity>>(MainActivity.this) {
            @Override
            public void onSuccess(Response<ArrayList<ShoePartEntity>> response) {
                mPartLists = response.getData();
                mPartLists = new ArrayList<>();
                initShoePartAdapter(response.getData());
                binding.bottomRoot.setBackgroundResource(R.mipmap.complete_btn_bg);
                binding.returnBtn.setVisibility(View.GONE);
                binding.completeBtn.setVisibility(View.GONE);
                mBtnStatus = 2;
            }

            @Override
            public void onFailure(String errorMsg, Response<ArrayList<ShoePartEntity>> response) {
                ToastUtil.showShortToast(getApplicationContext(), response.getMessage());
            }
        });
    }

    /**
     * 获取材质
     */
    private void getShoeMaterial(String positionId) {
        new GetShoeMaterial(positionId).execute(new HttpSubscriber<ArrayList<ShoeMaterialEntity>>(MainActivity.this) {
            @Override
            public void onSuccess(Response<ArrayList<ShoeMaterialEntity>> response) {
                mMaterialLists = new ArrayList<>();
                mMaterialLists = response.getData();
                initShoeMaterialAdapter(response.getData());
                mBtnStatus = 3;
                mBottomReturnStatus = 1;
                binding.bottomRoot.setBackgroundResource(R.color.white);
                binding.returnBtn.setVisibility(View.VISIBLE);
                binding.completeBtn.setVisibility(View.VISIBLE);
                mCompletePart.setProductName(mMaterialLists.get(0).getProductName());
            }

            @Override
            public void onFailure(String errorMsg, Response<ArrayList<ShoeMaterialEntity>> response) {
                ToastUtil.showShortToast(getApplicationContext(), response.getMessage());
            }
        });
    }

    /**
     * 鞋子部位底部适配器
     */
    private void initShoePartAdapter(final ArrayList<ShoePartEntity> list) {
        final ShoePartAdapter mPartAdapter = new ShoePartAdapter();
        binding.shoePartRv.setLayoutManager(new LinearLayoutManager(this, OrientationHelper.HORIZONTAL, false));
        binding.shoePartRv.setAdapter(mPartAdapter);
        mPartAdapter.setList(list);
        binding.shoePartRv.setVisibility(View.VISIBLE);
        mPartAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, int which) {
                getShoeMaterial(list.get(position).getPositionId());
                mCompletePart.setPositionName(list.get(position).getName());
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }

    /**
     * 鞋子材质底部适配器
     */
    private void initShoeMaterialAdapter(final ArrayList<ShoeMaterialEntity> list) {
        final ShoeMaterialAdapter mMaterialAdapter = new ShoeMaterialAdapter();
        binding.shoePartRv.setLayoutManager(new LinearLayoutManager(this, OrientationHelper.HORIZONTAL, false));
        binding.shoePartRv.setAdapter(mMaterialAdapter);
        mMaterialAdapter.setList(list);
        mMaterialAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, int which) {
                //获取材质信息 初始化上部分适配器
                initShoeMaterialRecyclerView(mMaterialLists, position);
                getShoeColors(mMaterialLists.get(position).getPositionId(), mMaterialLists.get(position).getMaterialId());
                mCompletePart.setMaterialName(list.get(position).getMaterialName());
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }

    /**
     * 获取颜色
     */
    private void getShoeColors(Long positionId, Long materialId) {
        new GetShoeColors(positionId, materialId).execute(new HttpSubscriber<ArrayList<ShoeColorEntity>>(MainActivity.this) {
            @Override
            public void onSuccess(Response<ArrayList<ShoeColorEntity>> response) {
                mColorLists = new ArrayList<>();
                mColorLists = response.getData();
                initColorsAdapter(mColorLists);
                mBottomReturnStatus = 2;
            }

            @Override
            public void onFailure(String errorMsg, Response<ArrayList<ShoeColorEntity>> response) {
                ToastUtil.showShortToast(getApplicationContext(), response.getMessage());
            }
        });
    }

    /**
     * 初始化底部颜色适配器
     */
    private void initColorsAdapter(final ArrayList<ShoeColorEntity> lists) {
        final ShoeColorsAdapter mColorAdapter = new ShoeColorsAdapter();
        binding.shoePartRv.setLayoutManager(new LinearLayoutManager(this, OrientationHelper.HORIZONTAL, false));
        binding.shoePartRv.setAdapter(mColorAdapter);
        mColorAdapter.setList(lists);

        mColorAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, int which) {
                initColorRecyclerView(lists, position);
                //TODO 底部颜色
                mCompletePart.setColorId(String.valueOf(lists.get(position).getColorId()));
                mCompletePart.setColorName(lists.get(position).getColorName());
                mCompletePart.setPositionId(String.valueOf(lists.get(position).getPositionId()));
                mCompletePart.setMaterialId(String.valueOf(lists.get(position).getMaterialId()));
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }

    /**
     * 鞋子材质上方展示
     */
    private void initShoeMaterialRecyclerView(ArrayList<ShoeMaterialEntity> list, int position) {
        ShoeMaterialInfoAdapter mMaterialInfoAdapter = new ShoeMaterialInfoAdapter();
        binding.mainRv.setLayoutManager(new LinearLayoutManager(this));
        binding.mainRv.setAdapter(mMaterialInfoAdapter);
        ShoeMaterialEntity shoeMaterialEntity = list.get(position);
        ArrayList<ShoeMaterialEntity> entities = new ArrayList<>();
        entities.add(shoeMaterialEntity);
        mMaterialInfoAdapter.setList(entities);
    }

    /**
     * 鞋子颜色上方展示
     */
    private void initColorRecyclerView(ArrayList<ShoeColorEntity> list, int position) {
        ShoeColorInfoAdapter mColorInfoAdapter = new ShoeColorInfoAdapter();
        binding.mainRv.setLayoutManager(new LinearLayoutManager(this));
        binding.mainRv.setAdapter(mColorInfoAdapter);
        ShoeColorEntity shoeColorEntity = list.get(position);
        ArrayList<ShoeColorEntity> entities = new ArrayList<>();
        entities.add(shoeColorEntity);
        mColorInfoAdapter.setList(entities);
    }


    @Override
    protected void loadData(Bundle savedInstanceState) {
        getShoeView();
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        binding.mainTopBar.setViewGone();
        binding.mainTopBar.setCenterTitleText(R.mipmap.app_name_icon);
        binding.mainTopBar.setLeftImageResource(R.mipmap.open_menu);
        binding.mainTopBar.setRightImageResource(R.mipmap.forward_icon);
        binding.mainTopBar.setAlignRightLeftImageResource(R.mipmap.main_price_icon);
        binding.mainTopBar.setNavigationTopBarClickListener(this);
    }

    @Override
    protected void initListeners() {
    }

    @Override
    public void leftImageClick() {
        binding.mainDrawerLayout.openDrawer(binding.mainLeftView);
    }

    @Override
    public void rightImageClick() {

    }

    @Override
    public void alignRightLeftImageClick() {

    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {

    }

    @Override
    public void onDrawerOpened(View drawerView) {

    }

    @Override
    public void onDrawerClosed(View drawerView) {

    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }


    @Override
    public void onBackPressed() {
        if (binding.mainDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.mainDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * 底部按钮切换
     */
    private void checkBtn() {
        if (mBtnStatus == 1) {
            binding.bottomRoot.setVisibility(View.VISIBLE);
            binding.bottomRoot.setBackgroundResource(R.mipmap.complete_btn_bg);
            mBtnStatus = 2;
            getShoePart();
        } else if (mBtnStatus == 2) {
            binding.bottomRoot.setVisibility(View.VISIBLE);
            binding.bottomRoot.setBackgroundResource(R.mipmap.editorial_design_btn_bg);
            mBtnStatus = 1;
            binding.shoePartRv.setVisibility(View.GONE);
        } else if (mBtnStatus == 4) {
            ToastUtil.showShortToast(getApplicationContext(), "完成");
        }
    }

    /**
     * 加入购物车
     */
    private void addShoppingCar() {
        new AddShoppingCarCase(88l).execute(new HttpSubscriber<Response>(MainActivity.this) {

            @Override
            public void onSuccess(Response<Response> response) {
                ToastUtil.showShortToast(getApplicationContext(), "加入购物车成功");
                mBottomReturnStatus = 4;
                binding.addToShoppingBtn.setText("查看购物车");
                binding.addToShoppingBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mBottomReturnStatus == 4) {
                            Intent intent = new Intent(MainActivity.this, ShoppingCarActivity.class);
                            startActivity(intent);
                        }
                        binding.addToShoppingRoot.setVisibility(View.GONE);
                        binding.bottomRoot.setVisibility(View.VISIBLE);
                        binding.bottomRoot.setBackgroundResource(R.mipmap.editorial_design_btn_bg);
                        binding.shoePartRv.setVisibility(View.GONE);
                        mBtnStatus = 1;
                        mBottomReturnStatus = 1;
//                        getShoeView();

                    }
                });
            }

            @Override
            public void onFailure(String errorMsg, Response<Response> response) {

            }
        });
    }

    public class Presenter {
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.bottom_root:
                    checkBtn();
                    break;
                case R.id.main_user_photo:
                    break;
                case R.id.main_user_change_num_icon:
                    break;
                case R.id.shopping_car_root:
                    openActivity(ShoppingCarActivity.class);
                    binding.mainDrawerLayout.closeDrawer(GravityCompat.START);
                    break;
                case R.id.my_order_root:
                    openActivity(MyOrderActivity.class);
                    binding.mainDrawerLayout.closeDrawer(GravityCompat.START);
                    break;
                case R.id.my_address_root:
                    openActivity(DistributionAddressActivity.class);
                    binding.mainDrawerLayout.closeDrawer(GravityCompat.START);
                    break;
                case R.id.customer_service_root:
                    break;
                case R.id.feedback_root:
                    openActivity(FeedBackActivity.class);
                    binding.mainDrawerLayout.closeDrawer(GravityCompat.START);
                    break;
                case R.id.logout:
                    break;
                case R.id.return_btn:
                    if (mBottomReturnStatus == 1) {
                        ToastUtil.showShortToast(getApplicationContext(), "材质底部返回部位");
                        binding.bottomRoot.setBackgroundResource(R.mipmap.complete_btn_bg);
                        binding.returnBtn.setVisibility(View.GONE);
                        binding.completeBtn.setVisibility(View.GONE);
                        mBtnStatus = 2;
                        getShoePart();
                    } else if (mBottomReturnStatus == 2) {
                        ToastUtil.showShortToast(getApplicationContext(), "颜色底部返回");
                        initShoeMaterialAdapter(mMaterialLists);
                        mBottomReturnStatus = 1;
                    } else if (mBottomReturnStatus == 3) {
                        ToastUtil.showShortToast(getApplicationContext(), "加入购物车成功");
                    }
                    break;
                case R.id.complete_btn:
                    if (mBottomReturnStatus == 1) {
                        ToastUtil.showShortToast(getApplicationContext(), "材质底部选中");
                    } else if (mBottomReturnStatus == 2) {
                        ToastUtil.showShortToast(getApplicationContext(), "颜色底部选中");
                        new CompletePart("88", "1", mCompletePart.getPositionId(), mCompletePart.getMaterialId(), mCompletePart.getColorId(),
                                mCompletePart.getProductName(), mCompletePart.getPositionName(), mCompletePart.getMaterialName(),
                                mCompletePart.getColorName()).execute(new HttpSubscriber<Response>(MainActivity.this) {
                            @Override
                            public void onSuccess(Response<Response> response) {
                                ToastUtil.showShortToast(getApplicationContext(), response.getMessage());
                                binding.bottomRoot.setBackgroundResource(R.mipmap.complete_btn_bg);
                                binding.returnBtn.setVisibility(View.GONE);
                                binding.completeBtn.setVisibility(View.GONE);
                                mBtnStatus = 4;
                                mBottomReturnStatus = 3;
                                /**
                                 * 完成某部位编辑  可以加入购物车 底部状态需要进行变化
                                 */
                                binding.addToShoppingRoot.setVisibility(View.VISIBLE);
                                if (mBottomReturnStatus == 3)
                                    binding.addToShoppingBtn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            ToastUtil.showShortToast(getApplicationContext(), "加入购物车");
                                            addShoppingCar();
                                        }
                                    });
                            }

                            @Override
                            public void onFailure(String errorMsg, Response<Response> response) {
                                ToastUtil.showShortToast(getApplicationContext(), response.getMessage());

                            }
                        });
                    } else if (mBottomReturnStatus == 3) {
                        ToastUtil.showShortToast(getApplicationContext(), "进行付款");
                    }
                case R.id.add_to_shopping_btn:
                    break;
                default:
                    break;
            }
        }

    }
}
