package method;

import Biz.UserBiz;
import Entity.User;
import com.mongodb.gridfs.GridFSDBFile;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;

/**
 * Created by 47 on 2016/6/12.
 */
public class ShowUserPhotoAction extends ActionSupport {
    private UserBiz userBiz;
    private String content;
    private InputStream stream;

    public InputStream getStream() {
        return stream;
    }
    public void setStream(InputStream stream) {
        this.stream = stream;
    }
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

    public String execute(){
        ActionContext context = ActionContext.getContext();
        Map<String, Object> hSession = context.getSession();
        User user = (User)hSession.get("user");
        GridFSDBFile file=userBiz.getUserPhoto(user);
        try {
            if (file != null) {
                stream = file.getInputStream();
            } else {
                File local=new File("D:\\47\\sophomore\\web\\bookstore3\\webapp\\jsp\\resource\\image\\C.Cicon.jpeg");
                stream =new FileInputStream(local);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return SUCCESS;
    }
}
