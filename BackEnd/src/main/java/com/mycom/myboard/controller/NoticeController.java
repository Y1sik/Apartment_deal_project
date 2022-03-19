package com.mycom.myboard.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mycom.myboard.dto.NoticeDto;
import com.mycom.myboard.dto.NoticeParamDto;
import com.mycom.myboard.dto.NoticeResultDto;
import com.mycom.myboard.dto.NoticeDto;
import com.mycom.myboard.dto.UserDto;
import com.mycom.myboard.service.NoticeService;

// vue에서 port 번호 설정
// npm run serve -- --port 5500
@CrossOrigin(
		origins = "http://localhost:5500",
		allowCredentials = "true",
		allowedHeaders = "*",
		methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.HEAD, RequestMethod.OPTIONS,}
		)
@RestController
public class NoticeController {
	
	@Autowired
	NoticeService service;
	
//	@Autowired
//	GoodService goodservice;
	
	private static final int SUCCESS = 1;

    @GetMapping(value="/notices") 
    private ResponseEntity<NoticeResultDto> noticeList(NoticeParamDto noticeParamDto){

//        String s = null;
//        s.length();

	    NoticeResultDto noticeResultDto;
	
	    System.out.println("noticeList : " + noticeParamDto);
	    System.out.println(service);
	    if( noticeParamDto.getSearchWord().isEmpty() ) {
	    	System.out.println("noticeResultDto");
	        noticeResultDto = service.noticeList(noticeParamDto);
	    }else {
	        noticeResultDto = service.noticeListSearchWord(noticeParamDto);
	    }
	    
	    if( noticeResultDto.getResult() == SUCCESS ) {
	        return new ResponseEntity<NoticeResultDto>(noticeResultDto, HttpStatus.OK);
	    }else {
	        return new ResponseEntity<NoticeResultDto>(noticeResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	
	@GetMapping(value="/notices/{noticeId}")
	private ResponseEntity<NoticeResultDto> noticeDetail(@PathVariable int noticeId, HttpSession session){
	
		
	    NoticeParamDto noticeParamDto = new NoticeParamDto();
	    noticeParamDto.setNoticeId(noticeId);
	    noticeParamDto.setUserSeq( ((UserDto) session.getAttribute("userDto")).getUserSeq());
	    
	    NoticeResultDto noticeResultDto = service.noticeDetail(noticeParamDto);
	    System.out.println("noticeResultDto: " + noticeResultDto);
	    if( noticeResultDto.getResult() == SUCCESS ) {
	        return new ResponseEntity<NoticeResultDto>(noticeResultDto, HttpStatus.OK);
	    }else {
	        return new ResponseEntity<NoticeResultDto>(noticeResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
	    }         
	}
	
//	@PutMapping(value="/goods")
//	private ResponseEntity<Integer> goodUpdate(@RequestBody GoodDto goodDto){
//		System.out.println("goodUpdate: "+goodDto);
//		int result = goodservice.goodUpdate(goodDto);
//		return new ResponseEntity<Integer>(result, HttpStatus.OK);
//	}
	
	@PostMapping(value="/notices")
	private ResponseEntity<NoticeResultDto> noticeInsert(
	        NoticeDto noticeDto, 
	        MultipartHttpServletRequest request) {
	    System.out.println("controller insert 들어옴");
	    // LoginFilter 먼저 적용 필요!!
	    HttpSession session = request.getSession();
	    UserDto userDto = (UserDto) session.getAttribute("userDto");
	    
	    noticeDto.setUserSeq(userDto.getUserSeq());
	            
	    NoticeResultDto noticeResultDto = service.noticeInsert(noticeDto, request);
	    System.out.println("noticeInsert " + noticeResultDto);
	    if( noticeResultDto.getResult() == SUCCESS ) {
	        return new ResponseEntity<NoticeResultDto>(noticeResultDto, HttpStatus.OK);
	    }else {
	        return new ResponseEntity<NoticeResultDto>(noticeResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
	    }         
	}
	
	// PUT + multipart/form-data (X)
	// In RESTful,
	// PUT & DELETE methods are defined to be idempotent
	// 만약 글만 수정한다면?? PUT mapping OK  여러 번 수정해도 back-end 결과가 같음
	// 첨부파일이 포함되면 idempotent 하지 않음.
	
	@PostMapping(value="/notices/{noticeId}") 
	private ResponseEntity<NoticeResultDto> noticeUpdate(
	        NoticeDto noticeDto, 
	        MultipartHttpServletRequest request){
		System.out.println("noticeUpdate : "+noticeDto);
	    HttpSession session = request.getSession();
	    UserDto userDto = (UserDto) session.getAttribute("userDto");
	    
	    noticeDto.setUserSeq(userDto.getUserSeq());
	    
	    NoticeResultDto noticeResultDto = service.noticeUpdate(noticeDto, request);
	    System.out.println("noticeUpdate2 : "+noticeResultDto);
	    if( noticeResultDto.getResult() == SUCCESS ) {
	        return new ResponseEntity<NoticeResultDto>(noticeResultDto, HttpStatus.OK);
	    }else {
	        return new ResponseEntity<NoticeResultDto>(noticeResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
	    }        
	}
	
	@DeleteMapping(value="/notices/{noticeId}") 
	private ResponseEntity<NoticeResultDto> noticeDelete(@PathVariable(value="noticeId") int noticeId){
	    NoticeResultDto noticeResultDto = service.noticeDelete(noticeId);
	    
	    if( noticeResultDto.getResult() == SUCCESS ) {
	        return new ResponseEntity<NoticeResultDto>(noticeResultDto, HttpStatus.OK);
	    }else {
	        return new ResponseEntity<NoticeResultDto>(noticeResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
	    }      
	}
}
