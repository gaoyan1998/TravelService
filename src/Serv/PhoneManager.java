package Serv;

import Control.BaseServlet;
import DB.DbManager;
import Model.Code;
import Model.Phone;
import Util.GsonUtil;
import Util.Http;
import Util.HttpConfig;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by ikiler on 2019/2/23.
 * Email : ikiler@126.com
 */
@WebServlet(name = "PhoneManager", value = "/PhoneManager")
public class PhoneManager extends BaseServlet {

    @Override
    public void doAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
    }

    public void sel() {
        List<Phone> list = DbManager.getPhone(getUser().getId());
        if (list != null)
            setResult(new Code(HttpConfig.REQUEST_SUCCESS, " success", GsonUtil.GsonString(list)));
        else
            setResult(new Code(HttpConfig.SERVER_ERR, "err", ""));
    }

    public void del() {
        String id = getRequest().getParameter("id");
        setResult(DbManager.deletePhone(id, getUser().getId()),"");
    }

    public void add() {
        String json = Http.getStringFromReq(getRequest());
        Phone phone = GsonUtil.GsonToBean(json, Phone.class);
        phone.setUserId(getUser().getId());
        setResult(DbManager.addPhone(phone),"");
    }

    @Override
    public void update() {

    }
}
