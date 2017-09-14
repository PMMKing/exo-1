package com.page.pay;

import com.framework.domain.response.BaseResult;

public class ProductPayResult extends BaseResult {

	public PayData data;
	public static class PayData implements BaseData {
		public String params;
	}


}
