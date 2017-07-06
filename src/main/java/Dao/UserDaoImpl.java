package Dao;

import Bean.User;
import Utils.Constant;
import Utils.DBUtils;
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
    public static String sendPost(String photo1,String photo2){
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(Constant.faceppURL);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print("api_key="+Constant.faceppAPIKey+"&api_secret="+Constant.faceppAPISecret+"&image_base64_1="+photo1+"&image_base64_2="+photo2);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }

    public static void main(String[] args){
        System.out.println(sendPost(
                Photo2Base64.GetImageStr("D:\\Shared\\Tmp\\1.jpg"),
                Photo2Base64.GetImageStr("D:\\Shared\\Tmp\\2.jpg")
        ));
    }
}
