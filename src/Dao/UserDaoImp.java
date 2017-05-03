package Dao;

import Entity.User;
import Entity.UserProfile;
import com.mongodb.DB;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import method.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by 47 on 2016/6/4.
 */
public class UserDaoImp extends HibernateDaoSupport implements UserDao {
    private MongoTemplate mongoTemplate;
    SessionFactory sessionFactory;

    public MongoTemplate getMongoTemplate() {
        return mongoTemplate;
    }
    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public User getUserByUsername(String username) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        String hql = "from User where username=?";
        org.hibernate.Query query = session.createQuery(hql);
        query.setParameter(0, username, StandardBasicTypes.STRING);
        List<User> users = query.list();
        session.getTransaction().commit();
        if (users != null && users.size() != 0) {
            return users.get(0);
        } else {
            return null;
        }
    }

    public User getUserById(int userId) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        String hql = "from User where id=?";
        org.hibernate.Query query = session.createQuery(hql);
        query.setParameter(0, userId, StandardBasicTypes.INTEGER);
        List<User> users = query.list();
        session.getTransaction().commit();
        if (users != null && users.size() != 0) {
            return users.get(0);
        } else {
            return null;
        }
    }

    public void updateUser(User user) {
        String sql = "from User as user where user.id=?";
        List<User> result=(List<User>) this.getHibernateTemplate().find(sql,user.getId());
        User temp=result.get(0);
        temp.setCoin(user.getCoin());
        temp.setEmail(user.getEmail());
        temp.setPassword(user.getPassword());
        temp.setUsername(user.getUsername());
        temp.setRole(user.getRole());
        temp.setPayPass(user.getPayPass());
        temp.setMobile(user.getMobile());
        this.getHibernateTemplate().update(temp);
    }

    public void addUser(User user) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
    }

    public void removeUser(int userId) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        User user = (User) session.get(User.class, userId);
        if (session.get(User.class, userId) == null) {
            return;
        }
        session.delete(user);
        session.getTransaction().commit();
    }

    public List getAllUser() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        if(!session.getTransaction().isActive()){
            session.beginTransaction();
        }
        org.hibernate.Query query = session.createQuery("from User");
        List list = query.list();
        User[] userArray = new User[list.size()];
        list.toArray(userArray);
        session.getTransaction().commit();
        return list;

    }

    public UserProfile getUserProfileByMysqlId(long mysqlId){
        Query query = new Query();
        Criteria criteria = Criteria.where("mysqlId").is(mysqlId);
        query.addCriteria(criteria);
        UserProfile userProfile;
        try{
            userProfile=mongoTemplate.findOne(query, UserProfile.class);
            return userProfile;
        }
        catch (Exception ex){
            return null;
        }
    }
    public void addUserProfile(UserProfile userProfile){
        mongoTemplate.insert(userProfile);
        userProfile.setId(getUserProfileByMysqlId(userProfile.getMysqlId()).getId());
    }
    public void updateUserProfile(UserProfile userProfile){
        Query query = new Query();
        Criteria criteria = Criteria.where("id").is(userProfile.getId());
        query.addCriteria(criteria);
        Update update = new Update();
        update.set("mysqlId", userProfile.getMysqlId());
        update.set("profile", userProfile.getProfile());
        this.mongoTemplate.updateFirst(query, update, UserProfile.class);
    }

    public void updateUserPhoto(File userPhoto,String username) {
        DB db=mongoTemplate.getDb();
        GridFS gfs=new GridFS(db,"photo");
        GridFSDBFile imageForOutFut = gfs.findOne(username);
        if(imageForOutFut!=null){
            deleteUserPhoto(username);
        }
        addUserPhoto(userPhoto,username);
    }

    public GridFSDBFile getUserPhoto(String username) {
        DB db=mongoTemplate.getDb();
        GridFS gfs=new GridFS(db,"photo");
        GridFSDBFile imageForOutFut = gfs.findOne(username);
        return imageForOutFut;
    }

    public void addUserPhoto(File userPhoto,String username){
        DB db=mongoTemplate.getDb();
        GridFS gfs=new GridFS(db,"photo");
        GridFSInputFile gfsFile=null;
        try{
            gfsFile=gfs.createFile(userPhoto);
        }catch (IOException e){
            e.printStackTrace();
        }
        gfsFile.setFilename(username);
        gfsFile.save();
    }

    public void deleteUserPhoto(String username){
        DB db=mongoTemplate.getDb();
        GridFS gfs=new GridFS(db,"photo");
        gfs.remove(gfs.findOne(username));
    }
}

