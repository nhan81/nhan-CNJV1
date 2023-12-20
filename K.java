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
import DAO.KDAO;
import DAO.menu;

@WebServlet(urlPatterns = {"/K"})
public class K extends HttpServlet {
 private static final long serialVersionUID = 1L;
    public K() {
        super();
    }
    KDAO k = new KDAO();
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
        out.println("</form>");
        
        menu menu = new menu();
        menu.htmenu(res, out);
        
        out.println("<h2>Tìm Kiếm Khối</h2>");
        out.println("<form method='get'>");
        out.println("  <label for='searchMaKhoi'>Mã Khối:</label>");
        out.println("  <input type='text' id='searchMaKhoi' name='searchMaKhoi' required>");
        out.println("  <input type='submit' value='Tìm Kiếm'>");
        out.println("</form>");

        
        out.println("<h2>Form Nhập Dữ Liệu Khối</h2>");
        out.println("<form method='get'>"); 
        
        out.println("  <label for='MaKhoi'>Mã Khối:</label>");
        out.println("  <input type='text' id='MaKhoi' name='MaKhoi' required><br>");

        out.println("  <label for='TenKhoi'>Tên Khối:</label>");
        out.println("  <input type='text' id='TenKhoi' name='TenKhoi' required><br>");

        out.println("  <label for='GhiChu'>Ghi Chú:</label>");
        out.println("  <input type='text' id='GhiChu' name='GhiChu'><br>");
        out.println("</body>");
        out.println("<style>");
        out.println("body, table {border-collapse: collapse; width: 80%;}");
        out.println("th, td {border: 1px solid #dddddd; text-align: left; padding: 8px;}");
        out.println("th {background-color: #f2f2f2;}");
        out.println("</style>");
        out.println("</html>");
       
        out.println("  <input type='submit' value='Submit'>");
        out.println("</form>");
        String tk = req.getParameter("searchMaKhoi");
        if(tk==null)
        {
        k.displayData(out);
        }
        else
        {
        k.TKKhoi(req, res, out);
        }
        k.xoaKhoi(req, res, out);
        k.nhapKhoi(req, res);
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
            String maKhoi = request.getParameter("MaKhoi"); // Assuming you pass the MaKhoi as a parameter
            out.println("<h2>Form Nhập Dữ Liệu Khối</h2>");
            out.println("<form method='post'>");

            out.println("  <input type='hidden' name='MaKhoi' value='" + maKhoi + "'/>");
            out.println("  <label for='tenKhoi'>Tên Khối:</label>");
            out.println("  <input type='text' id='TenKhoi' name='TenKhoi' required><br>");

            out.println("  <label for='ghiChu'>Ghi Chú:</label>");
            out.println("  <input type='text' id='GhiChu' name='GhiChu'><br>");

            // Add more fields as needed

            out.println("  <input type='submit' value='Submit'>");
            out.println("</form>");

            out.println("</body>");
            out.println("</html>");

            String tenKhoi = request.getParameter("TenKhoi");
            String ghiChu = request.getParameter("GhiChu");
            // Retrieve other parameters as needed

            if (tenKhoi != null && maKhoi != null) {
                // Call the method to handle the update for Khoi
                // Assuming you have a method named suaKhoi in your 'in' object
                k.suaKhoi(request, response, out);
            }
    }

}
