package com.page.home.model;

import com.framework.domain.response.BaseResult;

/**
 * Created by chenxi.cui on 2017/9/17.
 */

public class ContactResult extends BaseResult {
    public ContactData data;

    public static class ContactData implements BaseData {
        public String content;
    }
}
