package com.keruiyun.saike.wheelpicker;

public class DateModel {
	private int id;
	private boolean isClose;
	private long closeDate;
	private long openDate;

	public boolean isClose() {
		return isClose;
	}

	public void setClose(boolean isClose) {
		this.isClose = isClose;
	}

	public long getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(long closeDate) {
		this.closeDate = closeDate;
	}

	public long getOpenDate() {
		return openDate;
	}

	public void setOpenDate(long openDate) {
		this.openDate = openDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
