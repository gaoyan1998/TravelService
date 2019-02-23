package Serv;

import DB.DbManager;
import Model.Code;
import Model.Phone;
import Model.User;
import Util.AuthUtil;
import Util.GsonUtil;
import Util.Http;
import Util.HttpConfig;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by ikiler on 2019/2/23.
 * Email : ikiler@126.com
 */
@WebServlet(name = "phone", value = "/phone")
public class phone extends HttpServlet {

    HttpServletRequest request;
    HttpServletResponse response;
    User user;
    Code code;


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.request = request;
        this.response = response;
        user = AuthUtil.Auth(request);

        String action = request.getParameter("action");
        switch (action) {
            case "add":
                add();
                break;
            case "delete":
                del();
                break;
            case "select":
                sel();
                break;
        }
        response.getWriter().append(GsonUtil.GsonString(code));
    }

    private void sel() {
        List<Phone> list = DbManager.getPhone(user.getId());
        if (list != null)
            code = new Code(HttpConfig.REQUEST_SUCCESS, "sel phone success", GsonUtil.GsonString(list));
        else
            code = new Code(HttpConfig.SERVER_ERR, "err", "");

    }

    private void del() {
        String id = request.getParameter("id");
        int flage = DbManager.deletePhone(id, user.getId());
        if (flage > 0) {
            code = new Code(HttpConfig.REQUEST_SUCCESS, "del phone success", "");
        } else if (flage == 0) {
            code = new Code(HttpConfig.PARMATER_ERR, "phone not set", "");
        } else {
            code = new Code(HttpConfig.SERVER_ERR, "err", "");
        }
    }

    private void add() {
        String json = Http.getStringFromReq(request);
        Phone phone = GsonUtil.GsonToBean(json, Phone.class);
        phone.setUserId(user.getId());
        int flage = DbManager.addPhone(phone);
        if (flage > 0) {
            code = new Code(HttpConfig.REQUEST_SUCCESS, "add phone success", "");
        } else {
            code = new Code(HttpConfig.SERVER_ERR, "err", "");
        }
    }
}
