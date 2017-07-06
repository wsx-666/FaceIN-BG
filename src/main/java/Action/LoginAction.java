package Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import javax.annotation.Resource;
import Dao.UserDao;
import Dao.UserDaoImpl;
import Bean.User;
import java.util.Map;

/**
 * Created by SSS on 2017/7/4.
 */
public class LoginAction extends ActionSupport {
    /*
    * Login Default
    * Param:null
    * Dao:LoginPassword()  LoginPhoto()
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
    //实现账号登录
    public String loginPassword() throws Exception{
            System.out.println("execute");
            if(userDao==null)
                userDao=new UserDaoImpl();
            User checkUser=userDao.checkUser(user.getUname(),user.getPassword());
            if(null==checkUser){
                return ERROR;
            }
            else {
                System.out.println("user ok"+checkUser+" uname:"+checkUser.getUname()+" password:"+checkUser.getPassword());
                ActionContext.getContext().put("tip", getText("success"));
                ActionContext.getContext().put("uname", user.getUname());
                Map session = ActionContext.getContext().getSession();
                user.setUname(user.getUname());
                session.put("user", user);
                return SUCCESS;
            }
    }

    //实现刷脸登录
    public String loginPhoto()throws Exception{
        return null;
    }
    /*
    * Param Exam
    * 重写validate完成输入校验
    * */
    @Override
    public void validate() {
        if(user.getUname()==null||user.getUname().trim().equals(""))
            addFieldError("uname","用户名不能为空");
        else if(user.getUname().matches("[\\w]{6,15}$"))
            addFieldError("uname","用户名不合法");
        if(user.getPassword()==null||user.getPassword().trim().equals(""))
            addFieldError("password","密码不能为空");
    }
    /*
    * Error return
    * */
    @Override
    public void addFieldError(String fieldName, String errorMessage) {
        super.addFieldError(fieldName, errorMessage);
    }
}
