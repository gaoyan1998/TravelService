package Serv;

import Config.HttpConfig;
import DB.DbManager;
import Model.Code;
import Model.User;
import Util.GsonUtil;
import Util.Http;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ikiler on 2019/2/22.
 * Email : ikiler@126.com
 */
@WebServlet("/register")
public class Register extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String json = Http.getStringFromReq(request);

        User user = GsonUtil.GsonToBean(json,User.class);
        Code code;

        if (user.getName().equals("") || user.getPwd().equals("") || user.getEmail().equals("")){
            code = new Code(HttpConfig.PARMATER_ERR,"参数错误","");
        }
        if (DbManager.getUser(user.getName()) != null){
            code = new Code(HttpConfig.USER_ALREDAY_EXITS,"该用户已存在","");
        }else {
            DbManager.addUser(user.getName(),user.getPwd(),user.getEmail());
            code = new Code(HttpConfig.REQUEST_SUCCESS,"注册成功","");
        }
        response.getWriter().append(GsonUtil.GsonString(code));
    }
}
