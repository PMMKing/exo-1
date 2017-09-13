package com.page.login.activity;

import com.framework.domain.response.BaseResult;

/**
 * Created by chenxi.cui on 2017/9/12.
 */

public class LoginResult extends BaseResult {

    public LoginData data;

    public static class LoginData implements BaseData {
        public String token;
        public String portrait;
        public String userId;
        public String phone;
        public String roomId;
        public String nickname;
    }
}
