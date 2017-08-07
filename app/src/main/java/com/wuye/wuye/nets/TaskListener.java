package com.wuye.wuye.nets;

/**
 * 
 * @author zexu
 *
 */
public interface TaskListener {
    public void onTaskComplete(NetworkTask task);

    public void onTaskCancel(NetworkTask task);

}
