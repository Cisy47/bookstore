package functional.WebService.SOAP;

import Dao.UserDao;
import Entity.User;

import javax.jws.WebParam;

/**
 * Created by 47 on 2017/5/15.
 */

public class UserServiceImpl implements UserService {

    private UserDao userDao;

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User getUser(@WebParam(name = "userId") int id){
        return userDao.getUserById(id);
    }
}

