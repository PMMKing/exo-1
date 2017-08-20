package com.page.uc.bean;

import com.framework.domain.response.BaseResult;

import java.util.List;

/**
 * Created by chenxi.cui on 2017/8/19.
 */

public class BuidingsResult extends BaseResult {
    public BuildingData data;

    public static class BuildingData implements BaseData {
        public List<ComBean> datas;
    }
}
