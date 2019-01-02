package com.jinxun.hunting_goods.network;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by neil on 2017/8/4.
 */
@Documented
@Target(PARAMETER)
@Retention(RUNTIME)
public @interface PartName {
  String value() default "";
}