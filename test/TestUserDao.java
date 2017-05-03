import Dao.UserDao;
import Dao.UserDaoImp;
import Entity.User;
import org.junit.Test;

/**
 * Created by 47 on 2016/6/4.
 */
public class TestUserDao {

    @Test
    public void testUserDao() {
        UserDao userDao = new UserDaoImp();
        User user = userDao.getUserByUsername("amber");
        System.out.print(user);
    }
}
