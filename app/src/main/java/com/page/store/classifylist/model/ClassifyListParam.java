package com.page.store.classifylist.model;

import com.framework.domain.param.BaseParam;

/**
 * Created by shucheng.qu on 2017/9/5.
 */

public class ClassifyListParam extends BaseParam {

    public String categoryId;
    public int pageNo = 1;
    public int pageSize = 40;
}
