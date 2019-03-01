package DB;

import Model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbManager {

    public static User getUser(String name) {
        User user = null;
        String sql = "SELECT * FROM travelapp.user where user_name = ?";
        try {
            ResultSet resultSet = JdbcUtil.query(sql, name);
            //如果返回结果为空，直接返回null；
            if (!resultSet.next()) {
                user = null;
            } else {
                user = new User();
                user.setId(resultSet.getInt("_id"));
                user.setName(name);
                user.setPwd(resultSet.getString("user_pwd"));
                user.setEmail(resultSet.getString("user_email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.releaseConn();
        }
//        System.out.println("DbManager:获取用户信息");
        return user;
    }

    public static int addUser(String name, String pwd, String email) {
        String sql = "INSERT INTO `travelapp`.`user` (`user_name`, `user_pwd`, `user_email`) VALUES (?, ?, ?)";
        int flage = JdbcUtil.executelSql(sql, name, pwd, email);
//        System.out.println("DbManager:添加用户");
        return flage;
    }

    public static List<Note> getNote(User user) {
        String sql = "SELECT * FROM travelapp.note where user_id = ?";
        List<Note> list = null;
        try {
            ResultSet resultSet = JdbcUtil.query(sql, user.getId() + "");
            if (!resultSet.next()) {
                list = null;
            } else {
                list = new ArrayList<>();
                do {
                    Note note = new Note();
                    note.setId(resultSet.getInt("_id"));
                    note.setContent(resultSet.getString("content"));
                    note.setUserId(resultSet.getInt("user_id"));
                    note.setPicPath(resultSet.getString("pic_path"));
                    note.setVoicePath(resultSet.getString("voice_path"));
                    list.add(note);
                } while (resultSet.next());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.releaseConn();
        }
        System.out.println("DbManager:返回用户日记列表，用户名：" + user.getName() + " 数量：" + list.size());
        return list;
    }

    public static int addNote(Note note) {
        String sql = "INSERT INTO `travelapp`.`note` (`user_id`, `content`, `pic_path`, `voice_path`) VALUES (?, ?, ?, ?)";
        int flage = JdbcUtil.executelSql(sql, note.getUserId() + "", note.getContent(), note.getPicPath(), note.getVoicePath());
//        System.out.println("DbManager:添加日记");
        return flage;
    }

    public static int deleteNote(String id) {
        String sql = "delete from travelapp.note where _id = ?";
        int flage = JdbcUtil.executelSql(sql, id);
        ;
//        System.out.println("DbManager:删除用户日记");
        return flage;
    }

    public static int addPhone(Phone phone) {
        String sql = "INSERT INTO `travelapp`.`phone` (`phone_name`, `phone_number`, `user_id`) VALUES (?, ?, ?)";
        int flage = JdbcUtil.executelSql(sql, phone.getName(), phone.getNumber(), phone.getUserId() + "");
//        System.out.println("DbManager:添加手机号");
        return flage;
    }

    public static int deletePhone(String id, int userId) {
        String sql = "delete  FROM travelapp.phone where _id = ? and user_id = ?";
        int flage = JdbcUtil.executelSql(sql, id + "", userId + "");
//        System.out.println("DbManager:删除手机号");
        return flage;
    }

    public static List<Phone> getPhone(int id) {

        List<Phone> list = null;

        String sql = "SELECT * FROM travelapp.phone where user_id = ?";
        try {
            ResultSet resultSet = JdbcUtil.query(sql, id + "");
            if (!resultSet.next()) {
                list = null;
            } else {
                list = new ArrayList<>();
                do {
                    Phone phone = new Phone();
                    phone.setId(resultSet.getInt("_id"));
                    phone.setName(resultSet.getString("phone_name"));
                    phone.setNumber(resultSet.getString("phone_number"));
                    phone.setUserId(resultSet.getInt("user_id"));
                    list.add(phone);
                } while (resultSet.next());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        System.out.println("DbManager:查询手机号");
        return list;
    }

    public static List<Food> getFood(String url) {
//        System.out.println("查询食物");

        List<Food> list = null;
        String sql = "SELECT * FROM travelapp.food";
        try {
            ResultSet resultSet = JdbcUtil.query(sql);
            if (!resultSet.next()) {
                list = null;
            } else {
                list = new ArrayList<>();
                do {
                    Food food = new Food();
                    food.setId(resultSet.getInt("_id"));
                    food.setImagePath(url + "/" + resultSet.getString("img"));
                    food.setName(resultSet.getString("name"));
                    food.setText(resultSet.getString("text"));
                    list.add(food);
                } while (resultSet.next());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.releaseConn();
        }
        return list;
    }

    public static int addFood(Food food) {
        String sql = "INSERT INTO `travelapp`.`food` (`name`, `text`, `img`) VALUES (?,?,?)";
        int flage = JdbcUtil.executelSql(sql, food.getName(), food.getText(), food.getImagePath());
//        System.out.println("添加食物");
        return flage;
    }

    public static int upDateFood(Food food) {
        String sql = "UPDATE `travelapp`.`food` SET `name` = ?, `text` = ?, `img` = ? WHERE (`_id` = ?)";
        int flage = JdbcUtil.executelSql(sql, food.getName(), food.getText(), food.getImagePath(), food.getId() + "");
        return flage;
    }

    public static int deleteFood(String id) {
        String sql = "DELETE FROM `travelapp`.`food` WHERE (`_id` = ?)";
        int flage = JdbcUtil.executelSql(sql, id);
        return flage;
    }

    public static int addSpot(Spot spot) {
        String sql = "INSERT INTO `travelapp`.`spot` (`name`, `text`, `img`) VALUES (?,?,?)";
        int flage = JdbcUtil.executelSql(sql, spot.getName(), spot.getText(), spot.getImagePath());
        return flage;
    }

    public static int upDateSpot(Spot spot) {
        System.out.println("修改景点信息");
        String sql = "UPDATE `travelapp`.`spot` SET `name` = ?, `text` = ?, `img` = ? WHERE (`_id` = ?)";
        int flage = JdbcUtil.executelSql(sql, spot.getName(), spot.getText(), spot.getImagePath(), spot.getId() + "");
        return flage;
    }

    public static int deleteSpot(String id) {
        String sql = "DELETE FROM `travelapp`.`spot` WHERE (`_id` = ?)";
        int flage = JdbcUtil.executelSql(sql, id);
        return flage;
    }

    public static List<Food> getSpot(String url) {

        List<Food> list = null;
        String sql = "SELECT * FROM travelapp.spot";
        try {
            ResultSet resultSet = JdbcUtil.query(sql);
            if (!resultSet.next()) {
                list = null;
            } else {
                list = new ArrayList<>();
                do {
                    Spot spot = new Spot();
                    spot.setId(resultSet.getInt("_id"));
                    spot.setImagePath(url + "/" + resultSet.getString("img"));
                    spot.setName(resultSet.getString("name"));
                    spot.setText(resultSet.getString("text"));
                    list.add(spot);
                } while (resultSet.next());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.releaseConn();
        }
        return list;
    }

    public static List<Addr> getAddr() {
        String sql = "SELECT * FROM travelapp.addr";
        List<Addr> list = null;
        try {
            ResultSet resultSet = JdbcUtil.query(sql);
            if (!resultSet.next()) {
                list = null;
            } else {
                list = new ArrayList<>();
                do {
                    Addr addr = new Addr();
                    addr.setId(resultSet.getInt("_id"));
                    addr.setName(resultSet.getString("addr_name"));
                    list.add(addr);
                } while (resultSet.next());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.releaseConn();
        }
        return list;
    }

    public static Ticket getTicket(String from, String to) {
        String sql = "SELECT * FROM travelapp.ticket where ticket_from = ? and ticket_to = ?";
        Ticket ticket = null;
        try {
            ResultSet resultSet = JdbcUtil.query(sql, from, to);
            if (!resultSet.next()) {
                return null;
            } else {
                ticket = new Ticket();
                ticket.setId(resultSet.getInt("_id"));
                ticket.setFrom(resultSet.getInt("ticket_from"));
                ticket.setTo(resultSet.getInt("ticket_to"));
                ticket.setRest(resultSet.getInt("ticket_rest"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            JdbcUtil.releaseConn();
        }
        return ticket;
    }

    public static Ticket getTicket(String ticketId) {
        String sql = "SELECT * FROM travelapp.ticket where _id = ?";
        Ticket ticket = null;
        try {
            ResultSet resultSet = JdbcUtil.query(sql, ticketId);
            if (!resultSet.next()) {
                return null;
            } else {
                ticket = new Ticket();
                ticket.setId(resultSet.getInt("_id"));
                ticket.setFrom(resultSet.getInt("ticket_from"));
                ticket.setTo(resultSet.getInt("ticket_to"));
                ticket.setRest(resultSet.getInt("ticket_rest"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            JdbcUtil.releaseConn();
        }
        return ticket;
    }

    public static List<PersonTicket> getPersonTicket(int userId){
        List<PersonTicket> list = null;
        String sql = "SELECT * FROM travelapp.person_ticket where user_id = ?";
        try {
            ResultSet resultSet = JdbcUtil.query(sql,userId+"");
            if (!resultSet.next()){
                return null;
            }
            list = new ArrayList<>();
            do {
                PersonTicket ticket = new PersonTicket();
                ticket.setId(resultSet.getInt("_id"));
                ticket.setUserId(resultSet.getInt("user_id"));
                ticket.setTicketId(resultSet.getInt("ticket_id"));
                list.add(ticket);
            }while (resultSet.next());
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return list;
    }
    public static int buyTicket(int userId, String ticketId) {
        Ticket ticket = getTicket(ticketId + "");
        if (ticket.getRest() <= 0)
            return 0;
        String sqlTicket = "UPDATE `travelapp`.`ticket` SET `ticket_rest` = ticket_rest - 1 WHERE (`_id` = ?)";
        int flage = JdbcUtil.executelSql(sqlTicket, ticketId + "");
        String sqlUser = "INSERT INTO `travelapp`.`person_ticket` (`user_id`, `ticket_id`) VALUES (?, ?)";
        int flage2 = JdbcUtil.executelSql(sqlUser, userId + "", ticketId + "");
        return Math.min(flage, flage2);
    }
    public static int delTicket(PersonTicket personTicket) {
        String sqlTicket = "UPDATE `travelapp`.`ticket` SET `ticket_rest` = ticket_rest + 1 WHERE (`_id` = ?)";
        int flage = JdbcUtil.executelSql(sqlTicket, personTicket.getTicketId() + "");
        String sqlUser = "DELETE FROM `travelapp`.`person_ticket` WHERE (`_id` = ?)";
        int flage2 = JdbcUtil.executelSql(sqlUser, personTicket.getId()+"");
        return Math.min(flage, flage2);
    }
}
