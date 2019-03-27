package com.test.session;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GetSessionServlet
 */
public class GetSessionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//修改请求报文中的JSESSIONIDcookie的有效时间
		Cookie[] cookies = request.getCookies();
		
		if(cookies!=null) {
			for (Cookie cookie : cookies) {
				if("JSESSIONID".equals(cookie.getName())) {
					//修改原本cookie的时间为1小时
					cookie.setMaxAge(60*60);
					//替换浏览器之前的cookie
					response.addCookie(cookie);
					//开发中一定不要改此cookie时间
				}
			}
		}
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
