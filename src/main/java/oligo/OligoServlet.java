package oligo;

import java.io.IOException;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

@WebServlet(
        name = "OligoServlet",
        urlPatterns = {"/oligo"}
)
public class OligoServlet extends HttpServlet {

    OligoService oligoService = new OligoService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("fileOrText");
        String result;
        if (action!=null) {
            switch (action) {
                case "file":
                    result = oligoService.uploadFile();
                    forwardResult(req,resp,result);
                    break;
                case "text":
                    result = oligoService.uploadText();
                    forwardResult(req,resp,result);
                    break;
            }
        }
        else{
            result = "null or something?";
            forwardResult(req,resp,result);
        }
    }

    private void forwardResult(HttpServletRequest req, HttpServletResponse resp, String result)
            throws ServletException, IOException{
        String nextJSP = "/jsp/oligo-page.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        req.setAttribute("finalResult", result);
        dispatcher.forward(req, resp);
    }

}
