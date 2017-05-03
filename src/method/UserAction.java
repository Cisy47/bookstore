package method;

import Biz.UserBiz;
import Entity.User;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by 47 on 2016/6/11.
 */

public class UserAction extends ActionSupport {
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
                parameters.get("coin") == null ||
                parameters.get("password")==null||
                parameters.get("mobile") == null ||
                parameters.get("email") == null ||
                parameters.get("role") == null ){
            json.put("status","error");
            json.put("warning","信息不完整！");
            content = json.toString();
            return SUCCESS;
        }
        if (((String[])parameters.get("username"))[0] == "" ||
                ((String[])parameters.get("coin"))[0] == "" ||
                ((String[])parameters.get("password"))[0] == ""||
        ((String[])parameters.get("mobile"))[0] == "" ||
                ((String[])parameters.get("email"))[0] == "" ||
                ((String[])parameters.get("role"))[0] == "" ){
            json.put("status","error");
            json.put("warning","信息不完整！");
            content = json.toString();
            return SUCCESS;
        }
        String username = ((String[])parameters.get("username"))[0];
        String mobile = ((String[])parameters.get("mobile"))[0];
        String email = ((String[])parameters.get("email"))[0];
        String password=((String[])parameters.get("password"))[0];
        int role = Integer.parseInt(((String[])parameters.get("role"))[0]);
        int coin = Integer.parseInt(((String[])parameters.get("coin"))[0]);
        User user=new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setMobile(mobile);
        user.setEmail(email);
        user.setCoin(coin);
        user.setRole(role);
        userBiz.addUser(user);
        json.put("status", "success");
        json.put("message", "添加成功!");
        content = json.toString();
        return SUCCESS;
    }

    public String getUser() throws Exception{
        ActionContext context = ActionContext.getContext();
        HttpServletRequest request = (HttpServletRequest)context.get(ServletActionContext.HTTP_REQUEST);
        String username = request.getUserPrincipal().getName();
        User user = userBiz.getUserByAccount(username);
        context.getSession().put("user",user);
        return SUCCESS;
    }

    public String addUserProfile() throws Exception{
        ActionContext context = ActionContext.getContext();
        Map<String, Object> parameters = context.getParameters();
        Map<String, Object> hSession = context.getSession();
        JSONObject json = new JSONObject();
        if (parameters.get("key") == null ||
                parameters.get("value") == null ||
                ((String[])parameters.get("key"))[0] == "" ||
                ((String[])parameters.get("value"))[0] == "" ){
            json.put("status", "Error");
            json.put("message", "参数不全或参数为空！");
            content = json.toString();
            return SUCCESS;
        }
        userBiz.addUserProfileItem((User)hSession.get("user"), ((String[])parameters.get("key"))[0], ((String[])parameters.get("value"))[0]);
        json.put("status", "Success");
        json.put("message", "添加成功！");
        content = json.toString();
        return SUCCESS;
    }

    public String changePassword() throws Exception{
        ActionContext context = ActionContext.getContext();
        Map<String, Object> parameters = context.getParameters();
        Map<String, Object> hSession = context.getSession();
        JSONObject json = new JSONObject();
        if (parameters.get("originalPassword") == null ||
                parameters.get("newPassword") == null ||
                ((String[])parameters.get("originalPassword"))[0] == "" ||
                ((String[])parameters.get("newPassword"))[0] == "" ){
            json.put("status", "Error");
            json.put("message", "缺少参数或参数为空！");
            content = json.toString();
            return SUCCESS;
        }
        String originalPassword = ((String[])parameters.get("originalPassword"))[0];
        String newPassword = ((String[])parameters.get("newPassword"))[0];
        User user = (User)hSession.get("user");
        if (userBiz.changePassword(user, originalPassword, newPassword)){
            json.put("status", "Success");
            json.put("message", "修改成功！");
            content = json.toString();
            return SUCCESS;
        }else{
            json.put("status", "Wrong");
            json.put("message", "修改失败，原密码不正确！");
            content = json.toString();
            return SUCCESS;
        }

    }

    public String modifyUser() throws Exception{
        ActionContext context = ActionContext.getContext();
        Map<String, Object> parameters = context.getParameters();
        Map<String, Object> hSession = context.getSession();
        JSONObject json = new JSONObject();
        if (parameters.get("userId") == null){
            json.put("status", "Error");
            json.put("status", "错误的参数!");
            content = json.toString();
            return SUCCESS;
        }
        if (parameters.get("role") != null){
            if (((String[])parameters.get("role"))[0] != ""){
                int temp = Integer.parseInt(((String[])parameters.get("role"))[0]);
                if (temp !=1&&temp!=2){
                    parameters.remove("role");
                }
            }
        }
        userBiz.ModifyUser(parameters);
        json.put("status", "Success");
        json.put("message", "更改成功！");
        content = json.toString();
        return SUCCESS;
    }

}
