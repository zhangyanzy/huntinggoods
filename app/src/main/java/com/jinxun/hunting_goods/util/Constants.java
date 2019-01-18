package com.jinxun.hunting_goods.util;

/**
 * Created by admin on 2019/1/8.
 */

public interface Constants {

    interface SPREF {
        /**
         * 保存在手机里面的文件名 用户有关
         */
        String FILE_USER_NAME = "share_data";
        /**
         * 保存在手机里面的文件名 应用有关
         */
        String FILE_APP_NAME = "share_app";
        /**
         * 是否开启分享功能
         */
        String IS_SHOW_SHARE = "isShowShare";

        //保存到sharedPreferenced的数据的key
        String IS_LOGIN = "isLogin";                //登录状态
        String LOGIN_MODE = "loginMode";            //登录方式
        String USER_ID = "userId";                  //用户id
        String USER_PHONE = "userPhone";            //手机号
        String USER_PHOTO = "headPortrait";         //头像
        String USER_NAME = "userName";              //用户姓名
        String NICK_NAME = "nickName";              //用户昵称
        String GENDER = "gender";                   //性别
        String AGE = "age";                         //年龄
        String TOKEN = "token";                     //用户登录返回的唯一识别码
        String DEVICE_TOKEN = "deviceToken";        //友盟推送的设备识别号
        String ALIAS = "alias";                     // 友盟推送的用户别名
        String INVITE_CODE = "inviteCode";          // 邀請碼
        String IS_CERTIFICATION = "realInfoAuditStatus"; //是否通过实名认证


        //        int TYPE_PHONE = 0;    //手机登录
        int TYPE_WECHAT = 1;   //微信登录
        int TYPE_QQ = 2;       //qq登陆
        int TYPE_SINA = 3;     //新浪微博登录
        int TYPE_ALI = 4;      //支付宝账户
        int TYPE_BANK = 5;     //银行卡账户
        int TYPE_PHONE = 6;    //手机登录

        String SERVICE_PHONE = "servicePhone";      //客服电话
        String SEARCH_HISTORY = "searchHistory"; //搜索历史
        String AREA_NAME = "areaName"; //用户定位城市名称
        String AREA_CODE = "areaCode"; //用户定位城市Code
        String MESSAGE_PUSH = "messagePush"; //消息推送时间
        String ACTIVITY_RANGE = "activityRange"; //首页获取线下活动范围
        String SHOW_NEWER_ACTIVITY = "isShowNewerActivity";   //是否弹窗提示新手任务

    }
}
