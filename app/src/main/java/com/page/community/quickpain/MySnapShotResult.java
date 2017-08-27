package com.page.community.quickpain;

import com.framework.domain.response.BaseResult;

import java.util.List;

/**
 * Created by chenxi.cui on 2017/8/27.
 */

public class MySnapShotResult extends BaseResult {
    public MySnapShotData data;

    public static class MySnapShotData implements BaseData {
        public List<SnapShot> snapshots;
    }

    public static class SnapShot implements BaseData {
        public String id;
        public String pic1;
        public String pic2;
        public String pic3;
        public String intro;
        public String createtime;
    }

}
