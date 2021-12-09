import java.sql.*;

public class QueryDemo {
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost/test";
    private static final String USER = "root";
    private static final String PASS = "";

    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            String sql = "select id, name, age, sex from user where id=?";

            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, 1);
            ResultSet resultSet = stmt.executeQuery();
            printRS(resultSet);

            stmt = conn.prepareStatement("select id, name, age, sex from user where age=? and name=?");
            stmt.setInt(1, 12);
            stmt.setString(2, "yan");
            resultSet = stmt.executeQuery();
            printRS(resultSet);

            stmt = conn.prepareStatement("select id, name, age, sex from user where age>?");
            stmt.setInt(1, 20);
            resultSet = stmt.executeQuery();
            printRS(resultSet);

            // 清理
            resultSet.close();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(stmt != null) {
                    stmt.close();
                }
            } catch(SQLException e) {
                e.printStackTrace();
            }
            try {
                if(conn != null) {
                    conn.close();
                }
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void printRS(ResultSet resultSet) throws SQLException {
        while(resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            int age = resultSet.getInt("age");
            String sex = resultSet.getString("sex");
            System.out.println("id:" + id + ", name:" + name + ", age" + age + ", sex" + sex);
        }
    }
}