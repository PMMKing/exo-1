package com.page.uc.payfee.model;

import com.framework.domain.param.BaseParam;
import com.page.uc.payfee.model.WaitFeeResult.Data.Datas;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by shucheng.qu on 2017/9/14.
 */

public class WaitFeeParam extends BaseParam {
    public ArrayList<Datas> params = new ArrayList<Datas>();

}
