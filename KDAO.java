
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
public class KDAO {
    Connection con ;
    Statement stmt ;
    int MaKhoi;
    String TenKhoi;
    String GhiChu;

    public KDAO() {
    }
      public void openConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mamnon?useUnicode=true&characterEncoding=UTF-8", "root", "Nhat123456789");
    }
    private KDAO Parameter(HttpServletRequest request) {
        KDAO ka = new KDAO();
        ka.MaKhoi = Integer.parseInt(request.getParameter("MaKhoi"));
        ka.TenKhoi = request.getParameter("TenKhoi");
        ka.GhiChu = request.getParameter("GhiChu");
        // Add more fields as needed
        try {
            // Additional code for database operations if required
        } catch (NumberFormatException e) {
            e.printStackTrace();
            // Handle the exception as needed
        }
        return ka;
    }

    public void displayData(PrintWriter out) {
        try {
            // Open connection, create statement, and execute query
            openConnection();
            Statement stmt = con.createStatement();
            String sql = "SELECT * FROM khoi"; // Change table name if needed
            ResultSet rs = stmt.executeQuery(sql);

            // Display data in a table
            out.println("<table border=1 width=50% height=50%>");
            out.println("<tr><th>Mã Khối</th><th>Tên Khối</th><th>Ghi Chú</th></tr>");

            // Iterate through the result set and display each row in the table
            while (rs.next()) {
                int maKhoi = rs.getInt("MaKhoi");
                String tenKhoi = rs.getString("TenKhoi");
                String ghiChu = rs.getString("GhiChu");

                out.println("<tr><td>" + maKhoi + "</td><td>" + tenKhoi + "</td><td>" + ghiChu + "</td>" +
                        "<td><form method='get'>" +
                        "<input type='hidden' name='maKhoiXoa' value='" + maKhoi + "'>" +
                        "<input type='submit' value='Xoa'>" +
                        "</form></td></td>"+
                        "<td><form method='post'>" +
                        "<input type='hidden' name='MaKhoi' value='" + maKhoi + "'>" +
                        "<input type='submit' value='Sửa'>" +
                        "</form></td></tr>");
            }

            out.println("</table>");
            con.close();
        } catch (Exception e) {
            out.println("Lỗi: " + e.getMessage());
        }
    }
    public void nhapKhoi(HttpServletRequest request, HttpServletResponse response) {
    try {
        openConnection();
        KDAO khoi = Parameter(request); // Assuming you have a Parameter method in KDAO

        // Assuming your table name for Khoi is "khoi"
        String sql = "INSERT INTO khoi (MaKhoi, TenKhoi, GhiChu) VALUES (?, ?, ?)";

        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setInt(1, khoi.MaKhoi);
            statement.setString(2, khoi.TenKhoi);
            statement.setString(3, khoi.GhiChu);

            // Execute the insert statement
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                PrintWriter out = response.getWriter();
                System.out.println("Dữ liệu Khoi đã được chèn thành công!");
            } else {
                PrintWriter out = response.getWriter();
                System.out.println("Chèn dữ liệu Khoi không thành công!");
            }
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void xoatubang(String tableName, String columnName, int value) throws Exception 
       {
        String sql = "DELETE FROM " + tableName + " WHERE " + columnName + " = ?";
        try (PreparedStatement statement = con.prepareStatement(sql)) 
            {
            statement.setInt(1, value);
            statement.executeUpdate();
            }
       }
    public void xoaKhoi(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
    try {
        openConnection();

        // Retrieve the MaKhoi parameter from the request
        String maKhoiParam = request.getParameter("maKhoiXoa");
        if (maKhoiParam != null && !maKhoiParam.isEmpty()) {
            int maKhoi = Integer.parseInt(maKhoiParam);

            // Start a transaction
            con.setAutoCommit(false);

            try {
                xoatubang("lop", "MaKhoi", maKhoi); 
                xoatubang("khoi", "MaKhoi", maKhoi);// Replace "your_table_name1" with the actual table name
                // You may need to call xoatubang for other tables related to Khoi

                con.commit();

                out.println("Xóa khối thành công!");
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
    public void TKKhoi(HttpServletRequest req, HttpServletResponse res, PrintWriter out) {
    String searchMaKhoi = req.getParameter("searchMaKhoi"); // Change the parameter name based on your form

    try {
        openConnection();
        String sql = "SELECT * FROM khoi WHERE MaKhoi=?";
        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, searchMaKhoi);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                out.println("<style>");
                out.println("body, table {border-collapse: collapse; width: 80%;}");
                out.println("th, td {border: 1px solid #dddddd; text-align: left; padding: 8px;}");
                out.println("th {background-color: #f2f2f2;}");
                out.println("</style>");
                out.println("<table>");
                out.println("<tr><th>Mã Khối</th><th>Tên Khối</th><th>Ghi Chú</th></tr>");
                out.println("<tr><td>" + resultSet.getString("MaKhoi") + "</td><td>" + resultSet.getString("TenKhoi") + "</td>"
                        + "<td>" + resultSet.getString("GhiChu") + "</td>"
                        + "<td><form method='get'>" +
                        "<input type='hidden' name='maKhoiXoa' value='" + resultSet.getString("MaKhoi") + "'>" +
                        "<input type='submit' value='Xoa'>" +
                        "</form></td></td>"+
                        "<td><form method='post'>" +
                        "<input type='hidden' name='MaKhoi' value='" + resultSet.getString("MaKhoi") + "'>" +
                        "<input type='submit' value='Sửa'>" +
                        "</form></td></tr>");

            } else {
                // Handle the case where no matching record is found
                out.println("Không tìm thấy Khối với Mã Khối: " + searchMaKhoi);
            }
        }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
        }
    }
    public void suaKhoi(HttpServletRequest req, HttpServletResponse res, PrintWriter out) {
    res.setContentType("text/html");
    try {
        openConnection();
        KDAO khoi = Parameter(req); // Assuming you have a Parameter method in KDAO
        String sql = "UPDATE khoi SET TenKhoi=?, GhiChu=? WHERE MaKhoi=?";
        
        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, khoi.TenKhoi);
            statement.setString(2, khoi.GhiChu);
            statement.setInt(3, khoi.MaKhoi);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                out.println("Dữ liệu Khoi đã được cập nhật thành công!");
            } else {
                out.println("Không thể cập nhật dữ liệu Khoi! Hãy chắc chắn rằng Mã Khối tồn tại.");
            }
        }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
        }
    }
}
