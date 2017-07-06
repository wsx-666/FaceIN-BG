package Bean;

import java.io.Serializable;

/**
 * Created by SSS on 2017/7/4.
 */
public class User implements Serializable {
    private static final long serialVersionUID = 107717250835356582L;
    private String uname;
    private String password;
    private String photo;

    public User(){

    }
    public User(String uname, String password) {
        this.uname = uname;
        this.password = password;
    }
    public User(String photo){
        this.photo=photo;
    }

    public String getUname() {return uname;}

    public String getPassword() {return password;}

    public String getPhoto() {return photo;}

    public void setUname(String uname) {this.uname = uname;}

    public void setPassword(String password) {this.password = password;}

    public void setPhoto(String photo) {this.photo = photo;}
}
