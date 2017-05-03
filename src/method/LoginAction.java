package method;

import Biz.UserBiz;
import Entity.User;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import net.sf.json.JSONObject;

import java.util.Map;

/**
 * Created by 47 on 2016/6/4.
 */
public class LoginAction extends ActionSupport {
    private String content;
    private UserBiz userBiz;

    public LoginAction() {
    }

    public UserBiz getUserBiz() {
        return this.userBiz;
    }

    public void setUserBiz(UserBiz userBiz) {
        this.userBiz = userBiz;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String execute() throws Exception {
        ActionContext context = ActionContext.getContext();
        Map parameters = context.getParameters();
        JSONObject json = new JSONObject();
        if (parameters.get("username") != null && parameters.get("password") != null) {
            String username = ((String[]) parameters.get("username"))[0];
            String password = ((String[]) parameters.get("password"))[0];
            User user = userBiz.getUserByUsernameAndPassword(username, password);
            if (user != null) {
                json.put("status", "Success");
                json.put("message", "恭喜您登陆成功！");
                Map hSession = context.getSession();
                hSession.put("user", user);
            } else {
                json.put("status", "Wrong");
                json.put("message", "错误的用户名及密码！");
            }
            this.setContent(json.toString());
            System.out.println(content);
            return SUCCESS;
        } else {
            json.put("status", "Error");
            json.put("message", "用户名或密码为空！");
            this.setContent(json.toString());
            return SUCCESS;
        }
    }



}