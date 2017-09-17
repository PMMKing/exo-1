package com.page.uc.bean;

import com.framework.domain.response.BaseResult;

/**
 * Created by chenxi.cui on 2017/8/19.
 * "cityname":"",
 08-21 23:06:47.386 2937-3061/com.haolb.client V/response: 		"isSupport":0,
 08-21 23:06:47.386 2937-3061/com.haolb.client V/response: 		"token":"1a880b0b5302480f93355c415a4850b0",
 08-21 23:06:47.386 2937-3061/com.haolb.client V/response: 		"nickname":"DyCtmF",
 08-21 23:06:47.386 2937-3061/com.haolb.client V/response: 		"userId":207,
 08-21 23:06:47.386 2937-3061/com.haolb.client V/response: 		"cityid":1,
 08-21 23:06:47.386 2937-3061/com.haolb.client V/response: 		"phone":"15811508404",
 08-21 23:06:47.386 2937-3061/com.haolb.client V/response: 		"expireTime":"1508513744131",
 08-21 23:06:47.386 2937-3061/com.haolb.client V/response: 		"hxpwd":"",
 08-21 23:06:47.386 2937-3061/com.haolb.client V/response: 		"portrait":"http://112.123.63.69:8081/yjwy/portrait",
 08-21 23:06:47.386 2937-3061/com.haolb.client V/response: 		"isActive":0

 *
 */

public class LoginResult extends BaseResult {

    public LoginData data;

    public static class LoginData implements BaseData {
        public String token;
        public String portrait;
        public String userId;
        public String phone;
        public String roomId;
        public String info;
        public String nickname;
    }
}
