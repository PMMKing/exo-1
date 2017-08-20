package com.page.uc.bean;

import com.framework.domain.response.BaseResult;

import java.util.List;

/**
 * Created by chenxi.cui on 2017/8/19.
 */

public class UnitsResult extends BaseResult {
    public UnitsData data;

    public static class UnitsData implements BaseData {
        public List<ComBean> datas;
    }
}
