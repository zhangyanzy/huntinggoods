package com.jinxun.hunting_goods.weight.cleartextfield.validator;

/**
 * Created by wangcong on 2017/3/1.
 */

public interface FieldValidator<T> {

    boolean isValid(T editText);

    String getError();

}
