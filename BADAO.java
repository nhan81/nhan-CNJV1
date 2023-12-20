
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
public class BADAO {
        Connection con ;
        Statement stmt ;
        int       MaBuoi ;
        String    TenBuoiAn;
    public BADAO() {
    }
      public void openConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mamnon?useUnicode=true&characterEncoding=UTF-8", "root", "Nhat123456789");
    }
    private BADAO Parameter(HttpServletRequest request) {
        BADAO ba = new BADAO();
        ba.MaBuoi = Integer.parseInt(request.getParameter("MaBuoi"));
        ba.TenBuoiAn = request.getParameter("TenBuoiAn");
        // Add more fields as needed
        try {
            // Additional code for database operations if required
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return ba;
    }
    public void displayData(PrintWriter out) {
        try {
            // Open connection, create statement, and execute query
            openConnection();
            stmt = con.createStatement();
            String sql = "select * from buoian"; // Change table name if needed
            ResultSet rs = stmt.executeQuery(sql);

            // Display data in a table
            out.println("<table border=1 width=50% height=50%>");
            out.println("<tr><th>Mã Buổi</th><th>Tên Buổi Ăn</th></tr>");

            // Iterate through the result set and display each row in the table
            while (rs.next()) {
                int maBuoi = rs.getInt("MaBuoi");
                String tenBuoiAn = rs.getString("TenBuoiAn");

                out.println("<tr><td>" + maBuoi + "</td><td>" + tenBuoiAn + "</td>" +
                        "<td><form method='get'>" +
                        "<input type='hidden' name='maBuoiXoa' value='" + maBuoi + "'>" +
                        "<input type='submit' value='Xoa'>" +
                        "</form></td></td>"+
                        "<td><form method='post'>" +
                        "<input type='hidden' name='MaBuoi' value='" + maBuoi + "'>" +
                        "<input type='submit' value='Sửa'>" +
                        "</form></td></tr>");
            }

            out.println("</table>");
            con.close();
        } catch (Exception e) {
            out.println("Lỗi: " + e.getMessage());
        }
    }
    public void nhapBuoiAn(HttpServletRequest request, HttpServletResponse response) {
    try {
        openConnection();
        BADAO buoiAn = Parameter(request);
        String sql = "INSERT INTO buoian (MaBuoi, TenBuoiAn) VALUES (?, ?)";

        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setInt(1, buoiAn.MaBuoi);
            statement.setString(2, buoiAn.TenBuoiAn);

            // Execute the insert statement
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                PrintWriter out = response.getWriter();
                System.out.println("Dữ liệu BuoiAn đã được chèn thành công!");
            } else {
                PrintWriter out = response.getWriter();
                System.out.println("Chèn dữ liệu BuoiAn không thành công!");
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
    public void xoaBuoiAn(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
    try {
        openConnection();

        // Retrieve the MaBuoi parameter from the request
        String maBuoiParam = request.getParameter("maBuoiXoa");
        if (maBuoiParam != null && !maBuoiParam.isEmpty()) {
            int maBuoi = Integer.parseInt(maBuoiParam);

            // Start a transaction
            con.setAutoCommit(false);

            try {
                xoatubang("thucdon", "MaBuoi", maBuoi);  // Replace "table1" with the actual table name
                xoatubang("buoian", "MaBuoi", maBuoi);  // Replace "table2" with the actual table name

                con.commit();

                out.println("Xóa buổi ăn thành công!");
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
    public void TKBuoiAn(HttpServletRequest req, HttpServletResponse res, PrintWriter out) {
    String searchMaBuoi = req.getParameter("searchMaBuoi"); // Change the parameter name based on your form

    try {
        openConnection();
        String sql = "SELECT * FROM buoian WHERE MaBuoi=?";
        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, searchMaBuoi);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                out.println("<style>");
                out.println("body, table {border-collapse: collapse; width: 80%;}");
                out.println("th, td {border: 1px solid #dddddd; text-align: left; padding: 8px;}");
                out.println("th {background-color: #f2f2f2;}");
                out.println("</style>");
                out.println("<table>");
                out.println("<tr><th>Mã Buổi</th><th>Tên Buổi Ăn</th></tr>");
                out.println("<tr><td>" + resultSet.getString("MaBuoi") + "</td><td>" + resultSet.getString("TenBuoiAn") + "</td>"
                        + "<td><form method='get'>" +
                        "<input type='hidden' name='maBuoiXoa' value='" + resultSet.getString("MaBuoi") + "'>" +
                        "<input type='submit' value='Xoa'>" +
                        "</form></td></td>"+
                        "<td><form method='post'>" +
                        "<input type='hidden' name='MaBuoi' value='" + resultSet.getString("MaBuoi") + "'>" +
                        "<input type='submit' value='Sửa'>" +
                        "</form></td></tr>");
                        
                        
            } else {
                // Handle the case where no matching record is found
                out.println("Không tìm thấy BuoiAn với Mã Buổi: " + searchMaBuoi);
            }
        }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
        }
    }
    public void suaBuoiAn(HttpServletRequest req, HttpServletResponse res, PrintWriter out) {
    res.setContentType("text/html");
    try {
        openConnection();
        BADAO buoiAn = Parameter(req);
        String sql = "UPDATE buoian SET TenBuoiAn=? WHERE MaBuoi=?";
        
        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, buoiAn.TenBuoiAn);
            statement.setInt(2, buoiAn.MaBuoi);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                out.println("Dữ liệu BuoiAn đã được cập nhật thành công!");
            } else {
                out.println("Không thể cập nhật dữ liệu BuoiAn! Hãy chắc chắn rằng Mã Buổi tồn tại.");
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
        out.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
    }
}
 
}
