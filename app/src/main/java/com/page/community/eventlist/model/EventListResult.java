package com.page.community.eventlist.model;

import com.framework.domain.response.BaseResult;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shucheng.qu on 2017/8/24.
 */

public class EventListResult extends BaseResult {


    public Data data;

    public static class Data implements Serializable {
        public int totalNum;
        public List<ActivityList> activityList;

        public static class ActivityList implements Serializable {
            public int id;
            public String title;
            public Object pic;
            public String time;
            public int islimit;
            public int persons;
            public String place;
            public String intro;
            public String createtime;
            public Object customername;
        }
    }
}
