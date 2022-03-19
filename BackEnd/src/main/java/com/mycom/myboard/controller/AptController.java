package com.mycom.myboard.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mycom.myboard.dto.AptDto;
import com.mycom.myboard.dto.AptParamDto;
import com.mycom.myboard.dto.InterestDto;
import com.mycom.myboard.service.AptService;


// vue에서 port 번호 설정
// npm run serve -- --port 5500
@CrossOrigin(
		origins = "http://localhost:5500",
		allowCredentials = "true",
		allowedHeaders = "*",
		methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.HEAD, RequestMethod.OPTIONS,}
		)
@RestController
public class AptController {
	
	@Autowired
	AptService service;
	
	private static final int SUCCESS = 1;

    @GetMapping(value="/markers")
    private ResponseEntity<ArrayList<AptDto>> markerList(AptParamDto aptParamDto){

    	ArrayList<AptDto> aptDto;
	    
	    aptDto = service.marker(aptParamDto);
	    System.out.println("marker"+aptParamDto);
	    return new ResponseEntity<ArrayList<AptDto>>(aptDto, HttpStatus.OK);
	}
	
    @GetMapping(value="/selectmarkers")
    private ResponseEntity<AptDto> markerInfo(AptDto dto){
    	System.out.println("들어옴");
    	System.out.println(dto);
    	
	    
	    AptDto aptDto = service.markerInfo(dto);
	    System.out.println("markerInfo "+aptDto);
	    return new ResponseEntity<AptDto>(aptDto, HttpStatus.OK);
	}
    
    @GetMapping(value="/interests")
    private ResponseEntity<Integer> interest(InterestDto dto){
    	int result = service.interest(dto);
    	System.out.println("result : " + result);
        return new ResponseEntity<Integer>(result, HttpStatus.OK);
    	
    }
    
    @GetMapping(value="/interestlists/{userSeq}")
    private ResponseEntity<List<InterestDto>> interestList(@PathVariable(value="userSeq") int userSeq){
    	System.out.println(userSeq);
    	List<InterestDto> list = service.interestList(userSeq);
        return new ResponseEntity<List<InterestDto>>(list, HttpStatus.OK);
    	
    }
    
    @DeleteMapping(value="/interestdelete/{aptName}")
    private ResponseEntity<Integer> interestList(@PathVariable(value="aptName") String aptName){
    	System.out.println(aptName);
    	int result = service.interestDelete(aptName);
        return new ResponseEntity<Integer>(result, HttpStatus.OK);
    	
    }
	
}
