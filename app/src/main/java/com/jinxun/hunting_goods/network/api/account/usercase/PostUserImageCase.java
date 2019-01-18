package com.jinxun.hunting_goods.network.api.account.usercase;

import com.jinxun.hunting_goods.network.BaseUseCase;
import com.jinxun.hunting_goods.network.api.account.AccountServiceApi;

import java.util.HashMap;
import java.util.Map;

import okhttp3.RequestBody;
import rx.Observable;

/**
 * Created by admin on 2019/1/8.
 */

public class PostUserImageCase extends BaseUseCase<AccountServiceApi> {

//    private String token;
    private Map<String, RequestBody> params;

    public PostUserImageCase(Map<String, RequestBody> params) {
//        this.token = token;
        this.params = params;
    }

    @Override
    protected Observable buildCase() {
        return createConnection().upLoadImg(params);
    }
}
