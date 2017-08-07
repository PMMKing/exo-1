package com.wuye.wuye.nets;

import android.content.Context;
import android.database.Cursor;
import android.net.Proxy;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.wuye.wuye.WApplication;
import com.wuye.wuye.config.AppConfig;
import com.wuye.wuye.net.base.BaseResult;
import com.wuye.wuye.utils.LogUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.util.Iterator;
import java.util.LinkedList;

public class NetworkManager implements TaskListener {


    private static class QTasks extends AsyncTask<Void, Integer, BaseResult> {

        private NetworkTask networkTask = null;
        private TaskListener listener = null;
        String proxyHost;
        int proxyPort;
        private String hostUrl;

        public QTasks(TaskListener aListener, NetworkTask netTask) {
            listener = aListener;
            networkTask = netTask;
        }

        public boolean cancelWithHandler(Handler handler) {
            if (networkTask.handler == handler && networkTask.param.cancelAble) {
                networkTask.cancel = true;
                cancel(true);
                return true;
            }
            return false;
        }

        public boolean cancelWithKey(ServiceMap key) {
            if (networkTask.param.key == key && networkTask.param.cancelAble) {
                networkTask.cancel = true;
                cancel(true);
                return true;
            }
            return false;
        }

        public boolean cancelWithType(int type) {
            // type为all时可以结束cancelable的类型
            if (type == ServiceMap.NET_TASKTYPE_ALL) {
                networkTask.cancel = true;
                cancel(true);
                return true;
            } else if (networkTask.param.key.getCode() == type && networkTask.param.cancelAble) {
                networkTask.cancel = true;
                cancel(true);
                return true;
            }
            return false;
        }

        public boolean cancelWithUrl(String url) {
            if (networkTask.param.url.equals(url) && networkTask.param.cancelAble) {
                networkTask.cancel = true;
                cancel(true);
                return true;
            }
            return false;
        }

        public boolean cancelWithParam(NetworkParam param) {
            if (networkTask.param.equals(param) && networkTask.param.cancelAble) {
                networkTask.cancel = true;
                cancel(true);
                return true;
            }
            return false;
        }

        public NetworkParam getNetworkParam() {
            return networkTask == null ? null : networkTask.param;
        }

        @SuppressWarnings("deprecation")
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (!networkTask.cancel) {
                // set host
                switch (networkTask.param.key.getCode()) {
                    default:
                        // 未指定host使用默认
                        hostUrl = TextUtils.isEmpty(networkTask.param.hostPath) ? AppConfig.COMMON_URL + networkTask.param.key.getDesc()
                                : networkTask.param.hostPath;
                        break;
                }
//                // -- end -- set host
//                // set proxy
//                if (!NetConnChangeReceiver.netGetted) {
//                    NetConnChangeReceiver.dealNetworkInfo(MainApplication.getInstance());
//                }
//                if (NetConnChangeReceiver.wifi) {
//                    // WIFI: global http proxy
//                    proxyHost = NetworkManager.getProxyHost(false);
//                    proxyPort = NetworkManager.getProxyPort(false);
//                    if (EqualUtils.equalsIgnoreCase(proxyHost, Proxy.getDefaultHost())) {
//                        proxyHost = null;
//                    }
//                } else {
//                    // GPRS: APN http proxy
//                    proxyHost = NetworkManager.getProxyHost(true);
//                    proxyPort = NetworkManager.getProxyPort(true);
//                }
//                // -- end -- set proxy

            } else {
                cancel(false);
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            if (listener != null) {
                listener.onTaskCancel(networkTask);
            }
        }

        public byte[] doRequest() {
//            if (networkTask.param.param == null) {
//                return null;
//            }
//
//            networkTask.param.param.cparam = new CommonParam();
//            networkTask.param.param.cparam.imei = imei;
//            networkTask.param.param.cparam.versionCode = MainApplication.getInstance().versionCode;
//            networkTask.param.param.cparam.versionName = MainApplication.getInstance().versionName;
//            networkTask.param.param.cparam.city = UCUtils.getInstance().getCity().id;
//            String userId = UCUtils.getInstance().getUserid();
//            networkTask.param.param.cparam.userId = TextUtils.isEmpty(userId) ? null : Integer.parseInt(userId);
//            String token = UCUtils.getInstance().getToken();
//            networkTask.param.param.cparam.token = TextUtils.isEmpty(token) ? "" : token;
//            String bjson = JSON.toJSONString(networkTask.param.param);
//            String b = SecureUtil.encode(bjson, networkTask.param.ke);
//            networkTask.param.url = "b=" + b + "&key=" + networkTask.param.ke + "&ver=1";
//
//
//            if (networkTask.cancel) {
//                return null;
//            }
//
//            if (AppConfig.DEBUG) {
//                synchronized (LogUtils.class) {
//                    LogUtils.v("request", "API=" + networkTask.param.key.name());
//                    LogUtils.v("request", networkTask.param.url);
//                    LogUtils.v("request", "b=" + JSON.toJSONString(networkTask.param.param, true));
//                }
//            }
//            FileInputStream fis = null;
//            HttpClient httpClient = NetworkManager.getHttpClient(proxyHost, proxyPort);
//            try {
//                HttpPost request;
//
//                if (networkTask.param.key.getCode() == ServiceMap.NET_TASKTYPE_CONTROL) {
//                    request = new HttpPost(hostUrl);
//                    request.addHeader("Content-Type", "application/x-www-form-urlencoded");
//                    // request.addHeader("User-Agent",
//                    // "Mozilla/5.0 (Linux; U; Android 2.2) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1");
//                    request.setEntity(new StringEntity(networkTask.param.url));
//                } else if (networkTask.param.key.getCode() == ServiceMap.NET_TASKTYPE_FILE) {
//                    if (TextUtils.isEmpty(networkTask.param.filePath)) {
//                        request = new HttpPost(hostUrl);
//                        request.addHeader("Content-Type", "application/x-www-form-urlencoded");
//                        // request.addHeader("User-Agent",
//                        // "Mozilla/5.0 (Linux; U; Android 2.2) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1");
//                        request.setEntity(new StringEntity(networkTask.param.url));
//                    } else {
//                        String url = hostUrl;
//                        if (url.lastIndexOf("?") != -1) {
//                            url += "&";
//                        } else {
//                            url += "?";
//                        }
//                        url += networkTask.param.url;
//                        request = new HttpPost(url);
//                        request.addHeader("Content-Type", "application/octet-stream");
//                        File file = new File(networkTask.param.filePath);
//                        InputStreamEntity ise = new InputStreamEntity(fis = new FileInputStream(file), file.length());
//                        request.setEntity(ise);
//                    }
//                } else {
//                    //不支持的类型
//                    return null;
//                }
//
//
//                HttpResponse response = httpClient.execute(request);
//                int statusCode = response.getStatusLine().getStatusCode();
//                LogUtils.v("response", "http status code : %d" + statusCode);
//                if (statusCode == HttpStatus.SC_OK) {
//                    byte[] data = EntityUtils.toByteArray(response.getEntity());
//                    if (!networkTask.cancel) {
//                        return data;
//                    }
//                }
//            } catch (Exception e) {
//                LogUtils.e("error", e.getLocalizedMessage() + e);
//            } finally {
//                if (httpClient != null) {
//                    httpClient.getConnectionManager().shutdown();
//                }
//                if (fis != null) {
//                    try {
//                        fis.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
            return null;
        }


        @Override
        protected BaseResult doInBackground(Void... params) {
            if (!networkTask.cancel) {
                BaseResult baseResult = Response.dealWithResponse(doRequest(), networkTask.param);
//            	if(networkTask.param.key == ServiceMap.GET_FRIENDS && baseResult != null) {
//            		if(baseResult.bstatus.code == 0) {
//            			//更新本地数据库记录
////            			FriendListResult friendListResult = (FriendListResult) baseResult;
//            			String uidStr = UCUtils.getInstance().getUserid();
//            			int myId = Integer.parseInt(uidStr);
////            			friendListResult.data.friendList = FriendManager.sync(friendListResult.data.friendList,myId);
//            		}
//            	}
                return baseResult;
            }
            cancel(true);
            return null;
        }

        @Override
        protected void onPostExecute(BaseResult result) {
            Message m = null;
            if (!networkTask.cancel) {
                networkTask.param.result = result;
                if (networkTask.handler != null) {
                    if (networkTask.param.result != null) {
                        m = networkTask.handler.obtainMessage(TaskStatus.SUCCESS, networkTask.param);
                    } else {
                        m = networkTask.handler.obtainMessage(TaskStatus.ERROR, networkTask.param);
                    }
                    networkTask.handler.sendMessage(m);
                }
                if (listener != null) {
                    listener.onTaskComplete(networkTask);
                }
            } else {
                if (listener != null) {
                    listener.onTaskCancel(networkTask);
                }
            }
        }

    }

    private static NetworkManager singleInstance = null;

    public static NetworkManager getInstance() {
        synchronized (NetworkManager.class) {
            if (singleInstance == null) {
                singleInstance = new NetworkManager();
            }
        }
        return singleInstance;
    }

    private final LinkedList<NetworkTask> listSequence = new LinkedList<NetworkTask>();
    @SuppressWarnings("unchecked")
    private final LinkedList<QTasks>[] tasks = new LinkedList[ServiceMap.NET_TASKTYPE_ALL];
    /**
     * 不设置上限
     */
    private final int maxCount = Integer.MAX_VALUE;
    public static final Uri PREFERRED_APN_URI = Uri.parse("content://telephony/carriers/preferapn");
    // 电信CTWAP时apn的名称:#777,ctwap
    public static final String CTWAP_APN_NAME_1 = "#777";
    public static final String CTWAP_APN_NAME_2 = "ctwap";

    private NetworkManager() {
        // maxCount = MainConstants.NET_MAXTASK_COUNT;
        tasks[ServiceMap.NET_TASKTYPE_CONTROL] = new LinkedList<QTasks>();
        tasks[ServiceMap.NET_TASKTYPE_FILE] = new LinkedList<QTasks>();
    }

    private boolean addCurrentTask(int taskType, QTasks task) {
        boolean suc = false;
        LinkedList<QTasks> taskList = tasks[taskType];
        if (taskList == null) {
            throw new IllegalArgumentException("no task type = " + taskType);
        }
        synchronized (taskList) {
            if (taskList.size() < maxCount) {
                taskList.add(task);
                suc = true;
            }
        }
        return suc;
    }

    public void addTask(NetworkParam param, Handler handler) {
        boolean isRepeat = false;
        NetworkTask task = new NetworkTask(param, handler);
        synchronized (this.listSequence) {
            Iterator<NetworkTask> listSequenceIterator = this.listSequence.iterator();
            while (listSequenceIterator.hasNext()) {
                NetworkTask networkTask = listSequenceIterator.next();
                NetworkParam tmp = networkTask.param;
                if (tmp.equals(param)) {
                    isRepeat = true;
                    break;
                }
            }

            LinkedList<QTasks> taskList = tasks[param.key.getCode()];
            if (taskList == null) {
                throw new IllegalArgumentException("param.key.getCode() returns not task type");
            }
            synchronized (taskList) {
                for (int i = 0; i < taskList.size(); i++) {
                    NetworkParam tmp = taskList.get(i).getNetworkParam();
                    if (tmp.equals(param)) {
                        isRepeat = true;
                        break;
                    }
                }
            }

            if (isRepeat) {
                return;
            }

            if (task.handler != null) {
                Message m = task.handler.obtainMessage(TaskStatus.START, task.param);
                task.handler.sendMessage(m);
            }

            switch (param.addType) {
                case Request.NET_ADD_ONORDER:
                    this.listSequence.add(task);
                    break;
                case Request.NET_ADD_INSERT2HEAD:
                    this.listSequence.add(0, task);
                    break;
                case Request.NET_ADD_CANCELPRE: {
                    Iterator<NetworkTask> it = this.listSequence.iterator();
                    while (it.hasNext()) {
                        NetworkTask nt = it.next();
                        if (param.key.getCode() == nt.param.key.getCode() && nt.param.cancelAble) {
                            it.remove();
                        }
                    }

                    synchronized (taskList) {
                        Iterator<QTasks> itt = taskList.iterator();
                        while (itt.hasNext()) {
                            QTasks qtask = itt.next();
                            qtask.cancelWithType(param.key.getCode());
                            itt.remove();
                        }
                    }

                    this.listSequence.add(0, task);
                }
                break;
                case Request.NET_ADD_CANCELSAMET: {

                    Iterator<NetworkTask> it = this.listSequence.iterator();
                    while (it.hasNext()) {
                        NetworkTask nt = it.next();
                        if (param.key == nt.param.key && nt.param.cancelAble) {
                            it.remove();
                        }
                    }
                    synchronized (taskList) {
                        Iterator<QTasks> itt = taskList.iterator();
                        while (itt.hasNext()) {
                            QTasks qtask = itt.next();
                            if (qtask.networkTask.param.key == param.key) {
                                qtask.cancelWithType(param.key.getCode());
                                itt.remove();
                            }
                        }
                    }

                    this.listSequence.add(task);

                }
                break;
                default:
                    break;
            }
        }
        checkTasks();
    }


    public void checkTasks() {
        if (this.listSequence.size() == 0) {
            return;
        }
        boolean flag = true;
        synchronized (this.listSequence) {
            Iterator<NetworkTask> it = this.listSequence.iterator();
            while (it.hasNext()) {
                NetworkTask nt = it.next();
                QTasks task = new QTasks(this, nt);
                flag = addCurrentTask(nt.param.key.getCode(), task);
                if (flag) {
                    it.remove();
                    task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }
            }
        }
    }

    private void removeCurrentTask(NetworkTask netTask) {
        if (netTask.handler != null) {
            Message m = netTask.handler.obtainMessage(TaskStatus.END, netTask.param);
            netTask.handler.sendMessage(m);
        }
        LinkedList<QTasks> taskList = tasks[netTask.param.key.getCode()];
        synchronized (taskList) {
            Iterator<QTasks> it = taskList.iterator();
            while (it.hasNext()) {
                QTasks task = it.next();
                if (task.networkTask == netTask) {
                    it.remove();
                }
            }
        }
    }

    @Override
    public void onTaskCancel(NetworkTask task) {
        removeCurrentTask(task);
        checkTasks();
    }

    @Override
    public void onTaskComplete(NetworkTask task) {
        removeCurrentTask(task);
        checkTasks();
    }

}
