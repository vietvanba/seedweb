package ptithcm.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import ptithcm.entity.User;

public class AdminInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response,Object handler) throws IOException
	{
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("myLogin");
		if(user==null||user.getAdmin()==false)
		{
			response.sendRedirect(request.getContextPath()+"/home/login.htm");
			return false;
		}
		return true;
	}
}