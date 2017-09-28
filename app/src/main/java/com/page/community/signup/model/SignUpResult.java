package com.page.community.signup.model;

import com.framework.domain.response.BaseResult;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shucheng.qu on 2017/9/28.
 */

public class SignUpResult extends BaseResult {

    public Data data;

    public static class Data implements Serializable {
        public List<Datas> datas;

        public static class Datas implements Serializable {
            public String id;
            public String nickname;
            public String phone;
        }
    }
}
