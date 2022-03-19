package com.mycom.myboard.service;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mycom.myboard.dto.NoticeDto;
import com.mycom.myboard.dto.NoticeParamDto;
import com.mycom.myboard.dto.NoticeResultDto;

public interface NoticeService {
	public NoticeResultDto noticeDetail(NoticeParamDto noticeParamDto);

	public NoticeResultDto noticeDelete(int noticeId);

	public NoticeResultDto noticeUpdate(NoticeDto dto, MultipartHttpServletRequest request);

	public NoticeResultDto noticeInsert(NoticeDto dto, MultipartHttpServletRequest request);

	public NoticeResultDto noticeList(NoticeParamDto noticeParamDto);

	public NoticeResultDto noticeListSearchWord(NoticeParamDto noticeParamDto);
}
