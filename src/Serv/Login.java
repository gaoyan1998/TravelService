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


@WebServlet("/Login")

public class Login extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String mJson = Http.getStringFromReq(request);

        User bodyUser = GsonUtil.GsonToBean(mJson,User.class);
        Code code = new Code();
        String name = bodyUser.getName();
        String pwd = bodyUser.getPwd();
        User user = DbManager.getUser(name);

        //if has return
        if (user != null){
            if (user.getPwd().equals(pwd)){
                code.setCode(HttpConfig.REQUEST_SUCCESS);
                code.setMsg("login successful");
                code.setData(GsonUtil.GsonString(user));
                String json = GsonUtil.GsonString(code);
                response.getWriter().append(json);
            }else {
                code.setCode(HttpConfig.PASSWORD_INCORRECT);
                code.setMsg("password incorrect");
                String json = GsonUtil.GsonString(code);
                response.getWriter().append(json);
            }
        }else {
            code.setCode(HttpConfig.USER_UNSET);
            code.setMsg("user unset");
            String json = GsonUtil.GsonString(code);
            response.getWriter().append(json);
        }
    }
}
