package com.page.uc.bean;

import com.framework.domain.response.BaseResult;

/**
 * Created by chenxi.cui on 2017/8/27.
 */

public class NickNameResult extends BaseResult {
    public NickNameData data;

    public static class NickNameData implements BaseData {
        public String nickname;

    }
}
