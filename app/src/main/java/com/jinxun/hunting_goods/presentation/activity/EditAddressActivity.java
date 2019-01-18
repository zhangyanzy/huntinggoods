package com.jinxun.hunting_goods.presentation.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.jinxun.hunting_goods.R;
import com.jinxun.hunting_goods.base.BaseActivity;
import com.jinxun.hunting_goods.databinding.ActivityEditAddressBinding;
import com.jinxun.hunting_goods.network.HttpSubscriber;
import com.jinxun.hunting_goods.network.api.address.usercase.ChangeAddress;
import com.jinxun.hunting_goods.network.api.address.usercase.DeleteAddress;
import com.jinxun.hunting_goods.network.api.address.usercase.GetAddressById;
import com.jinxun.hunting_goods.network.api.address.usercase.GetAreaCase;
import com.jinxun.hunting_goods.network.api.address.usercase.GetCityCase;
import com.jinxun.hunting_goods.network.api.address.usercase.GetProvinceCase;
import com.jinxun.hunting_goods.network.api.address.usercase.PostAddress;
import com.jinxun.hunting_goods.network.bean.Response;
import com.jinxun.hunting_goods.network.bean.address.AddAddressRequest;
import com.jinxun.hunting_goods.network.bean.address.AddressEntity;
import com.jinxun.hunting_goods.network.bean.address.ProvinceEntity;
import com.jinxun.hunting_goods.util.Constants;
import com.jinxun.hunting_goods.util.IsEmpty;
import com.jinxun.hunting_goods.util.SpUtils;
import com.jinxun.hunting_goods.util.ToastUtil;
import com.jinxun.hunting_goods.weight.NavigationTopBar;
import com.wcong.validator.ValidateResultCall;
import com.wcong.validator.Validator;
import com.wcong.validator.rules.RequiredRule;
import com.wcong.validator.rules.regex.PhoneNumberRule;

import java.util.ArrayList;
import java.util.List;

public class EditAddressActivity extends BaseActivity implements NavigationTopBar.NavigationTopBarClickListener {


    private ActivityEditAddressBinding binding;

    private OptionsPickerView mOptionsPickerView;

    private List<ProvinceEntity> provinceLists = new ArrayList<>();//省
    private List<ProvinceEntity> cityLists = new ArrayList<>();//市
    private List<ProvinceEntity> areaLists = new ArrayList<>();//区

    private Validator mAddressValidator;//地址校验
    private AddAddressRequest mRequest;

    private Long provinceCode;
    private Long cityCode;
    private Long areaCode;
    private String id;
    private String token;

    private boolean isChange = false;

    @Override
    protected void initComponent() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_address);
        binding.setPresenter(new Presenter());
        registerEditValidator();
    }

    @Override
    protected void loadData(Bundle savedInstanceState) {
        token = (String) SpUtils.init(Constants.SPREF.TOKEN).get(Constants.SPREF.TOKEN, "");
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        if (null == id) { //新建地址
            isChange = false;
            return;
        }
        if (id != null) {//代表需要地址编辑
            binding.deleteAddressBtn.setVisibility(View.VISIBLE);
            getAddressById();
            isChange = true;
        }
    }


    private void getAddressById() {
        new GetAddressById(id, token).execute(new HttpSubscriber<AddressEntity>(EditAddressActivity.this) {
            @Override
            public void onSuccess(Response<AddressEntity> response) {
                AddressEntity entity = response.getData();
                binding.nameEdit.setText(entity.getName());
                binding.numEdit.setText(entity.getPhone());
                binding.provinceTv.setText(entity.getProvince());
                binding.cityTv.setText(entity.getCity());
                binding.areaTv.setText(entity.getDistrict());
                binding.addressInfoEdit.setText(entity.getAddress());
                if (entity.getIsDefault() == 1) {
                    binding.setDefault.setChecked(true);
                } else {
                    binding.setDefault.setChecked(false);
                }
                provinceCode = entity.getProvinceCode();
                cityCode = entity.getCityCode();
                areaCode = entity.getDistrictCode();
            }

            @Override
            public void onFailure(String errorMsg, Response<AddressEntity> response) {
                ToastUtil.showShortToast(getApplicationContext(), response.getMessage());
            }
        });
    }

    private void getProvince() {
        new GetProvinceCase().execute(new HttpSubscriber<ArrayList<ProvinceEntity>>(EditAddressActivity.this) {
            @Override
            public void onSuccess(Response<ArrayList<ProvinceEntity>> response) {
                provinceLists = response.getData();
                if (!IsEmpty.list(provinceLists))
                    setCityPicker("省份选择", provinceLists, 0);
            }

            @Override
            public void onFailure(String errorMsg, Response<ArrayList<ProvinceEntity>> response) {
                ToastUtil.showShortToast(getApplicationContext(), response.getMessage());
            }
        });
    }

    private void getCity() {
        if (provinceCode == null) {
            ToastUtil.showShortToast(getApplicationContext(), "请先选择省");
            return;
        }
        new GetCityCase(provinceCode).execute(new HttpSubscriber<ArrayList<ProvinceEntity>>(EditAddressActivity.this) {
            @Override
            public void onSuccess(Response<ArrayList<ProvinceEntity>> response) {
                cityLists = response.getData();
                if (!IsEmpty.list(cityLists))
                    setCityPicker("城市选择", cityLists, 1);
            }

            @Override
            public void onFailure(String errorMsg, Response<ArrayList<ProvinceEntity>> response) {
                ToastUtil.showShortToast(getApplicationContext(), response.getMessage());
            }
        });
    }

    private void getArea() {
        if (cityCode == null) {
            ToastUtil.showShortToast(getApplicationContext(), "请先选择市");
            return;
        }
        new GetAreaCase(cityCode).execute(new HttpSubscriber<ArrayList<ProvinceEntity>>(EditAddressActivity.this) {
            @Override
            public void onSuccess(Response<ArrayList<ProvinceEntity>> response) {
                areaLists = response.getData();
                if (!IsEmpty.list(areaLists))
                    setCityPicker("区域选择", areaLists, 2);
            }

            @Override
            public void onFailure(String errorMsg, Response<ArrayList<ProvinceEntity>> response) {
                ToastUtil.showShortToast(getApplicationContext(), response.getMessage());
            }
        });
    }


    private void deleteAddress() {
        new DeleteAddress(id, token).execute(new HttpSubscriber<Response>(EditAddressActivity.this) {
            @Override
            public void onSuccess(Response<Response> response) {
                ToastUtil.showShortToast(getApplicationContext(), response.getMessage());
                finish();
            }

            @Override
            public void onFailure(String errorMsg, Response<Response> response) {
                ToastUtil.showShortToast(getApplicationContext(), response.getMessage());
            }
        });
    }

    /**
     * 注册表达校验器
     */
    private void registerEditValidator() {
        mAddressValidator = new Validator();
        mAddressValidator.register(binding.nameEdit, new RequiredRule(getString(R.string.name_not_null)));
        mAddressValidator.register(binding.numEdit, new RequiredRule(getString(R.string.num_not_null)));
        mAddressValidator.register(binding.numEdit, new PhoneNumberRule(getString(R.string.num_not_sure)));
        mAddressValidator.register(binding.addressInfoEdit, new RequiredRule(getString(R.string.address_not_null)));
    }


    private void createAddress() {
        mRequest = new AddAddressRequest();
        mRequest.setToken(token);
        mRequest.setName(binding.nameEdit.getText().toString().trim());
        mRequest.setPhone(binding.numEdit.getText().toString().trim());
        mRequest.setProvince(binding.provinceTv.getText().toString().trim());
        mRequest.setCity(binding.cityTv.getText().toString().trim());
        mRequest.setDistrict(binding.areaTv.getText().toString().trim());
        mRequest.setAddress(binding.addressInfoEdit.getText().toString().trim());
        if (binding.setDefault.isChecked()) {
            mRequest.setIsDefault(1);
        } else {
            mRequest.setIsDefault(0);
        }
        mRequest.setProvinceCode(String.valueOf(provinceCode));
        mRequest.setCityCode(String.valueOf(cityCode));
        mRequest.setDistrictCode(String.valueOf(areaCode));
    }

    /**
     * 提交地址
     *
     * @param request
     */
    private void postAddress(AddAddressRequest request) {
        new PostAddress(request.getToken(), request.getName(), request.getPhone(), request.getProvince(),
                request.getProvinceCode(), request.getCity(), request.getCityCode(), request.getDistrict(),
                request.getDistrictCode(), request.getAddress(), request.getIsDefault()
        ).execute(new HttpSubscriber<Response>(EditAddressActivity.this) {
            @Override
            public void onSuccess(Response<Response> response) {
                ToastUtil.showShortToast(getApplicationContext(), response.getMessage());
                finish();
            }

            @Override
            public void onFailure(String errorMsg, Response<Response> response) {
                ToastUtil.showShortToast(getApplicationContext(), response.getMessage());
            }
        });
    }

    /**
     * 修改地址
     *
     * @param request
     */
    private void changeAddress(AddAddressRequest request) {
        Long uuid = Long.valueOf(this.id);
        new ChangeAddress(uuid, request.getToken(), request.getName(), request.getPhone(), request.getProvince(),
                request.getProvinceCode(), request.getCity(), request.getCityCode(), request.getDistrict(),
                request.getDistrictCode(), request.getAddress(), request.getIsDefault()).execute(new HttpSubscriber<Response>(EditAddressActivity.this) {
            @Override
            public void onSuccess(Response<Response> response) {
                ToastUtil.showShortToast(getApplicationContext(), response.getMessage());
                finish();
            }

            @Override
            public void onFailure(String errorMsg, Response<Response> response) {
                ToastUtil.showShortToast(getApplicationContext(), response.getMessage());
            }
        });


    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        binding.editAddressTopBar.setLeftImageResource(R.mipmap.back_icon);
        binding.editAddressTopBar.setCenterTitleText("编辑地址");
        binding.editAddressTopBar.setViewGone();
        binding.editAddressTopBar.setNavigationTopBarClickListener(this);
    }

    @Override
    protected void initListeners() {

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

    private void setCityPicker(String titleText, final List list, final int addressType) {
        mOptionsPickerView = new OptionsPickerView.Builder(EditAddressActivity.this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                if (addressType == 0) {
                    binding.provinceTv.setText(provinceLists.get(options1).getAreaName());
                    provinceCode = provinceLists.get(options1).getAreaCode();
                } else if (addressType == 1) {
                    binding.cityTv.setText(cityLists.get(options1).getAreaName());
                    cityCode = cityLists.get(options1).getAreaCode();
                } else {
                    binding.areaTv.setText(areaLists.get(options1).getAreaName());
                    areaCode = areaLists.get(options1).getAreaCode();
                }
            }
        }).setTitleText(titleText).build();
        mOptionsPickerView.setPicker(list);
        mOptionsPickerView.show();

    }

    public class Presenter {
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.province_tv:
                    getProvince();
                    break;
                case R.id.city_tv:
                    getCity();
                    break;
                case R.id.area_tv:
                    getArea();
                    break;
                case R.id.save_address_btn:
                    mAddressValidator.validateAll(new ValidateResultCall() {
                        @Override
                        public void onSuccess() {
                            if (provinceCode == null || cityCode == null || areaCode == null) {
                                ToastUtil.showShortToast(getApplicationContext(), "请完善省市区");
                                return;
                            } else {
                                createAddress();
                                if (isChange) {
                                    changeAddress(mRequest);
                                } else {
                                    postAddress(mRequest);
                                }
                            }
                        }

                        @Override
                        public void onFailure(TextView view, String message) {
                            ToastUtil.showShortToast(getApplicationContext(), message);
                        }
                    });
                    break;
                case R.id.delete_address_btn:
                    deleteAddress();
                    break;
                default:
                    break;
            }
        }
    }
}
