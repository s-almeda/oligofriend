package oligo;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(
        name = "OligoServlet",
        urlPatterns = {"/oligo"}
)

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10,    // 10 MB
        maxFileSize = 1024 * 1024 * 50,        // 50 MB
        maxRequestSize = 1024 * 1024 * 100)    // 100 MB

public class OligoServlet extends HttpServlet {
    private OligoService oligoService = new OligoService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        forwardResult(req, resp, null);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ArrayList<String> result = new ArrayList<>();


        if (ServletFileUpload.isMultipartContent(req)) {
            System.out.println("FILE UPLOAD");
            int min_s = 90;
            int max_s = 100;
            int min_o = 25;
            int max_o = 60;

            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);

            try {

                List<FileItem> items = upload.parseRequest(req);
                for (FileItem item : items) {
                    System.out.println("parsing uploaded file contents: " + item.getString());
                    String fileContents = item.getString();
                    try {
                        result = oligoService.uploadText(fileContents, min_s, max_s, min_o, max_o);
                    } catch (Exception e) {
                        result = null;
                    }
                }

            } catch (Exception e) {
                System.out.println("NO FILE UPLOADED");
                result.add("No File Uploaded");
                forwardResult(req, resp, result);
                return;

            }
            //System.out.println("forwarding file..., result size:" + result.size());
            forwardResult(req, resp, result);
            return;
        } else {
            System.out.println("TEXTINPUT");
            String input = req.getParameter("textInput");
                    /*int min_s = Integer.parseInt(req.getParameter("min_s"));
                    int max_s = Integer.parseInt(req.getParameter("max_s"));
                    int min_o = Integer.parseInt(req.getParameter("min_o"));
                    int max_o = Integer.parseInt(req.getParameter("max_o"));
                    */
            int min_s = 90;
            int max_s = 100;
            int min_o = 25;
            int max_o = 60;

            try {
                System.out.println("submitting text input");
                result = oligoService.uploadText(input, min_s, max_s, min_o, max_o);

            } catch (Exception e) {
                result = null;
            }

            //System.out.println("forwarding text..., result size:" + result.size());
            forwardResult(req, resp, result);
            //return;

        }

       // System.out.println("forwarding file..., result size:" + result.size());
       // forwardResult(req, resp, result);

    }

    private void forwardResult(HttpServletRequest req, HttpServletResponse resp, ArrayList<String> result)
            throws ServletException, IOException {
        //System.out.println("forwardResult, result size:" + result.size());
        String nextJSP;
        int submitted;
        if (result == null) {
            System.out.println("RESULT IS NULL");
            submitted = -1;
            nextJSP = "/jsp/oligo-page.jsp";
        } else {
            submitted = 2;
            nextJSP = "/jsp/oligo-page.jsp";
        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        req.setAttribute("finalResult", result);
        req.setAttribute("submit", submitted);

        dispatcher.forward(req, resp);
    }
}
