package com.page.uc.bean;

import com.framework.domain.response.BaseResult;

/**
 * Created by chenxi.cui on 2017/8/27.
 */

public class UpdateMyPortraitResult extends BaseResult {

    public UpdateMyPortraitData data;

    public static class UpdateMyPortraitData implements BaseData {

        public String portrait;
    }
}
