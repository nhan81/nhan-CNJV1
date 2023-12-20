
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
public class MADAO {
    Connection con ;
    Statement stmt ;
    int MaMonAn;                           
    String TenMonAn;
    String DinhDuong;
    String GhiChu;
    public MADAO() {
    }
      public void openConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mamnon?useUnicode=true&characterEncoding=UTF-8", "root", "Nhat123456789");
    }
        private MADAO Parameter(HttpServletRequest request) {
        MADAO ma = new MADAO();
        ma.MaMonAn = Integer.parseInt(request.getParameter("MaMonAn"));
        ma.TenMonAn = request.getParameter("TenMonAn");
        ma.DinhDuong = request.getParameter("DinhDuong");
        ma.GhiChu = request.getParameter("GhiChu");
        // Add more fields as needed
        try {
            // Additional code for database operations if required
        } catch (NumberFormatException e) {
            e.printStackTrace();
            // Handle the exception as needed
        }
        return ma;
    }
    public void displayData(PrintWriter out) {
    try {
        // Open connection, create statement, and execute query
        openConnection();
        Statement stmt = con.createStatement();
        String sql = "SELECT * FROM monan"; // Change table name to "monan"
        ResultSet rs = stmt.executeQuery(sql);

        // Display data in a table
        out.println("<table border=1 width=50% height=50%>");
        out.println("<tr><th>Mã Món Ăn</th><th>Tên Món Ăn</th><th>Định Dưỡng</th><th>Ghi Chú</th></tr>");

        // Iterate through the result set and display each row in the table
        while (rs.next()) {
            int maMonAn = rs.getInt("MaMonAn");
            String tenMonAn = rs.getString("TenMonAn");
            String dinhDuong = rs.getString("DinhDuong");
            String ghiChu = rs.getString("GhiChu");

            out.println("<tr><td>" + maMonAn + "</td><td>" + tenMonAn + "</td><td>" + dinhDuong + "</td><td>" + ghiChu + "</td>" +
                    "<td><form method='get'>" +
                    "<input type='hidden' name='maMonAnXoa' value='" + maMonAn + "'>" +
                    "<input type='submit' value='Xoa'>" +
                    "</form></td></td>"+
                    "<td><form method='post'>" +
                    "<input type='hidden' name='MaMonAn' value='" + maMonAn + "'>" +
                    "<input type='submit' value='Sửa'>" +
                    "</form></td></tr>");
        }

        out.println("</table>");
        con.close();
        } catch (Exception e) {
            out.println("Lỗi: " + e.getMessage());
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
    public void nhapMonAn(HttpServletRequest request, HttpServletResponse response) {
    try {
        openConnection();
        MADAO monAn = Parameter(request); // Assuming you have a Parameter method in MonAnDAO

        // Assuming your table name for MonAn is "monan"
        String sql = "INSERT INTO monan (MaMonAn, TenMonAn, DinhDuong, GhiChu) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setInt(1, monAn.MaMonAn);
            statement.setString(2, monAn.TenMonAn);
            statement.setString(3, monAn.DinhDuong);
            statement.setString(4, monAn.GhiChu);

            // Execute the insert statement
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                PrintWriter out = response.getWriter();
                System.out.println("Dữ liệu MonAn đã được chèn thành công!");
            } else {
                PrintWriter out = response.getWriter();
                System.out.println("Chèn dữ liệu MonAn không thành công!");
            }
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void xoaMonAn(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
    try {
        openConnection();

        // Retrieve the MaMonAn parameter from the request
        String maMonAnParam = request.getParameter("maMonAnXoa");
        if (maMonAnParam != null && !maMonAnParam.isEmpty()) {
            int maMonAn = Integer.parseInt(maMonAnParam);

            // Start a transaction
            con.setAutoCommit(false);

            try {
                xoatubang("thucdon", "MaMonAn", maMonAn);  
                xoatubang("monan", "MaMonAn", maMonAn);

                con.commit();

                out.println("Xóa món ăn thành công!");
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
    public void TKMonAn(HttpServletRequest req, HttpServletResponse res, PrintWriter out) {
    String searchMaMonAn = req.getParameter("searchMaMonAn"); // Change the parameter name based on your form

    try {
        openConnection();
        String sql = "SELECT * FROM monan WHERE MaMonAn=?";
        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, searchMaMonAn);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                out.println("<style>");
                out.println("body, table {border-collapse: collapse; width: 80%;}");
                out.println("th, td {border: 1px solid #dddddd; text-align: left; padding: 8px;}");
                out.println("th {background-color: #f2f2f2;}");
                out.println("</style>");
                out.println("<table>");
                out.println("<tr><th>Mã Món Ăn</th><th>Tên Món Ăn</th><th>Dinh Dưỡng</th><th>Ghi Chú</th></tr>");
                out.println("<tr><td>" + resultSet.getString("MaMonAn") + "</td><td>" + resultSet.getString("TenMonAn") + "</td>"
                        + "<td>" + resultSet.getString("DinhDuong") + "</td>"
                        + "<td>" + resultSet.getString("GhiChu") + "</td>"
                        + "<td><form method='get'>" +
                        "<input type='hidden' name='maMonAnXoa' value='" + resultSet.getString("MaMonAn") + "'>" +
                        "<input type='submit' value='Xoa'>" +
                        "</form></td></td>"+
                        "<td><form method='post'>" +
                        "<input type='hidden' name='MaMonAn' value='" + resultSet.getString("MaMonAn") + "'>" +
                        "<input type='submit' value='Sửa'>" +
                        "</form></td></tr>");

            } else {
                // Handle the case where no matching record is found
                out.println("Không tìm thấy Món Ăn với Mã Món Ăn: " + searchMaMonAn);
            }
        }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
        }
    }
    public void suaMonAn(HttpServletRequest req, HttpServletResponse res, PrintWriter out) {
    res.setContentType("text/html");
    try {
        openConnection();
        MADAO monAn = Parameter(req); // Assuming you have a Parameter method in MADAO
        String sql = "UPDATE monan SET TenMonAn=?, DinhDuong=?, GhiChu=? WHERE MaMonAn=?";
        
        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, monAn.TenMonAn);
            statement.setString(2, monAn.DinhDuong);
            statement.setString(3, monAn.GhiChu);
            statement.setInt(4, monAn.MaMonAn);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                out.println("Dữ liệu Món Ăn đã được cập nhật thành công!");
            } else {
                out.println("Không thể cập nhật dữ liệu Món Ăn! Hãy chắc chắn rằng Mã Món Ăn tồn tại.");
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
        out.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
    }
}



}
