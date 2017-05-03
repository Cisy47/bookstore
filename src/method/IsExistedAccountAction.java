package method;

import Biz.UserBiz;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import net.sf.json.JSONObject;

import java.util.Map;

/**
 * Created by 47 on 2016/6/5.
 */
public class IsExistedAccountAction extends ActionSupport {
    private String content;
    private UserBiz userBiz;
    public UserBiz getUserBiz() {
        return userBiz;
    }
    public void setUserBiz(UserBiz userBiz) {
        this.userBiz = userBiz;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String execute() throws Exception{
        ActionContext context = ActionContext.getContext();
        Map<String, Object> parameters = context.getParameters();
        JSONObject json = new JSONObject();
        if (parameters.get("username") == null){
            json.put("status", "Error");
            json.put("message", "用户名不能为空！");
            content = json.toString();
            return SUCCESS;
        }
        String username = ((String[])parameters.get("username"))[0];
        if (userBiz.isAvailable(username)){
            json.put("status", "Available");
            json.put("message", "用户名可用！");
            content = json.toString();
            return SUCCESS;
        }else{
            json.put("status", "Existed");
            json.put("message", "用户名已存在！");
            content = json.toString();
            return SUCCESS;
        }
    }
}