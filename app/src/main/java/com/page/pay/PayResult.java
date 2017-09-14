package com.page.pay;

import com.framework.domain.response.BaseResult;

public class PayResult extends BaseResult {

	public PayData data;
	public static class PayData implements BaseData {
		public String params;
	}


}
