package com.mycom.myboard.service;

import java.util.ArrayList;
import java.util.List;

import com.mycom.myboard.dto.AptDto;
import com.mycom.myboard.dto.AptParamDto;
import com.mycom.myboard.dto.InterestDto;

public interface AptService {
	public ArrayList<AptDto> marker(AptParamDto aptParamDto);

	public AptDto markerInfo(AptDto dto);
	
	public int interest(InterestDto dto);
	
	public List<InterestDto> interestList(int userSeq);
	
	public int interestDelete(String aptName);
	
}
