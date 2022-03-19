package com.mycom.myboard.dto;

public class FreeParamDto {
	
	private int limit;
	private int offset;
	private String searchWord;
	
	private int freeId;
	private int userSeq;
	
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public String getSearchWord() {
		return searchWord;
	}
	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}
	public int getFreeId() {
		return freeId;
	}
	public void setFreeId(int freeId) {
		this.freeId = freeId;
	}
	public int getUserSeq() {
		return userSeq;
	}
	public void setUserSeq(int userSeq) {
		this.userSeq = userSeq;
	}
	@Override
	public String toString() {
		return "FreeParamDto [limit=" + limit + ", offset=" + offset + ", searchWord=" + searchWord + ", freeId="
				+ freeId + ", userSeq=" + userSeq + "]";
	}
	
	
	
}
