package com.mycom.myboard.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.mycom.myboard.dao.AptDao;
import com.mycom.myboard.dto.AptDto;
import com.mycom.myboard.dto.AptParamDto;
import com.mycom.myboard.dto.InterestDto;

@Service
public class AptServiceImpl implements AptService{

	@Autowired
	AptDao dao;
	
	@Override
	public ArrayList<AptDto> marker(AptParamDto aptParamDto) {
		return dao.marker(aptParamDto);
	}

	@Override
	public AptDto markerInfo(AptDto dto) {
		return dao.markerInfo(dto);
	}

	@Override
	public int interest(InterestDto dto) {
		// TODO Auto-generated method stub
		System.out.println(dto);
		int result = 0;
		try { 
			result = dao.insertToInterest(dto);
		}catch(DuplicateKeyException e) {
			result = -1;
		}
		return result;
	}
	@Override
	public List<InterestDto> interestList(int userSeq) {
		// TODO Auto-generated method stub
		
		return dao.interestList(userSeq);
	}

	@Override
	public int interestDelete(String aptName) {
		// TODO Auto-generated method stub
		return dao.interestDelete(aptName);
	}
	

}
