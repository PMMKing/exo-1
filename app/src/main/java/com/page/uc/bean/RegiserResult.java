package com.page.uc.bean;

import com.framework.domain.response.BaseResult;

/**
 * Created by chenxi.cui on 2017/8/20.
 */

public class RegiserResult extends BaseResult {
    public RegiserData data;
    public static class RegiserData {

        public String id;
        public String phone;
        public String nickname;
        public float balance;
    }
}
