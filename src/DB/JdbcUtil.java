package DB;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class JdbcUtil {

    public static Connection connection = null;
    public static Statement statement = null;
    public static PreparedStatement preparedStatement = null;
    private static ResultSet resultSet = null;

    //私有静态变量，用以读取配置文件
    private static Properties config = new Properties();

    static {
        try {
            //配置资源文件
            config.load(JdbcUtil.class.getClassLoader().getResourceAsStream("db.properties"));

            //加载驱动
            Class.forName(config.getProperty("driver"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(config.getProperty("url"), config.getProperty("username"), config.getProperty("password"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

    public static ResultSet getResult(String sql, String ... content ) throws SQLException {
         connection = null;
         statement = null;
         resultSet = null;
        // 调用工具类中的静态方法来获取连接
        connection = getConnection();
        statement = connection.createStatement();
        preparedStatement = connection.prepareStatement(sql);
        for (int i = 0; i<content.length;i++){
            preparedStatement.setString(i+1,content[i]);
        }
        resultSet = preparedStatement.executeQuery();
        return resultSet;
    }

    //用以关闭连接，释放资源
    public static void releaseConn(){
        releaseConn(connection,statement,resultSet);
    }
    public static void releaseConn(Connection connection, Statement statement,
                                   ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            //resultSet = null;
        }

        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            // statement = null;
        }

        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            //connection = null;
        }
    }


}