package Biz;

/**
 * Created by 47 on 2016/6/4.
 */
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import Entity.User;
import Entity.UserProfile;
import com.mongodb.gridfs.GridFSDBFile;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface UserBiz {
    User getUserByUsernameAndPassword(String var1, String var2);

    boolean isAvailable(String var1);

    void ModifyUser(Map<String, Object> var1);

    boolean addUser(User var1);

    void removeUser(int var1);

    List getAllUser();

    boolean changePassword(User var1, String var2, String var3);

    public void addUserProfileItem(User user, String key, String value);
    public UserProfile getUserProfileByMysqlId(long mysqlId);
    public void removeUserProfileItem(User user, String key);

    void addUserPhoto(File userPhoto,User user);

    GridFSDBFile getUserPhoto(User user);

    User getUserByAccount(String username);

    Boolean verUserPayPass(String userName,String payPass);

    void decMoney(int userId,int num) throws Exception;
}

