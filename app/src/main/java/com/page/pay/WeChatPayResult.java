package com.page.pay;

import com.framework.domain.response.BaseResult;

import java.io.Serializable;

/**
 * Created by chenxi.cui on 2017/9/20.
 */

public class WeChatPayResult extends BaseResult {

    public WeChatPayData data;
    public static class WeChatPayData implements BaseData {
        public String sign;
        public String appid;
        public String timestamp;
        public String prepayid;
        public String nonce_str;
        public String packageStr;
        public String partnerid;
    }
}
