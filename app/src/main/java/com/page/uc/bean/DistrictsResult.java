package com.page.uc.bean;

import com.framework.domain.response.BaseResult;

import java.util.List;

/**
 * Created by chenxi.cui on 2017/8/19.
 */

public class DistrictsResult extends BaseResult {
    public DistrictsData data;

    public static class DistrictsData implements BaseData {
        public List<ComBean> districts;
    }
}
