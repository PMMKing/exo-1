package com.framework.view.pull;

/**
 * 
 * @author xutao
 * 
 */
public enum SwipRefreshLayoutDirection {

	TOP(0), // 只有下拉刷新
	BOTTOM(1), // 只有加载更多
	BOTH(2);// 全都有

	private int mValue;

	SwipRefreshLayoutDirection(int value) {
		this.mValue = value;
	}

	public static SwipRefreshLayoutDirection getFromInt(int value) {
		for (SwipRefreshLayoutDirection direction : SwipRefreshLayoutDirection
				.values()) {
			if (direction.mValue == value) {
				return direction;
			}
		}
		return BOTH;
	}

}
