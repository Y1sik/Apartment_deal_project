package com.mycom.myboard.dto;

public class GoodDto {
	private int userSeq;
	private int freeId;
	private boolean checkedValues;
	
	public GoodDto() {}
	
	public GoodDto(int userSeq, int freeId, boolean checkedValues) {
		super();
		this.userSeq = userSeq;
		this.freeId = freeId;
		this.checkedValues = checkedValues;
	}
	public int getUserSeq() {
		return userSeq;
	}
	public void setUserSeq(int userSeq) {
		this.userSeq = userSeq;
	}
	public int getFreeId() {
		return freeId;
	}
	public void setFreeId(int freeId) {
		this.freeId = freeId;
	}
	public boolean isCheckedValues() {
		return checkedValues;
	}
	public void setCheckedValues(boolean checkedValues) {
		this.checkedValues = checkedValues;
	}

	@Override
	public String toString() {
		return "GoodDto [userSeq=" + userSeq + ", freeId=" + freeId + ", checkedValues=" + checkedValues + "]";
	}
	
	
	
}
