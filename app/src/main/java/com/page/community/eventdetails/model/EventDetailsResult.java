package com.page.community.eventdetails.model;

import com.framework.domain.response.BaseResult;

import java.io.Serializable;

/**
 * Created by shucheng.qu on 2017/8/29.
 */

public class EventDetailsResult extends BaseResult {


    public Data data;

    public static class Data implements Serializable {
        public int id;
        public String title;
        public String pic;
        public String time;
        public int islimit;
        public String persons;
        public String place;
        public String intro;
        public String createtime;
        public String customername;
    }
}
