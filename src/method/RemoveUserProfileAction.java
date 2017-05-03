package method;

import Biz.UserBiz;
import Entity.User;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import net.sf.json.JSONObject;

import java.util.Map;

/**
 * Created by 47 on 2016/6/12.
 */
public class RemoveUserProfileAction extends ActionSupport {
    private String content;
    private UserBiz userBiz;

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public UserBiz getUserBiz() {
        return userBiz;
    }
    public void setUserBiz(UserBiz userBiz) {
        this.userBiz = userBiz;
    }
    public String execute() throws Exception{
        ActionContext context = ActionContext.getContext();
        Map<String, Object> parameters = context.getParameters();
        Map<String, Object> hSession = context.getSession();
        JSONObject json = new JSONObject();
        if (parameters.get("key") == null ||
                ((String[])parameters.get("key"))[0] == ""){
            json.put("status", "Error");
            json.put("message", "参数不全或参数为空！");
            content = json.toString();
            return SUCCESS;
        }
        userBiz.removeUserProfileItem((User)hSession.get("user"), ((String[])parameters.get("key"))[0]);
        json.put("status", "Success");
        json.put("message", "删除成功！");
        content = json.toString();
        return SUCCESS;
    }
}
