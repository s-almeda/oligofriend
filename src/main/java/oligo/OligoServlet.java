package oligo;

import java.io.IOException;
import java.io.File;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import java.sql.SQLException;

import javax.servlet.RequestDispatcher;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import javax.servlet.*;
import org.apache.commons.io.output.*;
import java.util.logging.Level;
import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.*;
import java.util.logging.Logger;
import javax.servlet.http.Part;
import java.io.FileNotFoundException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.*;

@WebServlet(
        name = "OligoServlet",
        urlPatterns = {"/oligo"}
)

@MultipartConfig(fileSizeThreshold=1024*1024*10, 	// 10 MB
        maxFileSize=1024*1024*50,      	// 50 MB
        maxRequestSize=1024*1024*100)   	// 100 MB

public class OligoServlet extends HttpServlet {

    private final static Logger LOGGER =
            Logger.getLogger(OligoServlet.class.getCanonicalName());


    OligoService oligoService = new OligoService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        forwardResult(req,resp,null);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ArrayList<String> result = new ArrayList<String>();

        if (ServletFileUpload.isMultipartContent(req)) {
            int min_s = 90;
            int max_s = 100;
            int min_o = 25;
            int max_o = 60;
            //FILE//
            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);

            try {
                List items = upload.parseRequest(req);
                Iterator iterator = items.iterator();
                while (iterator.hasNext()) {
                    System.out.println("yay");
                    FileItem item = (FileItem) iterator.next();
                    if (!item.isFormField()) {
                        String fileName = item.getName();

                        String root = getServletContext().getRealPath("/");
                        File path = new File(root + "/fileuploads");
                        if (!path.exists()) {
                            boolean status = path.mkdirs();
                        }

                        File uploadedFile = new File(path + "/" + fileName);
                        item.write(uploadedFile);

                        long fileSize = uploadedFile.length();
                        byte[] b = new byte[(int)fileSize];
                        item.getInputStream().read(b);
                        char[] a = new char[(int)fileSize];
                        for (int i =0; i< fileSize; i++){
                            a[i] = (char)b[i];
                            if((char)b[i] == 0)
                                break;
                        }
                        String input = new String(a);
                        System.out.print(input);
                        try {
                            result = oligoService.uploadText(input, min_s, max_s, min_o, max_o);
                        }
                        catch(Exception e){
                            result = null;
                        }
                    }
                }

                System.out.println("BIG YAY");
            }
            catch (Exception e) {

            }
        }


                    ///////// text ///
         else{
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

            }


            forwardResult(req,resp,result);

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

    private String getFileName(final Part part) {
        final String partHeader = part.getHeader("content-disposition");
        //LOGGER.log(Level.INFO, "Part Header = {0}", partHeader);
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(
                        content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

}
