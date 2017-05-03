package method;

import Biz.UserBiz;
import Entity.User;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import net.sf.json.JSONObject;

import java.io.File;
import java.util.Map;

/**
 * Created by 47 on 2016/6/13.
 */
public class AddUserPhotoAction extends ActionSupport {
    private UserBiz userBiz;
    private String content;
    private File file;

    public File getFile() {
        return file;
    }
    public void setFile(File file) {
        this.file = file;
    }
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

    public String execute(){
        ActionContext context = ActionContext.getContext();
        Map<String, Object> parameters = context.getParameters();
        Map<String, Object> hSession = context.getSession();
        JSONObject json = new JSONObject();
        User user = (User)hSession.get("user");
        userBiz.addUserPhoto(file,user);
        json.put("message","成功上传头像");
        content=json.toString();
        return SUCCESS;
    }
}
