
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import DAO.BADAO;
import DAO.menu;

@WebServlet("/BA")
public class BA extends HttpServlet {
 private static final long serialVersionUID = 1L;
    public BA() {
        super();
    }
    BADAO ba = new BADAO();
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
        
        out.println("<h2>Tìm Kiếm Buổi Ăn</h2>");
        out.println("<form method='get'>");
        out.println("  <label for='searchMaBuoi'>Mã Buổi:</label>");
        out.println("  <input type='text' id='searchMaBuoi' name='searchMaBuoi' required>");
        out.println("  <input type='submit' value='Tìm Kiếm'>");
        out.println("</form>");
        
        out.println("<h2>Form Nhập Dữ Liệu Buổi Ăn</h2>");
        out.println("<form method='get'"); // Replace YourServletURL with the actual URL

        out.println("  <label for='MaBuoi'>Mã Buổi:</label>");
        out.println("  <input type='text' id='MaBuoi' name='MaBuoi' required><br>");

        out.println("  <label for='TenBuoiAn'>Tên Buổi Ăn:</label>");
        out.println("  <input type='text' id='TenBuoiAn' name='TenBuoiAn' required><br>");

        out.println("  <input type='submit' value='Submit'>");
        out.println("</form>");

        out.println("</body>");
        out.println("<style>");
        out.println("body, table {border-collapse: collapse; width: 80%;}");
        out.println("th, td {border: 1px solid #dddddd; text-align: left; padding: 8px;}");
        out.println("th {background-color: #f2f2f2;}");
        out.println("</style>");
        out.println("</html>");
        String tk = req.getParameter("searchMaBuoi");
        if(tk==null)
        {
        ba.displayData(out);
        }
        else
        {
        ba.TKBuoiAn(req, res, out);
        }
        ba.xoaBuoiAn(req, res, out);
        ba.nhapBuoiAn(req, res);
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

            String maBuoi = request.getParameter("MaBuoi"); // Assuming you pass the MaBuoi as a parameter
            out.println("<h2>Form Nhập Dữ Liệu Buổi Ăn</h2>");
            out.println("<form method='post'>");

            out.println("  <input type='hidden' name='MaBuoi' value='" + maBuoi + "'/>");
            out.println("  <label for='tenBuoiAn'>Tên Buổi Ăn:</label>");
            out.println("  <input type='text' id='TenBuoiAn' name='TenBuoiAn' required><br>");

            // Add more fields as needed

            out.println("  <input type='submit' value='Submit'>");
            out.println("</form>");

            out.println("</body>");
            out.println("</html>");

            String tenBuoiAn = request.getParameter("TenBuoiAn");
            // Retrieve other parameters as needed

            if (tenBuoiAn != null && maBuoi != null) {
                // Call the method to handle the update for buoian
                // Assuming you have a method named suaBuoiAn in your 'in' object
                ba.suaBuoiAn(request, response, out);
            }

    }

}
