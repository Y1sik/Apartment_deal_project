package com.mycom.myboard.dto;

public class InterestDto {
	private int house_no;
	private int userSeq;
	private String aptName;
	private String dong;
	private String floor;
	private String area;
	private String dealAmount;
	public int getHouse_no() {
		return house_no;
	}
	public void setHouse_no(int house_no) {
		this.house_no = house_no;
	}
	public int getUserSeq() {
		return userSeq;
	}
	public void setUserSeq(int userSeq) {
		this.userSeq = userSeq;
	}
	public String getAptName() {
		return aptName;
	}
	public void setAptName(String aptName) {
		this.aptName = aptName;
	}
	public String getDong() {
		return dong;
	}
	public void setDong(String dong) {
		this.dong = dong;
	}
	public String getFloor() {
		return floor;
	}
	public void setFloor(String floor) {
		this.floor = floor;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getDealAmount() {
		return dealAmount;
	}
	public void setDealAmount(String dealAmount) {
		this.dealAmount = dealAmount;
	}
	@Override
	public String toString() {
		return "InterestDto [house_no=" + house_no + ", userSeq=" + userSeq + ", aptName=" + aptName + ", dong=" + dong
				+ ", floor=" + floor + ", area=" + area + ", dealAmount=" + dealAmount + "]";
	}
	
	
	
	
}
