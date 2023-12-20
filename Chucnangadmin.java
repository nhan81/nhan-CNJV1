import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import DAO.userDAO;
@WebServlet("/Chucnangadmin")
public class Chucnangadmin extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    public Chucnangadmin() {
        super();
    }
    userDAO in = new userDAO();
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException 
    {      
           PrintWriter out = res.getWriter();
           in.xoa(req, res, out);
    }
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
            res.setContentType("text/html;charset=UTF-8");
            req.setCharacterEncoding("UTF-8");
            PrintWriter out = res.getWriter();

            out.println("<html>");
            out.println("<head>");
            out.println("<title>Form Nhập Dữ Liệu Người Dùng</title>");
            out.println("</head>");
            out.println("<body>");

            String gmail = req.getParameter("gmail");
            String iduser = req.getParameter("iduser");
            String username = req.getParameter("vt");
            out.println("<h2>Form Nhập Dữ Liệu Người Dùng</h2>");
            out.println("<form method='post'>");
            out.println("  <input type='hidden' name='gmail' value='" + gmail + "'/>");
            out.println("  <input type='hidden' name='iduser' value='" + iduser + "'/>");
            out.println("Username: <input type=\"text\" name=\"username\"><br>");
            out.println("Password: <input type=\"password\" name=\"password\"><br>");

        
            out.println("VT: <input type=\"text\" name=\"vt\"><br>");
            out.println("<input type=\"submit\" value=\"Submit\">");
            out.println("</form>");
            out.println("</body></html>");

            out.println("  <input type='submit' value='Submit'>");
            out.println("</form>");
            out.println("</body>");
            out.println("</html>");
        
  
            if(username !=null){
            in.sua(req, res, out);
            }
           }

}