package com.page.home;

import com.framework.domain.response.BaseResult;

import java.util.List;

/**
 * Created by chenxi.cui on 2017/9/12.
 */

public class WorkerRepairResult extends BaseResult {

    public WorkerRepairData data;

    public static class WorkerRepairData implements BaseData {
        public int totalNum;
        public List<repair> repairList;
    }

    /**
     * "address":"123123",
     * 09-12 15:43:48.305 7539-7664/com.haolb.worker V/response: 				"statusCN":"已评价",
     * 09-12 15:43:48.305 7539-7664/com.haolb.worker V/response: 				"createtime":"2017-09-09 12:17:31",
     * 09-12 15:43:48.305 7539-7664/com.haolb.worker V/response: 				"status":7,
     * 09-12 15:43:48.305 7539-7664/com.haolb.worker V/response: 				"id":6,
     * 09-12 15:43:48.305 7539-7664/com.haolb.worker V/response: 				"phone":"13212345678",
     * 09-12 15:43:48.305 7539-7664/com.haolb.worker V/response: 				"intro":"我勒个去",
     * 09-12 15:43:48.305 7539-7664/com.haolb.worker V/response: 				"pic":"123123"
     * 09-12 15:43:48.305 7539-7664/com.haolb.worker V/response: 			}
     */
    public static class repair implements BaseData {
        public String statusCN;
        public String address;
        public String createtime;
        public String phone;
        public String intro;
        public String pic;
        public int id;
        public int status;
        public int type;
    }
}
