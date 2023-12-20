
package DAO;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class userDAO {
    Connection con ;
    Statement stmt ;
    int iduser;
    String username;
    String password;
    String gmail;
    int vt;
    private PreparedStatement pstmt;
    public userDAO() {
    }
      public void openConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mamnon?useUnicode=true&characterEncoding=UTF-8", "root", "Nhat123456789");
        }
      private userDAO Parametter(HttpServletRequest request) {
        userDAO user = new userDAO();

        try {
            user.username = request.getParameter("username");
            user.password = request.getParameter("password");
            user.gmail = request.getParameter("gmail");
            user.iduser = Integer.parseInt(request.getParameter("iduser"));
            user.vt = Integer.parseInt(request.getParameter("vt"));
            
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return user;
    }
    public void displayData(PrintWriter out) {
    try {
        openConnection();
        stmt = con.createStatement();
        String sql = "SELECT * FROM user";
        ResultSet rs = stmt.executeQuery(sql);
        out.println("<table border=1>");
        out.println("<tr><th>ID User</th><th>Username</th><th>Password</th><th>Vị trí</th></th><th>gmail</th>");
        while (rs.next()) {
            String idUser = rs.getString("iduser");
            String username = rs.getString("username");
            String password = rs.getString("password");
            String gmail = rs.getString("gmail");
            String viTri = rs.getString("vt");
            out.println("<tr><td>" + idUser + "</td><td>" + username + "</td><td>" 
            + password + "</td><td>" + viTri + "</td><td>" + gmail +"</td></td>"+
            "<td><a href='Xemuser?idUserXoa=" + gmail + "'>Xóa</a></td>" +
            "<td><form action='Xemuser' method='post'>" +
            "<input type='hidden' name='gmail' value='" + gmail + "'>" +
            "<input type='hidden' name='iduser' value='" + idUser + "'>"+
            "<input type='submit' value='Sửa'>" +
            "</form></td></tr>");
        }
        out.println("</table>");
        con.close();
    } catch (Exception e) {
        out.println("Lỗi: " + e.getMessage());
    }
}
public String getUserRole(String username, String password) {
    String role = "đangnhapsai";

    try {
        // Kiểm tra dữ liệu đầu vào
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            // Xử lý thông báo lỗi hoặc thực hiện các hành động khác
            return role;
        }

        openConnection();
        String sql = "SELECT vt FROM user WHERE gmail=? AND password=?";
        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.setString(2, password);

            try (ResultSet res = statement.executeQuery()) {
                if (res.next()) {
                    int vtkt = res.getInt("vt");
                    if (vtkt == 1) {
                        role = "admin";
                    } 
                    else if(vtkt == 0) {
                        role = "user";
                    }
                } else {
                    // Không tìm thấy người dùng với username và password cung cấp
                    // Có thể xử lý thông báo lỗi hoặc thực hiện các hành động khác
                }
            }
        }
    } catch (Exception e) {
        e.printStackTrace(); // In ra thông tin lỗi để kiểm tra
    }

    return role;
}

private int getCurrentMaxId() throws SQLException {
    // Query the database to get the current maximum iduser
    String query = "SELECT MAX(iduser) AS maxId FROM user";

    try (Statement statement = con.createStatement();
         ResultSet resultSet = statement.executeQuery(query)) {

        if (resultSet.next()) {
            return resultSet.getInt("maxId");
        }
    }

    return 0; // Default if no records are found
}
  public void nhapUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
    try {
        openConnection();
        int currentMaxId = getCurrentMaxId();
        int idusermoi = currentMaxId + 1;
        
        userDAO user = Parametter(request);
        user.iduser = idusermoi;

        String sql = "INSERT INTO user (iduser,gmail, username, password , vt) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setInt(1, idusermoi);
            statement.setString(2, user.gmail);
            statement.setString(3, user.username);
            statement.setString(4, user.password);
            statement.setInt(5, user.vt);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                PrintWriter out = response.getWriter();
                out.println("Dữ liệu người dùng đã được chèn thành công!");
            } else {
                PrintWriter out = response.getWriter();
                out.println("Không thể chèn dữ liệu người dùng!");
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
        PrintWriter out = response.getWriter();
        out.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
    }
}
public void xoatubang(String tableName, String columnName, String value) throws Exception 
       {
        String sql = "DELETE FROM " + tableName + " WHERE " + columnName + " = ?";
        try (PreparedStatement statement = con.prepareStatement(sql)) 
            {
            statement.setString(1, value);
            statement.executeUpdate();
            }
       }
public void xoa(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
    try {
        openConnection();

        // Retrieve the idUser parameter from the request
        String gmail = request.getParameter("idUserXoa");
        if (gmail != null && !gmail.isEmpty()) {
  

            // Start a transaction
            con.setAutoCommit(false);

            try {
                xoatubang("user", "gmail", gmail);
                // You may need to adjust the table name and column name based on your database schema

                con.commit();

                out.println("Xóa người dùng thành công!");
            } catch (Exception e) {
                // Rollback the transaction if an error occurs
                con.rollback();
                out.println("Lỗi: " + e.getMessage());
            } finally {
                // Reset auto-commit to true
                con.setAutoCommit(true);
            }
        }
    } catch (Exception e) {
        out.println("Lỗi: " + e.getMessage());
    } finally {
        out.close();
    }
}
public void sua(HttpServletRequest req, HttpServletResponse res, PrintWriter out) {
    res.setContentType("text/html");

    try {
        openConnection();
        userDAO user = Parametter(req);
      
        String sql = "UPDATE user SET iduser=? ,vt=? ,username=?, password=? WHERE gmail=?";
        try (PreparedStatement statement = con.prepareStatement(sql)) {

            statement.setInt(1, user.iduser);
            statement.setInt(2, user.vt);
            statement.setString(3, user.username);
            statement.setString(4, user.password);
            statement.setString(5, user.gmail);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                out = res.getWriter();
                out.println("Dữ liệu người dùng đã được cập nhật thành công!");
            } else {
                out = res.getWriter();
                out.println("Không thể cập nhật dữ liệu người dùng! Hãy chắc chắn rằng gmail tồn tại.");
            }

        }
    } catch (Exception e) {
        e.printStackTrace();
        out.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
    }
}
     public void searchUser(HttpServletRequest req, HttpServletResponse res, PrintWriter out) {
    String searchUserId = req.getParameter("searchUserId");
    
    try {
        openConnection();
        String sql = "SELECT * FROM user WHERE username =?";
        
        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, searchUserId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                out.println("<style>");
                out.println("body");
                out.println("table {border-collapse: collapse; width: 80%;}");
                out.println("th, td {border: 1px solid #dddddd; text-align: left; padding: 8px;}");
                out.println("th {background-color: #f2f2f2;}");
                out.println("</style>");
                out.println("<table>");
                out.println("<tr><th>ID Người Dùng</th><th>Tên Người Dùng</th><th>Mật Khẩu</th><th>Email</th><th>Vị Trí</th></tr>");
                out.println("<tr><td>" + resultSet.getInt("iduser") + "</td><td>" + resultSet.getString("username") + "</td><td>" + resultSet.getString("password") + "</td><td>" + resultSet.getString("gmail") + "</td><td>" + resultSet.getString("vt") + "</td>"
                        + "<td><a href='Xemuser?idUserXoa=" + resultSet.getString("gmail") + "'>Xóa</a></td>"
                        + "<td><form action='Xemuser' method='post'>" +
                        "<input type='hidden' name='gmail' value='" + resultSet.getString("gmail") + "'>" +
                        "<input type='hidden' name='iduser' value='" + resultSet.getInt("iduser") + "'>"+
                        "<input type='submit' value='Sửa'>" +
                        "</form></td></tr>");
            } else {
                // Người dùng không tồn tại
                out.println("Không tìm thấy người dùng với ID: " + searchUserId);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
        out.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
    }
}
public void qmk(HttpServletRequest req, HttpServletResponse res, PrintWriter out) {
    res.setContentType("text/html");

    try {
        openConnection();
        userDAO user = Parametter(req);
      
        String sql = "UPDATE user SET password=? WHERE gmail=?";
        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, user.password);
            statement.setString(2, user.gmail);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                out = res.getWriter();
                out.println("Dữ liệu người dùng đã được cập nhật thành công!");
            } else {
                out = res.getWriter();
                out.println("Không thể cập nhật dữ liệu người dùng! Hãy chắc chắn rằng gmail tồn tại.");
            }

        }
    } catch (Exception e) {
        e.printStackTrace();
        out.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
    }
}
}
