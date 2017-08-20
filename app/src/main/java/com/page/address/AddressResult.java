package com.page.address;

import com.framework.domain.response.BaseResult;

import java.util.List;

/**
 * Created by chenxi.cui on 2017/8/19.
 */

public class AddressResult extends BaseResult {
    public AddressData data;
    public static class AddressData implements BaseData {
        public List<Address> addresses;
    }
}
