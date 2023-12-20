
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
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import DAO.userDAO;

@WebServlet("/user")
public class Xulidangnhap extends HttpServlet {
private static final long serialVersionUID = 1L;
    public Xulidangnhap() {
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
        
        // Tạo biểu mẫu nhập liệu
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
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String userRole = in.getUserRole(username, password);
        if(userRole == "admin")
        {
            res.sendRedirect("XemUser");
        }
        else if(userRole == "user")
        {
            res.sendRedirect("L"); 
            out.print(userRole);
        }
        else
        {
        out.print("lổi đăng nhập");
        }
        }
        
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
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
        
        // Tạo biểu mẫu nhập liệu
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<title>Đăng kí</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h2>Đăng kí</h2>");
        out.println("<form method='post'>");
            
        out.println("  <label for='gmail'>Gmail:</label>");
        out.println("  <input type='email' id='gmail' name='gmail' required><br>");
        
        out.println("  <label for='username'>Username:</label>");
        out.println("  <input type='text' id='username' name='username' required><br>");
        
        out.println("  <label for='password'>Password:</label>");
        out.println("  <input type='password' id='password' name='password' required><br>");
        out.println("  <input type='submit' value='Submit'>");
        out.println("</form>");
        String gmail = req.getParameter("gmail");
        if(gmail !=null)
        {
        in.nhapUser(req, res);
        }
    }


}
