package com.jinxun.hunting_goods.network.api.shoe.usercase;

import com.jinxun.hunting_goods.network.BaseUseCase;
import com.jinxun.hunting_goods.network.api.shoe.ShoeServiceApi;

import rx.Observable;

/**
 * Created by zhangyan on 2018/12/13.
 */

public class CompletePart extends BaseUseCase<ShoeServiceApi> {

    private String userId;//用户ID
    private String productId;//产品ID
    private String productName;//产品名称



    private String positionId;//部位ID
    private String materialId;//材料ID

    private String positionName;//部位名称
    private String materialName;//材料名称

    private String colorName;//颜色名称
    private String colorId;//颜色ID


    public CompletePart(String userId, String productId, String positionId, String materialId, String colorId,
                        String productName, String positionName, String materialName, String colorName) {
        this.userId = userId;
        this.productId = productId;
        this.positionId = positionId;
        this.materialId = materialId;
        this.colorId = colorId;
        this.productName = productName;
        this.positionName = positionName;
        this.materialName = materialName;
        this.colorName = colorName;
    }

    @Override
    protected Observable buildCase() {
        return createConnection().completePartEdit(userId,productId,positionId,materialId,colorId,productName,
                positionName,materialName,colorName);
    }
}
