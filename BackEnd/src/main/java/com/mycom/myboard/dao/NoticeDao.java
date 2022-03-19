package com.mycom.myboard.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mycom.myboard.dto.NoticeDto;
import com.mycom.myboard.dto.NoticeFileDto;
import com.mycom.myboard.dto.NoticeParamDto;

@Mapper // root context의 <mybatis-spring:scan base-package="com.mycom.myboard.dao"/>로 인해 mapper선언 해주고 여기를 확인한다
public interface NoticeDao {
	public NoticeDto noticeDetail(NoticeParamDto noticeParamDto);
	public List<NoticeFileDto> noticeDetailFileList(int noticeId);

	// map - Dto
	public int noticeUserReadCount(NoticeParamDto noticeParamDto); 

	// map - @param
	public int noticeUserReadInsert(
	        @Param("noticeId") int noticeId, 
	        @Param("userSeq") int userSeq ); 

	public int noticeReadCountUpdate(int noticeId);


	public int noticeDelete(int noticeId);    
	public int noticeFileDelete(int noticeId);
	public List<String> noticeFileUrlDeleteList(int noticeId);
	public int noticeReadCountDelete(int noticeId);

	public int noticeInsert(NoticeDto dto);
	public int noticeFileInsert(NoticeFileDto dto);

	public List<NoticeDto> noticeList(NoticeParamDto noticeParamDto);
	public int noticeListTotalCount();

	public List<NoticeDto> noticeListSearchWord(NoticeParamDto noticeParamDto);
	public int noticeListSearchWordTotalCount(NoticeParamDto noticeParamDto);


	public int noticeUpdate(NoticeDto dto);
}
