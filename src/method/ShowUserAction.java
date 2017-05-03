package method;

import Biz.UserBiz;
import Entity.User;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import net.sf.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Created by 47 on 2016/6/6.
 */
public class ShowUserAction extends ActionSupport {
    private String content;
    User[] userArray;
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

    public User[] getUserArray() {
        return userArray;
    }

    public void setUserArray(User[] userArray) {
        this.userArray = userArray;
    }

    public String execute() throws Exception{
        ActionContext context = ActionContext.getContext();
        Map<String, Object> hSession = context.getSession();
        JSONObject json = new JSONObject();
        List arr=userBiz.getAllUser();
        userArray=new User[arr.size()];
        arr.toArray(userArray);
        context.put("userArray",userArray);
        return SUCCESS;
    }
}
