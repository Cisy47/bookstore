package Biz;

import Dao.UserDao;
import Dao.UserDaoImp;
import Entity.User;
import Entity.UserProfile;
import com.mongodb.gridfs.GridFSDBFile;
import net.sf.json.JSONObject;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import security.RSAutil;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by 47 on 2016/6/4.
 */
public class UserBizImp implements UserBiz {
    UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
    public void UserBizImpl(){
        userDao = new UserDaoImp();
    }

    public User getUserByUsernameAndPassword(String username, String password){
        User user = userDao.getUserByUsername(username);
        if (user == null){
            return null;
        }
        if (user.getPassword().equals(password)){
            return user;
        }else{
            return null;
        }
    }

    public boolean isAvailable(String username){
        if (userDao.getUserByUsername(username) == null){
            return true;
        }else{
            return false;
        }
    }

    public void ModifyUser(Map<String, Object> parameters){
        int userId = Integer.parseInt(((String[])parameters.get("userId"))[0]);
        User user = userDao.getUserById(userId);
        if (parameters.get("username") != null){
            user.setUsername(((String[])parameters.get("username"))[0]);
        }
        if (parameters.get("coin") != null){
            user.setCoin(Integer.parseInt(((String[])parameters.get("coin"))[0]));
        }
        if (parameters.get("mobile") != null){
            user.setMobile(((String[])parameters.get("mobile"))[0]);
        }
        if(parameters.get("email")!=null){
            user.setEmail(((String[])parameters.get("email"))[0]);
        }
        if (parameters.get("password") != null){
            user.setPassword(((String[])parameters.get("password"))[0]);
        }
        if(parameters.get("role")!=null){
            user.setRole(Integer.parseInt(((String[])parameters.get("role"))[0]));
        }
        userDao.updateUser(user);
    }

    public boolean addUser(User user){
        if (isAvailable(user.getUsername())){
            userDao.addUser(user);
            return true;
        }else{
            return false;
        }
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void removeUser(int userId){
        userDao.removeUser(userId);
    }

    //获取所有用户和其统计信息
    public List getAllUser(){
        return userDao.getAllUser();
    }

    public boolean changePassword(User user, String originalPassword, String newPassword){
        //不从数据库中重新取出密码
        if (!user.getPassword().equals(originalPassword))
            return false;
        user.setPassword(newPassword);
        userDao.updateUser(user);
        return true;
    }

    public void addUserProfileItem(User user, String key, String value){
        UserProfile userProfile = userDao.getUserProfileByMysqlId(user.getId());
        if (userProfile == null){
            userProfile = new UserProfile();
            userProfile.setMysqlId(user.getId());
            userDao.addUserProfile(userProfile);
        }
        String profile = userProfile.getProfile();
        JSONObject json;
        if (profile == null)
            json = new JSONObject();
        else
            json = JSONObject.fromObject(profile);
        json.put(key, value);
        userProfile.setProfile(json.toString());
        userDao.updateUserProfile(userProfile);
    }
    public UserProfile getUserProfileByMysqlId(long mysqlId){
        return userDao.getUserProfileByMysqlId(mysqlId);
    }
    public void removeUserProfileItem(User user, String key){
        UserProfile userProfile = userDao.getUserProfileByMysqlId(user.getId());
        if (userProfile == null){
            return;
        }
        String profile = userProfile.getProfile();
        JSONObject json;
        if (profile == null)
            return;
        else
            json = JSONObject.fromObject(profile);
        json.remove(key);
        userProfile.setProfile(json.toString());
        userDao.updateUserProfile(userProfile);
    }

    public void addUserPhoto(File userPhoto, User user) {
        userDao.updateUserPhoto(userPhoto,user.getUsername());
    }

    public GridFSDBFile getUserPhoto(User user) {
        return userDao.getUserPhoto(user.getUsername());
    }

    public User getUserByAccount(String username) {
        User user = userDao.getUserByUsername(username);
        if (user == null){
            return null;
        }else{
            return user;
        }
    }

    public Boolean verUserPayPass(String username,String payPass){
        User u = getUserByAccount(username);
        String privateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC+JK4K7hiD9YDKOnTqqJoT+KgjYz8AJP1RNRXFa0zshmVcxBk+MufQfXHTOYn9UsiGcGWLJvTpAu+2usEVG3iOc4lHkuOhOD+Dxdh1Vc3pr5cWOoW85Q+DNns53LA6hfGI74ztau9PReCrIGoKcMAOljV65wOp8gNhg4Ecpu0ySHQ3hlUFx9QtvVKb5Dt536NRrx5c9A+Eyta+FCQh5kmH6EIis2NdTHJ9k+HW1K+qglgCtvIdmzwudtS2MhALH06VH37ElPHcv2Hz0jgP6GoDgJmrFTN+Juy7leBgqehKuLY4JHYV59J9iUJ3wXKynTARP+vUvXjWqeGgxC2tSzyPAgMBAAECggEAMhFkhtpFOFIoFJgp+zRkRgf+9jqG91nGHmEVF4P2oH2PKUs1vmwXII43r8AB9uOai9QC2Q5sBQNR7dLlTtKJ/zCrIF6sc+JkzyUEp3jtnLAw35iPaLsER6/L6OOUwARPIpi5ijbTRxOGYmlJovAnkm+5K2CzVUe13jKLh+joool/ReZk0Rsr4tVLSLmvzDA/sRwYun0x0+jl5EZSQfwsVyN9bD5rY/In/EuvH9yj5R4lPe+mimF4Os6IgTsP5LzqDTAiFx5NNioFRJ2SkcTmM0CZQeMIBuvvF2HCtJlDEfCytD7wYup3GBvar2ccOe9T3YhJdsj5bfAJHVJtamxQwQKBgQDnYReMMzqAh2HOFL8QymzOjImsrOz6NCZatq38TU1hSe9PK+C0sFGhkd788y4AuURS1Btu4i7F+hOYcj1z3L+NSPGE3yLHVjakMrrNbA9rwG/t7oU0cG7d0WWM9bcTQiCSNcUyt69BGH3dZdqee1tITzqghE7+gh9RYiVcI6/8LwKBgQDSYEuWFLMUsR/s7unSHCucuEXjwbYrvknv8Y81sjvrWktNXrJoYlbGy/7HYA6lxzchtSxhPuUSjopwQ5scgMhqf8Gxz7jsDN9ak2dErF7cWRFYfh6aKhkbEw9oG01jIX15MK0TbMafoJslDhPQF1cP9i0+ZGg+gPbASdeUVRTNoQKBgQCOjwDOLgYeiMtXCOtL8hymCmsNDCKaaiUzgRijuhEyHzamJhe13Gj/TnwAh+hRI9UX333jjNJawqDuLXz1dQ5Eg6vjPQQVo2XZNzRnOuwpbJDKHUrPK3Lzkn+qIP6ii/y7eQu+GvSM/AUYsxfGy6RLYh1yJvLw1sVrBDiWk5prmwKBgFvgrmI3XBa3XKgPl5KptupVGEDmAveLvaLLLq5WzxB0eNqrduNbv2ZHBVhxvTPtk0hnZaB65XR7SD7LZ9zE6cKJVUCg5bRB0vIt2jYFydAWHhs1yYuuwxQt+NaQxfV7VN8uwQfww7ZHYDqIsWJ6Lw3Lh+rt0xEpJZrJJRulJNbBAoGBAK1OEnfBpSB99N8gdhp+ZGLsDfwFCQ2Cd4Jpsd4hxdwbXevNuA1OiE20sHPuKqEqfOKocgTMobCwbSfnymatRydVoeUumkEc4Ja+XDgH+P1eXQLdIuRCwh0AXl+vkuOCBDMw367Zp/j6vwPlNKh9ZmOBPwhV0Syv2Z8uGkTZ6g+f";
        String p = RSAutil.decrypt(privateKey,payPass);
        if(u==null||u.getPayPass()==null){
            return false;
        }
        return p.equals(u.getPayPass());
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void decMoney(int userId,int num) throws Exception{
        User user=userDao.getUserById(userId);
        if(user.getCoin()<num)
            throw new Exception("余额不足");
        user.setCoin(user.getCoin()-num);
        userDao.updateUser(user);
    }
}
