
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import DAO.MADAO;
import DAO.menu;
@WebServlet("/MA")
public class MA extends HttpServlet {
 private static final long serialVersionUID = 1L;
    public MA() {
        super();
    }
    MADAO ma = new MADAO();
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
            
            out.println("<h2>Tìm Kiếm Món Ăn</h2>");
            out.println("<form method='get'>");
            out.println("  <label for='searchMaMonAn'>Mã Món Ăn:</label>");
            out.println("  <input type='text' id='searchMaMonAn' name='searchMaMonAn' required>");
            out.println("  <input type='submit' value='Tìm Kiếm'>");
            out.println("</form>");

            out.println("<h2>Form Nhập Dữ Liệu Món Ăn</h2>");
            out.println("<form method='get'>");
            out.println("  <label for='MaMonAn'>Mã Món Ăn:</label>");
            out.println("  <input type='text' id='MaMonAn' name='MaMonAn' required><br>");

            out.println("  <label for='TenMonAn'>Tên Món Ăn:</label>");
            out.println("  <input type='text' id='TenMonAn' name='TenMonAn' required><br>");

            out.println("  <label for='DinhDuong'>Dinh Dưỡng:</label>");
            out.println("  <input type='text' id='DinhDuong' name='DinhDuong' required><br>");

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

            String tkMonAn = req.getParameter("searchMaMonAn");
            if (tkMonAn == null) {
                // Display data for MonAn
                // Replace the following method with the appropriate method in your MADAO object
                ma.displayData(out);
            } else {
                // Search for MonAn based on the provided parameter
                // Replace the following method with the appropriate method in your MADAO object
                ma.TKMonAn(req, res, out);
            }
            // Add the delete and insert methods for MonAn similar to what you did for Lop
            ma.xoaMonAn(req, res, out);
            ma.nhapMonAn(req, res);

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

            String maMonAn = request.getParameter("MaMonAn"); // Assuming you pass the MaMonAn as a parameter
            out.println("<h2>Form Nhập Dữ Liệu Món Ăn</h2>");
            out.println("<form method='post'>");

            out.println("  <input type='hidden' name='MaMonAn' value='" + maMonAn + "'/>");
            out.println("  <label for='tenMonAn'>Tên Món Ăn:</label>");
            out.println("  <input type='text' id='TenMonAn' name='TenMonAn' required><br>");

            out.println("  <label for='dinhDuong'>Dinh Dưỡng:</label>");
            out.println("  <input type='text' id='DinhDuong' name='DinhDuong' required><br>");

            out.println("  <label for='ghiChu'>Ghi Chú:</label>");
            out.println("  <input type='text' id='GhiChu' name='GhiChu'><br>");

            // Add more fields as needed

            out.println("  <input type='submit' value='Submit'>");
            out.println("</form>");

            out.println("</body>");
            out.println("</html>");

            String tenMonAn = request.getParameter("TenMonAn");
            String dinhDuong = request.getParameter("DinhDuong");
            String ghiChuMonAn = request.getParameter("GhiChu");
            // Retrieve other parameters as needed

            if (tenMonAn != null && maMonAn != null) {
                // Call the method to handle the update for MonAn
                // Assuming you have a method named suaMonAn in your 'madao' object
                ma.suaMonAn(request, response, out);
            }

    }
}
