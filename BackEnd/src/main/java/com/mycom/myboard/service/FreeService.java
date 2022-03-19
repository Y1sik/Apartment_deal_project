package com.mycom.myboard.service;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mycom.myboard.dto.FreeDto;
import com.mycom.myboard.dto.FreeParamDto;
import com.mycom.myboard.dto.FreeResultDto;

public interface FreeService {
	public FreeResultDto freeDetail(FreeParamDto freeParamDto);

	public FreeResultDto freeDelete(int freeId);

	public FreeResultDto freeUpdate(FreeDto dto, MultipartHttpServletRequest request);

	public FreeResultDto freeInsert(FreeDto dto, MultipartHttpServletRequest request);

	public FreeResultDto freeList(FreeParamDto freeParamDto);

	public FreeResultDto freeListSearchWord(FreeParamDto freeParamDto);
}
