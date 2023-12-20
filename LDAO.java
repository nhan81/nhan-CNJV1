
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
public class LDAO {
    Connection con ;
    Statement stmt ;
    int MaLop;
    String TenLop;
    String GiaoVienPhuTrach;
    int MaKhoi;
    public LDAO() {
    }
      public void openConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mamnon?useUnicode=true&characterEncoding=UTF-8", "root", "Nhat123456789");
    }
       private LDAO Parameter(HttpServletRequest request) {
        LDAO la = new LDAO();
        la.MaLop = Integer.parseInt(request.getParameter("MaLop"));
        la.TenLop = request.getParameter("TenLop");
        la.GiaoVienPhuTrach = request.getParameter("GiaoVienPhuTrach");
        la.MaKhoi = Integer.parseInt(request.getParameter("MaKhoi"));
        // Add more fields as needed
        try {
            // Additional code for database operations if required
        } catch (NumberFormatException e) {
            e.printStackTrace();
            // Handle the exception as needed
        }
        return la;
    }
    public void displayData(PrintWriter out) {
    try {
        // Open connection, create statement, and execute query
        openConnection();
        Statement stmt = con.createStatement();
        String sql = "SELECT * FROM lop"; // Change table name if needed
        ResultSet rs = stmt.executeQuery(sql);

        // Display data in a table
        out.println("<table border=1 width=50% height=50%>");
        out.println("<tr><th>Mã Lớp</th><th>Tên Lớp</th><th>Giáo Viên Phụ Trách</th><th>Mã Khối</th></tr>");

        // Iterate through the result set and display each row in the table
        while (rs.next()) {
            int maLop = rs.getInt("MaLop");
            String tenLop = rs.getString("TenLop");
            String giaoVienPhuTrach = rs.getString("GiaoVienPhuTrach");
            int maKhoi = rs.getInt("MaKhoi");

            out.println("<tr><td>" + maLop + "</td><td>" + tenLop + "</td><td>" + giaoVienPhuTrach + "</td><td>" + maKhoi + "</td>" +
                    "<td><form method='get'>" +
                    "<input type='hidden' name='maLopXoa' value='" + maLop + "'>" +
                    "<input type='submit' value='Xoa'>" +
                    "</form></td></td>"+
                    "<td><form method='post'>" +
                    "<input type='hidden' name='MaLop' value='" + maLop + "'>" +
                    "<input type='submit' value='Sửa'>" +
                    "</form></td></tr>");
        }

        out.println("</table>");
        con.close();
        } catch (Exception e) {
            out.println("Lỗi: " + e.getMessage());
        }
    }
    public void nhapLop(HttpServletRequest request, HttpServletResponse response) {
    try {
        openConnection();
        LDAO lop = Parameter(request); // Assuming you have a Parameter method in LDAO

        // Assuming your table name for Lop is "lop"
        String sql = "INSERT INTO lop (MaLop, TenLop, GiaoVienPhuTrach, MaKhoi) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setInt(1, lop.MaLop);
            statement.setString(2, lop.TenLop);
            statement.setString(3, lop.GiaoVienPhuTrach);
            statement.setInt(4, lop.MaKhoi);

            // Execute the insert statement
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                PrintWriter out = response.getWriter();
                System.out.println("Dữ liệu Lop đã được chèn thành công!");
            } else {
                PrintWriter out = response.getWriter();
                System.out.println("Chèn dữ liệu Lop không thành công!");
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
    public void xoaLop(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
    try {
        openConnection();

        // Retrieve the MaLop parameter from the request
        String maLopParam = request.getParameter("maLopXoa");
        if (maLopParam != null && !maLopParam.isEmpty()) {
            int maLop = Integer.parseInt(maLopParam);

            // Start a transaction
            con.setAutoCommit(false);

            try {
                xoatubang("thucdon", "MaLop", maLop);  // Replace "your_table_name2" with the actual table name
                xoatubang("lop", "MaLop", maLop);

                con.commit();

                out.println("Xóa lớp thành công!");
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
    public void TKLop(HttpServletRequest req, HttpServletResponse res, PrintWriter out) {
    String searchMaLop = req.getParameter("searchMaLop"); // Change the parameter name based on your form

    try {
        openConnection();
        String sql = "SELECT * FROM lop WHERE MaLop=?";
        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, searchMaLop);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                out.println("<style>");
                out.println("body, table {border-collapse: collapse; width: 80%;}");
                out.println("th, td {border: 1px solid #dddddd; text-align: left; padding: 8px;}");
                out.println("th {background-color: #f2f2f2;}");
                out.println("</style>");
                out.println("<table>");
                out.println("<tr><th>Mã Lớp</th><th>Tên Lớp</th><th>Giáo Viên Phụ Trách</th><th>Mã Khối</th></tr>");
                out.println("<tr><td>" + resultSet.getString("MaLop") + "</td><td>" + resultSet.getString("TenLop") + "</td>"
                        + "<td>" + resultSet.getString("GiaoVienPhuTrach") + "</td>"
                        + "<td>" + resultSet.getString("MaKhoi") + "</td>"
                        + "<td><form method='get'>" +
                        "<input type='hidden' name='maLopXoa' value='" + resultSet.getString("MaLop") + "'>" +
                        "<input type='submit' value='Xoa'>" +
                        "</form></td></td>"+
                        "<td><form method='post'>" +
                        "<input type='hidden' name='MaLop' value='" + resultSet.getString("MaLop") + "'>" +
                        "<input type='submit' value='Sửa'>" +
                        "</form></td></tr>");

            } else {
                // Handle the case where no matching record is found
                out.println("Không tìm thấy Lớp với Mã Lớp: " + searchMaLop);
            }
        }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
        }
    }
    public void suaLop(HttpServletRequest req, HttpServletResponse res, PrintWriter out) {
    res.setContentType("text/html");
    try {
        openConnection();
        LDAO lop = Parameter(req); // Assuming you have a Parameter method in LDAO
        String sql = "UPDATE lop SET TenLop=?, GiaoVienPhuTrach=?, MaKhoi=? WHERE MaLop=?";
        
        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, lop.TenLop);
            statement.setString(2, lop.GiaoVienPhuTrach);
            statement.setInt(3, lop.MaKhoi);
            statement.setInt(4, lop.MaLop);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                out.println("Dữ liệu Lop đã được cập nhật thành công!");
            } else {
                out.println("Không thể cập nhật dữ liệu Lop! Hãy chắc chắn rằng Mã Lớp tồn tại.");
            }
        }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
        }
    }

    
}
