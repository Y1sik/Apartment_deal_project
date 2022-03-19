package com.mycom.myboard.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mycom.myboard.dto.ReviewDto;
import com.mycom.myboard.dto.ReviewFileDto;
import com.mycom.myboard.dto.ReviewParamDto;

@Mapper // root context의 <mybatis-spring:scan base-package="com.mycom.myboard.dao"/>로 인해 mapper선언 해주고 여기를 확인한다
public interface ReviewDao {
	public ReviewDto reviewDetail(ReviewParamDto reviewParamDto);
	public List<ReviewFileDto> reviewDetailFileList(int reviewId);

	// map - Dto
	public int reviewUserReadCount(ReviewParamDto reviewParamDto); 

	// map - @param
	public int reviewUserReadInsert(
	        @Param("reviewId") int reviewId, 
	        @Param("userSeq") int userSeq ); 

	public int reviewReadCountUpdate(int reviewId);


	public int reviewDelete(int reviewId);    
	public int reviewFileDelete(int reviewId);
	public List<String> reviewFileUrlDeleteList(int reviewId);
	public int reviewReadCountDelete(int reviewId);

	public int reviewInsert(ReviewDto dto);
	public int reviewFileInsert(ReviewFileDto dto);

	public List<ReviewDto> reviewList(ReviewParamDto reviewParamDto);
	public int reviewListTotalCount();

	public List<ReviewDto> reviewListSearchWord(ReviewParamDto reviewParamDto);
	public int reviewListSearchWordTotalCount(ReviewParamDto reviewParamDto);


	public int reviewUpdate(ReviewDto dto);
}
