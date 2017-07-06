package Dao;
import Bean.User;

/**
 * 类说明：用户基本操作类UsersDAO接口类
 * Created by SSS on 2017/7/5.
 */
public interface UserDao {
    /*
    * 检测用户是否存在数据库，并返回该用户
    * @param account    用户名
    * @param password   密码
     * @return User 根据用户名与密码查询的结果，没有该用户或者密码不正确则返回null
    * */
    public User checkUser(String uname,String password);
    public User checkUser(String photo);
    public int addUser(String uname,String password,String photo);

}
