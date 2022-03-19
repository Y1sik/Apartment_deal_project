package com.mycom.myboard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mycom.myboard.dto.UserDto;
import com.mycom.myboard.dto.UserResultDto;
import com.mycom.myboard.service.UserService;

@CrossOrigin(
		origins = "http://localhost:5500",
		allowCredentials = "true",
		allowedHeaders = "*",
		methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.HEAD, RequestMethod.OPTIONS,}
		)
@RestController //@Controller+@responseBody 서버는 항상 json으로 주기 때문에 계속 쓰기 귀찮
public class UserController {
	
	@Autowired
	UserService userService;
	
	private static final int SUCCESS = 1;
	
	//@RestController로 인해서 @ResponseBody 쓰지 않아도 된다
	@PostMapping(value="/register")
	// ResponseEntity => restapi를 이용해서 http 상태코드를 성공 실패여부로 같이 넘겨준다
	public ResponseEntity<UserResultDto> register(@RequestBody UserDto userDto) {
		UserResultDto userResultDto = userService.userRegister(userDto);
		if(userResultDto.getResult() == SUCCESS) {
			return new ResponseEntity<UserResultDto>(userResultDto, HttpStatus.OK);
		}else {
			return new ResponseEntity<UserResultDto>(userResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping(value="/update")
	public ResponseEntity<Integer> update(@RequestBody UserDto userDto){
		
		int n = userService.userUpdate(userDto);
		
		if( n == 1 ) {
			return new ResponseEntity<Integer>(n, HttpStatus.OK);
		}else {
			return new ResponseEntity<Integer>(n, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping(value="/delete/{userSeq}")
	public ResponseEntity<Integer> delete(@PathVariable int userSeq){
		
		System.out.println(userSeq);
		int n = userService.userDelete(userSeq);
		
		if( n == 1 ) {
			return new ResponseEntity<Integer>(n, HttpStatus.OK);
		}else {
			return new ResponseEntity<Integer>(n, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
