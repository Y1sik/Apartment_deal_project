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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mycom.myboard.dto.FreeDto;
import com.mycom.myboard.dto.FreeParamDto;
import com.mycom.myboard.dto.FreeResultDto;
import com.mycom.myboard.dto.GoodDto;
import com.mycom.myboard.dto.UserDto;
import com.mycom.myboard.service.FreeService;
import com.mycom.myboard.service.GoodService;

// vue에서 port 번호 설정
// npm run serve -- --port 5500
@CrossOrigin(
		origins = "http://localhost:5500",
		allowCredentials = "true",
		allowedHeaders = "*",
		methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.HEAD, RequestMethod.OPTIONS,}
		)
@RestController
public class FreeController {
	
	@Autowired
	FreeService service;
	
	@Autowired
	GoodService goodservice;
	
	private static final int SUCCESS = 1;

    @GetMapping(value="/frees") 
    private ResponseEntity<FreeResultDto> freeList(FreeParamDto freeParamDto){

//        String s = null;
//        s.length();

	    FreeResultDto freeResultDto;
	
	    System.out.println("freeList : " + freeParamDto);
	    System.out.println(service);
	    if( freeParamDto.getSearchWord().isEmpty() ) {
	    	System.out.println("freeResultDto");
	        freeResultDto = service.freeList(freeParamDto);
	    }else {
	        freeResultDto = service.freeListSearchWord(freeParamDto);
	    }
	    
	    if( freeResultDto.getResult() == SUCCESS ) {
	        return new ResponseEntity<FreeResultDto>(freeResultDto, HttpStatus.OK);
	    }else {
	        return new ResponseEntity<FreeResultDto>(freeResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	
	@GetMapping(value="/frees/{freeId}")
	private ResponseEntity<FreeResultDto> freeDetail(@PathVariable int freeId, HttpSession session){
	
		
	    FreeParamDto freeParamDto = new FreeParamDto();
	    freeParamDto.setFreeId(freeId);
	    freeParamDto.setUserSeq( ((UserDto) session.getAttribute("userDto")).getUserSeq());
	    
	    FreeResultDto freeResultDto = service.freeDetail(freeParamDto);
	    System.out.println("freeResultDto: " + freeResultDto);
	    if( freeResultDto.getResult() == SUCCESS ) {
	        return new ResponseEntity<FreeResultDto>(freeResultDto, HttpStatus.OK);
	    }else {
	        return new ResponseEntity<FreeResultDto>(freeResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
	    }         
	}
	
	@PutMapping(value="/goods")
	private ResponseEntity<Integer> goodUpdate(@RequestBody GoodDto goodDto){
		System.out.println("goodUpdate: "+goodDto);
		int result = goodservice.goodUpdate(goodDto);
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
	
	@PostMapping(value="/frees")
	private ResponseEntity<FreeResultDto> freeInsert(
	        FreeDto freeDto, 
	        MultipartHttpServletRequest request) {
	    
	    // LoginFilter 먼저 적용 필요!!
	    HttpSession session = request.getSession();
	    UserDto userDto = (UserDto) session.getAttribute("userDto");
	    
	    freeDto.setUserSeq(userDto.getUserSeq());
	            
	    FreeResultDto freeResultDto = service.freeInsert(freeDto, request);
	    System.out.println("freeInsert " + freeResultDto);
	    if( freeResultDto.getResult() == SUCCESS ) {
	        return new ResponseEntity<FreeResultDto>(freeResultDto, HttpStatus.OK);
	    }else {
	        return new ResponseEntity<FreeResultDto>(freeResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
	    }         
	}
	
	// PUT + multipart/form-data (X)
	// In RESTful,
	// PUT & DELETE methods are defined to be idempotent
	// 만약 글만 수정한다면?? PUT mapping OK  여러 번 수정해도 back-end 결과가 같음
	// 첨부파일이 포함되면 idempotent 하지 않음.
	
	@PostMapping(value="/frees/{freeId}") 
	private ResponseEntity<FreeResultDto> freeUpdate(
	        FreeDto freeDto, 
	        MultipartHttpServletRequest request){
		System.out.println("freeUpdate : "+freeDto);
	    HttpSession session = request.getSession();
	    UserDto userDto = (UserDto) session.getAttribute("userDto");
	    
	    freeDto.setUserSeq(userDto.getUserSeq());
	    
	    FreeResultDto freeResultDto = service.freeUpdate(freeDto, request);
	    System.out.println("freeUpdate2 : "+freeResultDto);
	    if( freeResultDto.getResult() == SUCCESS ) {
	        return new ResponseEntity<FreeResultDto>(freeResultDto, HttpStatus.OK);
	    }else {
	        return new ResponseEntity<FreeResultDto>(freeResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
	    }        
	}
	
	@DeleteMapping(value="/frees/{freeId}") 
	private ResponseEntity<FreeResultDto> freeDelete(@PathVariable(value="freeId") int freeId){
	    FreeResultDto freeResultDto = service.freeDelete(freeId);
	    
	    if( freeResultDto.getResult() == SUCCESS ) {
	        return new ResponseEntity<FreeResultDto>(freeResultDto, HttpStatus.OK);
	    }else {
	        return new ResponseEntity<FreeResultDto>(freeResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
	    }         
	}
}
