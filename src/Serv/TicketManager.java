package Serv;

import Control.BaseServlet;
import DB.DbManager;
import Model.Code;
import Model.PersonTicket;
import Model.Ticket;
import Model.User;
import Util.GsonUtil;
import Util.Http;
import Util.HttpConfig;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by ikiler on 2019/2/26.
 * Email : ikiler@126.com
 */
@WebServlet(value = "/TicketManager")
public class TicketManager extends BaseServlet {
    @Override
    public void doAction(HttpServletRequest request, HttpServletResponse response, String action) throws IOException {
        super.doAction(request, response, action);
        if (action.equals("buy")){
            buy();
        }else if (action.equals("selectPerson")){
            selPerson();
        }
    }

    private void selPerson() {
        List<PersonTicket> list = DbManager.getPersonTicket(getUser().getId());
        if (list!=null){
            setResult(new Code(HttpConfig.REQUEST_SUCCESS,"success",GsonUtil.GsonString(list)));
        }else {
            setResult(new Code(HttpConfig.SELECT_NONE,"not found",""));
        }

    }

    private void buy() {
        User user = getUser();
        String ticketId = getRequest().getParameter("id");
        setResult(DbManager.buyTicket(user.getId(),ticketId),"");
    }

    @Override
    public void sel() throws IOException {
        String json = Http.getStringFromReq(getRequest());
        Ticket ticket = GsonUtil.GsonToBean(json, Ticket.class);
        Ticket writeTicket = DbManager.getTicket(ticket.getFrom() + "", ticket.getTo() + "");
        if (writeTicket != null)
            setResult(1, GsonUtil.GsonString(writeTicket));
        else
            setResult(-1, "");
    }

    @Override
    public void del() {
        String json = Http.getStringFromReq(getRequest());
        PersonTicket ticket = GsonUtil.GsonToBean(json,PersonTicket.class);
        setResult(DbManager.delTicket(ticket),"");
    }

    @Override
    public void add() {

    }

    @Override
    public void update() {

    }
}
