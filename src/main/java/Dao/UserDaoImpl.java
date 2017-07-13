package Dao;

import Bean.User;
import Utils.Constant;
import Utils.DBUtils;
import Utils.FaceRequestUtil;
import Utils.Photo2Base64;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 类说明：实现UserDao接口
 * Created by SSS on 2017/7/5.
 */
public class UserDaoImpl implements UserDao {
    public User checkUser(String uname, String password) {
        User result=null;
        DBUtils pool=null;
        Connection con=null;
        try{
            pool=DBUtils.getInstance();
            con=pool.getConnection();
        }catch (Exception se){
            System.out.println("数据库连接失败！");
            se.printStackTrace();
        }
        System.out.println("Check() start sql");
        try{
            //select之后的字段必须指定，不能使用*代替
            String sql="select uname,password from user where uname = ? and password = ?";
            PreparedStatement preparedStatement=con.prepareStatement(sql);
            preparedStatement.setString(1,uname);
            preparedStatement.setString(2,password);
            ResultSet resultSet=preparedStatement.executeQuery();
            while(resultSet.next()){
                result=new User(resultSet.getString(1),
                        resultSet.getString(2));
            }
            System.out.println("Check() end sql");
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            pool.release(con);
        }
        return result;
    }

    public User checkUser(String photo) {
        return null;
    }

    public int addUser(String uname, String password, String photo) {
        return 0;
    }
    public static void main(String[] args){
        try {
            System.out.println(FaceRequestUtil.faceCompare("D:\\Shared\\Tmp\\1.jpg", "D:\\Shared\\Tmp\\2.jpg"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
