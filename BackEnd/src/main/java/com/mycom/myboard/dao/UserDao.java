package com.mycom.myboard.dao;

import org.apache.ibatis.annotations.Mapper;

import com.mycom.myboard.dto.UserDto;
import com.mycom.myboard.dto.UserResultDto;

@Mapper
public interface UserDao {
	public int userRegister(UserDto userDto);
	public int userUpdate(UserDto userDto);
	public int userDelete(int userSeq);
}
