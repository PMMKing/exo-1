package com.page.store.orderaffirm.activity;

import com.framework.domain.response.BaseResult;

/**
 * Created by chenxi.cui on 2017/9/16.
 */

public class SubmitResult extends BaseResult {
    public SubmitData data;

    public static class SubmitData implements BaseData {
        public int id;
    }
}
