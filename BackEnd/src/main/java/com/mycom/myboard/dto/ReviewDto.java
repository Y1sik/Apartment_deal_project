package com.mycom.myboard.dto;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class ReviewDto {
	private int reviewId;
	private int userSeq;
	private String userName;
	private String userProfileImageUrl;
	private String title;
	private String content;
	//private Date regDt; 얘는 그냥 스트링으로 넘겨줌
	//private LocalDate regDt; 년월일 시분초를 알아서 나눠서 넘겨줌
	private LocalDateTime regDt;
	private int readCount;

	private boolean sameUser;
	private boolean good;
	
	private List<ReviewFileDto> fileList;

	public ReviewDto() {};
	public ReviewDto(int userSeq, String title, String content) {
		this.userSeq = userSeq;
		this.title = title;
		this.content = content;
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

	
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserProfileImageUrl() {
		return userProfileImageUrl;
	}
	public void setUserProfileImageUrl(String userProfileImageUrl) {
		
		if( userProfileImageUrl == null || "null".equals(userProfileImageUrl) || "".equals(userProfileImageUrl)) {
			this.userProfileImageUrl = "/img/noProfile.png";
		}else {
			this.userProfileImageUrl = userProfileImageUrl;
		}
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public LocalDateTime getRegDt() {
		return regDt;
	}
	public void setRegDt(Date regDt) {
		this.regDt = LocalDateTime.ofInstant(
				regDt.toInstant(), ZoneId.systemDefault()
		);	// for Mybatis Date Mapping
	}
	public int getReadCount() {
		return readCount;
	}
	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}

	public List<ReviewFileDto> getFileList() {
		return fileList;
	}
	public void setFileList(List<ReviewFileDto> fileList) {
		this.fileList = fileList;
	}
	
	public boolean isSameUser() {
		return sameUser;
	}
	public void setSameUser(boolean sameUser) {
		this.sameUser = sameUser;
	}
	public boolean isGood() {
		return good;
	}
	public void setGood(boolean good) {
		this.good = good;
	}
	@Override
	public String toString() {
		return "ReviewDto [reviewId=" + reviewId + ", userSeq=" + userSeq + ", userName=" + userName
				+ ", userProfileImageUrl=" + userProfileImageUrl + ", title=" + title + ", content=" + content
				+ ", regDt=" + regDt + ", readCount=" + readCount + ", sameUser=" + sameUser + ", good=" + good
				+ ", fileList=" + fileList + "]";
	}
	
	
	
	
	
}

