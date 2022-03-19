package com.mycom.myboard.dto;

import java.util.List;

public class FreeResultDto {
	private int result;
	private FreeDto dto;
	private List<FreeDto> list;
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
	public FreeDto getDto() {
		return dto;
	}
	public void setDto(FreeDto dto) {
		this.dto = dto;
	}

	public List<FreeDto> getList() {
		return list;
	}
	public void setList(List<FreeDto> list) {
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
		return "FreeResultDto [result=" + result + ", dto=" + dto + ", list=" + list + ", count=" + count + ", check="
				+ check + "]";
	}
	
	
	

}
