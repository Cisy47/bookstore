package functional.WebService.SOAP;

import Biz.UserBiz;
import Dao.UserDao;
import Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * Created by siqi.lou on 11/05/2017.
 */
@Stateless
@WebService
@Component("WebService")
public class WebServiceBean {
    @Autowired
    private UserDao userDao;

    @WebMethod
    public User getUser(int id){
        return userDao.getUserById(id);
    }
}
