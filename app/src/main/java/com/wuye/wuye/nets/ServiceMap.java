package com.wuye.wuye.nets;


import com.wuye.wuye.net.base.BaseResult;
import com.wuye.wuye.page.login.model.LoginResult;

/**
 * 
 * @author zexu
 */
public enum ServiceMap implements Enums.IType {
    LOGOUT("/customerLogout.do",BaseResult.class),//退出
    GET_VERIFICATION_CODE("/getVerificationCode.do",BaseResult.class), //获取验证码
    FEED_BACK("/feedback.do",BaseResult.class), //反馈
    CHOOSE_COMMUNITY("/chooseCommunity.do",BaseResult.class), //
    SETDEFAULTCOMMUNITY("/setDefaultCommunity.do",BaseResult.class), //设置我的小区
    DEL_COMMUNITY("/deleteMyCommunity.do",BaseResult.class), //删除小区
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