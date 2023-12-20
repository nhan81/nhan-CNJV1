/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import DAO.LDAO;
import DAO.menu;
@WebServlet("/L")

public class L extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public L() {
        super();
    }
    LDAO l = new LDAO();
      protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
            res.setContentType("text/html;charset=UTF-8");
            req.setCharacterEncoding("UTF-8");
            res.setCharacterEncoding("UTF-8");
            PrintWriter out = res.getWriter();
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<meta charset='UTF-8'>");
            out.println("</head>");
            out.println("<body>");
            
            menu menu = new menu();
            menu.htmenu(res, out);
            
            out.println("<h2>Tìm Kiếm Lớp</h2>");
            out.println("<form method='get'>");
            out.println("  <label for='searchMaLop'>Mã Lớp:</label>");
            out.println("  <input type='text' id='searchMaLop' name='searchMaLop' required>");
            out.println("  <input type='submit' value='Tìm Kiếm'>");
            out.println("</form>");

            out.println("<h2>Form Nhập Dữ Liệu Lớp</h2>");
            out.println("<form method='get'>");
            out.println("  <label for='MaLop'>Mã Lớp:</label>");
            out.println("  <input type='text' id='MaLop' name='MaLop' required><br>");

            out.println("  <label for='TenLop'>Tên Lớp:</label>");
            out.println("  <input type='text' id='TenLop' name='TenLop' required><br>");

            out.println("  <label for='GiaoVienPhuTrach'>Giáo Viên Phụ Trách:</label>");
            out.println("  <input type='text' id='GiaoVienPhuTrach' name='GiaoVienPhuTrach' required><br>");

            out.println("  <label for='MaKhoi'>Mã Khối:</label>");
            out.println("  <input type='text' id='MaKhoi' name='MaKhoi' required><br>");

            out.println("</body>");
            out.println("<style>");
            out.println("body, table {border-collapse: collapse; width: 80%;}");
            out.println("th, td {border: 1px solid #dddddd; text-align: left; padding: 8px;}");
            out.println("th {background-color: #f2f2f2;}");
            out.println("</style>");
            out.println("</html>");

            out.println("  <input type='submit' value='Submit'>");
            out.println("</form>");

            String tkLop = req.getParameter("searchMaLop");
            if (tkLop == null) {
                l.displayData(out);
            } else {
                l.TKLop(req, res, out);
            }
            l.xoaLop(req, res, out);
            l.nhapLop(req, res);

            
    }

  
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            response.setContentType("text/html;charset=UTF-8");
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();

            out.println("<html>");
            out.println("<head>");
            out.println("<title>Form Nhập Dữ Liệu</title>");
            out.println("</head>");
            out.println("<body>");

            String maLop = request.getParameter("MaLop"); // Assuming you pass the MaLop as a parameter
            out.println("<h2>Form Nhập Dữ Liệu Lớp</h2>");
            out.println("<form method='post'>");

            out.println("  <input type='hidden' name='MaLop' value='" + maLop + "'/>");
            out.println("  <label for='tenLop'>Tên Lớp:</label>");
            out.println("  <input type='text' id='TenLop' name='TenLop' required><br>");

            out.println("  <label for='giaoVienPhuTrach'>Giáo Viên Phụ Trách:</label>");
            out.println("  <input type='text' id='GiaoVienPhuTrach' name='GiaoVienPhuTrach' required><br>");

            out.println("  <label for='maKhoi'>Mã Khối:</label>");
            out.println("  <input type='text' id='MaKhoi' name='MaKhoi' required><br>");

            // Add more fields as needed

            out.println("  <input type='submit' value='Submit'>");
            out.println("</form>");

            out.println("</body>");
            out.println("</html>");

            String tenLop = request.getParameter("TenLop");
            String giaoVienPhuTrach = request.getParameter("GiaoVienPhuTrach");
            String maKhoiLop = request.getParameter("MaKhoi");
            // Retrieve other parameters as needed

            if (tenLop != null && maLop != null) {
                // Call the method to handle the update for Lop
                // Assuming you have a method named suaLop in your 'l' object
                l.suaLop(request, response, out);
            }
    }

}
