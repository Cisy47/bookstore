package method;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Created by 47 on 2016/6/9.
 */
public class LogoutAction extends ActionSupport {
    public String execute() throws Exception{
        ActionContext.getContext().getSession().clear();;
        return SUCCESS;
    }
}