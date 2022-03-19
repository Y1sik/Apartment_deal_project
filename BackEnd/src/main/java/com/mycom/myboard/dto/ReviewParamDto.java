package com.mycom.myboard.dto;

public class ReviewParamDto {
	
	private int limit;
	private int offset;
	private String searchWord;
	
	private int reviewId;
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
	public int getReviewId() {
		return reviewId;
	}
	public void setReviewId(int reviewId) {
		this.reviewId = reviewId;
	}
	public int getUserSeq() {
		return userSeq;
	}
	public void setUserSeq(int userSeq) {
		this.userSeq = userSeq;
	}
	@Override
	public String toString() {
		return "ReviewParamDto [limit=" + limit + ", offset=" + offset + ", searchWord=" + searchWord + ", reviewId="
				+ reviewId + ", userSeq=" + userSeq + "]";
	}
	
	
	
	
}
