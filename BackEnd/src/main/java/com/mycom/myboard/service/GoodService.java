package com.mycom.myboard.service;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mycom.myboard.dto.FreeDto;
import com.mycom.myboard.dto.FreeParamDto;
import com.mycom.myboard.dto.FreeResultDto;
import com.mycom.myboard.dto.GoodDto;

public interface GoodService {
	
	public Integer goodUpdate(GoodDto dto);
	
}
