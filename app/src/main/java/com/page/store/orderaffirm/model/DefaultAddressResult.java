package com.page.store.orderaffirm.model;

import com.framework.domain.response.BaseResult;

import java.io.Serializable;

/**
 * Created by shucheng.qu on 2017/9/7.
 */

public class DefaultAddressResult extends BaseResult {

    public Address data;

    public static class Address implements Serializable{
        public String cid;
        public String name;
        public String phone;
        public String address;
    }
}
