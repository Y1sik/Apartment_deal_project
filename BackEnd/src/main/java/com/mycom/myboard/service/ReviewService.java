package com.mycom.myboard.service;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mycom.myboard.dto.ReviewDto;
import com.mycom.myboard.dto.ReviewParamDto;
import com.mycom.myboard.dto.ReviewResultDto;

public interface ReviewService {
	public ReviewResultDto reviewDetail(ReviewParamDto reviewParamDto);

	public ReviewResultDto reviewDelete(int reviewId);

	public ReviewResultDto reviewUpdate(ReviewDto dto, MultipartHttpServletRequest request);

	public ReviewResultDto reviewInsert(ReviewDto dto, MultipartHttpServletRequest request);

	public ReviewResultDto reviewList(ReviewParamDto reviewParamDto);

	public ReviewResultDto reviewListSearchWord(ReviewParamDto reviewParamDto);
}
