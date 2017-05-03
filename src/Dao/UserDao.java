package Dao;

import Entity.User;
import Entity.UserProfile;
import com.mongodb.gridfs.GridFSDBFile;

import java.io.File;
import java.util.List;

/* UserDao 用户持久化操作类
 * */

public interface UserDao {
	//通过username获取user
	User getUserByUsername(String username);
	//通过ID获取user
	User getUserById(int userId);
	//更新user基础信息
	void updateUser(User user);
	//添加user
	void addUser(User user);
	//通过ID删除user
	void removeUser(int userId);
	//获取所有user列表,并获得其订单数
	List getAllUser();
	//获取UserProfile
	public UserProfile getUserProfileByMysqlId(long mysqlId);
	//添加UserProfile
	public void addUserProfile(UserProfile userProfile);
	//更新UserProfile
	public void updateUserProfile(UserProfile userProfile);
	// 更新照片
	void updateUserPhoto(File userPhoto,String username);
	// 获取照片
	GridFSDBFile getUserPhoto(String username);
	// 插入照片
	void addUserPhoto(File userPhoto,String username);
	// 删除照片
	void deleteUserPhoto(String username);

}
