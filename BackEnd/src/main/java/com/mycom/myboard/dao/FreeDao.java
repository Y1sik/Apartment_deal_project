package com.mycom.myboard.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mycom.myboard.dto.FreeDto;
import com.mycom.myboard.dto.FreeFileDto;
import com.mycom.myboard.dto.FreeParamDto;

@Mapper // root context의 <mybatis-spring:scan base-package="com.mycom.myboard.dao"/>로 인해 mapper선언 해주고 여기를 확인한다
public interface FreeDao {
	public FreeDto freeDetail(FreeParamDto freeParamDto);
	public List<FreeFileDto> freeDetailFileList(int freeId);

	// map - Dto
	public int freeUserReadCount(FreeParamDto freeParamDto); 

	// map - @param
	public int freeUserReadInsert(
	        @Param("freeId") int freeId, 
	        @Param("userSeq") int userSeq ); 

	public int freeReadCountUpdate(int freeId);


	public int freeDelete(int freeId);    
	public int freeFileDelete(int freeId);
	public List<String> freeFileUrlDeleteList(int freeId);
	public int freeReadCountDelete(int freeId);

	public int freeInsert(FreeDto dto);
	public int freeFileInsert(FreeFileDto dto);

	public List<FreeDto> freeList(FreeParamDto freeParamDto);
	public int freeListTotalCount();

	public List<FreeDto> freeListSearchWord(FreeParamDto freeParamDto);
	public int freeListSearchWordTotalCount(FreeParamDto freeParamDto);


	public int freeUpdate(FreeDto dto);
}
