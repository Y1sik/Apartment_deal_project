package com.mycom.myboard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mycom.myboard.dao.GoodDao;
import com.mycom.myboard.dto.GoodDto;

@Service
public class GoodServiceImpl implements GoodService {

	@Autowired
	GoodDao dao;
	
	@Override
	@Transactional
	public Integer goodUpdate(GoodDto dto) { // 유저 좋아요 체크시 업데이트, 좋아요 체크 할 때마다 게시판 작성자 포인트 증가 또는 감소
		int freeId = dto.getFreeId();
		int userSeq = dao.goodFreeUser(freeId);
		if(dto.isCheckedValues()) {
			dao.goodPlus(userSeq);
		}else {
			dao.goodMinus(userSeq);
		}
		return dao.goodUpdate(dto);
	}

	

}
