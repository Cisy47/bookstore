package Filter;

import net.sf.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/* Ajax消息: 登陆Filter
 * */

public class AjaxMessageLoginedFilter implements Filter{

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response,
						 FilterChain chain) throws IOException, ServletException {

		System.out.println("AjaxMessagePageFilter");
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpSession hSession = req.getSession();
		if (hSession.getAttribute("user") == null){
			System.out.println("not login");
			resp.setContentType("text/html; charset=utf-8");
			PrintWriter out = resp.getWriter();
			JSONObject json = new JSONObject();
			json.put("status", "Error");
			json.put("message", "please login first!");
			out.write(json.toString());
			return;
		}
		//已经登录
		System.out.println("already login");
		chain.doFilter(request, response);
	}

	public void init(FilterConfig arg0) throws ServletException {

	}
}
