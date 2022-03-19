package com.mycom.myboard.dto;

import java.util.List;

public class NoticeResultDto {
	private int result;
	private NoticeDto dto;
	private List<NoticeDto> list;
	private int count;
	private boolean check;
	
	public boolean isCheck() {
		return check;
	}
	public void setCheck(boolean check) {
		this.check = check;
	}
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public NoticeDto getDto() {
		return dto;
	}
	public void setDto(NoticeDto dto) {
		this.dto = dto;
	}

	public List<NoticeDto> getList() {
		return list;
	}
	public void setList(List<NoticeDto> list) {
		this.list = list;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	@Override
	public String toString() {
		return "NoticeResultDto [result=" + result + ", dto=" + dto + ", list=" + list + ", count=" + count + ", check="
				+ check + "]";
	}
	
	
	
	
	

}
