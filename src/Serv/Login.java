package Serv;

import Util.HttpConfig;
import Model.Code;
import Model.User;
import Util.AuthUtil;
import Util.GsonUtil;

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
        User user = AuthUtil.Auth(request);
        Code code = new Code();
        code.setCode(HttpConfig.REQUEST_SUCCESS);
        code.setMsg("login successful");
        code.setData(GsonUtil.GsonString(user));
        String json = GsonUtil.GsonString(code);
        response.getWriter().append(json);
    }
}
