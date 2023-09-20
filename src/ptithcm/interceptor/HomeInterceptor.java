package ptithcm.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import ptithcm.entity.User;

public class HomeInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response,Object handler) throws IOException
	{
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("myLogin");
		if(user==null)
		{
			return true;
		}
		if(user.getAdmin()==true)
		{
			response.sendRedirect(request.getContextPath()+"/admin/index.htm");
			return false;
		}
		return true;
	}
}