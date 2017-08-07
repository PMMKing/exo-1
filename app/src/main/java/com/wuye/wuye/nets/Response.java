package com.wuye.wuye.nets;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wuye.wuye.config.AppConfig;
import com.wuye.wuye.net.base.BaseResult;
import com.wuye.wuye.utils.LogUtils;

/**
 * 处理数据解析
 *
 * @author zexu
 */
public final class Response {
    private Response() {
    }

    public static BaseResult dealWithResponse(byte[] data, NetworkParam param) {
        BaseResult result = null;
        if (data == null) {
            return null;
        }
        try {
            String str = null;
            ServiceMap serviceType = param.key;
            switch (serviceType.getCode()) {
                // 正常的请求
                case ServiceMap.NET_TASKTYPE_CONTROL:
                case ServiceMap.NET_TASKTYPE_FILE:
                    str = new String(data);
                    if (param.ke != null) {
                        //数据解密
//                        str = SecureUtil.decode(str, param.ke);
                    }
                    break;
            }
            LogUtils.d("response", "API=" + serviceType.name());
            if (AppConfig.DEBUG) {
                JSONObject obj = null;
                try {
                    obj = JSON.parseObject(str);
                } catch (Exception e) {
                    LogUtils.d("response", String.valueOf(str));
                }
                String[] formattedJsons = JSONObject.toJSONString(obj, true).split("\n");

                for (String line : formattedJsons) {
                    LogUtils.d("response", line);
                }
            }
            result = JSON.parseObject(str, serviceType.getClazz());
        } catch (Exception e) {
            LogUtils.d(e.getMessage());
        }
        return result;
    }

}
