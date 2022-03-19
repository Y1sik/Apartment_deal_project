package com.mycom.myboard.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mycom.myboard.dto.UserDto;
import com.mycom.myboard.service.LoginService;

@CrossOrigin(
		origins = "http://localhost:5500",
		allowCredentials = "true",
		allowedHeaders = "*",
		methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.HEAD, RequestMethod.OPTIONS,}
		)
@RestController
public class LoginController {

	@Autowired
	LoginService service;
	
	// json request로 넘어온다(body에 담겨온다)
	// request content type : application/json
	@PostMapping(value="/login")
	// 클라이언트가 json으로 보낼때 @RequestBody 사용
	public ResponseEntity<UserDto> login(@RequestBody UserDto dto, HttpSession session){
		UserDto userDto = service.login(dto);
		System.out.println("login : " + dto);
		if(userDto != null) {
			session.setAttribute("userDto", userDto);
			return new ResponseEntity<UserDto>(userDto, HttpStatus.OK);
		}
		return new ResponseEntity<UserDto>(userDto, HttpStatus.NOT_FOUND);
	}
	
	@GetMapping(value="/logout")
	public ResponseEntity<UserDto> logout(HttpSession session){
		System.out.println("logout : ");
		session.invalidate();
		return new ResponseEntity<UserDto>( HttpStatus.OK);
	}
	
}
