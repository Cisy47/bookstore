package Filter;

import Entity.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/* Ajax页面: 管理员登陆Filter
 * */

public class AjaxPagePriorityFilter implements Filter{

	public void destroy() {
		// TODO Auto-generated method stub
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		System.out.println("AjaxPagePriorityFilter");
		RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/PleaseLogin.jsp");
		RequestDispatcher dispatcher2 = request.getRequestDispatcher("/jsp/PriorityLimited.jsp");
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpSession hSession = req.getSession();
		if (hSession.getAttribute("user") == null){
			//没有登录
			System.out.println("没有登录");
			dispatcher.forward(request, response);
			return;
		}
		//已经登录
		System.out.println("已经登录");
		User user = (User)hSession.getAttribute("user");
		if (user.getRole()==1){
			System.out.println("没有权限");
			dispatcher2.forward(request, response);
			return;
		}
		System.out.println("有管理员权限");
		chain.doFilter(request, response);
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
}
