package com.mycom.myboard.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mycom.myboard.dto.UserDto;

@Component
public class LoginInterceptor implements HandlerInterceptor{ // HandlerInterceptorAdapter 는 deprecated
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		System.out.println(">>>>>>>>>>>> " + request.getRequestURI());
		
        HttpSession session = request.getSession();
        UserDto userDto = (UserDto) session.getAttribute("userDto");
        
        // CORS의 put, delete에서 options request가 브라우저에 의해서 발생되는데 이를 무조건 수락
        if(request.getMethod().equals("OPTIONS")) {
        	return true;
        }
        	
        if( userDto == null ) {
        	Gson gson = new Gson();

			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("result", "login");
			
			String jsonStr = gson.toJson(jsonObject);
			response.getWriter().write(jsonStr);

        	return false;
        }

        return true;
    }
}