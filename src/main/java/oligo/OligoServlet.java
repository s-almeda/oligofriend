package oligo;

import java.io.IOException;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import java.sql.SQLException;

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
        //String result;
        ArrayList<String> result = new ArrayList<String>();
        if (action!=null) {
            switch (action) {
                case "file":
                    result = oligoService.uploadFile();
                    forwardResult(req,resp,result);
                    break;
                case "text":
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
                        result = oligoService.uploadText(input, min_s, max_s, min_o, max_o);
                    }
                    catch(Exception e){
                        result = null;
                     }

                    forwardResult(req,resp,result);
                    break;
            }
        }
        else{
            result = null;
            forwardResult(req,resp,result);
        }
    }

    private void forwardResult(HttpServletRequest req, HttpServletResponse resp, ArrayList<String> result)
            throws ServletException, IOException{
        String nextJSP;
        int submitted = 0;
        if (result!= null) {
            submitted = 2;
           nextJSP = "/jsp/oligo-page.jsp";
        }
        else
        {   submitted = -1;
            nextJSP = "/jsp/oligo-page.jsp";}

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        req.setAttribute("finalResult", result);
        req.setAttribute("submit", submitted);


        dispatcher.forward(req, resp);
    }

}
