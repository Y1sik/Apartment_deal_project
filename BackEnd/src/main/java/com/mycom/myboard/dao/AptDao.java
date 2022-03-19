package com.mycom.myboard.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycom.myboard.dto.AptDto;
import com.mycom.myboard.dto.AptParamDto;
import com.mycom.myboard.dto.InterestDto;

@Mapper
public interface AptDao {
	public ArrayList<AptDto> marker(AptParamDto aptParamDto);
	
	public AptDto markerInfo(AptDto dto);
	
	public int insertToInterest(InterestDto dto);
	
	public AptDto selectToApt(int house_no);
	
	public List<InterestDto> interestList(int userSeq);
	
	public int interestDelete(String aptName);
}
