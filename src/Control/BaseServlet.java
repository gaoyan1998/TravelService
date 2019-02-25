package Control;

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
 * Created by ikiler on 2019/2/25.
 * Email : ikiler@126.com
 */
public abstract class BaseServlet extends HttpServlet {

    HttpServletRequest request;
    HttpServletResponse response;
    User user;
    Code code;


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.request = request;
        this.response = response;
        user = AuthUtil.AuthSession(request);
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
        doAction(request, response);
        write();
    }

    public void write() throws IOException {
        getResponse().getWriter().append(GsonUtil.GsonString(getResult()));
    }
    public void setResult(int flage,String json){
        if (flage > 0) {
            code = new Code(HttpConfig.REQUEST_SUCCESS, " success", json);
        } else if (flage == 0) {
            code = new Code(HttpConfig.PARMATER_ERR, " not set", "");
        } else {
            code = new Code(HttpConfig.SERVER_ERR, "err", "");
        }
    }
    public void setResult(Code code){
        this.code = code;
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public abstract void doAction(HttpServletRequest request, HttpServletResponse response) throws IOException;

    public abstract void sel() throws IOException;

    public abstract void del();

    public abstract void add();

    public abstract void update();



    public HttpServletRequest getRequest() {
        return request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public User getUser() {
        return user;
    }

    public Code getResult() {
        return code;
    }
}
