package com.wuye.wuye.nets;

public interface NetworkListener {

	public void onNetStart(NetworkParam param);

	public void onNetEnd(NetworkParam param);

	public void onNetError(NetworkParam param, int errCode);

	public void onMsgSearchComplete(NetworkParam param);

	public void onNetCancel(NetworkParam param);

}
