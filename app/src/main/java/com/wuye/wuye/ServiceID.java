package com.wuye.wuye;


import com.wuye.wuye.net.base.BaseResult;
import com.wuye.wuye.page.home.model.AccountResult;
import com.wuye.wuye.page.login.model.LoginResult;

/**
 * Created by shucheng.qu on 2017/6/5.
 */

public enum ServiceID {

    LOGINID("100002", LoginResult.class),//登录
    REGISTERACCOUNT("100003", BaseResult.class),//注册
    ACCOUNT("200003", AccountResult.class),//账户信息
    DELETEUCARD("400004", BaseResult.class),//删除银行卡
    ADDCARD("400005", BaseResult.class),//添加银行卡
    KOREAACTIVATE("300001", BaseResult.class),//新韩卡激活
    KOREALOSE("300002", BaseResult.class),//新韩卡挂失
    KOREAREFUND("300003", BaseResult.class),//新韩卡提现
    KOREAPAY("300004", BaseResult.class),//新韩卡充值
    LOGOUT("200005", BaseResult.class),//注销用户
    LOGINPSDRESET("200002", BaseResult.class),//登录密码重置
    PAYPWDRESET("200001", BaseResult.class),//支付密码重置
    ;

    private final String serviceID;
    private final Class<? extends BaseResult> mClazz;

    ServiceID(String serviceID, Class<? extends BaseResult> mClazz) {
        this.serviceID = serviceID;
        this.mClazz = mClazz;
    }

    public Class<? extends BaseResult> getClazz() {
        return mClazz;
    }

    public String getServiceID() {
        return serviceID;
    }

}
