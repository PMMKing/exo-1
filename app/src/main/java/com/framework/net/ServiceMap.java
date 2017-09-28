package com.framework.net;


import com.framework.domain.response.BaseResult;
import com.framework.utils.Enums;
import com.page.address.AddressResult;
import com.page.community.details.model.RepairDetailResult;
import com.page.community.eventdetails.model.EventDetailsResult;
import com.page.community.eventlist.model.EventListResult;
import com.page.community.quickpain.model.QpDetailResult;
import com.page.community.quickpain.model.ScommentsReault;
import com.page.community.serve.model.RepairResult;
import com.page.community.serve.model.SerDetailResult;
import com.page.community.serve.model.ServeResult;
import com.page.community.signup.model.SignUpResult;
import com.page.home.model.ContactResult;
import com.page.home.model.LinksResult;
import com.page.home.model.NoticeResult;
import com.page.home.model.QpListResult;
import com.page.home.model.ShopRecResult;
import com.page.pay.ProductPayResult;
import com.page.pay.WeChatPayResult;
import com.page.store.home.model.ClassifyResult;
import com.page.store.collect.model.CollectResult;
import com.page.store.home.model.FoodRecResult;
import com.page.store.orderaffirm.activity.SubmitResult;
import com.page.store.orderaffirm.model.DefaultAddressResult;
import com.page.store.orderdetails.model.OrderDetailResult;
import com.page.store.orderlist.model.OrderListResult;
import com.page.store.prodetails.model.PDResult;
import com.page.store.prodetails.model.PEResult;
import com.page.uc.bean.BuidingsResult;
import com.page.uc.bean.DistrictsResult;
import com.page.uc.bean.LoginResult;
import com.page.uc.bean.NickNameResult;
import com.page.uc.bean.RegiserResult;
import com.page.uc.bean.RoomsResult;
import com.page.uc.bean.UnitsResult;
import com.page.uc.bean.UpdateMyPortraitResult;
import com.page.uc.payfee.model.ubmitWuyeFeeResult;
import com.page.uc.payfee.model.FeeListResult;
import com.page.uc.payfee.model.FeeMonthResult;
import com.page.uc.payfee.model.WaitFeeResult;

import java.io.Serializable;

/**
 * @author zexu
 */
public enum ServiceMap implements Enums.IType, Serializable {
    OPENGATE("/opengate.do", BaseResult.class), //获取通讯录联系人
    getLinks("/getLinks.do", LinksResult.class),
    checkVersion("/checkVersion.do", BaseResult.class),
    alipayPayProduct("/alipayPayProduct.do", ProductPayResult.class),
    alipayPayWuyeFee("/alipayPayWuyeFee.do", ProductPayResult.class),
    wechatPayProduct("/wechatPayProduct.do", WeChatPayResult.class),//微信商城
    wechatPayWuyeFee("/wechatPayWuyeFee.do", WeChatPayResult.class),//微信物业缴费
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
    getWaterDetail("/getWaterDetail.do", SerDetailResult.class),//送水
    getHouses("/getHouses.do", ServeResult.class),//家政
    getHouseDetail("/getHouseDetail.do", SerDetailResult.class),//家政
    getWashes("/getWashes.do", ServeResult.class),//洗衣
    getMerchants("/getMerchants.do", ServeResult.class),//周边
    contact("/contact.do", ContactResult.class),//周边
    getWashDetail("/getWashDetail.do", SerDetailResult.class),//洗衣
    getMerchantDetail("/getMerchantDetail.do", SerDetailResult.class),//洗衣
    getActivityList("/getActivityList.do", EventListResult.class),//首页活动列表
    getMyActivityList("/getMyActivityList.do", EventListResult.class),//我的活动列表
    getActivity("/getActivity.do", EventDetailsResult.class),//活动详情
    submitActivity("/submitActivity.do", BaseResult.class),//添加活动
    joinActivity("/joinActivity.do", BaseResult.class),//参与活动
    updateActivity("/updateActivity.do", BaseResult.class),//活动修改
    canceljoinActivity("/canceljoinActivity.do", BaseResult.class),//取消参与活动
    getActivityJoinerList("/getActivityJoinerList.do", SignUpResult.class),//获取活动参与人
    submitSnapshot("/submitSnapshot.do", BaseResult.class),//发布随手拍
    getMySnapshots("/getMySnapshots.do", QpListResult.class),
    deleteSnapshot("/deleteSnapshot.do", BaseResult.class),//删除
    getSnapshots("/getSnapshots.do", QpListResult.class),
    getSnapshot("/getSnapshot.do", QpDetailResult.class),//详情
    updateSnapshot("/updateSnapshot.do", QpDetailResult.class),//编辑
    scomment("/scomment.do", BaseResult.class),//评论
    scomments("/scomments.do", ScommentsReault.class),//评论列表
    deleteScomment("/deleteScomment.do", BaseResult.class),//评论del
    submitRepair("/submitRepair.do", BaseResult.class),//add维修
    getRepair("/getRepair.do", RepairDetailResult.class),//维修detail
    evaluateRepair("/evaluateRepair.do", BaseResult.class),//维修评价
    getMyRepairs("/getMyRepairs.do", RepairResult.class),//修list
    getCategorys("/getCategorys.do", ClassifyResult.class),//商品分类
    getProduct("/getProduct.do", PDResult.class),//商品分类详情
    submitOrder("/submitOrder.do", SubmitResult.class),//提交订单
    getMyOrders("/getMyOrders.do", OrderListResult.class),//订单list
    getOrder("/getOrder.do", OrderDetailResult.class),//订单详情
    cancelOrder("/cancelOrder.do", BaseResult.class),//订单
    updateOrderConfirm("/orderEvaluate.do", BaseResult.class),//订单
    orderEvaluate("/updateOrderConfirm.do", BaseResult.class),//订单
    orderRefund("/orderRefund.do", BaseResult.class),//订单
    fav("/fav.do", BaseResult.class),//收藏or取消
    getFavList("/getFavList.do", CollectResult.class),//收藏list
    getDefaultAddress("/getDefaultAddress.do", DefaultAddressResult.class),//默认收货地址
    validStorage("/validStorage.do", BaseResult.class),//验证库存
    pcomments("/pcomments.do", PEResult.class),//商品评论list
    getNoticeList("/getNoticeList.do", NoticeResult.class),//首页公告
    getRecommendCategorys("/getRecommendCategorys.do", FoodRecResult.class),//超时首页视频推荐
    getParticularProducts("/getParticularProducts.do", ShopRecResult.class),//首页
    getMyWuyeFees("/getMyWuyeFees.do", WaitFeeResult.class),//物业代缴费
    submitWuyeFee("/submitWuyeFee.do", ubmitWuyeFeeResult.class),//物业代缴费
    wuyeFeeMonths("/wuyeFeeMonths.do", FeeMonthResult.class),//物业代缴费
    wuyeFeeOrders("/wuyeFeeOrders.do", FeeListResult.class),//物业代缴费
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
