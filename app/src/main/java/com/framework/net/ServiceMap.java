package com.framework.net;


import com.framework.domain.response.BaseResult;
import com.framework.utils.Enums;
import com.page.address.AddressResult;
import com.page.community.eventlist.model.EventListResult;
import com.page.community.quickpain.MySnapShotResult;
import com.page.community.serve.model.ServeResult;
import com.page.home.model.LinksResult;
import com.page.uc.bean.BuidingsResult;
import com.page.uc.bean.DistrictsResult;
import com.page.uc.bean.LoginResult;
import com.page.uc.bean.NickNameResult;
import com.page.uc.bean.RegiserResult;
import com.page.uc.bean.RoomsResult;
import com.page.uc.bean.UnitsResult;
import com.page.uc.bean.UpdateMyPortraitResult;

import java.io.Serializable;

/**
 * @author zexu
 */
public enum ServiceMap implements Enums.IType, Serializable {
    OPENGATE("/opengate.do", BaseResult.class), //获取通讯录联系人
    getLinks("/getLinks.do", LinksResult.class),
    checkVersion("/checkVersion.do", BaseResult.class),
    getAddresses("/getAddresses.do", AddressResult.class),
    submitAddress("/submitAddress.do", BaseResult.class),
    updateAddress("/updateAddress.do", BaseResult.class),
    deleteAddress("/deleteAddress.do", BaseResult.class),
    setDefaultAddress("/setDefaultAddress.do", BaseResult.class),
    getDistricts("/getDistricts.do", DistrictsResult.class),//获取小区列表
    getBuildings("/getBuildings.do", BuidingsResult.class),//获取栋号列表
    getUnits("/getUnits.do", UnitsResult.class),//获取单元列表
    getRooms("/getRooms.do", RoomsResult.class),//获取单元列表
    getVerificationCode("/getVerificationCode.do", BaseResult.class),
    quickRegister("/quickRegister.do", RegiserResult.class),
    customerLogin("/customerLogin.do", LoginResult.class),
    customerLogout("/customerLogout.do", BaseResult.class),
    updateNickname("/updateNickname.do", NickNameResult.class),
    UPDATE_MY_PROTRAIT("/updateMyPortrait.do", UpdateMyPortraitResult.class, ServiceMap.NET_TASKTYPE_FILE),
    uploadPic("/uploadPic.do", UpdateMyPortraitResult.class, ServiceMap.NET_TASKTYPE_FILE),

    getWaters("/getWaters.do", ServeResult.class),//送水
    getHouses("/getHouses.do", ServeResult.class),//家政
    getWashes("/getWashes.do", ServeResult.class),//洗衣
    getActivityList("/getActivityList.do", EventListResult.class),//首页活动列表
    getActivity("/getActivity.do", BaseResult.class),//活动详情
    submitActivity("/submitActivity.do", BaseResult.class),//添加活动
    submitSnapshot("/submitSnapshot.do", BaseResult.class),
    getMySnapshots("/getMySnapshots.do", MySnapShotResult.class),;


    private final String mType;
    private final Class<? extends BaseResult> mClazz;
    private final int mTaskType;
    private final static int NET_TASK_START = 0;
    public final static int NET_TASKTYPE_CONTROL = NET_TASK_START;
    public final static int NET_TASKTYPE_FILE = NET_TASKTYPE_CONTROL + 1;
    public final static int NET_TASKTYPE_ALL = NET_TASKTYPE_FILE + 1;

    ServiceMap(String type, Class<? extends BaseResult> clazz) {
        this(type, clazz, NET_TASKTYPE_CONTROL);
    }

    ServiceMap(String type, Class<? extends BaseResult> clazz, int taskType) {
        this.mType = type;
        this.mClazz = clazz;
        this.mTaskType = taskType;
    }

    /**
     * 创建接口对应的resp的Result的对象
     *
     * @return AbsResult或其子类的对象
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    @SuppressWarnings("unchecked")
    public <T extends BaseResult> T newResult() throws IllegalAccessException, InstantiationException {
        return (T) getClazz().newInstance();
    }

    @Override
    public String getDesc() {
        return mType;
    }

    public Class<? extends BaseResult> getClazz() {
        return mClazz;
    }

    @Override
    public int getCode() {
        return mTaskType;
    }
}
