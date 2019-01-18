package com.jinxun.hunting_goods.presentation.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.jinxun.hunting_goods.ImageViewEntity;
import com.jinxun.hunting_goods.R;
import com.jinxun.hunting_goods.base.recyclerview.BaseAppCompatActivity;
import com.jinxun.hunting_goods.base.recyclerview.BaseRecyclerViewAdapter;
import com.jinxun.hunting_goods.databinding.PriceDetailLayoutBinding;
import com.jinxun.hunting_goods.listener.OnViewPagerListener;
import com.jinxun.hunting_goods.network.HttpSubscriber;
import com.jinxun.hunting_goods.network.api.account.usercase.PostUserImageCase;
import com.jinxun.hunting_goods.network.api.address.usercase.LoginOutCase;
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
import com.jinxun.hunting_goods.network.bean.shoe.ViewShoe;
import com.jinxun.hunting_goods.presentation.adapter.ShoeColorInfoAdapter;
import com.jinxun.hunting_goods.presentation.adapter.ShoeColorsAdapter;
import com.jinxun.hunting_goods.presentation.adapter.ShoeInfoAdapter;
import com.jinxun.hunting_goods.presentation.adapter.ShoeMaterialAdapter;
import com.jinxun.hunting_goods.presentation.adapter.ShoeMaterialInfoAdapter;
import com.jinxun.hunting_goods.presentation.adapter.ShoePartAdapter;
import com.jinxun.hunting_goods.databinding.ActivityMainBinding;
import com.jinxun.hunting_goods.manager.MyLinearLayoutManager;
import com.jinxun.hunting_goods.network.bean.shoe.ShoeInfoEntity;
import com.jinxun.hunting_goods.presentation.adapter.ViewAdapter;
import com.jinxun.hunting_goods.util.Constants;
import com.jinxun.hunting_goods.util.FileUtil;
import com.jinxun.hunting_goods.util.GlideUtil;
import com.jinxun.hunting_goods.util.PhotoHelper;
import com.jinxun.hunting_goods.util.PhotoPickerUtil;
import com.jinxun.hunting_goods.util.SpUtils;
import com.jinxun.hunting_goods.util.ToastUtil;
import com.jinxun.hunting_goods.weight.NavigationTopBar;
import com.jph.takephoto.model.TResult;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.RequestBody;


/**
 * 切换商品主页  预估市值5000万
 */

public class MainActivity extends BaseAppCompatActivity implements NavigationTopBar.NavigationTopBarClickListener,
        DrawerLayout.DrawerListener {

    public static final String TAG = "MainActivity";

    private ActivityMainBinding binding;

    private PhotoHelper photoHelper;

    private BottomSheetDialog mSheetDialog;


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
    private View rootView;

    private CompletePartEntity mCompletePart = new CompletePartEntity();
    private MyLinearLayoutManager mManager;

    private String token;


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

    private String mPhoneNumber;


    @Override
    protected void initComponent() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setPresenter(new Presenter());
        binding.bottomRoot.setBackgroundResource(R.mipmap.editorial_design_btn_bg);
        rootView = binding.mainLeftView.getRootView();
        photoHelper = PhotoHelper.of(rootView, true);
    }

    private void initRecyclerView(ArrayList<ShoeInfoEntity> list) {
        if (mManager == null) {
            mManager = new MyLinearLayoutManager(this, OrientationHelper.VERTICAL);
        }
        ShoeInfoAdapter mMainAdapter = new ShoeInfoAdapter();
        binding.mainRv.setLayoutManager(mManager);
        binding.mainRv.setAdapter(mMainAdapter);
        mMainAdapter.setList(list);
        mMainAdapter.setOnRootClickListener(new ShoeInfoAdapter.onRootClickListener() {
            @Override
            public void onClick(View view, int position) {

            }
        });
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
        new GetShoePart(token).execute(new HttpSubscriber<ArrayList<ShoePartEntity>>(MainActivity.this) {
            @Override
            public void onSuccess(Response<ArrayList<ShoePartEntity>> response) {
                switch (response.getCode()) {
                    case "200":
                        mPartLists = response.getData();
                        mPartLists = new ArrayList<>();
                        initShoePartAdapter(response.getData());
                        binding.bottomRoot.setBackgroundResource(R.mipmap.complete_btn_bg);
                        binding.returnBtn.setVisibility(View.GONE);
                        binding.completeBtn.setVisibility(View.GONE);
                        mBtnStatus = 2;
                        break;
                    case "-1":
                        openActivity(LoginActivity.class);
                        finish();
                    default:
                        break;
                }
            }

            @Override
            public void onFailure(String errorMsg, Response<ArrayList<ShoePartEntity>> response) {
                ToastUtil.showShortToast(getApplicationContext(), response.getMessage());
                switch (response.getCode()) {
                    case "-1":
                        openActivity(LoginActivity.class);
                        finish();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    /**
     * 获取部位
     */
    private void getShoePartView() {
        binding.shoePartRv.setVisibility(View.VISIBLE);
        new GetShoePart(token).execute(new HttpSubscriber<ArrayList<ShoePartEntity>>(MainActivity.this) {
            @Override
            public void onSuccess(Response<ArrayList<ShoePartEntity>> response) {
                switch (response.getCode()) {
                    case "200":
                        mPartLists = response.getData();
                        mPartLists = new ArrayList<>();
                        initShoePartAdapter(response.getData());
                        binding.bottomRoot.setBackgroundResource(R.mipmap.complete_btn_bg);
                        binding.returnBtn.setVisibility(View.GONE);
                        binding.completeBtn.setVisibility(View.GONE);
                        break;
                    case "-1":
                        openActivity(LoginActivity.class);
                        finish();
                    default:
                        break;
                }
            }

            @Override
            public void onFailure(String errorMsg, Response<ArrayList<ShoePartEntity>> response) {
                ToastUtil.showShortToast(getApplicationContext(), response.getMessage());
                switch (response.getCode()) {
                    case "-1":
                        openActivity(LoginActivity.class);
                        finish();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    /**
     * 获取材质
     */
    private void getShoeMaterial(String token, String positionId) {
        new GetShoeMaterial(positionId, token).execute(new HttpSubscriber<ArrayList<ShoeMaterialEntity>>(MainActivity.this) {
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
                getShoeMaterial(token, list.get(position).getPositionId());
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
                getShoeColors(token, mMaterialLists.get(position).getPositionId(), mMaterialLists.get(position).getMaterialId());
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
    private void getShoeColors(String token, Long positionId, Long materialId) {
        new GetShoeColors(positionId, materialId, token).execute(new HttpSubscriber<ArrayList<ShoeColorEntity>>(MainActivity.this) {
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

    private void showDialog() {
        mSheetDialog = new BottomSheetDialog(this);
        PriceDetailLayoutBinding priceDetailLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(this),
                R.layout.price_detail_layout, null, false);
        mSheetDialog.setContentView(priceDetailLayoutBinding.getRoot());
        if (!mSheetDialog.isShowing())
            mSheetDialog.show();
        priceDetailLayoutBinding.priceClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSheetDialog.isShowing())
                    mSheetDialog.dismiss();
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
        token = (String) SpUtils.init(Constants.SPREF.TOKEN).get(Constants.SPREF.TOKEN, "");
        getShoeView();
        mPhoneNumber = (String) SpUtils.init(Constants.SPREF.USER_PHONE).get(Constants.SPREF.USER_PHONE, "");
        if (mPhoneNumber != null)
            binding.mainUserPhoneNum.setText(mPhoneNumber);
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        binding.mainTopBar.setViewGone();
        binding.mainTopBar.setCenterTitleText(R.mipmap.app_name_icon);
        binding.mainTopBar.setLeftImageResource(R.mipmap.open_menu);
        binding.mainTopBar.setLeftImageResource(R.mipmap.main_price_icon);
//        binding.mainTopBar.setAlignRightLeftImageResource(R.mipmap.main_price_icon);
        binding.mainTopBar.setNavigationTopBarClickListener(this);
    }

    @Override
    protected void initListeners() {

    }

    @Override
    public void leftImageClick() {
        if (token == null || token.equals("")) {
            openActivity(LoginActivity.class);
        } else {
            binding.mainDrawerLayout.openDrawer(binding.mainLeftView);

        }
    }

    @Override
    public void rightImageClick() {
        showDialog();
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
        } else if (mBtnStatus == 6) {
//            getShoePart();
            ToastUtil.showShortToast(getApplicationContext(), "下单");
            binding.addToShoppingRoot.setVisibility(View.VISIBLE);

//            mBtnStatus = 4;
//            mBottomReturnStatus = 3;
//            if (mBottomReturnStatus == 3)
//            binding.addToShoppingBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    ToastUtil.showShortToast(getApplicationContext(), "加入购物车");
////                        addShoppingCar();
//                    CompletePart();
//                }
//            });
            CompletePart();
        }
    }

    private void getComponentView() {

    }

    /**
     * 加入购物车
     */
    private void addShoppingCar() {
        new AddShoppingCarCase(token).execute(new HttpSubscriber<Response>(MainActivity.this) {

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
                ToastUtil.showShortToast(getApplicationContext(), response.getMessage());

            }
        });
    }

    private PhotoPickerUtil.OnItemClickListener listener = new PhotoPickerUtil.OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            photoHelper.onClick(position, getTakePhoto());
        }
    };

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        Log.i(TAG, "takeSuccess: " + result.getImage().getCompressPath());
        String imgUrl = result.getImage().getCompressPath();
        File file = new File(imgUrl);
        uploadImage(file);
    }


    private void uploadImage(File file) {
        Map<String, RequestBody> params = new HashMap<>();
        params.put("token", RequestBody.create(null, token));
        params.put("postfix", RequestBody.create(null, ".jpg"));
        params.put("base64Str", RequestBody.create(null, FileUtil.fileToStream(file)));

        new PostUserImageCase(params).execute(new HttpSubscriber<String>() {
            @Override
            public void onSuccess(Response<String> response) {
                String data = response.getData();
                if (null != data)
                    GlideUtil.load(getApplicationContext(), data, binding.mainUserPhoto);
            }

            @Override
            public void onFailure(String errorMsg, Response<String> response) {
                ToastUtil.showShortToast(getApplicationContext(), response.getMessage());
            }
        });
    }

    /**
     * 退出登录
     */
    private void loginOut() {
        if (null != token)
            new LoginOutCase(token).execute(new HttpSubscriber() {
                @Override
                public void onSuccess(Response response) {
                    switch (response.getCode()) {
                        case "-1":
                            SpUtils.init(Constants.SPREF.FILE_USER_NAME).clear();
                            openActivity(LoginActivity.class);
                            finish();
                            break;
                        case "200":
                            openActivity(LoginActivity.class);
                            finish();
                            break;
                        default:
                            break;
                    }
                }

                @Override
                public void onFailure(String errorMsg, Response response) {
                    ToastUtil.showShortToast(getApplicationContext(), response.getMessage());
                    switch (response.getCode()) {
                        case "-1":
                            openActivity(LoginActivity.class);
                            finish();
                            break;
                        default:
                            break;
                    }
                }
            });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void doPay() {

    }


    /**
     * 鞋子部位底部适配器
     */
    private void initShoeAdapter(final ArrayList<String> list) {
        final ViewAdapter adapter = new ViewAdapter();
        binding.shoePartRv.setLayoutManager(new LinearLayoutManager(this, OrientationHelper.HORIZONTAL, false));
        binding.shoePartRv.setAdapter(adapter);
        adapter.setList(list);
        binding.shoePartRv.setVisibility(View.VISIBLE);
    }

    private void CompletePart() {
        new CompletePart(token, "1", mCompletePart.getPositionId(), mCompletePart.getMaterialId(), mCompletePart.getColorId(),
                mCompletePart.getProductName(), mCompletePart.getPositionName(), mCompletePart.getMaterialName(),
                mCompletePart.getColorName()).execute(new HttpSubscriber<ImageViewEntity>(MainActivity.this) {
            @Override
            public void onSuccess(Response<ImageViewEntity> response) {
                switch (response.getCode()) {
                    case "200":
                        initShoeAdapter(response.getData().getImgList());
                        ToastUtil.showShortToast(getApplicationContext(), response.getMessage());
                        binding.bottomRoot.setBackgroundResource(R.mipmap.complete_btn_bg);
                        binding.returnBtn.setVisibility(View.GONE);
                        binding.completeBtn.setVisibility(View.GONE);
                        mBtnStatus = 4;
                        mBottomReturnStatus = 3;
                        /**
                         * 完成某部位编辑  可以加入购物车 底部状态需要进行变化
                         */
//                        binding.titleRoot.setVisibility(View.VISIBLE);
                        binding.addToShoppingRoot.setVisibility(View.VISIBLE);
                        if (mBottomReturnStatus == 3)
                            binding.addToShoppingBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ToastUtil.showShortToast(getApplicationContext(), "加入购物车");
                                    addShoppingCar();
                                }
                            });

                        break;
                    case "-1":
                        openActivity(LoginActivity.class);
                        finish();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onFailure(String errorMsg, Response<ImageViewEntity> response) {
                ToastUtil.showShortToast(getApplicationContext(), response.getMessage());
                switch (response.getCode()) {
                    case "201":
                        openActivity(LoginActivity.class);
                        finish();
                        break;
                    case "-1":
                        openActivity(LoginActivity.class);
                        finish();
                        break;
                    default:
                        break;
                }

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
                    if (null == token || token.equals("")) {
                        openActivity(LoginActivity.class);
                        finish();
                    } else {
                        PhotoPickerUtil.init(MainActivity.this);
                        PhotoPickerUtil.setContent("获取图片", new String[]{"拍照", "从相册选择"}, null);
                        PhotoPickerUtil.show(listener);
                    }
                    break;
                case R.id.main_user_change_num_icon:
                    if (token == null || token.equals("")) {
                        openActivity(LoginActivity.class);
                    } else {
                        if (mPhoneNumber != null) {
                            Intent intent = new Intent(MainActivity.this, ModificationActivity.class);
                            intent.putExtra("mPhoneNumber", mPhoneNumber);
                            startActivity(intent);
                        }
                        binding.mainDrawerLayout.closeDrawer(GravityCompat.START);
                    }
                    break;
                case R.id.shopping_car_root:
                    if (token == null || token.equals("")) {
                        openActivity(LoginActivity.class);
                    } else {
                        openActivity(ShoppingCarActivity.class);
                        binding.mainDrawerLayout.closeDrawer(GravityCompat.START);
                    }
                    break;
                case R.id.my_order_root:
                    if (token == null || token.equals("")) {
                        openActivity(LoginActivity.class);
                    } else {
                        openActivity(MyOrderActivity.class);
                        binding.mainDrawerLayout.closeDrawer(GravityCompat.START);
                    }
                    break;
                case R.id.my_address_root:
                    if (token == null || token.equals("")) {
                        openActivity(LoginActivity.class);
                    } else {
                        openActivity(DistributionAddressActivity.class);
                        binding.mainDrawerLayout.closeDrawer(GravityCompat.START);
                    }
                    break;
                case R.id.customer_service_root:
                    if (token == null || token.equals("")) {
                        openActivity(LoginActivity.class);
                    } else {
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "021—566669882"));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                    break;
                case R.id.feedback_root:
                    if (token == null || token.equals("")) {
                        openActivity(LoginActivity.class);
                    } else {
                        openActivity(FeedBackActivity.class);
                        binding.mainDrawerLayout.closeDrawer(GravityCompat.START);
                    }

                    break;
                case R.id.logout:
//                    openActivity(MapActivity.class);
                    if (token == null || token.equals("")) {
                        openActivity(LoginActivity.class);
                    } else {
                        loginOut();
                    }

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
                        ToastUtil.showShortToast(getApplicationContext(), "选中");
                        binding.bottomRoot.setBackgroundResource(R.mipmap.complete_btn_bg);
                        binding.completeBtn.setVisibility(View.GONE);
                        binding.returnBtn.setVisibility(View.GONE);
                        mBtnStatus = 6;
//                        mBottomReturnStatus = 3;
//                        CompletePart();
                        getShoePartView();
                    } else if (mBottomReturnStatus == 3) {
                        //TODO   预留支付接口
                        ToastUtil.showShortToast(getApplicationContext(), "进行付款");
                        doPay();
                    }
                case R.id.add_to_shopping_btn:
                    break;
//                case R.id.close:
////                    binding.titleRoot.setVisibility(View.GONE);
//                    break;
                default:
                    break;
            }
        }

    }
}
