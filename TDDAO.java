
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
public class TDDAO {
    Connection con ;
    Statement stmt ;
    int MaThucDon;
    String Ngay;
    int MaBuoi;
    int MaMonAn;
    int MaLop;
    String GhiChu;
    public TDDAO() {
    }
      public void openConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mamnon?useUnicode=true&characterEncoding=UTF-8", "root", "Nhat123456789");
    }
        private TDDAO Parameter(HttpServletRequest request) {
        TDDAO tddao = new TDDAO();
        tddao.MaThucDon = Integer.parseInt(request.getParameter("MaThucDon"));
        tddao.Ngay = request.getParameter("Ngay");
        tddao.MaBuoi = Integer.parseInt(request.getParameter("MaBuoi"));
        tddao.MaMonAn = Integer.parseInt(request.getParameter("MaMonAn"));
        tddao.MaLop = Integer.parseInt(request.getParameter("MaLop"));
        tddao.GhiChu = request.getParameter("GhiChu");
        // Add more fields as needed
        try {
            // Additional code for database operations if required
        } catch (NumberFormatException e) {
            e.printStackTrace();
            // Handle the exception as needed
        }
        return tddao;
    }
    public void displayData(PrintWriter out) {
    try {
        // Open connection, create statement, and execute query
        openConnection();
        stmt = con.createStatement();
        String sql = "SELECT * FROM thucdon"; // Change table name to "tddao"
        ResultSet rs = stmt.executeQuery(sql);

        // Display data in a table
        out.println("<table border=1 width=50% height=50%>");
        out.println("<tr><th>Mã Thực Đơn</th><th>Ngày</th><th>Mã Buổi</th><th>Mã Món Ăn</th><th>Mã Lớp</th><th>Ghi Chú</th></tr>");

        // Iterate through the result set and display each row in the table
        while (rs.next()) {
            int maThucDon = rs.getInt("Mathucdon");
            String ngay = rs.getString("Ngay");
            int maBuoi = rs.getInt("MaBuoi");
            int maMonAn = rs.getInt("MaMonAn");
            int maLop = rs.getInt("MaLop");
            String ghiChu = rs.getString("GhiChu");

            out.println("<tr><td>" + maThucDon + "</td><td>" + ngay + "</td><td>" + maBuoi + "</td><td>" + maMonAn + "</td><td>"
                    + maLop + "</td><td>" + ghiChu + "</td>" +
                    "<td><form method='get'>" +
                    "<input type='hidden' name='maThucDonXoa' value='" + maThucDon + "'>" +
                    "<input type='submit' value='Xoa'>" +
                    "</form></td></td>"+
                    "<td><form method='post'>" +
                    "<input type='hidden' name='MaThucDon' value='" + maThucDon + "'>" +
                    "<input type='submit' value='Sửa'>" +
                    "</form></td></tr>");
        }

        out.println("</table>");
        con.close();
        } catch (Exception e) {
            out.println("Lỗi: " + e.getMessage());
        }
    }
    public void nhapThucDon(HttpServletRequest request, HttpServletResponse response) {
    try {
        openConnection();
        TDDAO thucDon = Parameter(request); // Assuming you have a Parameter method in TDDAO

        // Assuming your table name for ThucDon is "thucdon"
        String sql = "INSERT INTO thucdon (MaThucDon, Ngay, MaBuoi, MaMonAn, MaLop, GhiChu) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setInt(1, thucDon.MaThucDon);
            statement.setString(2, thucDon.Ngay);
            statement.setInt(3, thucDon.MaBuoi);
            statement.setInt(4, thucDon.MaMonAn);
            statement.setInt(5, thucDon.MaLop);
            statement.setString(6, thucDon.GhiChu);

            // Execute the insert statement
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                PrintWriter out = response.getWriter();
                System.out.println("Dữ liệu ThucDon đã được chèn thành công!");
            } else {
                PrintWriter out = response.getWriter();
                System.out.println("Chèn dữ liệu ThucDon không thành công!");
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
    public void xoaThucDon(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
    try {
        openConnection();

        // Retrieve the MaThucDon parameter from the request
        String maThucDonParam = request.getParameter("maThucDonXoa");
        if (maThucDonParam != null && !maThucDonParam.isEmpty()) {
            int maThucDon = Integer.parseInt(maThucDonParam);

            // Start a transaction
            con.setAutoCommit(false);

            try {
                xoatubang("thucdon", "MaThucDon", maThucDon);  // Replace "your_table_name" with the actual table name
                // You may need to call xoatubang for other tables related to ThucDon

                con.commit();

                out.println("Xóa thực đơn thành công!");
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
    public void TKThucDon(HttpServletRequest req, HttpServletResponse res, PrintWriter out) {
    String searchMaThucDon = req.getParameter("searchMaThucDon"); // Change the parameter name based on your form

    try {
        openConnection();
        String sql = "SELECT * FROM thucdon WHERE MaThucDon=?";
        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, searchMaThucDon);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                out.println("<style>");
                out.println("body, table {border-collapse: collapse; width: 80%;}");
                out.println("th, td {border: 1px solid #dddddd; text-align: left; padding: 8px;}");
                out.println("th {background-color: #f2f2f2;}");
                out.println("</style>");
                out.println("<table>");
                out.println("<tr><th>Mã Thực Đơn</th><th>Ngày</th><th>Mã Buổi</th><th>Mã Món Ăn</th><th>Mã Lớp</th><th>Ghi Chú</th></tr>");
                out.println("<tr><td>" + resultSet.getString("MaThucDon") + "</td><td>" + resultSet.getString("Ngay") + "</td>"
                        + "<td>" + resultSet.getString("MaBuoi") + "</td>"
                        + "<td>" + resultSet.getString("MaMonAn") + "</td>"
                        + "<td>" + resultSet.getString("MaLop") + "</td>"
                        + "<td>" + resultSet.getString("GhiChu") + "</td>"
                        + "<td><form method='get'>" +
                        "<input type='hidden' name='maThucDonXoa' value='" + resultSet.getString("MaThucDon") + "'>" +
                        "<input type='submit' value='Xoa'>" +
                        "</form></td></td>"+
                        "<td><form method='post'>" +
                        "<input type='hidden' name='MaThucDon' value='" + resultSet.getString("MaThucDon") + "'>" +
                        "<input type='submit' value='Sửa'>" +
                        "</form></td></tr>");

            } else {
                // Handle the case where no matching record is found
                out.println("Không tìm thấy Thực Đơn với Mã Thực Đơn: " + searchMaThucDon);
            }
        }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
        }
    }
    public void suaThucDon(HttpServletRequest req, HttpServletResponse res, PrintWriter out) {
    res.setContentType("text/html");
    try {
        openConnection();
        TDDAO thucDon = Parameter(req); // Assuming you have a Parameter method in TDDAO
        String sql = "UPDATE thucdon SET Ngay=?, MaBuoi=?, MaMonAn=?, MaLop=?, GhiChu=? WHERE MaThucDon=?";
        
        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, thucDon.Ngay);
            statement.setInt(2, thucDon.MaBuoi);
            statement.setInt(3, thucDon.MaMonAn);
            statement.setInt(4, thucDon.MaLop);
            statement.setString(5, thucDon.GhiChu);
            statement.setInt(6, thucDon.MaThucDon);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                out.println("Dữ liệu Thực Đơn đã được cập nhật thành công!");
            } else {
                out.println("Không thể cập nhật dữ liệu Thực Đơn! Hãy chắc chắn rằng Mã Thực Đơn tồn tại.");
            }
        }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
        }
    }


}
