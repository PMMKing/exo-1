package com.framework.net;


import com.framework.domain.response.BaseResult;
import com.framework.utils.Enums;
import com.page.uc.bean.AddressResult;
import com.page.uc.bean.LoginResult;

/**
 * @author zexu
 */
public enum ServiceMap implements Enums.IType {
    OPENGATE("/opengate.do", BaseResult.class), //获取通讯录联系人
    getLinks("/getLinks.do", BaseResult.class),
    checkVersion("/checkVersion.do", BaseResult.class),
    getAddresses("/getAddresses.do", AddressResult.class),
    getDistricts("/getDistricts.do", BaseResult.class),//获取小区列表
    getBuildings("/getBuildings.do", BaseResult.class),//获取栋号列表
    getUnits("/getUnits.do", BaseResult.class),//获取单元列表
    getVerificationCode("/getVerificationCode.do", BaseResult.class),
    customerLogin("/customerLogin.do", LoginResult.class),
    customerLogout("/customerLogout.do", BaseResult.class),
    ;

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
