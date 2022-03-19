package com.mycom.myboard.dto;

public class AptParamDto {
	private double swLat;
	private double swLng;
	private double neLat;
	private double neLng;
	public double getSwLat() {
		return swLat;
	}
	public void setSwLat(double swLat) {
		this.swLat = swLat;
	}
	public double getSwLng() {
		return swLng;
	}
	public void setSwLng(double swLng) {
		this.swLng = swLng;
	}
	public double getNeLat() {
		return neLat;
	}
	public void setNeLat(double neLat) {
		this.neLat = neLat;
	}
	public double getNeLng() {
		return neLng;
	}
	public void setNeLng(double neLng) {
		this.neLng = neLng;
	}
	@Override
	public String toString() {
		return "AptParamDto [swLat=" + swLat + ", swLng=" + swLng + ", neLat=" + neLat + ", neLng=" + neLng + "]";
	}
	
	
}
