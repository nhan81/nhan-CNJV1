
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import DAO.TDDAO;
import DAO.menu;

@WebServlet("/TD")
public class TD extends HttpServlet {
 private static final long serialVersionUID = 1L;
    public TD() {
        super();
    } 
    TDDAO td = new TDDAO();
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
            res.setContentType("text/html;charset=UTF-8");
            req.setCharacterEncoding("UTF-8");
            res.setCharacterEncoding("UTF-8");
            PrintWriter out = res.getWriter();

            out.println("<html>");
            out.println("<head>");
            out.println("<title>Form Nhập Dữ Liệu</title>");
            out.println("</head>");
            out.println("<body>");
            
            menu menu = new menu();
            menu.htmenu(res, out);
           
            out.println("<h2>Tìm Kiếm Thực Đơn</h2>");
            out.println("<form method='get'>");
            out.println("  <label for='searchMaThucDon'>Mã Món Ăn:</label>");
            out.println("  <input type='text' id='searchMaThucDon' name='searchMaThucDon' required>");
            out.println("  <input type='submit' value='Tìm Kiếm'>");
            out.println("</form>");

            out.println("<h2>Form Nhập Dữ Liệu Thực Đơn</h2>");
            out.println("<form method='get'>");
            out.println("  <label for='MaThucDon'>Mã Thực Đơn:</label>");
            out.println("  <input type='text' id='MaThucDon' name='MaThucDon' required><br>");

            out.println("  <label for='Ngay'>Ngày:</label>");
            out.println("  <input type='text' id='Ngay' name='Ngay' required><br>");

            out.println("  <label for='MaLop'>Mã Lớp:</label>");
            out.println("  <input type='text' id='MaLop' name='MaLop' required><br>");

            out.println("  <label for='MaBuoi'>Mã Khối:</label>");
            out.println("  <input type='text' id='MaBuoi' name='MaBuoi' required><br>");

            out.println("  <label for='MaMonAn'>Mã Món Ăn:</label>");
            out.println("  <input type='text' id='MaMonAn' name='MaMonAn' required><br>");

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
           

            String tkThucDon = req.getParameter("searchMaThucDon");
            if (tkThucDon == null) {
                // Display data for ThucDon
                // Replace the following method with the appropriate method in your ThucDonDAO object
                td.displayData(out);
            } else {
                // Search for ThucDon based on the provided parameter
                // Replace the following method with the appropriate method in your ThucDonDAO object
                td.TKThucDon(req, res, out);
            }
            // Add the delete and insert methods for ThucDon similar to what you did for MonAn
            td.xoaThucDon(req, res, out);
            td.nhapThucDon(req, res);

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

            String maThucDon = request.getParameter("MaThucDon"); // Assuming you pass the MaThucDon as a parameter
            out.println("<h2>Form Nhập Dữ Liệu Thực Đơn</h2>");
            out.println("<form method='post'>");

            out.println("  <input type='hidden' name='MaThucDon' value='" + maThucDon + "'/>");
            out.println("  <label for='ngay'>Ngày:</label>");
            out.println("  <input type='text' id='Ngay' name='Ngay' required><br>");

            out.println("  <label for='maLop'>Mã Lớp:</label>");
            out.println("  <input type='text' id='MaLop' name='MaLop' required><br>");

            out.println("  <label for='maBuoi'>Mã Khối:</label>");
            out.println("  <input type='text' id='MaBuoi' name='MaBuoi' required><br>");

            out.println("  <label for='maMonAn'>Mã Món Ăn:</label>");
            out.println("  <input type='text' id='MaMonAn' name='MaMonAn' required><br>");

            out.println("  <label for='GhiChu'>Ghi Chú:</label>");
            out.println("  <input type='text' id='GhiChu' name='GhiChu'><br>");

            // Add more fields as needed

            out.println("  <input type='submit' value='Submit'>");
            out.println("</form>");

            out.println("</body>");
            out.println("</html>");

            String ngay = request.getParameter("Ngay");
            String maLop = request.getParameter("MaLop");
            String maKhoi = request.getParameter("MaKhoi");
            String maMonAnThucDon = request.getParameter("MaMonAn");
            String ghiChuThucDon = request.getParameter("GhiChuThucDon");
            // Retrieve other parameters as needed

            if (ngay != null && maThucDon != null) {
                // Call the method to handle the update for Thực Đơn
                // Assuming you have a method named suaThucDon in your 'thucDonDAO' object
                td.suaThucDon(request, response, out);
            }

        }
}
