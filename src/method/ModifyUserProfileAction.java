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
public class ModifyUserProfileAction extends ActionSupport {
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
        Map<String, Object> hSession = context.getSession();
        JSONObject json = new JSONObject();
        if (parameters.get("username") == null ||
                parameters.get("email") == null ||
                parameters.get("mobile") == null){
            json.put("status", "Error");
            json.put("message", "缺少参数！");
            content = json.toString();
            return SUCCESS;
        }
        User user = (User)hSession.get("user");
        String[] str= new String[1];
        str[0] = Long.toString(user.getId());
        parameters.put("userId", str);
        if (((String[])parameters.get("username"))[0] == "" &&
                ((String[])parameters.get("email"))[0] == "" &&
                ((String[])parameters.get("mobile"))[0] == ""){
            json.put("status", "Error");
            json.put("message", "参数均为空！");
            content = json.toString();
            return SUCCESS;
        }
        if (((String[])parameters.get("username"))[0] == ""){
            parameters.remove("username");
        }
        if (((String[])parameters.get("email"))[0] == ""){
            parameters.remove("email");
        }
        if (((String[])parameters.get("mobile"))[0] == ""){
            parameters.remove("mobile");
        }
        if (parameters.get("username") != null){
            user.setUsername(((String[])parameters.get("username"))[0]);
        }
        if (parameters.get("email") != null){
            user.setEmail((((String[])parameters.get("sex"))[0]));
        }
        if (parameters.get("mobile") != null){
            user.setMobile((((String[])parameters.get("mobile"))[0]));
        }
        userBiz.ModifyUser(parameters);
        json.put("status", "Success");
        json.put("message", "更改成功！");
        content = json.toString();
        return SUCCESS;
    }
}