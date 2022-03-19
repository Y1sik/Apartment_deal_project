package com.mycom.myboard.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycom.myboard.dto.FreeDto;
import com.mycom.myboard.dto.FreeParamDto;
import com.mycom.myboard.dto.GoodDto;

@Mapper // root context의 <mybatis-spring:scan base-package="com.mycom.myboard.dao"/>로 인해 mapper선언 해주고 여기를 확인한다
public interface GoodDao {
	
	public int goodUpdate(GoodDto dto);
	
	public boolean goodSelect(FreeParamDto freeParamDto);
	
	public int goodFreeUser(int freeId);
	
	public void goodPlus(int userSeq);
	
	public void goodMinus(int userSeq);
}
