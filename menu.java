
package DAO;

import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;

public class menu {
    public void htmenu(HttpServletResponse res,PrintWriter out)
    {   
        out.println("<div style='display: flex; justify-content: space-around; background-color: #f1f1f1; padding: 10px;'>");
        out.println("<a href='BA' style='text-decoration: none; color: #333;'>Buổi ăn</a>");
        out.println("<a href='K' style='text-decoration: none; color: #333;'>Khối</a>");
        out.println("<a href='L' style='text-decoration: none; color: #333;'>Lớp</a>");
        out.println("<a href='MA' style='text-decoration: none; color: #333;'>Món Ăn</a>");
        out.println("<a href='TD' style='text-decoration: none; color: #333;'>Thực Đơn</a>");
        out.println("<a href='index.jsp' style='text-decoration: none; color: #333;'>Đăng xuất </a>");
        out.println("</div>");
    }
}