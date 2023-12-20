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
import DAO.userDAO;
@WebServlet("/XemUser")
public class XemUser extends HttpServlet {
private static final long serialVersionUID = 1L;
    public XemUser() {
        super();
    }
    userDAO in = new userDAO();
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
        out.println("<body>");
        
        
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("</body>");
        out.println("<style>");
        out.println("body");
        out.println("table {border-collapse: collapse; width: 80%;}");
        out.println("th, td {border: 1px solid #dddddd; text-align: left; padding: 8px;}");
        out.println("th {background-color: #f2f2f2;}");
        out.println("</style>");
        out.println("</head>");
        out.println("</table>");
        out.println("</body></html>");
        
        out.println("<h2>Tìm Kiếm Người Dùng</h2>");
        out.println("<form method='get'>");
        out.println("  <label for='searchUserId'>ID Người Dùng:</label>");
        out.println("  <input type='text' id='searchUserId' name='searchUserId' required>");
        out.println("  <input type='submit' value='Tìm Kiếm'>");
        out.println("</form>");

         String su = req.getParameter("searchUserId");
        if(su==null)
        {
         in.displayData(out);
        }
        else
        {
        in.searchUser(req, res, out);
        }
        
        
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

}
