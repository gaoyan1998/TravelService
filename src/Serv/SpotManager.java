package Serv;

import Control.BaseServlet;
import Control.FileUpload;
import DB.DbManager;
import DB.JdbcUtil;
import Model.Code;
import Model.Food;
import Model.Spot;
import Util.GsonUtil;
import Util.Http;
import Util.HttpConfig;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ikiler on 2019/2/24.
 * Email : ikiler@126.com
 */
@WebServlet(name = "SpotManager", value = "/SpotManager")
public class SpotManager extends BaseServlet {

    @Override
    public void doAction(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }
    @Override
    public void sel() throws IOException {
        String json = GsonUtil.GsonString(DbManager.getSpot(JdbcUtil.localhost));
        Code code = new Code(HttpConfig.REQUEST_SUCCESS,"success",json);
        setResult(code);
    }

    @Override
    public void del() {
        String id = getRequest().getParameter("id");
        setResult(DbManager.deleteSpot(id),"");
    }

    @Override
    public void add() {
        FileUpload upload = new FileUpload(getRequest());
        String json = upload.getmJson();
        String path = upload.getPath();
        Spot spot = GsonUtil.GsonToBean(json, Spot.class);
        spot.setImagePath(path);
        setResult(DbManager.addSpot(spot), "");
    }

    @Override
    public void update() {
        FileUpload upload = new FileUpload(getRequest());
        String json = upload.getmJson();
        String path = upload.getPath();
        Spot spot = GsonUtil.GsonToBean(json, Spot.class);
        spot.setImagePath(path);
        setResult(DbManager.upDateSpot(spot), "");
    }
}
