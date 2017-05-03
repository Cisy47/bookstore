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
public class ShowUserProfileAction extends ActionSupport {
    private UserBiz userBiz;
    private Map<String, String> map;

    public UserBiz getUserBiz() {
        return userBiz;
    }
    public void setUserBiz(UserBiz userBiz) {
        this.userBiz = userBiz;
    }
    public Map getMap() {
        return map;
    }
    public void setMap(Map map) {
        this.map = map;
    }

    public String execute() throws Exception{
        ActionContext context = ActionContext.getContext();
        Map<String, Object> parameters = context.getParameters();
        Map<String, Object> hSession = context.getSession();
        User user = (User)hSession.get("user");
        //UserProfile userProfile = userBiz.getUserProfileByMysqlId(user.getId());
        JSONObject json=new JSONObject();
        //if(userProfile==null){
            json.put("warning","无个人信息");
            return SUCCESS;
        /*
        }else {
             json= JSONObject.fromObject(userProfile.getProfile());
             map = (Map)json;
             return SUCCESS;
        }*/
    }
}