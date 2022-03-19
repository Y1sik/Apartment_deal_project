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

import com.mycom.myboard.dto.ReviewDto;
import com.mycom.myboard.dto.ReviewParamDto;
import com.mycom.myboard.dto.ReviewResultDto;
import com.mycom.myboard.dto.GoodDto;
import com.mycom.myboard.dto.UserDto;
import com.mycom.myboard.service.ReviewService;
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
public class ReviewController {
	
	@Autowired
	ReviewService service;
	
//	@Autowired
//	GoodService goodservice;
	
	private static final int SUCCESS = 1;

    @GetMapping(value="/reviews") 
    private ResponseEntity<ReviewResultDto> reviewList(ReviewParamDto reviewParamDto){

//        String s = null;
//        s.length();

	    ReviewResultDto reviewResultDto;
	
	    System.out.println("reviewList : " + reviewParamDto);
	    System.out.println(service);
	    if( reviewParamDto.getSearchWord().isEmpty() ) {
	    	System.out.println("reviewResultDto");
	        reviewResultDto = service.reviewList(reviewParamDto);
	    }else {
	        reviewResultDto = service.reviewListSearchWord(reviewParamDto);
	    }
	    
	    if( reviewResultDto.getResult() == SUCCESS ) {
	        return new ResponseEntity<ReviewResultDto>(reviewResultDto, HttpStatus.OK);
	    }else {
	        return new ResponseEntity<ReviewResultDto>(reviewResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	
	@GetMapping(value="/reviews/{reviewId}")
	private ResponseEntity<ReviewResultDto> reviewDetail(@PathVariable int reviewId, HttpSession session){
	
		
	    ReviewParamDto reviewParamDto = new ReviewParamDto();
	    reviewParamDto.setReviewId(reviewId);
	    reviewParamDto.setUserSeq( ((UserDto) session.getAttribute("userDto")).getUserSeq());
	    
	    ReviewResultDto reviewResultDto = service.reviewDetail(reviewParamDto);
	    System.out.println("reviewResultDto: " + reviewResultDto);
	    if( reviewResultDto.getResult() == SUCCESS ) {
	        return new ResponseEntity<ReviewResultDto>(reviewResultDto, HttpStatus.OK);
	    }else {
	        return new ResponseEntity<ReviewResultDto>(reviewResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
	    }         
	}
	
//	@PutMapping(value="/goods")
//	private ResponseEntity<Integer> goodUpdate(@RequestBody GoodDto goodDto){
//		System.out.println("goodUpdate: "+goodDto);
//		int result = goodservice.goodUpdate(goodDto);
//		return new ResponseEntity<Integer>(result, HttpStatus.OK);
//	}
	
	@PostMapping(value="/reviews")
	private ResponseEntity<ReviewResultDto> reviewInsert(
	        ReviewDto reviewDto, 
	        MultipartHttpServletRequest request) {
	    System.out.println("controller insert 들어옴");
	    // LoginFilter 먼저 적용 필요!!
	    HttpSession session = request.getSession();
	    UserDto userDto = (UserDto) session.getAttribute("userDto");
	    
	    reviewDto.setUserSeq(userDto.getUserSeq());
	            
	    ReviewResultDto reviewResultDto = service.reviewInsert(reviewDto, request);
	    System.out.println("reviewInsert " + reviewResultDto);
	    if( reviewResultDto.getResult() == SUCCESS ) {
	        return new ResponseEntity<ReviewResultDto>(reviewResultDto, HttpStatus.OK);
	    }else {
	        return new ResponseEntity<ReviewResultDto>(reviewResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
	    }         
	}
	
	// PUT + multipart/form-data (X)
	// In RESTful,
	// PUT & DELETE methods are defined to be idempotent
	// 만약 글만 수정한다면?? PUT mapping OK  여러 번 수정해도 back-end 결과가 같음
	// 첨부파일이 포함되면 idempotent 하지 않음.
	
	@PostMapping(value="/reviews/{reviewId}") 
	private ResponseEntity<ReviewResultDto> reviewUpdate(
	        ReviewDto reviewDto, 
	        MultipartHttpServletRequest request){
		System.out.println("reviewUpdate : "+reviewDto);
	    HttpSession session = request.getSession();
	    UserDto userDto = (UserDto) session.getAttribute("userDto");
	    
	    reviewDto.setUserSeq(userDto.getUserSeq());
	    
	    ReviewResultDto reviewResultDto = service.reviewUpdate(reviewDto, request);
	    System.out.println("reviewUpdate2 : "+reviewResultDto);
	    if( reviewResultDto.getResult() == SUCCESS ) {
	        return new ResponseEntity<ReviewResultDto>(reviewResultDto, HttpStatus.OK);
	    }else {
	        return new ResponseEntity<ReviewResultDto>(reviewResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
	    }        
	}
	
	@DeleteMapping(value="/reviews/{reviewId}") 
	private ResponseEntity<ReviewResultDto> reviewDelete(@PathVariable(value="reviewId") int reviewId){
	    ReviewResultDto reviewResultDto = service.reviewDelete(reviewId);
	    
	    if( reviewResultDto.getResult() == SUCCESS ) {
	        return new ResponseEntity<ReviewResultDto>(reviewResultDto, HttpStatus.OK);
	    }else {
	        return new ResponseEntity<ReviewResultDto>(reviewResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
	    }      
	}
}
