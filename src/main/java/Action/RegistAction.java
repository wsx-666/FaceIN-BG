package Action;

import Bean.User;
import Dao.UserDaoImpl;
import com.opensymphony.xwork2.ActionSupport;

import javax.annotation.Resource;
import javax.swing.*;

/**
 * Created by SSS on 2017/7/6.
 */
public class RegistAction extends ActionSupport {
    /*
    *Regist Default
    * Param:null
    * Dao: regist()
    * return SUCCESS means:login ok
    *        ERROR means:login error
    * mark need return messages
    * */
    private static final long serialVersionUID = 5386429367683022172L;

    @Resource
    private UserDaoImpl userDao;
    private User user;

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    @Override
    public String execute() throws Exception {
        return super.execute();
    }
    //用户注册
    public String regist(){
        if(null==userDao)
            userDao=new UserDaoImpl();
        int result=userDao.addUser(user.getUname(),user.getPassword(),user.getPhoto());
        System.out.println(result);
        if(result!=-1){
            JOptionPane.showMessageDialog(null,"注册成功！");
            return SUCCESS;
        }
        else{
            JOptionPane.showMessageDialog(null,"注册失败！");
            return ERROR;
        }
    }
}
