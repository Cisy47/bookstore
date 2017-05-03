package Filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UserLoginedFilter implements Filter{

	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		RequestDispatcher dispatcher = request.getRequestDispatcher("Welcome.jsp");
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpSession hSession = req.getSession();
		if (req.getRequestURI().indexOf("Register.jsp") != -1){
			chain.doFilter(request, response);
			return;
		}
		if (hSession.getAttribute("user") == null){
			//没有登录
			System.out.println("没有登录");
			dispatcher.forward(request, response);
			return;
		}
		//已经登录
		System.out.println("已经登录");
		chain.doFilter(request, response);
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
